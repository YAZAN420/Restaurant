public class Notification implements OrderStatusListener{
    @Override
    public void onStatusChanged(Order order, String newStatus) {
        System.out.println("Notification: Order " + order.getOrderID() +" status: " + newStatus);
    }
}
