import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;
public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        MenuManager.setMenu(MenuHandle.loadMenu());
        MenuManager mg=new MenuManager();
        //mg.editFromMenu();
        //mg.addToMenu();
        OrderStorage.loadOrdersFromFile();
        //OrderStorage.clearFile();
        Order firstOrder = null;
        try {
            firstOrder = OrderSystem.createOrder();
            System.out.println("First order created successfully: " );
        } catch (CustomException e) {
            System.out.println("Error creating first order: " + e.getMessage());
        }

/*
        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Order secondOrder = null;
        try {
            secondOrder = OrderSystem.createOrder();
            System.out.println("Second order created successfully: " + secondOrder);
        } catch (CustomException e) {
            System.out.println("Error creating second order: " + e.getMessage());
        }

*/
    }
}