import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MealPanel extends JPanel {
    static Image image;
    public static ArrayList<Meal> cart = new ArrayList<>();
    public MealPanel(){
        setPreferredSize(new Dimension(200,100));
        setLayout(new BorderLayout());
    }
    public MealPanel(Meal meal){
        setPreferredSize(new Dimension(200,100));
        setLayout(new BorderLayout());
        String Meal_path= new MenuManager().callfindMeal(MenuContentJPanel.selectedCategory,meal.getMealName()).getImagePath();
        image = new ImageIcon(Meal_path).getImage().getScaledInstance(280,230,Image.SCALE_SMOOTH);;
        JLabel imge = new JLabel( new ImageIcon(image),SwingConstants.CENTER);
        JLabel text  = new JLabel(meal.getMealName(),SwingConstants.CENTER);
        text.setFont(new Font("Georgia", Font.BOLD, 16));
        text.setForeground(new Color(245, 245, 220));
        RoundButton b = new RoundButton(12);
        b.setLayout(new BorderLayout());
        b.add(imge,BorderLayout.NORTH);
        b.setBackground(new Color(85, 107, 47));
        b.setBorder(BorderFactory.createLineBorder(new Color(85, 107, 47), 3));
        b.add(text,BorderLayout.SOUTH);
        b.addActionListener(e -> {
            MainPanel.mainContent.removeAll();
            MainPanel.sideCategoryPanel.getSideMenu().setVisible(false);
            MealDetailsController con = new MealDetailsController(meal, cart);
            MainPanel.mainContent.add(con.getView().getMainPanel(), BorderLayout.CENTER);
        });
        setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 5)); 
        add(b,BorderLayout.NORTH);
    }
}

