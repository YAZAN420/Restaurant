import javax.swing.*;
import java.awt.*;

public class CustomerOrderDialogController {
    private CustomerOrderDialog view;
    public CustomerOrderDialogController() {
        view = new CustomerOrderDialog();
        view.getOrderTypeComboBox().addActionListener(e -> handleOrderTypeChange());
        view.getNotesCheckBox().addActionListener(e -> handleNotesCheckBox());
        view.getSubmitButton().addActionListener(e -> handleSubmit());

        view.getDialog().setVisible(true);
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
    }
    private void handleSubmit() {
        StringBuilder errorMessages = new StringBuilder();
        String orderType = (String) view.getOrderTypeComboBox().getSelectedItem();
        String tip = view.getTipField().getText();
        String notes = view.getNotesCheckBox().isSelected() ? view.getNotesTextArea().getText() : "";

        if (orderType == null || orderType.isEmpty()) {
            errorMessages.append("Please select an order type.\n");
        }

        if (tip == null || tip.isEmpty() || Double.parseDouble(tip)<0) {
            errorMessages.append("Please enter a tip amount.\n");
        }

        if ("Delivery".equals(orderType)) {
            String address = view.getAddressField().getText();
            if (address == null || address.isEmpty()) {
                errorMessages.append("Please enter a delivery address.\n");
            }
        } else {
            String tableNumber = view.getTableNumberField().getText();
            if (tableNumber == null || tableNumber.isEmpty() || Integer.parseInt(tableNumber)<1 || Integer.parseInt(tableNumber)>100) {
                errorMessages.append("Please enter a table number 1 -> 100.\n");
            }
        }

        NotificationView notificationView = new NotificationView(400);
        if (errorMessages.length() > 0) {
            JOptionPane.showMessageDialog(
                    null,
                    errorMessages.toString(),
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        else if ("Delivery".equals(orderType)) {
            String address = view.getAddressField().getText();
            OrderSystem.createOrder(MealPanel.cart, orderType, Double.parseDouble(tip), notes, address, -1, notificationView);
            this.view.getDialog().setVisible(false);
        } else {
            String tableNumber = view.getTableNumberField().getText();
            OrderSystem.createOrder(MealPanel.cart, orderType, Double.parseDouble(tip), notes, null, Integer.parseInt(tableNumber), notificationView);
            this.view.getDialog().setVisible(false);
        }

    }

}
