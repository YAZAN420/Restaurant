package Controller;

import Model.DeliveryOrderModel;
import Model.DineInOrderModel;
import Model.MealModel;
import Model.OrderModel;
import View.ReportsAndOrderView;
import View.UserOrderView;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderStorageController {
    public static void saveOrdersToFile(List<OrderModel> orders) {
        String filePath = "txtFiles/orders.dat";
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            objectOutputStream.writeObject(orders);
            OrderStorageController.rewriteTxtFileFromObjects("txtFiles/orders.dat", "txtFiles/FullOrder.txt");
        } catch (IOException e) {
            System.out.println("Error while saving orders: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<OrderModel> loadOrdersFromFile() {
        String filePath = "txtFiles/orders.dat";
        List<OrderModel> orders = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            orders = (List<OrderModel>) objectInputStream.readObject();
            orders.removeIf(order -> order.getStatus().equals("Preparing") ||
                    order.getStatus().equals("Prepared"));
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while loading orders: " + e.getMessage());
        }
        return orders;
    }

    public static void updateOrderInFile(OrderModel updatedOrder) {
        List<OrderModel> orders = OrderModel.getOrders();
        for (OrderModel order : orders) {
            if (order.getOrderID() == updatedOrder.getOrderID()) {
                orders.set(orders.indexOf(order), updatedOrder);
                break;
            }
        }
        saveOrdersToFile(orders);
    }

    public static void updateNameInOrders(String oldName, String newName) {
        for (OrderModel order : OrderModel.getOrders()) {
            if (order.getNameUser().equals(oldName)) {
                order.setNameUser(newName);
            }
        }
        saveOrdersToFile(OrderModel.getOrders());
        UserOrderView.orderStatusView.updateView2();
        ReportsAndOrderView.orderStatusView.updateView();
    }

    public static void clearFile() {
        try (FileOutputStream fos = new FileOutputStream("txtFiles/orders.dat", false)) {
        } catch (IOException e) {
            System.out.println("Error while clearing the file: " + e.getMessage());
        }
    }

    public static void writeOrderToFile(OrderModel order, String notes, int tableNumber, String address,
            String status) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("txtFiles/FullOrder.txt", true))) {
            writer.write("Order " + (order != null ? order.getOrderID() : 0) + "\n");
            order.setDateOfOrder(String.valueOf(LocalDate.now()));
            writer.write(order.getDateOfOrder() + "\n");
            writer.write(order.getNameUser() + "\n");
            if (order instanceof DineInOrderModel) {
                writer.write("Order Type: DineIn Order\n");
                writer.write("Table Number: " + tableNumber + "\n");
                writer.write("Meals required: " + order.getOrderList().size() + "\n");
                writer.write("Order Details:\n");
                for (MealModel m : order.getOrderList()) {
                    writer.write(m.getMealName() + ": " + m.getMealPrice() + "$\n");
                }
            } else if (order instanceof DeliveryOrderModel) {
                writer.write("Order Type: Delivery Order\n");
                writer.write("Address: " + address + "\n");
                writer.write("Meals required: " + Objects.requireNonNull(order).getOrderList().size() + "\n");
                writer.write("Order Details:\n");
                for (MealModel m : order.getOrderList()) {
                    writer.write(m.getMealName() + ": " + m.getMealPrice() + "$\n");
                }
            }
            if (order.getNotes() != null && !order.getNotes().isEmpty()) {
                writer.write("Notes: " + order.getNotes() + "\n");
            }
            writer.write("Tip: " + order.getTip() + "\n");
            writer.write("Total Price: " + OrderSystemController.bill(order) + "$\n");
            writer.write("Order Status: " + status + "\n");
            writer.write("____________________\n");

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    public static void rewriteTxtFileFromObjects(String objectFilePath, String txtFilePath) {
        List<OrderModel> orders = OrderModel.getOrders();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath, false))) {
        } catch (IOException e) {
            System.out.println("Error while clearing the file: " + e.getMessage());
        }
        for (OrderModel order : orders) {
            writeOrderToFile(order,
                    order.getNotes(),
                    (order instanceof DineInOrderModel) ? ((DineInOrderModel) order).getTableNumber() : -1,
                    (order instanceof DeliveryOrderModel) ? ((DeliveryOrderModel) order).getAddress() : null,
                    order.getStatus());
        }
    }
}
