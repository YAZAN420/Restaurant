import javax.swing.*;
import java.awt.*;

public class SideCategoryPanel {
    private final JPanel sideMenu;
    static JList<String> categoryList;
    MenuContentJPanel menuContentPanel;
    Color yellow = new Color(245, 245, 220);
    Color Cream_White=new Color(245, 245, 220);
    public SideCategoryPanel() {
        sideMenu = new JPanel(new BorderLayout());
        sideMenu.setBackground(yellow);
        sideMenu.setPreferredSize(new Dimension(200, 0));
        JLabel categoryTitle = new JLabel("CATEGORY",SwingConstants.CENTER);
        categoryTitle.setFont(new Font("Georgia", Font.BOLD, 20));
        categoryTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        sideMenu.add(categoryTitle, BorderLayout.NORTH);
        String[] categoryNames = MenuManager.getMenu().keySet().toArray(new String[0]);
        categoryList = new JList<>(categoryNames);
        categoryList.setFont(new Font("Georgia", Font.BOLD, 16));
        categoryList.setBackground(yellow);
        categoryList.setFixedCellHeight(60);
        categoryList.setSelectedIndex(0);
        categoryList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = categoryList.getSelectedValue();
                menuContentPanel.setSelectedCategory(selected);
            }
        });
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(categoryList);
        sideMenu.add(listScrollPane, BorderLayout.CENTER);
    }
    public JPanel getSideMenu() {
        return sideMenu;
    }

    public void setMenuContentPanel(MenuContentJPanel menuContentPanel) {
        this.menuContentPanel = menuContentPanel;
    }
}
