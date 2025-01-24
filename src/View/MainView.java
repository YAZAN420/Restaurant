package View;

import javax.swing.*;
import java.awt.*;

public class MainView extends JPanel {
    public static SideCategoryView sideCategoryPanel;
    public static MenuContentView menuContentPanel1;
    public static JPanel mainContent;
    public static NavigationBarPanelView navBar;
    public static JPanel cartPanel;
    public static ReportsAndOrderView reportsAndOrderPanel;

    public MainView() {
        navBar = new NavigationBarPanelView();
        mainContent = new JPanel();
        mainContent.setBounds(0, 0, 1920 - 385, 1080 - 215);
        mainContent.setVisible(true);
        mainContent.setLayout(new BorderLayout());
        sideCategoryPanel = new SideCategoryView();
        reportsAndOrderPanel = new ReportsAndOrderView();
        cartPanel = CartView.createCartPanel();
        setLayout(new BorderLayout());
        setBounds(0, 0, 1920 - 385, 1080 - 215);
        menuContentPanel1 = new MenuContentView("Pizza");
        sideCategoryPanel.setMenuContentPanel(menuContentPanel1);
        mainContent.add(menuContentPanel1);
        add(mainContent);
        add(navBar.getPanel(), BorderLayout.NORTH);
        add(sideCategoryPanel.getSideMenu(), BorderLayout.WEST);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
