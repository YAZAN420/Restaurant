import java.io.Serial;
import java.io.Serializable;

public class Notification implements OrderStatusListener, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Override
    public void onStatusChanged(Order order, String newStatus) {
        System.out.println("Notification: Order " + order.getOrderID() +" status: " + newStatus);
    }
}
