public interface OrderStatusListener {
    void onStatusChanged(Order order, String newStatus);
}
