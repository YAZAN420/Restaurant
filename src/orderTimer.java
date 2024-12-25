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
            @Override
            public void run() {
                order.setCancelable(false);
                order.setStatus("Prepared");
            }
        }, timeInSeconds * 1000L ); //BACK

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                order.setCancelable(false);
                order.setStatus("Delivered");
            }
        }, (timeInSeconds * 1000L) + 10000); //BACK
    }

    public void cancelOrder() {
        timer.cancel();
    }
}
