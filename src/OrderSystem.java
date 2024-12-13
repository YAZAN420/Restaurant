import java.io.*;
import java.util.*;
public class OrderSystem {
    public static Order createOrder() throws CustomException, IOException {
        int choice,num;
        ArrayList<Meal> meals;
        String address,notes;
        double tip;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Select Order Type:");
            System.out.println("1. Dine In");
            System.out.println("2. Delivery");
            choice = scanner.nextInt();
            meals = new ArrayList<>();
            boolean addingMeals = true;
            num = 0;
            address = null;
            while (addingMeals) {
                System.out.println("Enter Meal Name:");
                String mealName = scanner.next();
                boolean found = false;
                for (Map.Entry<String, List<Meal>> entry : MenuManager.getMenu().entrySet()) {
                    for (Meal m : entry.getValue()) {
                        if (mealName.equals(m.getMealName())) {
                            meals.add(m);
                            found = true;
                            break;
                        }
                    }
                    if (found)
                        break;
                }
                System.out.println("Do you want to add another meal? (yes/no)");
                String response = scanner.next();
                if (response.equalsIgnoreCase("no")) {
                    if (choice == 1) {
                        System.out.println("Enter Table Number:");
                        num = scanner.nextInt();
                        if (num <= 0 || num > 150)
                            throw new CustomException("Error: Table number is wrong, please enter number between 1 to 150 ");
                    } else {
                        System.out.println("Enter Your Address:");
                        address = scanner.next();
                    }
                    addingMeals = false;
                }
            }
            System.out.println("Enter tip amount:");
            tip = scanner.nextDouble();
            System.out.println("Do you want to add notes? (yes/no) ");
            String q = scanner.next();
            scanner.nextLine();
            notes = null;
            if (q.equals("yes")) {
                notes = scanner.nextLine();
            }
        }
        Order order = null;
        if (choice == 1) {
            try {
                order = new DineInOrder(meals, tip, num);
            } catch (CustomException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else if (choice == 2) {
            try {
                order = new DeliveryOrder(meals, tip, address);
            } catch (CustomException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid choice");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("FullOrder.txt", true))) {
            writer.write("Order " + Order.counter);
            writer.newLine();
            if (order instanceof DineInOrder) {
                writer.write("Order Type: " + "DineIn Order \n");
                writer.write("Table Number: " + num+"\n");
                writer.write("Meals required : " + order.getOrderList().size()+"\n");
                writer.write("Order Details:"+"\n");
                for (Meal m : order.getOrderList()) {
                    writer.write(m.getMealName() + ": " + m.getMealPrice() + "$\n");
                }
            } else {
                writer.write("Order Type: " + "Delivery Order \n");
                writer.write("Address: " + address+"\n");
                writer.write("Meals required : " + Objects.requireNonNull(order).getOrderList().size()+"\n");
                writer.write("Order Details:\n");
                for (Meal m : order.getOrderList()) {
                    writer.write(m.getMealName() + ": " + m.getMealPrice() + "$\n");
                }
            }
            if (notes != null)
                writer.write(notes);
            writer.newLine();
            writer.write("Tip: " + order.getTip()+"\n");
            writer.write("Total Price : " + order.bill() + "$\n");
            writer.write("____________________\n");
        } catch (Exception e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
        return order;
    }
}