package Controller;

import Model.OrderModel;
import View.ReportsAndOrderView;
import View.UserOrderView;

import java.io.Serial;
import java.io.Serializable;

public class OrderStatusFileUpdaterController implements OrderStatusListenerController, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void onStatusChanged(OrderModel order, String newStatus) {
        OrderStorageController.updateOrderInFile(order);
        OrderStorageController.rewriteTxtFileFromObjects("txtFiles/orders.dat", "txtFiles/FullOrder.txt");
        UserOrderView.orderStatusView.updateView2();
        ReportsAndOrderView.orderStatusView.updateView();

    }
}
