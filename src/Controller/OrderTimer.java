import java.util.Timer;
import java.util.TimerTask;

class OrderTimer {
    private final Timer timer;
    private Order order;
    public OrderTimer(Order order) {
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
                if(User.getCurrentUser() != null)
                ReportService.customerOrdersNumber.merge(User.getCurrentUser().getName(), 1, Integer::sum);
                new ReportService().giveReportInfo(order.getDateOfOrder(), order.getOrderList(), OrderSystem.bill(order));
                ReportHandle.save2Map(ReportService.customerOrdersNumber, "CustomerOrdersNumber.txt");
                ReportHandle.save2Map(ReportService.currentReport.mealsCounter, "ReportMealsCounter.txt");
                Reports_and_OrderPanel.updateView();

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
