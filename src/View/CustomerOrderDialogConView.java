package View;

import Controller.OrderSystemController;
import Controller.ServiceController;

import java.awt.*;

public class CustomerOrderDialogConView {
    public static CustomerOrderDialogConView a = new CustomerOrderDialogConView();
    private CustomerOrderDialogView view;

    public CustomerOrderDialogConView() {
        view = new CustomerOrderDialogView();
        view.getOrderTypeComboBox().addActionListener(e -> handleOrderTypeChange());
        handleOrderTypeChange();
        view.getNotesCheckBox().addActionListener(e -> handleNotesCheckBox());
        handleNotesCheckBox();
        view.getSubmitButton().addActionListener(e -> handleSubmit());

        MainView.cartPanel.add(view.getPanel(), BorderLayout.EAST);
        view.getPanel().setVisible(true);
    }

    private void handleOrderTypeChange() {
        String selectedOrderType = (String) view.getOrderTypeComboBox().getSelectedItem();
        if ("Delivery".equals(selectedOrderType)) {
            view.getAddressField().setEnabled(true);
            view.getTableNumberField().setEnabled(false);
            view.getAddressField().setBackground(new Color(211, 211, 211));
            view.getTableNumberField().setBackground(new Color(152, 147, 140));
        } else {
            view.getAddressField().setEnabled(false);
            view.getTableNumberField().setEnabled(true);
            view.getAddressField().setBackground(new Color(152, 147, 140));
            view.getTableNumberField().setBackground(new Color(211, 211, 211));
        }
    }

    private void handleNotesCheckBox() {
        view.getNotesTextArea().setEnabled(view.getNotesCheckBox().isSelected());
        if (view.getNotesCheckBox().isSelected())
            view.getNotesTextArea().setBackground(new Color(211, 211, 211));
        else
            view.getNotesTextArea().setBackground(new Color(152, 147, 140));
    }

    private void handleSubmit() {
        String orderType = (String) view.getOrderTypeComboBox().getSelectedItem();
        String tip = view.getTipField().getText();
        String notes = view.getNotesCheckBox().isSelected() ? view.getNotesTextArea().getText() : "";
        int error = 0;

        if (orderType == null || orderType.isEmpty()) {
            error++;
            MainFrameView.notificationView.showNotification("Please select an order type.\n", true);
        }

        else if (!ServiceController.isNumeric(tip) || Double.parseDouble(tip) < 0) {
            error++;
            MainFrameView.notificationView.showNotification("Please enter a tip amount.\n", true);
        }

        else if (view.getNotesCheckBox().isSelected() && view.getNotesTextArea().getText().trim().length() == 0) {
            error++;
            MainFrameView.notificationView.showNotification("Please enter a note.\n", true);
        }

        else if ("Delivery".equals(orderType)) {
            String address = view.getAddressField().getText();
            if (address == null || address.isEmpty()) {
                error++;
                MainFrameView.notificationView.showNotification("Please enter a delivery address.\n", true);
            }
        } else {
            String tableNumber = view.getTableNumberField().getText();
            if (!ServiceController.isNumeric(tableNumber) || Integer.parseInt(tableNumber) < 1
                    || Integer.parseInt(tableNumber) > 100) {
                error++;
                MainFrameView.notificationView.showNotification("Please enter a table number 1 -> 100.\n", true);
            }
        }

        if (error == 0)
            if ("Delivery".equals(orderType)) {
                String address = view.getAddressField().getText();
                OrderSystemController.createOrder(MealPanel.cart, orderType, Double.parseDouble(tip), notes, address,
                        -1);
                this.view.getPanel().setVisible(false);
            } else {
                String tableNumber = view.getTableNumberField().getText();
                OrderSystemController.createOrder(MealPanel.cart, orderType, Double.parseDouble(tip), notes, null,
                        Integer.parseInt(tableNumber));
                this.view.getPanel().setVisible(false);
            }

        clearFields();
        CartView.setTotalPrice(0.0);
        CartView.updateCartView(MealPanel.cart);
    }

    public void updatePanelVisibility() {
        if (MealPanel.cart.isEmpty()) {
            this.view.getPanel().setVisible(false);
        } else {
            this.view.getPanel().setVisible(true);
        }
    }

    private void clearFields() {
        view.getOrderTypeComboBox().setSelectedIndex(0);
        view.getTipField().setText("");
        view.getNotesCheckBox().setSelected(false);
        handleNotesCheckBox();
        view.getNotesTextArea().setText("");
        view.getAddressField().setText("");
        view.getTableNumberField().setText("");
    }

    public void setView(CustomerOrderDialogView view) {
        this.view = view;
    }

    public CustomerOrderDialogView getView() {
        return view;
    }
}
