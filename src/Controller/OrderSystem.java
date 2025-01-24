import View.CartView;

import java.util.*;
public class OrderSystem {
    public static void createOrder(ArrayList<Meal> meals, String choice, double tip, String notes, String address, int tableNumber) {
        ArrayList<Meal> mealCopy = new ArrayList<>(meals);
        int preparationTime = 0;
        for (Meal meal : meals) {
            preparationTime += meal.getMinutesNeeded() * 60;
        }
        Order order = null;
        String status = "Preparing";
        if (choice.equalsIgnoreCase("Delivery")) {
            order = new DeliveryOrder(mealCopy, tip, Order.counter, status, true, address, notes, preparationTime, User.getCurrentUser().getName());

        } else {
            order = new DineInOrder(mealCopy, tip, Order.counter, status, true, tableNumber, notes, preparationTime, User.getCurrentUser().getName());
        }
        OrderStorage.writeOrderToFile(order, notes, tableNumber, address, status);
        Reports_and_OrderPanel.orderStatusView.updateView(); 
        UserOrderPanel.orderStatusView.updateView2();
        CartView.getCartListModel().clear();
        MealPanel.cart.clear();
    }
    public static double bill(Order order) {
        double fullPrice = 0;
        for (Meal m : order.getOrderList()) {
            fullPrice += m.getMealPrice();
        }
        return Math.round((fullPrice + order.getTip()) * 100.0) / 100.0;
    }

    public static void addListener(ArrayList<OrderStatusListener> listeners, OrderStatusListener listener) {
        listeners.add(listener);
    }

    public static void notifyListeners(ArrayList<OrderStatusListener> listeners, Order order) {
        for (OrderStatusListener listener : listeners) {
            listener.onStatusChanged(order, order.getStatus());
        }
    }
}