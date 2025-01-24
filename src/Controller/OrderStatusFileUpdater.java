import java.io.Serial;
import java.io.Serializable;

public class OrderStatusFileUpdater implements OrderStatusListener, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Override
    public void onStatusChanged(Order order, String newStatus) {
        OrderStorage.updateOrderInFile(order);
        OrderStorage.rewriteTxtFileFromObjects("orders.dat", "FullOrder.txt");
        UserOrderPanel.orderStatusView.updateView2();
        Reports_and_OrderPanel.orderStatusView.updateView();

    }
}
