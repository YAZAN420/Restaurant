import java.awt.*;
import java.util.Arrays;
import javax.swing.*;

public class NotificationView extends JLabel{
    private JLabel notificationLabel;
    public NotificationView(int frameWidth) {
        notificationLabel = new JLabel("");
        notificationLabel.setOpaque(true);
        notificationLabel.setBackground(new Color(131, 213, 64));
        notificationLabel.setForeground(Color.decode("#fcf3e2"));
        notificationLabel.setBounds(0, 0, frameWidth - 385, 57);
        notificationLabel.setFont(new Font("Open Sans", Font.BOLD, 18));
        notificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        notificationLabel.setVisible(false);
    }
    public JLabel getLabel(){return notificationLabel;}

    public void showNotification(String message, boolean Warning) {
        if(MainPanel.navBar.getPanel().isShowing()) Arrays.stream(MainPanel.navBar.getPanel().getComponents()).forEach(component -> component.setVisible(false));
        if(Warning){notificationLabel.setBackground(new Color(211, 45, 45));}
        else {notificationLabel.setBackground(new Color(131, 213, 64));}
        notificationLabel.setText(message);
        notificationLabel.setVisible(true);
        Timer hideTimer = new Timer(2000, e -> {
            notificationLabel.setVisible(false);
//            MainPanel.navBar.reRender(User.getCurrentUser().getName());
            Arrays.stream(MainPanel.navBar.getPanel().getComponents()).forEach(component -> component.setVisible(true));
        });
        hideTimer.setRepeats(false);
        hideTimer.start();
    }
}
