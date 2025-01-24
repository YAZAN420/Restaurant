package View;

import java.awt.*;
import java.util.Arrays;
import javax.swing.*;

public class NotificationView extends JLabel {
    private JLabel notificationLabel;

    public NotificationView(int frameWidth) {
        notificationLabel = new JLabel("");
        notificationLabel.setOpaque(true);
        notificationLabel.setBackground(new Color(131, 213, 64));
        notificationLabel.setForeground(Color.decode("#fcf3e2"));
        notificationLabel.setBounds(0, 0, frameWidth - 385, 59);
        notificationLabel.setFont(new Font(PaletteView.font, Font.BOLD, 18));
        notificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        notificationLabel.setVisible(false);
    }

    public JLabel getLabel() {
        return notificationLabel;
    }

    public void showNotification(String message, boolean Warning) {
        try {
            if (MainView.navBar != null && MainView.navBar.getPanel() != null && MainView.navBar.getPanel().isShowing()) {
                Arrays.stream(MainView.navBar.getPanel().getComponents())
                        .forEach(component -> component.setVisible(false));
            }
        } catch (NullPointerException e) {
            System.out.println("Warning: NavigationBarPanelView is null or not properly initialized. Details: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error occurred while hiding components: " + e.getMessage());
        }

        try {
            if (Warning) {
                notificationLabel.setBackground(new Color(211, 45, 45));
            } else {
                notificationLabel.setBackground(new Color(131, 213, 64));
            }
            notificationLabel.setText(message);
            notificationLabel.setVisible(true);

            Timer hideTimer = new Timer(2000, e -> {
                notificationLabel.setVisible(false);
                try {
                    if (MainView.navBar != null && MainView.navBar.getPanel() != null) {
                        Arrays.stream(MainView.navBar.getPanel().getComponents())
                                .forEach(component -> component.setVisible(true));
                    }
                } catch (NullPointerException ex) {
                    System.out.println("Warning: Unable to show NavigationBarPanelView components. Details: " + ex.getMessage());
                } catch (Exception ex) {
                    System.out.println("Unexpected error occurred while showing components: " + ex.getMessage());
                }
            });
            hideTimer.setRepeats(false);
            hideTimer.start();

        } catch (Exception e) {
            System.out.println("Error occurred while showing notification: " + e.getMessage());
        }
    }

}
