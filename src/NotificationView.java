import java.awt.*;
import javax.swing.*;

public class NotificationView extends JLabel{
    private JLabel notificationLabel;
    public NotificationView(int frameWidth) {
        notificationLabel = new JLabel("");
        notificationLabel.setOpaque(true);
        notificationLabel.setBackground(Color.decode("#839df6"));
        notificationLabel.setForeground(Color.decode("#fcf3e2"));
        notificationLabel.setBounds(0, 0, frameWidth - 385, 50);
        notificationLabel.setFont(new Font("Open Sans", Font.BOLD, 18));
        notificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        notificationLabel.setVisible(false);
    }
    public JLabel getLabel(){return notificationLabel;}
    public void showNotification(String message) {
        notificationLabel.setText(message);
        notificationLabel.setVisible(true);
        Timer hideTimer = new Timer(1500, e -> notificationLabel.setVisible(false));
        hideTimer.setRepeats(false);
        hideTimer.start();
    }
}
