package View;

import javax.swing.*;
import java.awt.*;

public class CustomerOrderDialogView {
    private JPanel panel;
    private JComboBox<String> orderTypeComboBox;
    private JTextField addressField, tableNumberField, tipField;
    private JCheckBox notesCheckBox;
    private JTextArea notesTextArea;
    private JButton submitButton;

    public CustomerOrderDialogView() {

        Font font = new Font(PaletteView.font, Font.BOLD, 32);
        Color bgColor = new Color(211, 211, 211);

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(700, 550));
        panel.setLayout(new GridLayout(0, 2));
        panel.setBackground(bgColor);

        JLabel orderTypeLabel = new JLabel(" Select Order Type: ");
        orderTypeLabel.setBackground(bgColor);
        orderTypeLabel.setFont(font);
        orderTypeLabel.setOpaque(true);

        String[] orderTypes = { "Dine In", "Delivery" };
        orderTypeComboBox = new JComboBox<>(orderTypes);
        orderTypeComboBox.setSelectedItem("Dine In");
        orderTypeComboBox.setBackground(bgColor);

        JLabel addressLabel = new JLabel(" Delivery Address: ");
        addressLabel.setBackground(bgColor);
        addressLabel.setFont(font);
        addressLabel.setOpaque(true);
        addressField = new JTextField();
        addressField.setBackground(bgColor);
        addressField.setEnabled(false);

        JLabel tableNumberLabel = new JLabel(" Table Number: ");
        tableNumberLabel.setBackground(bgColor);
        tableNumberLabel.setFont(font);
        tableNumberLabel.setOpaque(true);
        tableNumberField = new JTextField();
        tableNumberField.setBackground(bgColor);
        tableNumberField.setEnabled(false);

        JLabel tipLabel = new JLabel(" Tip Amount: ");
        tipLabel.setBackground(bgColor);
        tipLabel.setFont(font);
        tipLabel.setOpaque(true);
        tipField = new JTextField();
        tipField.setBackground(bgColor);

        JLabel notesLabel = new JLabel(" Add Notes: ");
        notesLabel.setBackground(bgColor);
        notesLabel.setFont(font);
        notesLabel.setOpaque(true);
        notesCheckBox = new JCheckBox("Yes");
        notesCheckBox.setBackground(bgColor);

        JLabel notesInputLabel = new JLabel(" Enter Notes: ");
        notesInputLabel.setBackground(bgColor);
        notesInputLabel.setFont(font);
        notesInputLabel.setOpaque(true);
        notesTextArea = new JTextArea(3, 20);
        notesTextArea.setBackground(bgColor);
        notesTextArea.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(notesTextArea);

        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(bgColor);

        JButton backButton = new JButton();
        ImageIcon backImg = new ImageIcon("photos\\back.png");
        Image img = backImg.getImage();
        Image scaledImg = img.getScaledInstance(180, 135, Image.SCALE_SMOOTH);
        backImg = new ImageIcon(scaledImg);
        backButton.setIcon(backImg);
        backButton.setPreferredSize(new Dimension(180, 135));
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);

        backButton.addActionListener(e -> {
            panel.setVisible(false);
        });

        emptyPanel.setLayout(new BorderLayout());
        emptyPanel.add(backButton, BorderLayout.CENTER);
        submitButton = new JButton("Submit Order");
        submitButton.setBackground(bgColor);

        panel.add(orderTypeLabel);
        panel.add(orderTypeComboBox);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(tableNumberLabel);
        panel.add(tableNumberField);
        panel.add(tipLabel);
        panel.add(tipField);
        panel.add(notesLabel);
        panel.add(notesCheckBox);
        panel.add(notesInputLabel);
        panel.add(scrollPane);
        panel.add(emptyPanel);
        panel.add(submitButton);
    }

    public JPanel getPanel() {
        return panel;
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
