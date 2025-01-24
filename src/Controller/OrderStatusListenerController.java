package Controller;

import Model.OrderModel;

public interface OrderStatusListenerController {
    void onStatusChanged(OrderModel order, String newStatus);
}
