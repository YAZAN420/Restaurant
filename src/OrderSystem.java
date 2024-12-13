package com.company;

import java.io.*;
import java.util.*;

public class OrderSystem {

    public static Order createOrder() throws CustomException, IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select Order Type:");
        System.out.println("1. Dine In");
        System.out.println("2. Delivery");
        int choice = scanner.nextInt();

        ArrayList<Meal> meals = new ArrayList<>();
        boolean addingMeals = true;
        int num = 0;
        String address = null;

        while (addingMeals) {
            System.out.println("Enter Meal Name:");
            String mealName = scanner.next();

            boolean found = false;
            for (Map.Entry<String, List<Meal>> entry : Meal.menu.entrySet()) {
                for (Meal m : entry.getValue()) {
                    if (mealName.equals(m.getMealName())) {
                        meals.add(m);
                        found = true;
                        //System.out.println(m.getMealName());
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
        double tip = scanner.nextDouble();
        System.out.println("Do you want to add notes? (yes/no) ");
        String q = scanner.next();
        scanner.nextLine();
        String notes = null;
        if (q.equals("yes")) {
            notes = scanner.nextLine();
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

        //file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("FullOrder.txt", true))) {
            writer.write("Order " + Order.counter);
            writer.newLine();
            if (order instanceof DineInOrder) {
                writer.write("Order Type: " + "DineIn Order");
                writer.newLine();
                writer.write("Table Number: " + num);
                writer.newLine();
                writer.write("Meals required : " + order.getOrderList().size());
                writer.newLine();
                writer.write("Order Details:");
                writer.newLine();
                for (Meal m : order.getOrderList()) {
                    writer.write(m.getMealName() + ": " + m.getMealPrice() + "$");
                    writer.newLine();
                }
            } else {
                writer.write("Order Type: " + "Delivery Order");
                writer.newLine();
                writer.write("Address: " + address);
                writer.newLine();
                writer.write("Meals required : " + Objects.requireNonNull(order).getOrderList().size());
                writer.newLine();
                writer.write("Order Details:");
                writer.newLine();
                for (Meal m : order.getOrderList()) {
                    writer.write(m.getMealName() + ": " + m.getMealPrice() + "$");
                    writer.newLine();
                }

            }
            if (notes != null)
                writer.write(notes);
            writer.newLine();
            writer.write("Tip: " + order.getTip());
            writer.newLine();
            writer.write("Total Price : " + order.bill() + "$");
            writer.newLine();
            writer.write("____________________");
            writer.newLine();

        } catch (Exception e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }

        return order;
    }
}
