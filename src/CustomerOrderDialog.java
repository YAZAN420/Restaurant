import javax.swing.*;
import java.awt.*;

public class CustomerOrderDialog {
    private JDialog dialog;
    private JComboBox<String> orderTypeComboBox;
    private JTextField addressField, tableNumberField, tipField;
    private JCheckBox notesCheckBox;
    private JTextArea notesTextArea;
    private JButton submitButton;

    public CustomerOrderDialog() {
        dialog = new JDialog((Frame) null, "Order Information", true);
        dialog.setSize(400, 350);
        dialog.setLayout(new GridLayout(0, 2));
        Font font = new Font("Serif", Font.BOLD, 18);
        JLabel orderTypeLabel = new JLabel("Select Order Type: ");
        orderTypeLabel.setFont(font);
        orderTypeLabel.setBackground(new Color(253, 245, 230));
        orderTypeLabel.setOpaque(true);
        String[] orderTypes = { "Dine In", "Delivery" };
        orderTypeComboBox = new JComboBox<>(orderTypes);
        orderTypeComboBox.setBackground(new Color(211, 211, 211));
        JLabel addressLabel = new JLabel("Delivery Address: ");
        addressLabel.setFont(font);
        addressLabel.setBackground(new Color(253, 245, 230));
        addressLabel.setOpaque(true);
        addressField = new JTextField();
        addressField.setBackground(new Color(211, 211, 211));
        addressField.setEnabled(false);
        JLabel tableNumberLabel = new JLabel("Table Number: ");
        tableNumberLabel.setFont(font);
        tableNumberLabel.setBackground(new Color(253, 245, 230));
        tableNumberLabel.setOpaque(true);
        tableNumberField = new JTextField();
        tableNumberField.setBackground(new Color(211, 211, 211));
        tableNumberField.setEnabled(false);
        JLabel tipLabel = new JLabel("Tip Amount: ");
        tipLabel.setFont(font);
        tipLabel.setBackground(new Color(253, 245, 230));
        tipLabel.setOpaque(true);
        tipField = new JTextField();
        tipField.setBackground(new Color(211, 211, 211));
        JLabel notesLabel = new JLabel("Add Notes: ");
        notesLabel.setFont(font);
        notesLabel.setBackground(new Color(253, 245, 230));
        notesLabel.setOpaque(true);
        notesCheckBox = new JCheckBox("Yes");
        notesCheckBox.setBackground(new Color(211, 211, 211));
        JLabel notesInputLabel = new JLabel("Enter Notes: ");
        notesInputLabel.setFont(font);
        notesInputLabel.setBackground(new Color(253, 245, 230));
        notesInputLabel.setOpaque(true);
        notesTextArea = new JTextArea(3, 20);
        notesTextArea.setBackground(new Color(211, 211, 211));
        JScrollPane scrollPane = new JScrollPane(notesTextArea);
        notesTextArea.setEnabled(false);
        JLabel emptyLabel = new JLabel();
        emptyLabel.setBackground(new Color(253, 245, 230));
        emptyLabel.setOpaque(true);
        submitButton = new JButton("Submit Order");
        submitButton.setBackground(new Color(211, 211, 211));
        dialog.add(orderTypeLabel);
        dialog.add(orderTypeComboBox);
        dialog.add(addressLabel);
        dialog.add(addressField);
        dialog.add(tableNumberLabel);
        dialog.add(tableNumberField);
        dialog.add(tipLabel);
        dialog.add(tipField);
        dialog.add(notesLabel);
        dialog.add(notesCheckBox);
        dialog.add(notesInputLabel);
        dialog.add(scrollPane);
        dialog.add(emptyLabel);
        dialog.add(submitButton);
    }
    public JDialog getDialog() {
        return dialog;
    }
    public JComboBox<String> getOrderTypeComboBox() {
        return orderTypeComboBox;
    }
    public JTextField getAddressField() {
        return addressField;
    }
    public JTextField getTableNumberField() {
        return tableNumberField;
    }
    public JTextField getTipField() {
        return tipField;
    }
    public JCheckBox getNotesCheckBox() {
        return notesCheckBox;
    }
    public JTextArea getNotesTextArea() {
        return notesTextArea;
    }
    public JButton getSubmitButton() {
        return submitButton;
    }
}
