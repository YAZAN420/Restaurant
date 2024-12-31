import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationBarPanel{
    private final JPanel navBar;
    public NavigationBarPanel() {
        navBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        navBar.setBackground(new Color(85, 107, 47));
        String[] buttons = {"Home","cart",User.getCurrentUser().getName()};
        for (String text : buttons) {
            JButton button = createButton(text);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (button.getText() == "Home") {
                        MainPanel.mainContent.removeAll();
                        MainPanel.mainContent.add(MainPanel.menuContentPanel1, BorderLayout.CENTER);
                        MainPanel.menuContentPanel1.setSelectedCategory("Pizza");
                        MainPanel.sideCategoryPanel.getSideMenu().setVisible(true);
                        MainPanel.mainContent.repaint();
                    } else if (button.getText() == "cart") {
                        MainPanel.mainContent.removeAll();
                        MainPanel.mainContent.add(MainPanel.cartPanel, BorderLayout.CENTER);
                        MainPanel.sideCategoryPanel.getSideMenu().setVisible(false);
                        MainPanel.mainContent.repaint();
                    }else{
                        SideCategoryPanel.categoryList.clearSelection();
                        MainPanel.mainContent.removeAll();
                        MainPanel.sideCategoryPanel.getSideMenu().setVisible(false);
                        MainPanel.mainContent.repaint();
                    }
                }
            });
            navBar.add(button);
        }
    }
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Georgia", Font.BOLD, 20));
        button.setBackground(new Color(85, 107, 47));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }
    public JPanel getPanel() {
        return navBar;
    }
}