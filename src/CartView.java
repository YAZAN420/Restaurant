import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CartView {
    private static DefaultListModel<String> cartListModel;
    private static JList<String> cartList;

    public static JPanel createCartPanel() {

        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setSize(1000, 500);
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
            cartPanel.setVisible(false);
        });

        JButton totalButton = new JButton("Show Total");
        totalButton.setBackground(new Color(211, 211, 211));
        totalButton.setFont(new Font("Serif", Font.BOLD, 16));
        totalButton.addActionListener(e -> {
            double total = 0;
            for (Meal meal : Main.cart) {
                total += meal.getMealPrice();
            }
            JOptionPane.showMessageDialog(cartPanel, "Total: $" + total, "Cart Total", JOptionPane.INFORMATION_MESSAGE);
        });

        buttonPanel.add(backButton);
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
}
