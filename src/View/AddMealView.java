package View;

import Controller.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AddMealView {
    private JPanel mainPanel;
    public JLabel photoLabel;
    public String catogry;
    public BufferedImage image;
    MenuManagerController mg = new MenuManagerController();

    public AddMealView(String catogry) {
        this.catogry = catogry;
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setSize(new Dimension(680, 420));
        photoLabel = createPhotoLabel("photos/add-item-grey.png");
        JPanel topDetailsPanel = createAddDetailsPanel();
        mainPanel.add(topDetailsPanel, BorderLayout.CENTER);
        mainPanel.add(photoLabel, BorderLayout.EAST);
    }

    private JPanel createAddDetailsPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PaletteView.BackGround);
        panel.setLayout(null);
        panel.setOpaque(true);
        JLabel logoLabel = new JLabel("Add Meal");
        logoLabel.setFont(new Font(PaletteView.font, Font.BOLD, 40));
        logoLabel.setForeground(PaletteView.AdditionalColor);
        logoLabel.setBounds(360, 20, 200, 200);
        JTextArea IngredientsTextField = createTextArea(3, 0);
        JTextField PriceTextField = createTextField();
        JTextField nameTextField = createTextField();
        JTextField TimeTextField = createTextField();
        JButton backButton = createRoundedButton("Back");
        backButton.setBounds(210, 550, 110, 50);
        JButton upload = createStyledUploadButton();
        upload.setBounds(330, 550, 180, 50);
        JButton add = createRoundedButton("Add Meal");
        backButton.addActionListener(e -> {
            MainView.mainContent.removeAll();
            MainView.mainContent.add(MainView.menuContentPanel1, BorderLayout.CENTER);
            MainView.menuContentPanel1.setSelectedCategory(catogry);
            MainView.sideCategoryPanel.getSideMenu().setVisible(true);
            MainView.mainContent.revalidate();
            MainView.mainContent.repaint();
        });
        add.setBounds(520, 550, 160, 50);
        add.addActionListener(e -> {
            if (nameTextField.getText().length() != 0 && IngredientsTextField.getText().length() != 0
                    && TimeTextField.getText().length() != 0 && PriceTextField.getText().length() != 0) {
                try {
                    mg.addToMenu(catogry, nameTextField.getText(), IngredientsTextField.getText(),
                            Double.parseDouble(PriceTextField.getText()),
                            Integer.parseInt(TimeTextField.getText()));
                    if (image == null) {
                        File defaultImage = new File("photos\\deaultMeal.jpg");
                        File SaveToPhotos = new File("photos\\" + nameTextField.getText() + ".jpg");
                        try {
                            Files.copy(defaultImage.toPath(), SaveToPhotos.toPath(),
                                    StandardCopyOption.REPLACE_EXISTING);
                        } catch (Exception ek) {
                            ek.printStackTrace();
                        }
                    } else {
                        ImageIO.write(image, "jpg", new File("photos/" + nameTextField.getText() + ".jpg"));
                    }

                } catch (NumberFormatException e1) {
                    MainFrameView.notificationView.showNotification("Invalid Input : " + e1.getMessage(), true);
                } catch (IllegalArgumentException e2) {
                    MainFrameView.notificationView.showNotification(e2.getMessage(), true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                nameTextField.setText("");
                IngredientsTextField.setText("");
                TimeTextField.setText("");
                PriceTextField.setText("");
                MainView.mainContent.removeAll();
                MainView.mainContent.add(MainView.menuContentPanel1, BorderLayout.CENTER);
                MainView.menuContentPanel1.setSelectedCategory(catogry);
                MainView.sideCategoryPanel.getSideMenu().setVisible(true);
                MainView.mainContent.revalidate();
                MainView.mainContent.repaint();
            } else {
                MainFrameView.notificationView.showNotification("All Fields Are Required!", true);
            }
        });
        addPlaceholder(nameTextField, "Meal Name");
        addPlaceholder(IngredientsTextField, "Meal Ingredients");
        addPlaceholder(PriceTextField, "Meal Price");
        addPlaceholder(TimeTextField, "Meal Time");
        int x = 100;
        nameTextField.setBounds(x + 20, 190, 650, 50);
        IngredientsTextField.setBounds(x + 20, 290, 650, 100);
        PriceTextField.setBounds(x + 20, 440, 300, 50);
        TimeTextField.setBounds(x + 340, 440, 300, 50);
        panel.add(IngredientsTextField);
        panel.add(PriceTextField);
        panel.add(nameTextField);
        panel.add(TimeTextField);
        panel.add(upload);
        panel.add(add);
        panel.add(backButton);
        panel.add(logoLabel);
        return panel;
    }

    private JTextArea createTextArea(int rows, int cols) {
        JTextArea textArea = new JTextArea(rows, cols);
        textArea.setForeground(Color.decode("#77777d"));
        textArea.setFont(new Font(PaletteView.font, Font.PLAIN, 22));
        textArea.setBorder(null);
        textArea.setBorder(new EmptyBorder(10, 20, 10, 10));
        textArea.setBackground(Color.decode("#f2f2f2"));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(PaletteView.AdditionalColor);
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
            protected void paintBorder(Graphics g) {
            }
        };
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFont(new Font(PaletteView.font, Font.BOLD, 16));
        button.setPreferredSize(new Dimension(150, 40));
        return button;
    }

    private JButton createStyledUploadButton() {
        JButton uploadButton = new JButton("Choose new photo") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(PaletteView.AdditionalColor);
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
            protected void paintBorder(Graphics g) {
            }
        };
        uploadButton.setFocusPainted(false);
        uploadButton.setBorderPainted(false);
        uploadButton.setContentAreaFilled(false);
        uploadButton.setFont(new Font(PaletteView.font, Font.BOLD, 16));
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
                    ImageIcon icon = new ImageIcon(image);
                    ;
                    Image scaledImage = icon.getImage().getScaledInstance(560, 790, Image.SCALE_SMOOTH);
                    photoLabel.setIcon(new ImageIcon(scaledImage));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        return uploadButton;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setForeground(Color.decode("#77777d"));
        textField.setFont(new Font(PaletteView.font, Font.PLAIN, 22));
        textField.setBorder(null);
        textField.setBorder(new EmptyBorder(10, 20, 10, 10));
        textField.setBackground(Color.decode("#f2f2f2"));
        return textField;
    }

    private JLabel createPhotoLabel(String imagePath) {
        JLabel photoLabel = new JLabel();
        photoLabel.setBackground(PaletteView.AdditionalColor);
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setVerticalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(560, 850, Image.SCALE_SMOOTH);
            photoLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            photoLabel.setText("Image not found");
        }
        photoLabel.setBorder(new LineBorder(PaletteView.AdditionalColor, 25));
        photoLabel.setOpaque(true);
        return photoLabel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void addPlaceholder(JTextComponent textField, String placeholderText) {
        textField.setLayout(new BorderLayout());
        JLabel placeholderLabel = new JLabel(placeholderText);
        placeholderLabel.setFont(new Font(PaletteView.font, Font.BOLD, 18));
        placeholderLabel.setForeground(Color.decode("#77777d"));
        placeholderLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));
        textField.add(placeholderLabel);
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                placeholderLabel.setVisible(false);
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().trim().isEmpty()) {
                    placeholderLabel.setVisible(true);
                }
            }
        });
    }
}