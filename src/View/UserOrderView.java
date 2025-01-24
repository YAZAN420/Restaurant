package View;

import javax.swing.*;
import java.awt.*;

public class UserOrderView {
    public JPanel OrderPanel;
    public static OrderStatusView orderStatusView = new OrderStatusView("a");

    public UserOrderView() {
        OrderPanel = new JPanel(new BorderLayout());
        OrderPanel.setBackground(Color.LIGHT_GRAY);
        OrderPanel.setLayout(new FlowLayout());
        OrderPanel.add(orderStatusView.orderStatusPanel);
    }

    public JPanel getOrderPanel() {
        return OrderPanel;
    }
}
