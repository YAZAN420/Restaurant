import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
public class LoginRegisterView extends JFrame{
    public static NotificationView notificationView;
    public static JFrame frame;
    public static MainPanel mainPanel;
    public static JPanel loginPanel;
    @SuppressWarnings("deprecation" )
    LoginRegisterView() {
        frame = new JFrame();
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setLayout(null);
        JPanel x = new JPanel(); x.setBackground(Color.CYAN); x.setPreferredSize(new Dimension(1920,100));
        frame.add(x);

        notificationView = new NotificationView(frame.getWidth());
        frame.add(notificationView.getLabel());
        loginPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("photos/LoginAndRegister.png");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        loginPanel.setLayout(null);
        loginPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        JLabel logoLabel = new JLabel("LOGIN");
        logoLabel.setForeground(Color.decode("#fcf3e2"));
        logoLabel.setFont(new Font("Lobster", Font.BOLD, 40));
        logoLabel.setBounds(1100, 200, 200, 200);
        loginPanel.add(logoLabel);
        JTextField userText = new JTextField();
        addPlaceholder(userText, "📧   Email");
        userText.setBackground(Color.decode("#fcf3e2"));
        userText.setBounds(1050, 350, 250, 40);
        userText.setFocusable(false);
        userText.setFont(new Font("Open Sans", Font.PLAIN, 16));
        userText.setBorder(BorderFactory.createLineBorder(Color.decode("#fcf3e2"), 2));
        userText.setBorder(new EmptyBorder(5, 20, 5, 10));
        userText.setText("Yazanmahfooz9@gmail.com");
        loginPanel.add(userText);
        JPasswordField passText = new JPasswordField();
        addPlaceholder(passText, "🔒   Password");
        passText.setBounds(1050, 410, 250, 40);
        passText.setFocusable(false);
        passText.setFont(new Font("Open Sans", Font.PLAIN, 16));
        passText.setBorder(BorderFactory.createLineBorder(Color.decode("#fcf3e2"), 2));
        passText.setBorder(new EmptyBorder(5, 20, 5, 10));
        passText.setBackground(Color.decode("#fcf3e2"));
        passText.setText("Yazanmahfooz3");
        loginPanel.add(passText);
        JButton loginButton = createRoundedButton("Login");
        loginButton.setBounds(1190, 470, 150, 40);
        loginButton.addActionListener(e -> {
            if (userText.getText().trim().isEmpty() || passText.getText().trim().isEmpty()) {
                notificationView.showNotification("Please Fill All The Fields.", true);
                return;
            }
            try {
                new Login(userText.getText().trim(), passText.getText().trim());
                loginPanel.setVisible(false);
                mainPanel = new MainPanel();
                frame.add(mainPanel);
                Reports_and_OrderPanel.orderStatusView.updateView();
                UserOrderPanel.orderStatusView.updateView2();
                notificationView.showNotification("Login Successfully!", false);
            } catch (IllegalArgumentException err) {
                notificationView.showNotification(err.getMessage(), true);
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
        loginPanel.add(loginButton);
        JButton createAccountButton = createRoundedButton("Create Account");
        createAccountButton.setBounds(1020, 470, 160, 40);
        createAccountButton.addActionListener(e -> {
            loginPanel.setVisible(false);
            JPanel registerPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    ImageIcon icon = new ImageIcon("photos/LoginAndRegister.png");
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            registerPanel.setLayout(null);
            registerPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            JLabel registerLabel = new JLabel("REGISTER");
            registerLabel.setForeground(Color.decode("#fcf3e2"));
            registerLabel.setFont(new Font("Lobster", Font.BOLD, 40));
            registerLabel.setBounds(1070, 230, 300, 50);
            registerPanel.add(registerLabel);
            JTextField regUserText = new JTextField();
            regUserText.setBounds(1050, 300, 250, 40);
            regUserText.setFont(new Font("Open Sans", Font.PLAIN, 16));
            regUserText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            regUserText.setBorder(new EmptyBorder(5, 20, 5, 10));
            regUserText.setBackground(Color.decode("#fcf3e2"));
            addPlaceholder(regUserText, "👤   Username");
            registerPanel.add(regUserText);
            JTextField regEmailText = new JTextField();
            regEmailText.setBounds(1050, 360, 250, 40);
            regEmailText.setFont(new Font("Open Sans", Font.PLAIN, 16));
            regEmailText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            regEmailText.setBorder(new EmptyBorder(5, 20, 5, 10));
            regEmailText.setBackground(Color.decode("#fcf3e2"));
            addPlaceholder(regEmailText, "📧   Email");
            registerPanel.add(regEmailText);
            JPasswordField regPassText = new JPasswordField();
            regPassText.setBounds(1050, 420, 250, 40);
            regPassText.setFont(new Font("Open Sans", Font.PLAIN, 16));
            regPassText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            regPassText.setBorder(new EmptyBorder(5, 20, 5, 10));
            regPassText.setBackground(Color.decode("#fcf3e2"));
            addPlaceholder(regPassText, "🔒   Password");
            registerPanel.add(regPassText);
            JPasswordField regConfirmPassText = new JPasswordField();
            regConfirmPassText.setBounds(1050, 480, 250, 40);
            regConfirmPassText.setFont(new Font("Open Sans", Font.PLAIN, 16));
            regConfirmPassText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            regConfirmPassText.setBorder(new EmptyBorder(5, 20, 5, 10));
            regConfirmPassText.setBackground(Color.decode("#fcf3e2"));
            addPlaceholder(regConfirmPassText, "🔒   Confirm Password");
            registerPanel.add(regConfirmPassText);
            JButton registerButton = createRoundedButton("Register");
            registerButton.setBounds(1190, 540, 150, 40);
            registerButton.addActionListener(k -> {
                if (regUserText.getText().trim().isEmpty() || regEmailText.getText().trim().isEmpty()
                        || regPassText.getPassword().length == 0 || regConfirmPassText.getPassword().length == 0) {
                    notificationView.showNotification("Please Fill All The Fields.", true);
                    return;
                }
                try {
                    new Register(regUserText.getText(), regEmailText.getText(), regPassText.getText(),
                            regConfirmPassText.getText());
                    registerPanel.setVisible(false);
                    mainPanel = new MainPanel();
                    frame.add(mainPanel);
                    Reports_and_OrderPanel.orderStatusView.updateView();
                    UserOrderPanel.orderStatusView.updateView2();
                    notificationView.showNotification("Registerd Successfully!", false);
                } catch (IllegalArgumentException err) {
                    notificationView.showNotification(err.getMessage(), true);
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
            registerPanel.add(registerButton);
            JButton backButton = createRoundedButton("Back");
            backButton.setBounds(1020, 540, 150, 40);
            backButton.addActionListener(backEvent -> {
                registerPanel.setVisible(false);
                loginPanel.setVisible(true);
            });
            registerPanel.add(backButton);
            frame.add(registerPanel);
            registerPanel.setVisible(true);
        });
        loginPanel.add(createAccountButton);
        frame.getContentPane().setFocusable(true);
        frame.getContentPane().requestFocusInWindow();
        frame.add(loginPanel);
        frame.setVisible(true);
    }
    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Palette.AdditionalColor);
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
