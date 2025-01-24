package View;

import Controller.MenuManagerController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SideCategoryView {
    private static JPanel sideMenu;
    static JList<String> categoryList;
    static MenuContentView menuContentPanel;
    static DefaultListModel<String> listModel;

    public SideCategoryView() {
        sideMenu = new JPanel(new BorderLayout());
        sideMenu.setBackground(PaletteView.MainColor);
        sideMenu.setPreferredSize(new Dimension(200, 0));
        JLabel categoryTitle = new JLabel("CATEGORY", SwingConstants.CENTER);
        categoryTitle.setForeground(Color.WHITE);
        categoryTitle.setFont(new Font(PaletteView.font, Font.BOLD, 20));
        categoryTitle.setBorder(BorderFactory.createEmptyBorder(25, 0, 15, 0));
        sideMenu.add(categoryTitle, BorderLayout.NORTH);
        String[] categoryNames = MenuManagerController.getMenu().keySet().toArray(new String[0]);
        listModel = new DefaultListModel<>();
        for (int i = 0; i < categoryNames.length; i++)
            listModel.addElement(categoryNames[i]);

        categoryList = new JList<>(listModel);
        categoryList.setFont(new Font(PaletteView.font, Font.BOLD, 16));
        categoryList.setForeground(Color.WHITE);
        categoryList.setBackground(PaletteView.MainColor);
        categoryList.setCellRenderer(new CustomListCellRenderer());

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
        listScrollPane.setBorder(null);
        sideMenu.add(listScrollPane, BorderLayout.CENTER);
    }

    public static void updateView(String newKey) {
        listModel.addElement(newKey);
        categoryList.setSelectedIndex(listModel.indexOf(newKey));
        categoryList.revalidate();
        categoryList.repaint();
        MainView.menuContentPanel1.revalidate();
        MainView.menuContentPanel1.repaint();
    }

    public JPanel getSideMenu() {
        return sideMenu;
    }

    @SuppressWarnings("static-access")
    public void setMenuContentPanel(MenuContentView menuContentPanel) {
        this.menuContentPanel = menuContentPanel;
    }

    public static class CustomListCellRenderer extends DefaultListCellRenderer {
        private static final int PADDING_LEFT = 15;

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            EmptyBorder padding = new EmptyBorder(0, PADDING_LEFT, 0, 0);
            renderer.setBorder(padding);

            if (isSelected) {
                renderer.setBackground(list.getBackground().darker());
                renderer.setForeground(Color.BLACK);
            } else {
                renderer.setBackground(list.getBackground());
                renderer.setForeground(list.getForeground());
            }

            return renderer;
        }
    }

}
