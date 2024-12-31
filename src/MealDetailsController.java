import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MealDetailsController {
    private MealDetailsView view;
    private Meal meal;
    private ArrayList<Meal> cart;
    public MealDetailsController(Meal meal, ArrayList<Meal> cart) {
        this.meal = meal;
        this.cart = cart;
        view = new MealDetailsView(
                meal,
                this::handleAddMeal,
                this::handleDeleteMeal,
                this::handleBackAction
        );
    }
    private void handleAddMeal(ActionEvent e) {
        cart.add(meal);
        CartView.updateCartView(cart);
    }
    private void handleDeleteMeal(ActionEvent e) {
        if (cart.contains(meal)) {
            cart.remove(meal);
            CartView.updateCartView(cart);
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Meal not found in the cart!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
    private void handleBackAction(ActionEvent e) {
        view.getMainPanel().setVisible(false);
        MainPanel.menuContentPanel1.setVisible(true);
        MainPanel.sideCategoryPanel.getSideMenu().setVisible(true);
        MainPanel.mainContent.removeAll();
        MainPanel.mainContent.add(MainPanel.menuContentPanel1, BorderLayout.CENTER);
    }
    public MealDetailsView getView() {
        return view;
    }
}
