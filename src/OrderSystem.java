import java.util.*;
public class OrderSystem {
    public static double bill(Order order){
        double fullPrice = 0;
        for(Meal m : order.getOrderList()){
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


    public static Order createOrder(ArrayList<Meal> meals) throws CustomException {
        Scanner scanner = new Scanner(System.in);
        int choice, tableNumber = 0, preparationTime = 0;
        String address = null, notes = "No notes";
        double tip;

        for (Meal meal : meals) {
            preparationTime += meal.getMinutesNeeded() * 60;
        }

        System.out.println("Select Order Type:");
        System.out.println("1. Dine In");
        System.out.println("2. Delivery");
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number (1 or 2).");
            return null;
        }

        if (choice == 1) {
            System.out.println("Enter Table Number:");
            try {
                tableNumber = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid table number. Try again.");
                return null;
            }
        } else if (choice == 2) {
            System.out.println("Enter Your Address:");
            address = scanner.nextLine();
        }

        System.out.println("Enter Tip Amount:");
        try {
            tip = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid tip amount. Try again.");
            return null;
        }

        System.out.println("Do you want to add notes? (yes/no)");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            System.out.println("Enter your notes:");
            notes = scanner.nextLine();
        }

        Order order = null;
        String status = "Preparing";
        if (choice == 1) {
            order = new DineInOrder(meals, tip, Order.counter, status, true, tableNumber, notes, preparationTime);
        } else if (choice == 2) {
            order = new DeliveryOrder(meals, tip, Order.counter, status, true, address, notes, preparationTime);
        } else {
            System.out.println("Invalid choice.");
            return null;
        }

        Notification notificationService = new Notification();
        addListener(order.getListeners(), notificationService);

        OrderStatusFileUpdater fileUpdater = new OrderStatusFileUpdater();
        addListener(order.getListeners(), fileUpdater);

        OrderStorage.writeOrderToFile(order, notes, tableNumber, address, status);
        new ReportService().giveReportInfo(order.dateOfOrder, order.getOrderList(), bill(order));
        ReportService.customerOrdersNumber.merge(User.getCurrentUser().getName(), 1, Integer::sum);
        ReportHandle.save2Map(ReportService.customerOrdersNumber, "CustomerOrdersNumber.txt");
        ReportHandle.save2Map(ReportService.currentReport.mealsCounter, "ReportMealsCounter.txt");

        List<Order> currentOrders = OrderStorage.loadOrdersFromFile();
        currentOrders.add(order);
        OrderStorage.saveOrdersToFile(currentOrders);

        return order;
    }

}