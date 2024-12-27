import javax.swing.*;
import javax.swing.text.JTextComponent;

import java.awt.*;

public class LoginRegisterView {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        JPanel Loginpanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("img/LoginAndRegister.png");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        JLabel logoLabel = new JLabel("LOGIN");
        logoLabel.setForeground(Color.decode("#fcf3e2"));
        logoLabel.setFont(new Font("Lobster", Font.BOLD, 40));
        logoLabel.setBounds(1100, 250, 200, 200);
        Loginpanel.setLayout(null);
        Font buttonFont = new Font("Open Sans", Font.BOLD, 16);
        JTextField userText = new JTextField();

        addPlaceholder(userText, "👤   Username");
        userText.setBackground(Color.decode("#fcf3e2"));
        userText.setBounds(1050, 400, 250, 40);
        userText.setFocusable(false);
        userText.setFont(new Font("Open Sans", Font.PLAIN, 16));
        userText.setBorder(BorderFactory.createLineBorder(Color.decode("#fcf3e2"), 2));
        JPasswordField passText = new JPasswordField();
        addPlaceholder(passText, "🔒   Password");
        passText.setFocusable(false);
        passText.setBounds(1050, 460, 250, 40);
        passText.setFont(new Font("Open Sans", Font.PLAIN, 16));
        passText.setBorder(BorderFactory.createLineBorder(Color.decode("#fcf3e2"), 2));
        passText.setBackground(Color.decode("#fcf3e2"));
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(1190, 520, 150, 40);
        loginButton.setFont(buttonFont);
        loginButton.setBackground(Color.decode("#839df6"));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(Color.decode("#839df6"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(Color.decode("#839df6"));
            }
        });
        loginButton.addActionListener(e -> {
            if (userText.getText().trim().isEmpty() || passText.getText().trim().isEmpty())
                return;
            try {
                new Login(userText.getText().trim(), passText.getText().trim());
                userText.setText("");
                passText.setText("");
                HomeView h = new HomeView("Hello " + User.getCurrentUser().getName());
                Loginpanel.setVisible(false);
                JButton logoutButton = new JButton("Logout");
                logoutButton.setFont(new Font("Open Sans", Font.BOLD, 16));
                logoutButton.setBackground(new Color(70, 130, 180));
                logoutButton.setForeground(Color.WHITE);
                logoutButton.setFocusPainted(false);
                logoutButton.setFocusPainted(false);
                logoutButton.setBounds(1080, 630, 150, 40);
                frame.add(logoutButton);
                logoutButton.addActionListener(el -> {
                    User.setCurrentUser(null);
                    h.setVisible(false);
                    logoutButton.setVisible(false);
                    Loginpanel.setVisible(true);
                });
                frame.add(h);
            } catch (IllegalArgumentException err) {
                HomeView h = new HomeView(err.getMessage());
                Loginpanel.setVisible(false);
                frame.add(h);
            }
        });
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(1020, 520, 160, 40);
        createAccountButton.setFont(buttonFont);
        createAccountButton.setBackground(Color.decode("#839df6"));
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setFocusPainted(false);
        createAccountButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                createAccountButton.setBackground(Color.decode("#839df6"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                createAccountButton.setBackground(Color.decode("#839df6"));
            }
        });
        Loginpanel.add(userText);
        Loginpanel.add(passText);
        Loginpanel.add(loginButton);
        Loginpanel.add(createAccountButton);
        Loginpanel.add(logoLabel);
        frame.add(Loginpanel);
        frame.setVisible(true);
        SwingUtilities.invokeLater(() -> {
            userText.setFocusable(true);
            passText.setFocusable(true);
        });
        createAccountButton.addActionListener(e -> {
            Loginpanel.setVisible(false);
            JLabel registerLabel = new JLabel("REGISTER");
            registerLabel.setForeground(Color.decode("#fcf3e2"));
            registerLabel.setFont(new Font("Lobster", Font.BOLD, 40));
            registerLabel.setBounds(1070, 250, 300, 200);   
            JPanel registerPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    ImageIcon icon = new ImageIcon("img/LoginAndRegister.png");
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            registerPanel.setLayout(null);
            JTextField regUserText = new JTextField();
            regUserText.setBounds(1050, 400, 250, 40);
            regUserText.setFont(new Font("Open Sans", Font.PLAIN, 16));
            regUserText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            regUserText.setBackground(Color.decode("#fcf3e2"));
            addPlaceholder(regUserText, "👤   Usename");
            JTextField regEmailText = new JTextField();
            regEmailText.setBounds(1050, 460, 250, 40);
            regEmailText.setFont(new Font("Open Sans", Font.PLAIN, 16));
            regEmailText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            regEmailText.setBackground(Color.decode("#fcf3e2"));
            addPlaceholder(regEmailText, "📧   Email");
            JPasswordField regPassText = new JPasswordField();
            regPassText.setBounds(1050, 520, 250, 40);
            regPassText.setFont(new Font("Open Sans", Font.PLAIN, 16));
            regPassText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            regPassText.setBackground(Color.decode("#fcf3e2"));
            addPlaceholder(regPassText, "🔒   Password");
            JPasswordField regConfirmPassText = new JPasswordField();
            regConfirmPassText.setBounds(1050, 580, 250, 40);
            regConfirmPassText.setFont(new Font("Open Sans", Font.PLAIN, 16));
            regConfirmPassText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            regConfirmPassText.setBackground(Color.decode("#fcf3e2"));
            addPlaceholder(regConfirmPassText, "🔒   ConfirmPassword");
            JButton registerButton = new JButton("Register");
            registerButton.setBounds(1190, 640, 150, 40);
            registerButton.setFont(buttonFont);
            registerButton.setBackground(Color.decode("#839df6"));
            registerButton.setForeground(Color.WHITE);
            registerButton.setFocusPainted(false);
            registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    registerButton.setBackground(Color.decode("#839df6"));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    registerButton.setBackground(Color.decode("#839df6"));
                }
            });
            registerButton.addActionListener(k -> {
                if (regUserText.getText().trim().isEmpty() || regEmailText.getText().trim().isEmpty()
                        || regPassText.getText().trim().isEmpty() || regConfirmPassText.getText().trim().isEmpty())
                    return;
                try {
                    new Register(regUserText.getText(), regEmailText.getText(), regPassText.getText(),
                            regConfirmPassText.getText());
                    regUserText.setText("");
                    regEmailText.setText("");
                    regPassText.setText("");
                    regConfirmPassText.setText("");
                    registerPanel.setVisible(false);
                    HomeView h = new HomeView("Registerd");
                    h.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                    JButton logoutButton = new JButton("Logout");
                    logoutButton.setFont(new Font("Open Sans", Font.BOLD, 16));
                    logoutButton.setBackground(new Color(70, 130, 180));
                    logoutButton.setForeground(Color.WHITE);
                    logoutButton.setFocusPainted(false);
                    logoutButton.setFocusPainted(false);
                    logoutButton.setBounds(1080, 630, 150, 40);
                    frame.add(logoutButton);
                    logoutButton.addActionListener(el -> {
                        User.setCurrentUser(null);
                        h.setVisible(false);
                        logoutButton.setVisible(false);
                        Loginpanel.setVisible(true);
                    });
                    frame.add(h);
                } catch (IllegalArgumentException err) {
                    HomeView h = new HomeView(err.getMessage());
                    h.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                    registerPanel.setVisible(false);
                    frame.add(h);
                }
            });
            JButton backButton = new JButton("Back");
            backButton.setBounds(1020, 640, 150, 40);
            backButton.setFont(buttonFont);
            backButton.setBackground(Color.decode("#839df6"));
            backButton.setForeground(Color.WHITE);
            backButton.setFocusPainted(false);
            backButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    backButton.setBackground(Color.decode("#839df6"));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    backButton.setBackground(Color.decode("#839df6"));
                }
            });
            backButton.addActionListener(backEvent -> {
                registerPanel.setVisible(false);
                Loginpanel.setVisible(true);
            });
            registerPanel.add(regUserText);
            registerPanel.add(registerLabel);
            registerPanel.add(regEmailText);
            registerPanel.add(regPassText);
            registerPanel.add(regConfirmPassText);
            registerPanel.add(registerButton);
            registerPanel.add(backButton);
            frame.add(registerPanel);
            registerPanel.setVisible(true);
        });
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
}