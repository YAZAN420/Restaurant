import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    static Scanner scan = new Scanner(System.in);
    public static ArrayList<Meal> cart = new ArrayList<>();
    //aa
    @SuppressWarnings("unused")
    public static void main(String[] args) throws IOException {
        MenuManager.setMenu(MenuHandle.loadMenu());//imp
        ReportHandle.loadReport();//imp
        ReportHandle.load2Map(ReportService.customerOrdersNumber, "CustomerOrdersNumber.txt");//imp
        ReportHandle.load2Map(Report.mealsCounter, "ReportMealsCounter.txt");//imp
        OrderStorage.loadOrdersFromFile();
        //OrderStorage.clearFile();
//        Order firstOrder = null;
//        try {
//            firstOrder = OrderSystem.createOrder();
//            System.out.println("First order created successfully: ");
//        } catch (CustomException e) {
//            System.out.println("Error creating first order: " + e.getMessage());
//        }
//        System.out.println();
//        User.setUsers(User.loadUsers(Service.users));
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

        MenuManager mg = new MenuManager();
        JFrame frame = new JFrame("See More .. ");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1920 , 1080);

        frame.setLayout(new FlowLayout());

        MealDetailsController con = new MealDetailsController(mg.callfindMeal("Desserts", "Chocolate lava cake"), cart);

        frame.add(con.getView().getMainPanel());


        JPanel mealPanel = con.getView().getMainPanel();
        mealPanel.setVisible(false);


        JButton b = new JButton("Show Meal Details");
        b.addActionListener(e -> {
            mealPanel.setVisible(true);
            frame.revalidate();
            frame.repaint();
        });

        frame.add(b);




        MealDetailsController con2 = new MealDetailsController(mg.callfindMeal("Drinks", "Mango smoothie"), cart);

        frame.add(con2.getView().getMainPanel());


        JPanel mealPanel2 = con2.getView().getMainPanel();
        mealPanel2.setVisible(false);


        JButton b2 = new JButton("Show Meal Details");
        b2.addActionListener(e -> {
            mealPanel2.setVisible(true);
            frame.revalidate();
            frame.repaint();
        });

        frame.add(b2);






        JButton call = new JButton("order");

        call.addActionListener(e -> {
            con.getView().enableButtons(true);
            con2.getView().enableButtons(true);
//            OrderSystem.manageCart(); not needed..
//            try {
//                OrderSystem.createOrder(cart);
//            } catch (CustomException ex) {
//                ex.printStackTrace();
//            }
        });

        frame.add(call);

        JPanel cartPanel = CartView.createCartPanel();
        JButton viewCartButton = new JButton("View Cart");
        frame.add(cartPanel);
        cartPanel.setVisible(false);

        viewCartButton.addActionListener(e -> {
            cartPanel.setSize(300 , 300);
            CartView.updateCartView(Main.cart);
            cartPanel.setVisible(true);
        });
        frame.add(viewCartButton);

        frame.setVisible(true);


        //صفي تزبيط ال con و مكان نحط فيه ال cartList و مكان نحط هاد الحكي فيه بدل ال main
        // و اكمال انشاء الطلب صحي و القدرة على الغاؤه

    }
}