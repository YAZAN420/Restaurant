package Controller;

import Model.OrderModel;
import Model.UserModel;
import View.ReportsAndOrderView;

import java.util.Timer;
import java.util.TimerTask;

public class OrderTimerController {
    private final Timer timer;
    private OrderModel order;

    public OrderTimerController(OrderModel order) {
        this.order = order;
        this.timer = new Timer();
    }

    public void startOrderProcessing(int timeInSeconds) {
        timer.schedule(new TimerTask() {
            @SuppressWarnings("static-access")
            @Override
            public void run() {
                order.setCancelable(false);
                order.setStatus("Prepared");
                if (UserModel.getCurrentUser() != null)
                    ReportServiceController.customerOrdersNumber.merge(UserModel.getCurrentUser().getName(), 1,
                            Integer::sum);
                new ReportServiceController().giveReportInfo(order.getDateOfOrder(), order.getOrderList(),
                        OrderSystemController.bill(order));
                ReportHandleController.save2Map(ReportServiceController.customerOrdersNumber,
                        "txtFiles/CustomerOrdersNumber.txt");
                ReportHandleController.save2Map(ReportServiceController.currentReport.mealsCounter,
                        "txtFiles/ReportMealsCounter.txt");
                ReportsAndOrderView.updateView();

            }
        }, timeInSeconds * 1000L);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                order.setCancelable(false);
                order.setStatus("Delivered");
            }
        }, (timeInSeconds * 1000L) + 10000);
    }

    public void cancelOrder() {
        timer.cancel();
    }
}
