
// delete all run it from REGISTERlOGINVIEW
//import javax.swing.*;
// import java.awt.*;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Scanner;
// public class Main {
//     static Scanner scan = new Scanner(System.in);
//     public static ArrayList<Meal> cart = new ArrayList<>();
//     public static void main(String[] args) throws IOException {
//         MenuManager.setMenu(MenuHandle.loadMenu());
//         ReportHandle.loadReport();
//         ReportHandle.load2Map(ReportService.customerOrdersNumber, "CustomerOrdersNumber.txt");
//         ReportHandle.load2Map(Report.mealsCounter, "ReportMealsCounter.txt");
//         OrderStorage.loadOrdersFromFile();
//         MenuManager mg = new MenuManager();  
//         JFrame frame = new JFrame("See More .. ");  
//         frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
//         frame.setSize(1920 , 1080);  
//         frame.setLayout(new FlowLayout());  
//         MealDetailsController con = new MealDetailsController(mg.callfindMeal("Soups", "Miso Soup"), cart); 
//         frame.add(con.getView().getMainPanel());  
//         JPanel mealPanel = con.getView().getMainPanel();
//         mealPanel.setVisible(false); 
//         JButton b = new JButton("Show Meal Details");
//         b.addActionListener(e -> {
//             mealPanel.setVisible(true);
//             frame.revalidate();
//             frame.repaint();
//         });
//         frame.add(b);
//         MealDetailsController con2 = new MealDetailsController(mg.callfindMeal("Pasta", "Baked Ziti"), cart);
//         frame.add(con2.getView().getMainPanel());
//         JPanel mealPanel2 = con2.getView().getMainPanel();
//         mealPanel2.setVisible(false); 

//         JButton b2 = new JButton("Show Meal Details"); 
//         b2.addActionListener(e -> {
//             mealPanel2.setVisible(true);
//             frame.revalidate();
//             frame.repaint();
//         });
//         frame.add(b2);
//         JButton call = new JButton("order"); 
//         call.addActionListener(e -> {
//             con.getView().enableButtons(true);
//             con2.getView().enableButtons(true);
//         });
//         frame.add(call);
//         JPanel cartPanel = CartView.createCartPanel();
//         JButton viewCartButton = new JButton("View Cart");
//         frame.add(cartPanel);
//         cartPanel.setVisible(false);
//         viewCartButton.addActionListener(e -> {  //me
//             cartPanel.setSize(300 , 300);
//             CartView.updateCartView(Main.cart);
//             cartPanel.setVisible(true);
//         });
//         frame.add(viewCartButton);
//         frame.setVisible(true);
//     }
// }