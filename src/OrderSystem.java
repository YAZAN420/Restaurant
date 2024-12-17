import java.io.*;
import java.util.*;
public class OrderSystem {
    public static Order createOrder() throws CustomException {
        int choice,num;
        ArrayList<Meal> meals;
        String address,notes;
        double tip;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Select Order Type:");
            System.out.println("1. Dine In");
            System.out.println("2. Delivery");
            choice = scanner.nextInt();
            scanner.nextLine();
            meals = new ArrayList<>();
            boolean addingMeals = true;
            num = 0;
            address = null;
            while (addingMeals) {
                System.out.println("Enter Meal Category: ");
                String mealCategory = scanner.nextLine();
                System.out.println("Enter Meal Name: ");
                String mealName = scanner.nextLine();
                MenuManager m = new MenuManager();
                Meal meal = m.callfindMeal(mealCategory, mealName);
                if (meal != null) {
                    meals.add(meal);
                } else {
                    System.out.println("Category or meal is not available ! ");
                }
                System.out.println("Do you want to add another meal? (yes/no)");
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("no")) {
                    if (choice == 1) {
                        System.out.println("Enter Table Number:");
                        num = scanner.nextInt();
                        scanner.nextLine();
                    } else {
                        System.out.println("Enter Your Address:");
                        address = scanner.nextLine();
                    }
                    addingMeals = false;
                }
            }
            System.out.println("Enter tip amount:");
            tip = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Do you want to add notes? (yes/no) ");
            String q = scanner.nextLine();
            notes = null;
            if (q.equals("yes")) {
                notes = scanner.nextLine();
            }
        }
        Order order = null;
        String status = "Preparing";
        if (choice == 1) {
            try {
                order = new DineInOrder(meals, tip, Order.counter, status, true, num );
            } catch (CustomException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else if (choice == 2) {
            try {
                order = new DeliveryOrder(meals, tip, Order.counter, status, true, address);
            } catch (CustomException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid choice");
        }
        Notification notificationService = new Notification();
        if (order != null){
            order.addListener(notificationService);
            writeOrderToFile(order, notes, num, address ,status);}
        return order;
    }
    private static void writeOrderToFile(Order order, String notes, int tableNumber, String address, String status) {
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
            if (notes != null) {
                writer.write("Notes: " + notes + "\n");
            }
            writer.write("Tip: " + (order != null ? order.getTip() : 0) + "\n");
            writer.write("Total Price: " + (order != null ? order.bill() : 0) + "$\n");
            writer.write("Order Status: " + status + "\n");
            writer.write("____________________\n");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

}