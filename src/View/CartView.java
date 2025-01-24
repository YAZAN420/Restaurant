package View;

import Model.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartView {
    private static DefaultListModel<String> cartListModel;
    private static JList<String> cartList;
    public static JPanel orderPanel;
    private static JLabel totalPriceLabel;
    private static double totalPrice=0.0;

    public static JPanel createCartPanel() {
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setPreferredSize(new Dimension(600, 500));
        cartPanel.setBackground(PaletteView.BackGround);

        JLabel titleLabel = new JLabel("My Cart \uD83D\uDED2");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 38)); // Another font
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cartPanel.add(titleLabel, BorderLayout.NORTH);

        cartListModel = new DefaultListModel<>();
        cartList = new JList<>(cartListModel);
        cartList.setFont(new Font(PaletteView.font, Font.PLAIN, 32));
        cartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartList.setBackground(PaletteView.BackGround);

        CustomScrollView scrollPane = new CustomScrollView(cartList, Color.LIGHT_GRAY, Color.DARK_GRAY);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setBackground(PaletteView.AdditionalColor);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(PaletteView.BackGround);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(760, 0));
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        totalPriceLabel = new JLabel("Total Price: $0.00");
        totalPriceLabel.setFont(new Font(PaletteView.font, Font.BOLD, 32));
        totalPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalPriceLabel.setBorder(BorderFactory.createMatteBorder(0, 10, 10, 10, PaletteView.AdditionalColor));
        totalPriceLabel.setBackground(PaletteView.BackGround);
        leftPanel.add(totalPriceLabel, BorderLayout.SOUTH);

        centerPanel.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(PaletteView.BackGround);
        rightPanel.setBorder(new LineBorder(PaletteView.AdditionalColor, 10));
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
            MainView.mainContent.removeAll();
            MainView.mainContent.add(MainView.menuContentPanel1, BorderLayout.CENTER);
            MainView.menuContentPanel1.setSelectedCategory("Pizza");
            MainView.sideCategoryPanel.getSideMenu().setVisible(true);
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
                orderPanel = CustomerOrderDialogConView.a.getView().getPanel();
                orderPanel.setVisible(true);
                rightPanel.add(orderPanel);
                rightPanel.revalidate();
                rightPanel.repaint();
            } else {
                MainFrameView.notificationView.showNotification("Your cart is empty!", true);
            }
        });
        buttonPanel.add(backButton);
        buttonPanel.add(DoneButton);

        cartPanel.add(buttonPanel, BorderLayout.SOUTH);

        return cartPanel;
    }


    public static void updateCartView(ArrayList<MealModel> cart) {
        cartListModel.clear();

        Map<String, Integer> mealCountMap = new HashMap<>();
        totalPrice = 0.0;

        for (MealModel meal : cart) {
            String mealName = meal.getMealName();
            double mealPrice = meal.getMealPrice();
            mealCountMap.put(mealName, mealCountMap.getOrDefault(mealName, 0) + 1);
            totalPrice += mealPrice;
        }

        for (Map.Entry<String, Integer> entry : mealCountMap.entrySet()) {
            String mealDisplayText = entry.getKey() + " (x" + entry.getValue() + ")";
            cartListModel.addElement(mealDisplayText);
        }

        totalPrice = Math.round(totalPrice * 100.0) / 100.0;
        totalPriceLabel.setText("Total Price: $" + totalPrice);
    }

    public static DefaultListModel<String> getCartListModel() {
        return cartListModel;
    }

    public static JList<String> getCartList() {
        return cartList;
    }

    public static void setTotalPrice(double totalPrice) {
        CartView.totalPrice = totalPrice;
    }

    public static double getTotalPrice() {
        return totalPrice;
    }
}
