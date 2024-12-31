import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
public class LoginRegisterView extends JFrame{
    public static NotificationView notificationView;
    public static JFrame frame;
    public static MainPanel mainPanel;
    @SuppressWarnings("deprecation" )
    LoginRegisterView(){
       
        if(!Order.getOrders().isEmpty()){
            Order.counter = Order.getOrders().get(Order.getOrders().size() - 1).getOrderID()+ 1;
        }
        else {
            Order.counter = 1;
        }

        frame = new JFrame();
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setLayout(null); 
        notificationView=new NotificationView(frame.getWidth());
        frame.add(notificationView.getLabel());
        JPanel loginPanel = new JPanel() {
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
        logoLabel.setBounds(1100, 250, 200, 200);
        loginPanel.add(logoLabel);
        JTextField userText = new JTextField();
        addPlaceholder(userText, "👤   Username");
        userText.setBackground(Color.decode("#fcf3e2"));
        userText.setBounds(1050, 400, 250, 40);
        userText.setFocusable(false);
        userText.setFont(new Font("Open Sans", Font.PLAIN, 16));
        userText.setBorder(BorderFactory.createLineBorder(Color.decode("#fcf3e2"), 2));
        userText.setText("Yazanmahfooz9@gmail.com");
        loginPanel.add(userText);
        JPasswordField passText = new JPasswordField();
        addPlaceholder(passText, "🔒   Password");
        passText.setBounds(1050, 460, 250, 40);
        passText.setFocusable(false);
        passText.setFont(new Font("Open Sans", Font.PLAIN, 16));
        passText.setBorder(BorderFactory.createLineBorder(Color.decode("#fcf3e2"), 2));
        passText.setBackground(Color.decode("#fcf3e2"));
        passText.setText("Yazanmahfooz3$");
        loginPanel.add(passText);
        Font buttonFont = new Font("Open Sans", Font.BOLD, 16);
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(1190, 520, 150, 40);
        loginButton.setFont(buttonFont);
        loginButton.setBackground(Color.decode("#839df6"));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> {
            if (userText.getText().trim().isEmpty() || passText.getText().trim().isEmpty()) {
                notificationView.showNotification("Please Fill All The Fields.");
                return;
            }
            try {
                new Login(userText.getText().trim(), passText.getText().trim());
                loginPanel.setVisible(false);
                mainPanel = new MainPanel();
                frame.add(mainPanel);
                notificationView.showNotification("Login Successfully!");
            } catch (IllegalArgumentException err) {
                notificationView.showNotification(err.getMessage());
            } finally {
                userText.setText("");
                passText.setText("");
                addPlaceholder(userText, "👤   Username");
                addPlaceholder(passText, "🔒   Password");
            }
        });
        SwingUtilities.invokeLater(() -> {
            userText.setFocusable(true);
            passText.setFocusable(true);
        });
        loginPanel.add(loginButton);
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(1020, 520, 160, 40);
        createAccountButton.setFont(buttonFont);
        createAccountButton.setBackground(Color.decode("#839df6"));
        createAccountButton.setForeground(Color.WHITE);
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
            registerLabel.setBounds(1070, 330, 300, 50);
            registerPanel.add(registerLabel);
            JTextField regUserText = new JTextField();
            regUserText.setBounds(1050, 400, 250, 40);
            regUserText.setFont(new Font("Open Sans", Font.PLAIN, 16));
            regUserText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            regUserText.setBackground(Color.decode("#fcf3e2"));
            addPlaceholder(regUserText, "👤   Username");
            registerPanel.add(regUserText);
            JTextField regEmailText = new JTextField();
            regEmailText.setBounds(1050, 460, 250, 40);
            regEmailText.setFont(new Font("Open Sans", Font.PLAIN, 16));
            regEmailText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            regEmailText.setBackground(Color.decode("#fcf3e2"));
            addPlaceholder(regEmailText, "📧   Email");
            registerPanel.add(regEmailText);
            JPasswordField regPassText = new JPasswordField();
            regPassText.setBounds(1050, 520, 250, 40);
            regPassText.setFont(new Font("Open Sans", Font.PLAIN, 16));
            regPassText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            regPassText.setBackground(Color.decode("#fcf3e2"));
            addPlaceholder(regPassText, "🔒   Password");
            registerPanel.add(regPassText);
            JPasswordField regConfirmPassText = new JPasswordField();
            regConfirmPassText.setBounds(1050, 580, 250, 40);
            regConfirmPassText.setFont(new Font("Open Sans", Font.PLAIN, 16));
            regConfirmPassText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            regConfirmPassText.setBackground(Color.decode("#fcf3e2"));
            addPlaceholder(regConfirmPassText, "🔒   Confirm Password");
            registerPanel.add(regConfirmPassText);
            JButton registerButton = new JButton("Register");
            registerButton.setBounds(1190, 640, 150, 40);
            registerButton.setFont(buttonFont);
            registerButton.setBackground(Color.decode("#839df6"));
            registerButton.setForeground(Color.WHITE);
            registerButton.setFocusPainted(false);
            registerButton.addActionListener(k -> {
                if (regUserText.getText().trim().isEmpty() || regEmailText.getText().trim().isEmpty()
                        || regPassText.getPassword().length == 0 || regConfirmPassText.getPassword().length == 0) {
                    notificationView.showNotification("Please Fill All The Fields.");
                    return;
                }
                try {
                    new Register(regUserText.getText(), regEmailText.getText(), regPassText.getText(), regConfirmPassText.getText());
                    registerPanel.setVisible(false);
                    mainPanel = new MainPanel();
                    frame.add(mainPanel);
                    notificationView.showNotification("Registerd Successfully!");
                } catch (IllegalArgumentException err) {
                    notificationView.showNotification(err.getMessage());
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
            JButton backButton = new JButton("Back");
            backButton.setBounds(1020, 640, 150, 40);
            backButton.setFont(buttonFont);
            backButton.setBackground(Color.decode("#839df6"));
            backButton.setForeground(Color.WHITE);
            backButton.setFocusPainted(false);
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
