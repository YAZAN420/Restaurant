import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class CartView {
    private static DefaultListModel<String> cartListModel;
    private static JList<String> cartList;
    @SuppressWarnings("unused")
    public static JPanel createCartPanel() {
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setPreferredSize(new Dimension(300, 500));
        cartPanel.setBackground(new Color(253, 245, 230));
        JLabel titleLabel = new JLabel("Your Cart");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cartPanel.add(titleLabel, BorderLayout.NORTH);
        cartListModel = new DefaultListModel<>();
        cartList = new JList<>(cartListModel);
        cartList.setFont(new Font("Serif", Font.PLAIN, 18));
        cartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartList.setBackground(new Color(253, 245, 230));
        JScrollPane scrollPane = new JScrollPane(cartList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cartPanel.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setBackground(new Color(238, 121, 50));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);
        JButton backButton = new JButton("🔙");
        backButton.setPreferredSize(new Dimension(50, 25));
        backButton.setBackground(new Color(211, 211, 211));
        backButton.addActionListener(e -> {
            MainPanel.mainContent.removeAll();
            MainPanel.mainContent.add(MainPanel.menuContentPanel1, BorderLayout.CENTER);
            MainPanel.menuContentPanel1.setSelectedCategory("Pizza");
            MainPanel.sideCategoryPanel.getSideMenu().setVisible(true);
        });
        JButton totalButton = new JButton("Show Total");
        totalButton.setPreferredSize(new Dimension(150, 25));
        totalButton.setBackground(new Color(211, 211, 211));
        totalButton.setFont(new Font("Serif", Font.BOLD, 16));
        totalButton.addActionListener(e -> {
            double total = 0;
            for (Meal meal : MealPanel.cart) {
                total += meal.getMealPrice();
            }
            JOptionPane.showMessageDialog(cartPanel, "Total: $" + total, "Cart Total", JOptionPane.INFORMATION_MESSAGE);
        });
        JButton DoneButton = new JButton("✔");
        DoneButton.setPreferredSize(new Dimension(50, 25));
        DoneButton.setBackground(new Color(211, 211, 211));
        DoneButton.addActionListener(e -> {
            if(!cartListModel.isEmpty()){
                CustomerOrderDialogController a = new CustomerOrderDialogController();
            }
            else {
                JOptionPane.showMessageDialog(cartPanel, "Your cart is empty!", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(backButton);
        buttonPanel.add(DoneButton);
        buttonPanel.add(totalButton);
        cartPanel.add(buttonPanel, BorderLayout.SOUTH);
        return cartPanel;
    }
    public static void updateCartView(ArrayList<Meal> cart) {
        cartListModel.clear();
        for (Meal meal : cart) {
            cartListModel.addElement(meal.getMealName());
        }
    }
   public static DefaultListModel<String> getCartListModel() {
    return cartListModel;
}
    public static JList<String> getCartList() {
        return cartList;
    }
}
