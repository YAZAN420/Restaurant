import java.io.IOException;
import java.util.Scanner;
public class Main {
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        MenuManager.setMenu(MenuHandle.loadMenu());
        MenuManager mg=new MenuManager();
        mg.editFromMenu();
        mg.addToMenu();
    }
}