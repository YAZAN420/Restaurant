package View;

import Controller.OrderSystemController;
import Model.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OrderStatusView {
    JPanel orderStatusPanel;

    public OrderStatusView() {
        orderStatusPanel = new JPanel();
        orderStatusPanel.setBackground(Color.LIGHT_GRAY);
        orderStatusPanel.setPreferredSize(new Dimension(1920 - 385, 580));
        orderStatusPanel.setLayout(new GridLayout(1, 2, 20, 0));
        JPanel leftContainer = new JPanel(new BorderLayout());
        leftContainer.setBackground(Color.WHITE);
        JLabel leftTitle = new JLabel("All Orders", SwingConstants.CENTER);
        leftTitle.setFont(new Font(PaletteView.font, Font.BOLD, 28));
        leftTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 120, 20));
        CustomScrollView scrollPane = new CustomScrollView(leftPanel, Color.LIGHT_GRAY, Color.DARK_GRAY);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        addLabelsFromObjects(OrderModel.getOrders(), leftPanel);
        leftContainer.add(leftTitle, BorderLayout.NORTH);
        leftContainer.add(scrollPane, BorderLayout.CENTER);
        JPanel rightContainer = new JPanel(new BorderLayout());
        rightContainer.setBackground(Color.WHITE);
        JLabel rightTitle = new JLabel("My Orders", SwingConstants.CENTER);
        rightTitle.setFont(new Font(PaletteView.font, Font.BOLD, 28));
        rightTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 120, 20));
        CustomScrollView scrollPane2 = new CustomScrollView(rightPanel, Color.LIGHT_GRAY, Color.DARK_GRAY);
        scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        addLabelsFromObjects2(OrderModel.getOrders(), rightPanel);
        rightContainer.add(rightTitle, BorderLayout.NORTH);
        rightContainer.add(scrollPane2, BorderLayout.CENTER);
        orderStatusPanel.add(leftContainer);
        orderStatusPanel.add(rightContainer);
        orderStatusPanel.setBackground(Color.LIGHT_GRAY);
    }

    public OrderStatusView(String user) {
        orderStatusPanel = new JPanel();
        orderStatusPanel.setBackground(Color.LIGHT_GRAY);
        orderStatusPanel.setPreferredSize(new Dimension(1920 - 385, 800));
        orderStatusPanel.setLayout(new GridLayout(1, 2, 20, 0));
        JPanel rightContainer = new JPanel(new BorderLayout());
        rightContainer.setBackground(Color.WHITE);
        JLabel rightTitle = new JLabel("My Orders", SwingConstants.CENTER);
        rightTitle.setFont(new Font(PaletteView.font, Font.BOLD, 28));
        rightTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JPanel rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 120, 20));
        rightPanel.setLayout(new GridLayout(0, 2, 20, 20));
        CustomScrollView scrollPane2 = new CustomScrollView(rightPanel, Color.LIGHT_GRAY, Color.DARK_GRAY);
        scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        addLabelsFromObjects3(OrderModel.getOrders(), rightPanel);
        for (int i = rightPanel.getComponentCount(); i < 3; i++) {
            JPanel emptyPanel = new JPanel();
            rightPanel.add(emptyPanel);
        }
        rightContainer.add(rightTitle, BorderLayout.NORTH);
        rightContainer.add(scrollPane2, BorderLayout.CENTER);
        orderStatusPanel.add(rightContainer);
        orderStatusPanel.setBackground(Color.LIGHT_GRAY);
    }

    public void addLabelsFromObjects(List<OrderModel> orders, JPanel panel) {
        for (int i = orders.size() - 1; i >= 0; i--) {
            OrderModel obj = orders.get(i);
            JPanel panel1 = addOrderLabelsToPanel(obj);
            panel.add(panel1);
            panel.add(Box.createVerticalStrut(10));
        }
        panel.revalidate();
        panel.repaint();
    }

    public void addLabelsFromObjects2(List<OrderModel> orders, JPanel panel) {
        for (int i = orders.size() - 1; i >= 0; i--) {
            OrderModel obj = orders.get(i);
            if (UserModel.getCurrentUser() != null && obj.getNameUser().equals(UserModel.getCurrentUser().getName())) {
                JPanel panel1 = addOrderLabelsToPanel(obj);
                panel.add(panel1);
                panel.add(Box.createVerticalStrut(10));
            }
        }
        panel.revalidate();
        panel.repaint();
    }

    public void addLabelsFromObjects3(List<OrderModel> orders, JPanel panel) {
        panel.setLayout(new GridLayout(0, 2, 20, 20));
        for (int i = orders.size() - 1; i >= 0; i--) {
            OrderModel obj = orders.get(i);
            if (UserModel.getCurrentUser() != null && obj.getNameUser().equals(UserModel.getCurrentUser().getName())) {
                JPanel panel1 = addOrderLabelsToPanel(obj);
                panel.add(panel1);
            }
        }
    }

    public JPanel addOrderLabelsToPanel(OrderModel order) {
        StringBuilder labelText = new StringBuilder("<html><body style='width:500px'>");
        labelText.append(String.format("<br>"));
        labelText.append(String.format("  Order ID: %s<br>", order != null ? order.getOrderID() : "N/A"));
        labelText.append(String.format("  Date: %s<br>", order.getDateOfOrder()));
        labelText.append(String.format("  Customer: %s<br>", order.getNameUser()));

        if (order instanceof DineInOrderModel) {
            labelText.append(" Order Type: DineIn Order<br>");
            labelText.append(String.format("  Table Number: %d<br>", ((DineInOrderModel) order).getTableNumber()));
        } else if (order instanceof DeliveryOrderModel) {
            labelText.append("  Order Type: Delivery Order<br>");
            labelText.append(String.format("  Address: %s<br>", ((DeliveryOrderModel) order).getAddress()));
        }

        labelText.append(String.format("  Meals Required: %d<br>", order.getOrderList().size()));
        labelText.append(" Order Details:<br>");
        for (MealModel m : order.getOrderList()) {
            labelText.append(String.format("  %s: %.2f$<br>", m.getMealName(), m.getMealPrice()));
        }

        if (order.getNotes() != null && !order.getNotes().isEmpty()) {
            labelText.append(String.format("  Notes: %s<br>", order.getNotes()));
        }

        labelText.append(String.format("  Tip: %.2f<br>", order.getTip()));
        labelText.append(String.format("    Total Price: %.2f$<br>", OrderSystemController.bill(order)));
        labelText.append(String.format(" Order Status: %s<br>", order.getStatus()));
        labelText.append("  </body></html>");

        JLabel label = new JLabel(labelText.toString(), SwingConstants.CENTER);
        label.setFont(new Font(PaletteView.font, Font.PLAIN, 24));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);

        JButton deleteButton = new JButton();
        ImageIcon deleteIcon = new ImageIcon("photos\\declined.png");
        Image img3 = deleteIcon.getImage();
        Image scaledImg3 = img3.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        deleteIcon = new ImageIcon(scaledImg3);
        deleteButton.setIcon(deleteIcon);

        deleteButton.setContentAreaFilled(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusPainted(false);

        deleteButton.addActionListener(e -> {
            order.cancelOrder();
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(PaletteView.AdditionalColor, 3)); // (238, 121, 50)
        panel.setBackground(PaletteView.BackGround);

        panel.add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public JPanel getOrderStatusPanel() {
        return orderStatusPanel;
    }

    public void updateView() {
        orderStatusPanel.removeAll();

        JPanel leftContainer = new JPanel(new BorderLayout());
        leftContainer.setBackground(Color.WHITE);
        JLabel leftTitle = new JLabel("All Orders", SwingConstants.CENTER);
        leftTitle.setFont(new Font(PaletteView.font, Font.BOLD, 28));
        leftTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 120, 20));
        CustomScrollView scrollPane = new CustomScrollView(leftPanel, Color.LIGHT_GRAY, Color.DARK_GRAY);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        addLabelsFromObjects(OrderModel.getOrders(), leftPanel);

        leftContainer.add(leftTitle, BorderLayout.NORTH);
        leftContainer.add(scrollPane, BorderLayout.CENTER);

        JPanel rightContainer = new JPanel(new BorderLayout());
        rightContainer.setBackground(Color.WHITE);
        JLabel rightTitle = new JLabel("My Orders", SwingConstants.CENTER);
        rightTitle.setFont(new Font(PaletteView.font, Font.BOLD, 28));
        rightTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 120, 20));
        CustomScrollView scrollPane2 = new CustomScrollView(rightPanel, Color.LIGHT_GRAY, Color.DARK_GRAY);
        scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        addLabelsFromObjects2(OrderModel.getOrders(), rightPanel);

        rightContainer.add(rightTitle, BorderLayout.NORTH);
        rightContainer.add(scrollPane2, BorderLayout.CENTER);

        orderStatusPanel.add(leftContainer);
        orderStatusPanel.add(rightContainer);

        orderStatusPanel.revalidate();
        orderStatusPanel.repaint();
    }

    public void updateView2() {
        orderStatusPanel.removeAll();

        JPanel rightContainer = new JPanel(new BorderLayout());
        rightContainer.setBackground(Color.WHITE);

        JLabel rightTitle = new JLabel("My Orders", SwingConstants.CENTER);
        rightTitle.setFont(new Font(PaletteView.font, Font.BOLD, 28));
        rightTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 120, 20));

        rightPanel.setLayout(new GridLayout(0, 2, 20, 20));

        CustomScrollView scrollPane2 = new CustomScrollView(rightPanel, Color.LIGHT_GRAY, Color.DARK_GRAY);
        scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        addLabelsFromObjects3(OrderModel.getOrders(), rightPanel);
        for (int i = rightPanel.getComponentCount(); i < 3; i++) {
            JPanel emptyPanel = new JPanel();
            rightPanel.add(emptyPanel);
        }
        rightContainer.add(rightTitle, BorderLayout.NORTH);
        rightContainer.add(scrollPane2, BorderLayout.CENTER);

        orderStatusPanel.add(rightContainer);

        orderStatusPanel.revalidate();
        orderStatusPanel.repaint();
    }

}
