package View;

import Controller.MealDetailsController;
import Controller.MenuManagerController;
import Model.MealModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MealPanel extends JPanel {
    static Image image;
    public static ArrayList<MealModel> cart = new ArrayList<>();

    public MealPanel() {
        setBackground(PaletteView.BackGround);
        setPreferredSize(new Dimension(330, 500));
        setLayout(new BorderLayout());
    }

    @SuppressWarnings("static-access")
    public MealPanel(String emptyImagePath) {
        setBackground(PaletteView.BackGround);
        setPreferredSize(new Dimension(330, 500));
        setLayout(new BorderLayout());
        image = new ImageIcon(emptyImagePath).getImage().getScaledInstance(280, 320, Image.SCALE_SMOOTH);
        JLabel imge = new JLabel(new ImageIcon(image), SwingConstants.CENTER);
        RoundButtonView b = new RoundButtonView(12, false);
        b.setLayout(new BorderLayout());
        b.add(imge, BorderLayout.NORTH);
        b.setBackground(PaletteView.BackGround);
        b.setBorder(BorderFactory.createLineBorder(Color.WHITE, 20));
        b.addActionListener(e -> {
            MainView.sideCategoryPanel.getSideMenu().setVisible(false);
            MainView.mainContent.removeAll();
            String cat = MainView.menuContentPanel1.getSelectedCategory();
            JPanel m = new AddMealView(cat).getMainPanel();
            MainView.mainContent.add(m);
        });
        setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 0));
        add(b, BorderLayout.NORTH);
    }

    public MealPanel(MealModel meal) {
        setBackground(PaletteView.BackGround);
        setPreferredSize(new Dimension(330, 500));
        setLayout(new BorderLayout());
        String Meal_path = new MenuManagerController()
                .callfindMeal(MenuContentView.selectedCategory, meal.getMealName())
                .getImagePath();
        image = new ImageIcon(Meal_path).getImage().getScaledInstance(217, 280, Image.SCALE_SMOOTH);
        JLabel imge = new JLabel(new ImageIcon(image), SwingConstants.CENTER);
        JLabel text = new JLabel(meal.getMealName(), SwingConstants.CENTER);
        text.setFont(new Font(PaletteView.font, Font.BOLD, 16));
        text.setForeground(PaletteView.BackGround);
        text.setBorder(BorderFactory.createEmptyBorder(2, 2, 5, 2));
        RoundButtonView b = new RoundButtonView(12, true);
        b.setPreferredSize(new Dimension(290, 313));
        b.setLayout(new BorderLayout());
        b.add(imge, BorderLayout.CENTER);
        b.setBackground(PaletteView.AdditionalColor);
        b.add(text, BorderLayout.AFTER_LAST_LINE);
        b.addActionListener(e -> {
            MainView.mainContent.removeAll();
            MainView.sideCategoryPanel.getSideMenu().setVisible(false);
            MealDetailsController con = new MealDetailsController(meal);
            MainView.mainContent.add(con.getView().getMainPanel(), BorderLayout.CENTER);
        });
        setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 0));
        add(b, BorderLayout.NORTH);
    }
}
