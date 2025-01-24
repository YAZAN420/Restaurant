import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class EditMalePanel {
     private JPanel mainPanel;
     public JLabel photoLabel;
     MenuManager mg = new MenuManager();
    public EditMalePanel(Meal meal) {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setSize(new Dimension(680, 420));
        photoLabel = createPhotoLabel(meal.getImagePath());
        JPanel topDetailsPanel = createEditDetailsPanel(meal);
        mainPanel.add(topDetailsPanel, BorderLayout.CENTER);
        mainPanel.add(photoLabel, BorderLayout.EAST);
    } 

    private JPanel createEditDetailsPanel(Meal meal) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(253, 245, 230));
        panel.setLayout(null);
        panel.setOpaque(true);
        JLabel logoLabel = new JLabel("Edit Meal");
        logoLabel.setFont(new Font("Lobster", Font.BOLD, 40));
        logoLabel.setForeground(new Color(238, 121, 50));
        logoLabel.setBounds(360, -40, 200, 200);
        Font labelFont = new Font("Serif", Font.BOLD, 32);
        JLabel nameLabel = createLabel("Name", labelFont);
        JLabel categoryLabel = createLabel("Category", labelFont);
        JLabel ingredientsLabel = createLabel("Ingredients", labelFont);
        JLabel priceLabel = createLabel("Price", labelFont);
        JLabel timeLabel = createLabel("Time Needed", labelFont);
        JTextField nameText = createTextField(labelFont);
        JTextField categoryText = createTextField(labelFont);
        JTextField priceText = createTextField(labelFont);
        JTextField timeText = createTextField(labelFont);
        JTextArea ingredientsText = createTextArea(labelFont, 3, 0);
        nameLabel.setBounds(20, 140, 150, 50);
        nameLabel.setForeground(new Color(238, 121, 50));
        nameText.setBounds(245, 150, 450, 50);
        nameText.setText(meal.getMealName());
        categoryLabel.setBounds(20, 220, 160, 50);
        categoryLabel.setForeground(new Color(238, 121, 50));
        categoryText.setBounds(245, 230, 420, 50);
        categoryText.setText(meal.getCategory());
        ingredientsLabel.setBounds(20, 290, 190, 50);
        ingredientsLabel.setForeground(new Color(238, 121, 50));
        ingredientsText.setBounds(245, 300, 650, 100);
        ingredientsText.setText(meal.getIngredients());
        priceLabel.setBounds(20, 420, 150, 50);
        priceLabel.setForeground(new Color(238, 121, 50));
        priceText.setBounds(245, 430, 150, 50);
        priceText.setText(meal.getMealPrice()+"");
        timeLabel.setBounds(20, 500, 250, 50);
        timeLabel.setForeground(new Color(238, 121, 50));
        timeText.setBounds(245, 510, 180, 50);
        timeText.setText(meal.getMinutesNeeded()+"");
        JButton edit = new JButton("Edit Meal");
        edit.setBorder(null);
        edit.setBounds(350, 650, 160, 50);
        edit.setBackground(new Color(238, 121, 50));
        edit.setFont(new Font("Serif", Font.BOLD, 20));
        edit.setForeground(Color.decode("#fcf3e2"));
        edit.setFocusPainted(false);
        edit.setBorderPainted(false);
        edit.addActionListener(e -> {
            if (nameText.getText().length() != 0 && ingredientsText.getText().length() != 0
                    && timeText.getText().length() != 0 && priceText.getText().length() != 0
                    && categoryText.getText().length() != 0) {
                mg.editFromMenu(categoryText.getText(), nameText.getText(), ingredientsText.getText(), Double.parseDouble(priceText.getText()),Integer.parseInt(timeText.getText()));
                        MainPanel.mainContent.removeAll();
                        MainPanel.mainContent.add(MainPanel.menuContentPanel1, BorderLayout.CENTER);
                        MainPanel.menuContentPanel1.setSelectedCategory(meal.getCategory());
                        MainPanel.sideCategoryPanel.getSideMenu().setVisible(true);
                        MainPanel.mainContent.revalidate();
                        MainPanel.mainContent.repaint();
            }
        });
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
        return panel;
    }
    private JTextField createTextField(Font font) {
        JTextField textField = new JTextField();
        textField.setBorder(null);
        textField.setBorder(new EmptyBorder(5,15,5,10));
        textField.setFont(font);
        textField.setMaximumSize(new Dimension(200, 100));
        return textField;
    }

    private JLabel createLabel(String title, Font font) {
        JLabel label = new JLabel("<html>" + title + ":<br> </html>");
        label.setFont(font);
        label.setForeground(Color.BLACK);
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        label.setMaximumSize(new Dimension(600, 200));
        return label;
    }
    private JTextArea createTextArea(Font font,int rows,int cols) {
        JTextArea textArea = new JTextArea(rows,cols);
        textArea.setFont(font);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMaximumSize(new Dimension(200, 100));
        return textArea;
    }
    private JLabel createPhotoLabel(String imagePath) {
        JLabel photoLabel = new JLabel();
        photoLabel.setBackground(new Color(238, 121, 50));
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setVerticalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(560, 790, Image.SCALE_SMOOTH); 
            photoLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            photoLabel.setText("Image not found");
        }
        photoLabel.setBorder(new LineBorder(new Color(238, 121, 50), 25)); 
        photoLabel.setOpaque(true);
        return photoLabel;
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
