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
        Order o = null;
        try{
        o = OrderSystem.createOrder();}
        catch (CustomException e){
            System.out.println("k");
        }
        System.out.println("ok");
//        o.cancelOrder();
        System.out.println(o.getStatus());

    }
}