import Controller.*;
import Model.*;
import View.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        UserModel.setUsers(UserModel.loadUsers(new File("txtFiles/users.txt")));
        MenuManagerController.setMenu(MenuHandleController.loadMenu());
        ReportHandleController.loadReport();
        ReportHandleController.load2Map(ReportServiceController.customerOrdersNumber,
                "txtFiles/CustomerOrdersNumber.txt");
        ReportHandleController.load2Map(ReportModel.mealsCounter, "txtFiles/ReportMealsCounter.txt");
        OrderModel.setOrders(OrderStorageController.loadOrdersFromFile());
        if (!OrderModel.getOrders().isEmpty())
            OrderModel.counter = OrderModel.getOrders().get(OrderModel.getOrders().size() - 1).getOrderID() + 1;
        else
            OrderModel.counter = 1;
        new MainFrameView();
    }
}