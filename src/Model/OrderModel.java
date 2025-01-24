package Model;

import Controller.*;
import View.*;

import java.io.Serial;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public abstract class OrderModel implements Serializable {

    private static List<OrderModel> orders = new ArrayList<>();
    private final ArrayList<OrderStatusListenerController> listeners = new ArrayList<>();
    @Serial
    private static final long serialVersionUID = 1L;
    private ArrayList<MealModel> orderList;
    private String dateOfOrder;
    private String nameUser;
    private double tip;
    private final int orderID;
    private String status;
    private boolean isCancelable;
    private String notes;
    private int time;
    public static int counter;
    private transient OrderTimerController orderTimer;

    public OrderModel(ArrayList<MealModel> orderList, double tip, int orderID, String status, boolean isCancelable,
            String notes,
            int time, String nameUser) {
        this.nameUser = nameUser;
        this.dateOfOrder = String.valueOf(LocalDate.now());

        try {
            setOrderList(orderList);
            setTip(tip);
        } catch (CustomExceptionController e) {
            MainFrameView.notificationView.showNotification(e.getMessage(), true);
        }
        this.orderID = orderID;
        NotificationController notificationService = new NotificationController();
        OrderSystemController.addListener(this.getListeners(), notificationService);
        OrderStatusFileUpdaterController fileUpdater = new OrderStatusFileUpdaterController();
        OrderSystemController.addListener(this.getListeners(), fileUpdater);
        this.status = status;
        this.isCancelable = isCancelable;
        this.notes = notes;
        this.time = time;
        counter++;
        orders.add(this);
        OrderStorageController.saveOrdersToFile(orders);
        OrderSystemController.notifyListeners(listeners, this);
        this.orderTimer = new OrderTimerController(this);
        this.orderTimer.startOrderProcessing(time);
    }

    public void cancelOrder() {
        if (isCancelable) {
            try {
                orderTimer.cancelOrder();
                setStatus("Canceled");
                this.setCancelable(false);
            } catch (Exception e) {
                MainFrameView.notificationView.showNotification("Can not cancel this order", true);
            }
        } else {
            MainFrameView.notificationView.showNotification("Can not cancel this order", true);
        }
    }

    public void setStatus(String newStatus) {
        this.status = newStatus;
        OrderSystemController.notifyListeners(listeners, this);
    }

    public void setCancelable(boolean cancelable) {
        isCancelable = cancelable;
    }

    public void setOrderList(ArrayList<MealModel> orderList) throws CustomExceptionController {
        if (orderList != null && !orderList.isEmpty())
            this.orderList = orderList;
        else
            throw new CustomExceptionController("The order list is invalid or empty.");
    }

    public void setTip(double tip) throws CustomExceptionController {
        if (tip >= 0)
            this.tip = tip;
        else
            throw new CustomExceptionController("The tip amount must be a positive number.");
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public ArrayList<MealModel> getOrderList() {
        return orderList;
    }

    public double getTip() {
        return tip;
    }

    public static void setOrders(List<OrderModel> orders) {
        OrderModel.orders = orders;
    }

    public void setDateOfOrder(String dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getStatus() {
        return status;
    }

    public boolean isCancelable() {
        return isCancelable;
    }

    public String getNotes() {
        return notes;
    }

    public int getTime() {
        return time;
    }

    public ArrayList<OrderStatusListenerController> getListeners() {
        return listeners;
    }

    public static List<OrderModel> getOrders() {
        return orders;
    }

    public String getDateOfOrder() {
        return dateOfOrder;
    }

    public String getNameUser() {
        return this.nameUser;
    }
}
