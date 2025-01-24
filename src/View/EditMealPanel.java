import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;

public class EditMealPanel {
     private JPanel mainPanel;
     public JLabel photoLabel;
     public Color logoButtonsColor =Palette.AdditionalColor;
     BufferedImage image;
     MenuManager mg = new MenuManager();

     public EditMealPanel(Meal meal) {
        try {
            image=ImageIO.read(new File(meal.getImagePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setSize(new Dimension(680, 420));
        photoLabel = createPhotoLabel(meal.getImagePath());
        JPanel topDetailsPanel = createEditDetailsPanel(meal);
        mainPanel.add(topDetailsPanel, BorderLayout.CENTER);
        mainPanel.add(photoLabel, BorderLayout.EAST);
    } 

    private JPanel createEditDetailsPanel(Meal meal) {
        JPanel panel = new JPanel();
        panel.setBackground(Palette.BackGround);
        panel.setLayout(null);
        panel.setOpaque(true);
        JLabel logoLabel = new JLabel("Edit Meal");
        logoLabel.setFont(new Font("Lobster", Font.BOLD, 32));
        logoLabel.setForeground(logoButtonsColor);
        logoLabel.setBounds(420, 0, 200, 200);
        JLabel nameLabel = createLabel("Name");
        JLabel categoryLabel = createLabel("Category");
        JLabel ingredientsLabel = createLabel("Ingredients");
        JLabel priceLabel = createLabel("Price");
        JLabel timeLabel = createLabel("Time Needed");
        JTextField nameText = createTextField();
        JTextField categoryText = createTextField();
        JTextField priceText = createTextField();
        JTextField timeText = createTextField();
        int x = 100;
        JTextArea ingredientsText = createTextArea(3, 0);
        nameLabel.setBounds(x, 140, 150, 50);
        nameText.setBounds(x + 20, 190, 650, 50);
        nameText.setText(meal.getMealName());
        categoryLabel.setBounds(x, 240, 160, 50);
        categoryText.setBounds(x + 20, 290, 650, 50);
        categoryText.setText(meal.getCategory());
        ingredientsLabel.setBounds(x, 340, 190, 50);
        ingredientsText.setBounds(x + 20, 390, 650, 100);
        ingredientsText.setText(meal.getIngredients());
        priceLabel.setBounds(x, 490, 250, 50);
        priceText.setBounds(x + 20, 540, 300, 50);
        priceText.setText(meal.getMealPrice() + "");
        timeLabel.setBounds(x + 320, 490, 250, 50);
        timeText.setBounds(x + 340, 540, 300, 50);
        timeText.setText(meal.getMinutesNeeded() + "");
        JButton backButton = createRoundedButton("Back");
        backButton.setBounds(210, 650, 110, 50);
        JButton editPhoto = createStyledUploadButton();
        editPhoto.setBounds(x+230,650,180,50);
        JButton edit = createRoundedButton("Edit Meal");
        edit.setBounds(520, 650, 160, 50);
        backButton.addActionListener(e -> {
            MainPanel.mainContent.removeAll();
            MealDetailsController con = new MealDetailsController(meal);
            MainPanel.mainContent.add(con.getView().getMainPanel(), BorderLayout.CENTER);
            MainPanel.mainContent.revalidate();
            MainPanel.mainContent.repaint();
        });
        edit.addActionListener(e -> {
            if (nameText.getText().length() != 0 && ingredientsText.getText().length() != 0
                    && timeText.getText().length() != 0 && priceText.getText().length() != 0
                    && categoryText.getText().length() != 0) {
                try {
                    boolean update = false;
                    if(!MenuManager.getMenu().containsKey(categoryText.getText()))  update= true;
                    mg.editFromMenu(meal.getMealName(), meal.getCategory(), categoryText.getText(), nameText.getText(),
                            ingredientsText.getText(), Double.parseDouble(priceText.getText()),
                            Integer.parseInt(timeText.getText()), meal.getImagePath());
                            ImageIO.write(image, "jpg", new File(meal.getImagePath()));
                           if(update) {SideCategoryPanel.updateView(categoryText.getText());}
                            else SideCategoryPanel.categoryList.setSelectedIndex(SideCategoryPanel.listModel.indexOf(meal.getCategory()));
                } catch (Exception e1) {
                    LoginRegisterView.notificationView.showNotification("Invalid Input : " + e1.getMessage(), true);
                }
                MainPanel.mainContent.removeAll();
                MainPanel.mainContent.add(MainPanel.menuContentPanel1, BorderLayout.CENTER);
                MainPanel.menuContentPanel1.setSelectedCategory(meal.getCategory());
                MainPanel.sideCategoryPanel.getSideMenu().setVisible(true);
                MainPanel.mainContent.revalidate();
                MainPanel.mainContent.repaint();
            }
        });
        panel.add(editPhoto);
        panel.add(edit);
        panel.add(logoLabel);
        panel.add(nameLabel);
        panel.add(categoryLabel);
        panel.add(ingredientsLabel);
        panel.add(priceLabel);
        panel.add(timeLabel);
        panel.add(nameText);
        panel.add(categoryText);
        panel.add(ingredientsText);
        panel.add(priceText);
        panel.add(timeText);
        panel.add(backButton);
        return panel;
    }

    private JButton createStyledUploadButton() {
        JButton uploadButton = new JButton("Choose new photo") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(logoButtonsColor); 
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50); 
                g2d.setColor(Color.WHITE); 
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - fm.getDescent();
                g2d.drawString(getText(), x, y);
                g2d.dispose();
            }
            @Override
            protected void paintBorder(Graphics g) {}
        };
        uploadButton.setFocusPainted(false);
        uploadButton.setBorderPainted(false);
        uploadButton.setContentAreaFilled(false);
        uploadButton.setFont(new Font("Arial", Font.BOLD, 16)); 
        uploadButton.setPreferredSize(new Dimension(150, 40)); 
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "JPG", "jpg"));
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    image = ImageIO.read(selectedFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        return uploadButton;
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(logoButtonsColor); 
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50); 
                g2d.setColor(Color.WHITE); 
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - fm.getDescent();
                g2d.drawString(getText(), x, y);
                g2d.dispose();
            }
            @Override
            protected void paintBorder(Graphics g) {}
        };
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFont(new Font("Arial", Font.BOLD, 16)); 
        button.setPreferredSize(new Dimension(150, 40)); 
        return button;
    }
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setForeground(Color.decode("#77777d"));
        textField.setFont(new Font("Lobster", Font.PLAIN, 22));
        textField.setBorder(null);
        textField.setBorder(new EmptyBorder(10, 20, 10, 10));
        textField.setBackground(Color.decode("#f2f2f2"));
        return textField;
    }

    private JLabel createLabel(String title) {
        JLabel label = new JLabel("<html>" + title + ":<br> </html>");
        label.setForeground(logoButtonsColor);
        label.setFont(new Font("Lobster", Font.BOLD, 22));
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        label.setMaximumSize(new Dimension(600, 200));
        return label;
    }
    private JTextArea createTextArea(int rows,int cols) {
        JTextArea textArea = new JTextArea(rows,cols);
        textArea.setForeground(Color.decode("#77777d"));
        textArea.setFont(new Font("Lobster", Font.PLAIN, 22));
        textArea.setBorder(null);
        textArea.setBorder(new EmptyBorder(10, 20, 10, 10));
        textArea.setBackground(Color.decode("#f2f2f2"));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }
    private JLabel createPhotoLabel(String imagePath) {
        JLabel photoLabel = new JLabel();
        photoLabel.setBackground(logoButtonsColor);
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setVerticalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(560, 790, Image.SCALE_SMOOTH); 
            photoLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            photoLabel.setText("Image not found");
        }
        photoLabel.setBorder(new LineBorder(logoButtonsColor, 25)); 
        photoLabel.setOpaque(true);
        return photoLabel;
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
