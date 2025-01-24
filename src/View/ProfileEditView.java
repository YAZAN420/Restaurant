package View;

import Controller.OrderStorageController;
import Controller.ReportServiceController;
import Model.UserModel;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ProfileEditView extends JPanel {
    String oldName = UserModel.getCurrentUser().getName();
    BufferedImage image;
    Color color = PaletteView.AdditionalColor;

    public ProfileEditView() {
        try {
            image = ImageIO.read(new File(UserModel.getCurrentUser().getPhotoPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setSize(new Dimension(1920, 1080));
        JPanel accountPanel = accountPanel();
        add(accountPanel, BorderLayout.EAST);
        JPanel passwordPanel = passwordPanel();
        add(passwordPanel, BorderLayout.WEST);
    }

    public JPanel accountPanel() {
        JPanel accountPanel = new JPanel();
        accountPanel.setPreferredSize(new Dimension(767, 1080));
        accountPanel.setBackground(new Color(253, 245, 230));
        accountPanel.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 5, Color.LIGHT_GRAY));
        accountPanel.setLayout(null);
        JLabel accountLogo = createLogo("Your account settings");
        accountLogo.setBounds(150, 100, 400, 50);
        accountPanel.add(accountLogo);
        JLabel name = createLabel("Name");
        name.setBounds(150, 170, 100, 50);
        JTextField nameText = createText(UserModel.getCurrentUser().getName());
        nameText.setBounds(150, 220, 400, 50);
        JLabel email = createLabel("Email");
        email.setBounds(150, 270, 100, 50);
        JTextField emailText = createText(UserModel.getCurrentUser().getEmail());
        emailText.setBounds(150, 320, 400, 50);
        JLabel photo = createRoundedPhotoLabel(UserModel.getCurrentUser().getPhotoPath());
        photo.setBounds(150, 420, 120, 120);
        JButton uploadButton = createStyledUploadButton();
        uploadButton.setBounds(300, 470, 200, 50);
        JButton saveButton = createRoundedButton("SAVE SETTINGS");
        saveButton.setBounds(450, 600, 180, 50);
        saveButton.addActionListener(e -> {
            if (nameText.getText().length() != 0 && emailText.getText().length() != 0) {
                try {
                    String oldName = UserModel.getCurrentUser().getName();
                    UserModel.editUser(UserModel.getCurrentUser().getName(), nameText.getText(), emailText.getText(),
                            UserModel.getCurrentUser().getPhotoPath());
                    ImageIO.write(image, "jpg",
                            new File(UserModel.getCurrentUser().getPhotoPath()));
                    MainView.mainContent.removeAll();
                    MainView.mainContent.add(new ProfileEditView());
                    MainView.mainContent.repaint();
                    MainView.navBar.reRender(oldName);
                    MainView.mainContent.revalidate();
                    OrderStorageController.updateNameInOrders(oldName, UserModel.getCurrentUser().getName());
                    ReportServiceController.updateCustomerOrdersNumber(oldName, UserModel.getCurrentUser().getName());
                    oldName = UserModel.getCurrentUser().getName();
                    MainFrameView.notificationView.showNotification("Data updated successfully!", false);
                } catch (Exception el1) {
                    MainFrameView.notificationView.showNotification(el1.getMessage(), true);
                }
            }
        });
        accountPanel.add(saveButton);
        accountPanel.add(uploadButton);
        accountPanel.add(photo);
        accountPanel.add(nameText);
        accountPanel.add(name);
        accountPanel.add(emailText);
        accountPanel.add(email);
        return accountPanel;
    }

    @SuppressWarnings("deprecation")
    public JPanel passwordPanel() {
        JPanel passwordPanel = new JPanel();
        passwordPanel.setPreferredSize(new Dimension(767, 1080));
        passwordPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 0, Color.LIGHT_GRAY));
        passwordPanel.setLayout(null);
        passwordPanel.setBackground(Color.WHITE);
        JLabel passwordLogo = createLogo("Password change");
        passwordLogo.setBounds(150, 100, 350, 50);
        passwordPanel.add(passwordLogo);
        JLabel CurrentPassword = createLabel("Current password");
        CurrentPassword.setBounds(150, 170, 230, 50);
        JPasswordField CurrentPasswordText = createPasswordText("");
        CurrentPasswordText.setBounds(150, 220, 400, 50);
        JLabel NewPassword = createLabel("New password");
        NewPassword.setBounds(150, 270, 200, 50);
        JPasswordField NewPasswordText = createPasswordText("");
        NewPasswordText.setBounds(150, 320, 400, 50);
        JLabel ConfirmPassword = createLabel("Confirm password");
        ConfirmPassword.setBounds(150, 370, 230, 50);
        JPasswordField ConfirmPasswordText = createPasswordText("");
        ConfirmPasswordText.setBounds(150, 420, 400, 50);
        addPlaceholder(CurrentPasswordText, "********");
        addPlaceholder(ConfirmPasswordText, "********");
        addPlaceholder(NewPasswordText, "********");
        JButton savePassword = createRoundedButton("SAVE PASSWORD");
        savePassword.setBounds(450, 600, 180, 50);
        savePassword.addActionListener(e -> {
            if (CurrentPasswordText.getText().length() != 0 && ConfirmPasswordText.getText().length() != 0
                    && NewPasswordText.getText().length() != 0) {

                try {
                    UserModel.editUserPassword(oldName, CurrentPasswordText.getText(), NewPasswordText.getText(),
                            ConfirmPasswordText.getText());
                    MainFrameView.notificationView.showNotification("Password updated successfully!", false);
                } catch (Exception ek) {
                    MainFrameView.notificationView.showNotification(ek.getMessage(), true);
                } finally {
                    CurrentPasswordText.setText("");
                    NewPasswordText.setText("");
                    ConfirmPasswordText.setText("");
                    addPlaceholder(CurrentPasswordText, "********");
                    addPlaceholder(ConfirmPasswordText, "********");
                    addPlaceholder(NewPasswordText, "********");
                }
            } else {
                MainFrameView.notificationView
                        .showNotification("CurrentPassword ConfirmPassword NewPassword Are Required", true);
            }
        });
        passwordPanel.add(savePassword);
        passwordPanel.add(CurrentPassword);
        passwordPanel.add(NewPassword);
        passwordPanel.add(ConfirmPassword);
        passwordPanel.add(CurrentPasswordText);
        passwordPanel.add(NewPasswordText);
        passwordPanel.add(ConfirmPasswordText);
        return passwordPanel;
    }

    public static JLabel createRoundedPhotoLabel(String imagePath) {
        JLabel photoLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getIcon() != null) {
                    ImageIcon icon = (ImageIcon) getIcon();
                    Image image = icon.getImage();
                    BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(),
                            BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = bufferedImage.createGraphics();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    Shape circle = new java.awt.geom.Ellipse2D.Float(0, 0, getWidth(), getHeight());
                    g2d.setClip(circle);
                    g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                    g2d.dispose();
                    g.drawImage(bufferedImage, 0, 0, null);
                }
            }
        };
        photoLabel.setBackground(new Color(238, 121, 50));
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setVerticalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            photoLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            photoLabel.setText("Image not found");
        }
        photoLabel.setPreferredSize(new Dimension(80, 80));
        photoLabel.setOpaque(false);
        return photoLabel;
    }

    private JButton createStyledUploadButton() {
        JButton uploadButton = new JButton("Choose new photo");
        uploadButton.setBorder(null);
        uploadButton.setFocusPainted(false);
        uploadButton.setContentAreaFilled(false);
        uploadButton.setFont(new Font(PaletteView.font, Font.PLAIN, 24));
        uploadButton.setForeground(color);
        uploadButton.setHorizontalAlignment(SwingConstants.LEFT);
        uploadButton.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, color));
        uploadButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                uploadButton.setOpaque(true);
                uploadButton.setBackground(color);
                uploadButton.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                uploadButton.setOpaque(false);
                uploadButton.setBackground(null);
                uploadButton.setForeground(color);
            }
        });
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
                g2d.setColor(color);
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

    public JLabel createLogo(String text) {
        JLabel logo = new JLabel(text);
        logo.setForeground(color);
        logo.setFont(new Font(PaletteView.font, Font.BOLD, 32));
        return logo;
    }

    public JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.decode("#77777d"));
        label.setFont(new Font(PaletteView.font, Font.BOLD, 22));
        return label;
    }

    public JTextField createText(String text) {
        JTextField textfield = new JTextField(text);
        textfield.setForeground(Color.decode("#77777d"));
        textfield.setFont(new Font(PaletteView.font, Font.PLAIN, 22));
        textfield.setBorder(null);
        textfield.setBorder(new EmptyBorder(10, 20, 10, 10));
        textfield.setBackground(Color.decode("#f2f2f2"));
        return textfield;
    }

    public JPasswordField createPasswordText(String text) {
        JPasswordField textfield = new JPasswordField(text);
        textfield.setForeground(color);
        textfield.setFont(new Font(PaletteView.font, Font.PLAIN, 22));
        textfield.setBorder(null);
        textfield.setBorder(new EmptyBorder(10, 20, 10, 10));
        textfield.setBackground(Color.decode("#f2f2f2"));
        return textfield;
    }

    public static void addPlaceholder(JTextComponent textField, String placeholderText) {
        textField.setLayout(new BorderLayout());
        JLabel placeholderLabel = new JLabel(placeholderText);
        placeholderLabel.setFont(new Font(PaletteView.font, Font.BOLD, 18));
        placeholderLabel.setForeground(Color.decode("#c3c3c3"));
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
