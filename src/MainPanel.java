import javax.swing.*;
import java.awt.*;
public class MainPanel extends JPanel {
    static SideCategoryPanel sideCategoryPanel = new SideCategoryPanel();
    static MenuContentJPanel menuContentPanel1;
    static mainJpanel mainContent = new mainJpanel();
    static NavigationBarPanel navBar = new NavigationBarPanel();
    static JPanel cartPanel = CartView.createCartPanel();
    
    public MainPanel() {
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
