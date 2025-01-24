import View.CartView;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
public class MealDetailsController {
    private MealDetailsView view;
    private Meal meal;
    EditMealPanel editpanel;
    MenuManager mg = new MenuManager();
    public MealDetailsController(Meal meal) {
        this.meal = meal;
        editpanel = new EditMealPanel(meal);
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
            if(CartView.orderPanel != null && CartView.orderPanel.isVisible())
                CustomerOrderDialogController.a.updatePanelVisibility();
        } else {
            LoginRegisterView.notificationView.showNotification("Meal not found in the cart!", true);}
    }
    private void handleRemoveMeal(ActionEvent e) {
        mg.deleteFromMenu(meal.getCategory(), meal.getMealName());
        MainPanel.mainContent.removeAll();
        MainPanel.mainContent.add(MainPanel.menuContentPanel1, BorderLayout.CENTER);
        MainPanel.menuContentPanel1.setSelectedCategory(meal.getCategory());
        MainPanel.sideCategoryPanel.getSideMenu().setVisible(true);
        MainPanel.mainContent.revalidate();
        MainPanel.mainContent.repaint();
    }

    public void handleEditMeal(ActionEvent e) {
        MainPanel.mainContent.removeAll();
        MainPanel.mainContent.add(editpanel.getMainPanel(), BorderLayout.CENTER);
        MainPanel.mainContent.revalidate();
        MainPanel.mainContent.repaint();
    }
    private void handleBackAction(ActionEvent e) {
        MainPanel.menuContentPanel1.setVisible(true);
        MainPanel.sideCategoryPanel.getSideMenu().setVisible(true);
        MainPanel.mainContent.removeAll();
        MainPanel.mainContent.add(MainPanel.menuContentPanel1, BorderLayout.CENTER);
    }
    public MealDetailsView getView() {
        return view;
    }
}
