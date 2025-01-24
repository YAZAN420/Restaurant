import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class MealPanel extends JPanel {
    static Image image;
    public static ArrayList<Meal> cart = new ArrayList<>();
    public MealPanel(){
        setBackground(Palette.BackGround);
        setPreferredSize(new Dimension(330,500));
        setLayout(new BorderLayout());
    }
    @SuppressWarnings("static-access")
    public MealPanel(String emptyImagePath) {
        setBackground(Palette.BackGround);
        setPreferredSize(new Dimension(330, 500));
        setLayout(new BorderLayout());
        image = new ImageIcon(emptyImagePath).getImage().getScaledInstance(280, 320, Image.SCALE_SMOOTH);
        JLabel imge = new JLabel(new ImageIcon(image), SwingConstants.CENTER);
        RoundButton b = new RoundButton(12);
        b.setLayout(new BorderLayout());
        b.add(imge, BorderLayout.NORTH);
        b.setBackground(Palette.BackGround);
        b.setBorder(BorderFactory.createLineBorder(Color.WHITE, 20));
        b.addActionListener(e -> {
            MainPanel.sideCategoryPanel.getSideMenu().setVisible(false);
            MainPanel.mainContent.removeAll();
            String cat = MainPanel.menuContentPanel1.getSelectedCategory();
            JPanel m = new AddMealPanel(cat).getMainPanel();
            MainPanel.mainContent.add(m);
        });
        setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 0));
        add(b, BorderLayout.NORTH);
    }
    public MealPanel(Meal meal){
        setBackground(Palette.BackGround);
        setPreferredSize(new Dimension(330,500));
        setLayout(new BorderLayout());
        String Meal_path = new MenuManager().callfindMeal(MenuContentJPanel.selectedCategory, meal.getMealName())
                .getImagePath();
        image = new ImageIcon(Meal_path).getImage().getScaledInstance(217,280,Image.SCALE_SMOOTH);//217 294
        JLabel imge = new JLabel( new ImageIcon(image),SwingConstants.CENTER);
        JLabel text  = new JLabel(meal.getMealName(),SwingConstants.CENTER);
        text.setFont(new Font("Georgia", Font.BOLD, 16));
        text.setForeground(Palette.BackGround);
        text.setBorder(BorderFactory.createEmptyBorder(2,2,5,2));
        RoundButton b = new RoundButton(12);
        b.setPreferredSize(new Dimension(290,313));
        b.setLayout(new BorderLayout());
        b.add(imge,BorderLayout.CENTER);
        b.setBackground(Palette.AdditionalColor);
//        b.setBorder(BorderFactory.createLineBorder(new Color(85, 107, 47), 6));
        b.add(text,BorderLayout.AFTER_LAST_LINE);
        b.addActionListener(e -> {
            MainPanel.mainContent.removeAll();
            MainPanel.sideCategoryPanel.getSideMenu().setVisible(false);
            MealDetailsController con = new MealDetailsController(meal);
            MainPanel.mainContent.add(con.getView().getMainPanel(), BorderLayout.CENTER);
        });
        setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 0));//30 20 10 0
        add(b,BorderLayout.NORTH);
    }
}

