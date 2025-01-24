import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
public class CartView {
    private static DefaultListModel<String> cartListModel;
    private static JList<String> cartList;
    public static JPanel orderPanel; 

    public static JPanel createCartPanel() {
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setPreferredSize(new Dimension(600, 500));
        cartPanel.setBackground(Palette.BackGround);

        JLabel titleLabel = new JLabel("My Cart \uD83D\uDED2");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 38));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cartPanel.add(titleLabel, BorderLayout.NORTH);

        cartListModel = new DefaultListModel<>();
        cartList = new JList<>(cartListModel);
        cartList.setFont(new Font("Serif", Font.PLAIN, 32)); 
        cartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartList.setBackground(Palette.BackGround);

        CustomScrollPane scrollPane = new CustomScrollPane(cartList,Color.LIGHT_GRAY,Color.DARK_GRAY);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setBackground(Palette.AdditionalColor);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Palette.BackGround);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(760, 0));
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Palette.BackGround);
        rightPanel.setBorder(new LineBorder(Palette.AdditionalColor, 10));
        rightPanel.setPreferredSize(new Dimension(760, 0));
        centerPanel.add(rightPanel, BorderLayout.EAST);

        cartPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 40)); 
        buttonPanel.setOpaque(false);

        JButton backButton = new JButton();
        backButton.setPreferredSize(new Dimension(100, 60)); 
        ImageIcon backIcon = new ImageIcon("photos\\turn-back.png");
        Image img = backIcon.getImage();
        Image scaledImg = img.getScaledInstance(50, 40, Image.SCALE_SMOOTH);
        backIcon = new ImageIcon(scaledImg);
        backButton.setIcon(backIcon);
        backButton.setBackground(new Color(211, 211, 211));
        backButton.addActionListener(e -> {
            MainPanel.mainContent.removeAll();
            MainPanel.mainContent.add(MainPanel.menuContentPanel1, BorderLayout.CENTER);
            MainPanel.menuContentPanel1.setSelectedCategory("Pizza");
            MainPanel.sideCategoryPanel.getSideMenu().setVisible(true);
        });

        JButton totalButton = new JButton("Show Total");
        totalButton.setPreferredSize(new Dimension(150, 60)); 
        totalButton.setBackground(new Color(211, 211, 211));
        totalButton.setFont(new Font("Serif", Font.BOLD, 16));
        totalButton.addActionListener(e -> {
            double total = 0;
            for (Meal meal : MealPanel.cart) {
                total += meal.getMealPrice();
            }
            JOptionPane.showMessageDialog(cartPanel, "Total: $" + Math.round(total*100.0)/100.0 , "Cart Total", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton DoneButton = new JButton();
        ImageIcon DoneIcon = new ImageIcon("photos\\check-mark.png");
        Image img2 = DoneIcon.getImage();
        Image scaledImg2 = img2.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        DoneIcon = new ImageIcon(scaledImg2);
        DoneButton.setIcon(DoneIcon);
        DoneButton.setPreferredSize(new Dimension(100, 60)); 
        DoneButton.setBackground(new Color(211, 211, 211));
        DoneButton.addActionListener(e -> {
            if (!cartListModel.isEmpty()) {
                orderPanel = CustomerOrderDialogController.a.getView().getPanel();
                orderPanel.setVisible(true);
                rightPanel.add(orderPanel);
                rightPanel.revalidate();
                rightPanel.repaint();
            } else {
                LoginRegisterView.notificationView.showNotification("Your cart is empty!", true);
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
        for (Meal meal : MealPanel.cart) {
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


