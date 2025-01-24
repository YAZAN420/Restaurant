package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

import Controller.RegisterController;

import java.awt.*;

public class RegisterView extends JPanel {
    boolean activepass, activeConfirmPass;

    @SuppressWarnings("deprecation")
    RegisterView() {
        activepass = false;
        activeConfirmPass = false;
        setLayout(null);
        setBounds(0, 0, MainFrameView.frame.getWidth(), MainFrameView.frame.getHeight());
        JLabel registerLabel = new JLabel("REGISTER");
        registerLabel.setForeground(Color.decode("#fcf3e2"));
        registerLabel.setFont(new Font("Lobster", Font.BOLD, 40));
        registerLabel.setBounds(1070, 250, 300, 50);
        add(registerLabel);
        JTextField regUserText = new JTextField();
        regUserText.setBounds(1035, 320, 280, 40);
        regUserText.setFont(new Font("Open Sans", Font.PLAIN, 16));
        regUserText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        regUserText.setBorder(new EmptyBorder(5, 20, 5, 10));
        regUserText.setBackground(Color.decode("#fcf3e2"));
        addPlaceholder(regUserText, "👤   Username");
        add(regUserText);
        JTextField regEmailText = new JTextField();
        regEmailText.setBounds(1035, 380, 280, 40);
        regEmailText.setFont(new Font("Open Sans", Font.PLAIN, 16));
        regEmailText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        regEmailText.setBorder(new EmptyBorder(5, 20, 5, 10));
        regEmailText.setBackground(Color.decode("#fcf3e2"));
        addPlaceholder(regEmailText, "📧   Email");
        add(regEmailText);
        JPasswordField regPassText = new JPasswordField();
        regPassText.setBounds(1035, 440, 230, 40);
        regPassText.setFont(new Font("Open Sans", Font.PLAIN, 16));
        regPassText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        regPassText.setBorder(new EmptyBorder(5, 20, 5, 10));
        regPassText.setBackground(Color.decode("#fcf3e2"));
        addPlaceholder(regPassText, "🔒   Password");
        add(regPassText);
        JPasswordField regConfirmPassText = new JPasswordField();
        regConfirmPassText.setBounds(1035, 500, 230, 40);
        regConfirmPassText.setFont(new Font("Open Sans", Font.PLAIN, 16));
        regConfirmPassText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        regConfirmPassText.setBorder(new EmptyBorder(5, 20, 5, 10));
        regConfirmPassText.setBackground(Color.decode("#fcf3e2"));
        addPlaceholder(regConfirmPassText, "🔒   Confirm Password");
        add(regConfirmPassText);
        JButton passEye = createEyePhoto();
        passEye.setBounds(1265, 440, 50, 40);
        passEye.addActionListener(e -> {
            if (!activepass) {
                ImageIcon originalIcon1 = new ImageIcon("photos/hide.png");
                Image scaledImage1 = originalIcon1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
                regPassText.setEchoChar((char) 0);
                passEye.setIcon(scaledIcon1);
            } else {
                ImageIcon originalIcon = new ImageIcon("photos/view.png");
                Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                regPassText.setEchoChar('*');
                passEye.setIcon(scaledIcon);
            }
            activepass = !activepass;
        });
        add(passEye);
        JButton passConfrimEye = createEyePhoto();
        passConfrimEye.setBounds(1265, 500, 50, 40);
        passConfrimEye.addActionListener(e -> {
            if (!activeConfirmPass) {
                ImageIcon originalIcon1 = new ImageIcon("photos/hide.png");
                Image scaledImage1 = originalIcon1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
                regConfirmPassText.setEchoChar((char) 0);
                passConfrimEye.setIcon(scaledIcon1);
            } else {
                ImageIcon originalIcon = new ImageIcon("photos/view.png");
                Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                regConfirmPassText.setEchoChar('*');
                passConfrimEye.setIcon(scaledIcon);
            }
            activeConfirmPass = !activeConfirmPass;
        });
        add(passConfrimEye);
        JButton registerButton = createRoundedButton("Register");
        registerButton.setBounds(1190, 560, 150, 40);
        registerButton.addActionListener(k -> {
            if (regUserText.getText().trim().isEmpty() || regEmailText.getText().trim().isEmpty()
                    || regPassText.getPassword().length == 0 || regConfirmPassText.getPassword().length == 0) {
                MainFrameView.notificationView.showNotification("Please Fill All The Fields.", true);
                return;
            }
            try {
                new RegisterController(regUserText.getText(), regEmailText.getText(), regPassText.getText(),
                        regConfirmPassText.getText());
                setVisible(false);
                MainFrameView.mainPanel = new MainView();
                MainFrameView.frame.add(MainFrameView.mainPanel);
                ReportsAndOrderView.orderStatusView.updateView();
                UserOrderView.orderStatusView.updateView2();
                MainFrameView.notificationView.showNotification("Registerd Successfully!", false);
            } catch (IllegalArgumentException err) {
                MainFrameView.notificationView.showNotification(err.getMessage(), true);
            } finally {
                regUserText.setText("");
                regEmailText.setText("");
                regPassText.setText("");
                regConfirmPassText.setText("");
                addPlaceholder(regUserText, "👤   Username");
                addPlaceholder(regEmailText, "📧   Email");
                addPlaceholder(regPassText, "🔒   Password");
                addPlaceholder(regConfirmPassText, "🔒   Confirm Password");
            }
        });
        add(registerButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon icon = new ImageIcon("photos/login1.png");
        g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public JButton createEyePhoto() {
        ImageIcon originalIcon = new ImageIcon("photos/view.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JButton eye = new JButton(scaledIcon);
        eye.setVisible(true);
        eye.setSize(new Dimension(30, 30));
        eye.setBackground(Color.white);
        eye.setBackground(Color.decode("#fcf3e2"));
        eye.setFocusable(false);
        eye.setBorder(null);
        return eye;
    }

    public static void addPlaceholder(JTextComponent textField, String placeholderText) {
        textField.setLayout(new BorderLayout());
        JLabel placeholderLabel = new JLabel(placeholderText);
        placeholderLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
        placeholderLabel.setForeground(Color.decode("#3b3938"));
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
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(150, 40));
        return button;
    }
}
