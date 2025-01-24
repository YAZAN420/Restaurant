import javax.swing.*;
import java.awt.*;

public class HomeView extends JPanel {
    private JLabel displayLabel;
    public HomeView(String text) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        displayLabel = new JLabel(text, SwingConstants.CENTER);
        displayLabel.setFont(new Font("Arial", Font.BOLD, 24));
        displayLabel.setForeground(Color.BLACK);
        add(displayLabel, BorderLayout.CENTER);
    }
    public void setText(String text) {
        displayLabel.setText(text);
    }
}
