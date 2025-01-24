package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

import Controller.LoginController;

@SuppressWarnings("deprecation")
public class LoginView extends JPanel {
    boolean active;

    LoginView() {
        active = false;
        setLayout(null);
        setBounds(0, 0, MainFrameView.frame.getWidth(), MainFrameView.frame.getHeight());
        JLabel logoLabel = new JLabel("LOGIN");
        logoLabel.setForeground(Color.decode("#fcf3e2"));
        logoLabel.setFont(new Font("Lobster", Font.BOLD, 40));
        logoLabel.setBounds(1100, 220, 200, 200);
        add(logoLabel);
        JTextField userText = new JTextField();
        addPlaceholder(userText, "📧   Email");
        userText.setBackground(Color.decode("#fcf3e2"));
        userText.setBounds(1050, 370, 250, 40);
        userText.setFocusable(false);
        userText.setFont(new Font("Open Sans", Font.PLAIN, 16));
        userText.setBorder(BorderFactory.createLineBorder(Color.decode("#fcf3e2"), 2));
        userText.setBorder(new EmptyBorder(5, 20, 5, 10));
        userText.setText("");
        add(userText);
        JPasswordField passText = new JPasswordField();
        addPlaceholder(passText, "🔒   Password");
        passText.setBounds(1050, 430, 200, 40);
        passText.setFocusable(false);
        passText.setFont(new Font("Open Sans", Font.PLAIN, 16));
        passText.setBorder(BorderFactory.createLineBorder(Color.decode("#fcf3e2"), 2));
        passText.setBorder(new EmptyBorder(5, 20, 5, 10));
        passText.setBackground(Color.decode("#fcf3e2"));
        passText.setText("");
        add(passText);
        JButton passEye = createEyePhoto();
        passEye.setBounds(1250, 430, 50, 40);
        passEye.addActionListener(e -> {
            if (!active) {
                ImageIcon originalIcon1 = new ImageIcon("photos/hide.png");
                Image scaledImage1 = originalIcon1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
                passText.setEchoChar((char) 0);
                passEye.setIcon(scaledIcon1);
            } else {
                ImageIcon originalIcon = new ImageIcon("photos/view.png");
                Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                passText.setEchoChar('*');
                passEye.setIcon(scaledIcon);
            }
            active = !active;
        });
        add(passEye);
        JButton loginButton = createRoundedButton("Login");
        loginButton.setBounds(1190, 490, 150, 40);
        loginButton.addActionListener(e -> {
            if (userText.getText().trim().isEmpty() || passText.getText().trim().isEmpty()) {
                MainFrameView.notificationView.showNotification("Please Fill All The Fields.", true);
                return;
            }
            try {
                new LoginController(userText.getText().trim(), passText.getText().trim());
                setVisible(false);
                MainFrameView.mainPanel = new MainView();
                MainFrameView.frame.add(MainFrameView.mainPanel);
                ReportsAndOrderView.orderStatusView.updateView();
                UserOrderView.orderStatusView.updateView2();
                MainFrameView.notificationView.showNotification("Login Successfully!",
                        false);
            } catch (IllegalArgumentException err) {
                MainFrameView.notificationView.showNotification(err.getMessage(), true);
            } finally {
                userText.setText("");
                passText.setText("");
                addPlaceholder(userText, "📧   Email");
                addPlaceholder(passText, "🔒   Password");
            }
        });
        SwingUtilities.invokeLater(() -> {
            userText.setFocusable(true);
            passText.setFocusable(true);
        });
        this.add(loginButton);
        JButton createAccountButton = createRoundedButton("Create Account");
        createAccountButton.setBounds(1020, 490, 160, 40);
        createAccountButton.addActionListener(e -> {
            setVisible(false);
            JPanel registerPanel = new RegisterView();
            JButton backButton = createRoundedButton("Back");
            backButton.setBounds(1020, 560, 150, 40);
            backButton.addActionListener(backEvent -> {
                registerPanel.setVisible(false);
                setVisible(true);
            });
            registerPanel.add(backButton);
            MainFrameView.frame.add(registerPanel);
            registerPanel.setVisible(true);
        });
        add(createAccountButton);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon icon = new ImageIcon("photos/login1.png");
        g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
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