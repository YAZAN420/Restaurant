package View;

import Controller.MenuManagerController;
import Model.MealModel;
import Model.RoleModel;
import Model.UserModel;

import javax.swing.*;
import java.awt.*;

public class MenuContentView extends JPanel {
    static String selectedCategory = "Pizza";
    int row, height, empty_squares, col = 5;
    JPanel mealContainer;
    static CustomScrollView dsp;

    public MenuContentView(String selectedCategory) {
        setLayout(new BorderLayout());
        mealContainer = new JPanel();
        row = setCol();
        height = 400 * setCol();
        mealContainer.setPreferredSize(new Dimension(350, height));
        if (row == 1)
            row = 2;
        mealContainer.setLayout(new GridLayout(row, col, 5, 5));
        mealContainer.setBorder(BorderFactory.createEmptyBorder(40, 15, 35, 40));
        dsp = new CustomScrollView(mealContainer, PaletteView.MainColor, PaletteView.MainColor.darker());
        dsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        dsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(dsp, BorderLayout.CENTER);
        setSelectedCategory(selectedCategory);
    }

    public void createSquarePanels() {
        mealContainer.removeAll();
        mealContainer.setBackground(PaletteView.BackGround);

        if (selectedCategory != null) {
            java.util.List<MealModel> meals = MenuManagerController.getMenu().get(selectedCategory);
            if (meals == null)
                return;
            for (MealModel m : meals) {
                MealPanel mealPanel = new MealPanel(m);
                mealContainer.add(mealPanel);
            }
            empty_squares = row * col - meals.size();
            if (UserModel.getCurrentUser().getRole() != RoleModel.USER) {
                if (empty_squares > 0) {
                    empty_squares--;
                    mealContainer.add(new MealPanel("photos/add-item-grey1.png"));
                }
            }
            for (int i = 0; i < empty_squares; i++) {
                mealContainer.add(new MealPanel());
            }

        }
        revalidate();
        repaint();
    }

    @SuppressWarnings("static-access")
    public void setSelectedCategory(String category) {
        this.selectedCategory = category;
        row = setCol();
        if (row == 1)
            row = 2;
        height = 400 * setCol();
        mealContainer.setPreferredSize(new Dimension(350, height));
        mealContainer.setLayout(new GridLayout(row, col, 10, 10));
        createSquarePanels();
    }

    public static String getSelectedCategory() {
        return selectedCategory;
    }

    public int setCol() {
        if (selectedCategory == null || (!MenuManagerController.getMenu().containsKey(selectedCategory)))
            return 1;
        int size = MenuManagerController.getMenu().get(selectedCategory).size();
        if (UserModel.getCurrentUser().getRole() != RoleModel.USER)
            size += 1;
        int res = size / col;
        if (size % col != 0)
            res++;
        return res;
    }
}
