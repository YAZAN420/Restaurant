package Controller;

import Model.*;
import View.CartView;
import View.MealPanel;
import View.ReportsAndOrderView;
import View.UserOrderView;

import java.util.*;

public class OrderSystemController {
    public static void createOrder(ArrayList<MealModel> meals, String choice, double tip, String notes, String address,
            int tableNumber) {
        ArrayList<MealModel> mealCopy = new ArrayList<>(meals);
        int preparationTime = 0;
        for (MealModel meal : meals) {
            preparationTime += meal.getMinutesNeeded() * 60;
        }
        @SuppressWarnings("unused")
        OrderModel order = null;
        String status = "Preparing";
        if (choice.equalsIgnoreCase("Delivery")) {
            order = new DeliveryOrderModel(mealCopy, tip, OrderModel.counter, status, true, address, notes,
                    preparationTime, UserModel.getCurrentUser().getName());

        } else {
            order = new DineInOrderModel(mealCopy, tip, OrderModel.counter, status, true, tableNumber, notes,
                    preparationTime, UserModel.getCurrentUser().getName());
        }
        ReportsAndOrderView.orderStatusView.updateView();
        UserOrderView.orderStatusView.updateView2();
        CartView.getCartListModel().clear();
        MealPanel.cart.clear();
    }

    public static double bill(OrderModel order) {
        double fullPrice = 0;
        for (MealModel m : order.getOrderList()) {
            fullPrice += m.getMealPrice();
        }
        return Math.round((fullPrice + order.getTip()) * 100.0) / 100.0;
    }

    public static void addListener(ArrayList<OrderStatusListenerController> listeners,
            OrderStatusListenerController listener) {
        listeners.add(listener);
    }

    public static void notifyListeners(ArrayList<OrderStatusListenerController> listeners, OrderModel order) {
        for (OrderStatusListenerController listener : listeners) {
            listener.onStatusChanged(order, order.getStatus());
        }
    }
}