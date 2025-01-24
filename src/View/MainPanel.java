import View.CartView;

import javax.swing.*;
import java.awt.*;
public class MainPanel extends JPanel {
    public static SideCategoryPanel sideCategoryPanel;
    public static MenuContentJPanel menuContentPanel1;
    public static mainJpanel mainContent;
    public static NavigationBarPanel navBar;
    public static JPanel cartPanel;
    public static Reports_and_OrderPanel reportsAndOrderPanel;
    
    public MainPanel() {
        navBar= new NavigationBarPanel();
        mainContent = new mainJpanel();
        sideCategoryPanel = new SideCategoryPanel();
        reportsAndOrderPanel = new Reports_and_OrderPanel();
        cartPanel = CartView.createCartPanel();
        setLayout(new BorderLayout());
        setBounds(0, 0, 1920-385, 1080-215);
        menuContentPanel1 = new MenuContentJPanel("Pizza");
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
