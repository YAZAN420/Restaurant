import java.io.IOException;
import java.util.Scanner;
public class Main {
    static Scanner scan = new Scanner(System.in);
    @SuppressWarnings("unused")
    public static void main(String[] args) throws IOException {
        MenuManager.setMenu(MenuHandle.loadMenu());//imp
        ReportHandle.loadReport();//imp
        ReportHandle.load2Map(ReportService.customerOrdersNumber, "CustomerOrdersNumber.txt");//imp
        ReportHandle.load2Map(Report.mealsCounter, "ReportMealsCounter.txt");//imp
        OrderStorage.loadOrdersFromFile();
        //OrderStorage.clearFile();
        Order firstOrder = null;
        try {
            firstOrder = OrderSystem.createOrder();
            System.out.println("First order created successfully: ");
        } catch (CustomException e) {
            System.out.println("Error creating first order: " + e.getMessage());
        }
        System.out.println();
        User.setUsers(User.loadUsers(Service.users));
        // try (Scanner s = new Scanner(System.in)) {
        //     try {
        //         System.out.println("1-Login  2-Register");
        //         int val = s.nextInt();
        //         switch (val) {
        //             case 1:
        //                 try {
        //                     new Login();
        //                     User.getCurrentUser().changeRole(User.findUserByName("Yazan232"), Role.EMPLOYEE);
        //                 } catch (IllegalArgumentException e) {
        //                     System.out.println(e.getMessage());
        //                 }
        //                 break;
        //             case 2:
        //                 try {
        //                     new Register();
        //                 } catch (IllegalArgumentException e) {
        //                     System.out.println(e.getMessage());
        //                 }
        //                 break;
        //             default:
        //                 System.out.println("Invalid Input");
        //                 break;
        //         }
        //     } catch (Exception e) {
        //         System.out.println(e);
        //     }
        // }
    }
}