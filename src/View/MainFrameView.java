package View;

import java.awt.*;
import javax.swing.*;

public class MainFrameView extends JFrame {
    public static NotificationView notificationView;
    public static JFrame frame;
    public static MainView mainPanel;
    public static JPanel loginPanel;

    public MainFrameView() {
        frame = new JFrame();
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setLayout(null);
        Image im =new ImageIcon("photos/logo.png").getImage();  frame.setTitle("Dish Dash");
        frame.setIconImage(im);
        notificationView = new NotificationView(frame.getWidth());
        frame.add(notificationView.getLabel());
        loginPanel = new LoginView();
        frame.getContentPane().setFocusable(true);
        frame.getContentPane().requestFocusInWindow();
        frame.add(loginPanel);
        frame.setVisible(true);
    }
}
