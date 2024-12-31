import java.util.ArrayList;

public class Main {
    public static ArrayList<Meal> cart = new ArrayList<>();
    public static void main(String[] args){
         MenuManager.setMenu(MenuHandle.loadMenu());
        ReportHandle.loadReport();
        ReportHandle.load2Map(ReportService.customerOrdersNumber, "CustomerOrdersNumber.txt");
        ReportHandle.load2Map(Report.mealsCounter, "ReportMealsCounter.txt");
        Order.setOrders(OrderStorage.loadOrdersFromFile());
        new LoginRegisterView();
        //comment :)
    }
}