package Controller;

import Model.OrderModel;
import Model.UserModel;
import View.MainFrameView;

import java.io.Serial;
import java.io.Serializable;

public class NotificationController implements OrderStatusListenerController, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void onStatusChanged(OrderModel order, String newStatus) {
        if (UserModel.getCurrentUser() != null && UserModel.getCurrentUser().getName().equals(order.getNameUser()))
            MainFrameView.notificationView
                    .showNotification("Notification: Order " + order.getOrderID() + " status: " + newStatus, false);
    }
}
