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
    @SuppressWarnings("static-access")
    public static Order createOrder() throws CustomException {
        int choice,num,time=0;
        ArrayList<Meal> meals;
        String address,notes;
        double tip;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Select Order Type:");
            System.out.println("1. Dine In");
            System.out.println("2. Delivery");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number (1 or 2).");
                return null;
            }
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
                    time += meal.getMinutesNeeded()*60;
                } else {
                    System.out.println("Category or meal is not available ! ");
                }
                System.out.println("Do you want to remove meal? (yes/no)");
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("yes")) {
                    System.out.println("Enter Meal Name: ");
                    mealName = scanner.nextLine();
                    boolean mealRemoved = false;
                    for (Meal me : meals) {
                        if (me.getMealName().equalsIgnoreCase(mealName)) {
                            meals.remove(me);
                            mealRemoved = true;
                            break;
                        }
                    }
                    if (!mealRemoved) {
                        System.out.println("Meal not found in the order!");
                    }
                }
                System.out.println("Do you want to add another meal? (yes/no)");
                response = scanner.nextLine();
                if (response.equalsIgnoreCase("no")) {
                    if (choice == 1) {
                        System.out.println("Enter Table Number:");
                        try {
                            num = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid table number.");
                            return null;
                        }
                    } else {
                        System.out.println("Enter Your Address:");
                        address = scanner.nextLine();
                    }
                    addingMeals = false;
                }
            }
            System.out.println("Enter tip amount:");
            try {
                tip = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid tip amount.");
                return null;
            }
            System.out.println("Do you want to add notes? (yes/no) ");
            String q = scanner.nextLine();
            if (q.equals("yes")) {
                notes = scanner.nextLine();
            }
            else{
                notes = "no notes";
            }
        }
            Order order = null;
        String status = "Preparing";
        if (choice == 1) {
            try {
                order = new DineInOrder(meals, tip, Order.counter, status, true, num, notes, time);
            } catch (CustomException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else if (choice == 2) {
            try {
                order = new DeliveryOrder(meals, tip, Order.counter, status, true, address, notes, time);
            } catch (CustomException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid choice");
        }
        if (order != null){
            Notification notificationService = new Notification();
            addListener(order.getListeners(),notificationService);
            OrderStatusFileUpdater fileUpdater = new OrderStatusFileUpdater();
            addListener(order.getListeners(),fileUpdater);
            OrderStorage.writeOrderToFile(order, notes, num, address ,status);
            new ReportService().giveReportInfo(order.dateOfOrder,order.getOrderList(),bill(order)); //lujain
            //Order.numOfOrders++;
            ReportService.customerOrdersNumber.merge(User.getCurrentUser().getName(),1,Integer::sum);//lujain
            ReportHandle.save2Map(ReportService.customerOrdersNumber,"CustomerOrdersNumber.txt");//lujain
            ReportHandle.save2Map(ReportService.currentReport.mealsCounter,"ReportMealsCounter.txt");//lujain
            //ReportService.addMealsFromOrderToReport();//lujain
            List<Order> currentOrders = OrderStorage.loadOrdersFromFile();
            currentOrders.add(order);
            OrderStorage.saveOrdersToFile(currentOrders);}
        return order;
    }
}