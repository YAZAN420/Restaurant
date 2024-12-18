import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderStorage {
    public static void saveOrdersToFile(List<Order> orders) {
        String filePath = "orders.dat";
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            objectOutputStream.writeObject(orders);
            System.out.println("Orders have been saved to: " + filePath);
        } catch (IOException e) {
            System.out.println("Error while saving orders: " + e.getMessage());
        }
    }
    public static List<Order> loadOrdersFromFile() {
        String filePath = "orders.dat";
        List<Order> orders = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            orders = (List<Order>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while loading orders: " + e.getMessage());
        }
        return orders;
    }
    public static void updateOrderInFile(Order updatedOrder) {
        List<Order> orders = loadOrdersFromFile();
        for (Order order : orders) {
            if (order.getOrderID() == updatedOrder.getOrderID()) {
                orders.set(orders.indexOf(order), updatedOrder);
                break;
            }
        }
        saveOrdersToFile(orders);
    }
    public static void clearFile() {
        try (FileOutputStream fos = new FileOutputStream("orders.dat", false)) {

        } catch (IOException e) {
            System.out.println("Error while clearing the file: " + e.getMessage());
        }
    }
    public static void writeOrderToFile(Order order, String notes, int tableNumber, String address, String status) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("FullOrder.txt", true))) {
            writer.write("Order " + (order != null ? order.getOrderID() : 0));
            writer.newLine();
            if (order instanceof DineInOrder) {
                writer.write("Order Type: DineIn Order\n");
                writer.write("Table Number: " + tableNumber + "\n");
                writer.write("Meals required: " + order.getOrderList().size() + "\n");
                writer.write("Order Details:\n");
                for (Meal m : order.getOrderList()) {
                    writer.write(m.getMealName() + ": " + m.getMealPrice() + "$\n");
                }
            } else if (order instanceof DeliveryOrder) {
                writer.write("Order Type: Delivery Order\n");
                writer.write("Address: " + address + "\n");
                writer.write("Meals required: " + Objects.requireNonNull(order).getOrderList().size() + "\n");
                writer.write("Order Details:\n");
                for (Meal m : order.getOrderList()) {
                    writer.write(m.getMealName() + ": " + m.getMealPrice() + "$\n");
                }
            }
            if (order.getNotes() != null && !order.getNotes().isEmpty()) {
                writer.write("Notes: " + order.getNotes() + "\n");
            }
            writer.write("Tip: " + (order != null ? order.getTip() : 0) + "\n");
            writer.write("Total Price: " + (order != null ? order.bill() : 0) + "$\n");
            writer.write("Order Status: " + status + "\n");
            writer.write("____________________\n");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    public static void rewriteTxtFileFromObjects(String objectFilePath, String txtFilePath) {
        List<Order> orders = OrderStorage.loadOrdersFromFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath, false))) {
            for (Order order : orders) {
                writeOrderToFile(order,
                        order.getNotes(),
                        (order instanceof DineInOrder) ? ((DineInOrder) order).getTableNumber() : -1,
                        (order instanceof DeliveryOrder) ? ((DeliveryOrder) order).getAddress() : null,
                        order.getStatus());
            }
            System.out.println("File FullOrder.txt has been updated successfully.");
        } catch (IOException e) {
            System.out.println("Error while writing to the file: " + e.getMessage());
        }

    }
}
