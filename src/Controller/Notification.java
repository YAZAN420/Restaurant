import java.io.Serial;
import java.io.Serializable;

public class Notification implements OrderStatusListener, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Override
    public void onStatusChanged(Order order, String newStatus) {
        if(User.getCurrentUser() != null && User.getCurrentUser().getName().equals(order.getNameUser()))
        LoginRegisterView.notificationView.showNotification("Notification: Order " + order.getOrderID() +" status: " + newStatus, false);
    }
}


