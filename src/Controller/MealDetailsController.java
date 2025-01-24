package Controller;

import Model.MealModel;
import View.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

public class MealDetailsController {
    private MealDetailsView view;
    private MealModel meal;
    EditMealView editpanel;
    MenuManagerController mg = new MenuManagerController();

    public MealDetailsController(MealModel meal) {
        this.meal = meal;
        editpanel = new EditMealView(meal);
        view = new MealDetailsView(
                meal,
                this::handleAddMeal,
                this::handleDeleteMeal,
                this::handleBackAction,
                this::handleRemoveMeal,
                this::handleEditMeal);

    }

    private void handleAddMeal(ActionEvent e) {
        MealPanel.cart.add(meal);
        CartView.updateCartView(MealPanel.cart);
    }

    private void handleDeleteMeal(ActionEvent e) {
        if (MealPanel.cart.contains(meal)) {
            MealPanel.cart.remove(meal);
            CartView.updateCartView(MealPanel.cart);
            if (CartView.orderPanel != null && CartView.orderPanel.isVisible())
                CustomerOrderDialogConView.a.updatePanelVisibility();
        } else {
            MainFrameView.notificationView.showNotification("Meal not found in the cart!", true);
        }
    }

    private void handleRemoveMeal(ActionEvent e) {
        mg.deleteFromMenu(meal.getCategory(), meal.getMealName());
        MainView.mainContent.removeAll();
        MainView.mainContent.add(MainView.menuContentPanel1, BorderLayout.CENTER);
        MainView.menuContentPanel1.setSelectedCategory(meal.getCategory());
        MainView.sideCategoryPanel.getSideMenu().setVisible(true);
        MainView.mainContent.revalidate();
        MainView.mainContent.repaint();
    }

    public void handleEditMeal(ActionEvent e) {
        MainView.mainContent.removeAll();
        MainView.mainContent.add(editpanel.getMainPanel(), BorderLayout.CENTER);
        MainView.mainContent.revalidate();
        MainView.mainContent.repaint();
    }

    private void handleBackAction(ActionEvent e) {
        MainView.menuContentPanel1.setVisible(true);
        MainView.sideCategoryPanel.getSideMenu().setVisible(true);
        MainView.mainContent.removeAll();
        MainView.mainContent.add(MainView.menuContentPanel1, BorderLayout.CENTER);
    }

    public MealDetailsView getView() {
        return view;
    }
}
