import java.io.Serial;
import java.io.Serializable;

public class Notification implements OrderStatusListener, Serializable {
    private transient NotificationView notificationView = new NotificationView(400);
    @Serial
    private static final long serialVersionUID = 1L;
    @Override
    public void onStatusChanged(Order order, String newStatus) {
        notificationView.showNotification("Notification: Order " + order.getOrderID() +" status: " + newStatus);
    }
}
