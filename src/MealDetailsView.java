import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MealDetailsView {
    private JPanel mainPanel;
    private JButton addButton;
    private JButton deleteButton;

    public MealDetailsView(Meal meal, ActionListener addAction, ActionListener deleteAction, ActionListener backAction) {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setSize(new Dimension(680, 420));
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(new Color(253, 245, 230));
        JPanel topDetailsPanel = createDetailsPanel(meal);
        JPanel bottomButtonPanel = createButtonPanel(addAction, deleteAction, backAction);
        JScrollPane scrollPane = new JScrollPane(topDetailsPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        detailsPanel.add(scrollPane, BorderLayout.CENTER);
        detailsPanel.add(bottomButtonPanel, BorderLayout.SOUTH);
        JLabel photoLabel = createPhotoLabel(meal.getImagePath());
        mainPanel.add(detailsPanel, BorderLayout.CENTER);
        mainPanel.add(photoLabel, BorderLayout.EAST);
    }
    private JPanel createDetailsPanel(Meal meal) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(253, 245, 230));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(true);
        Font labelFont = new Font("Serif", Font.BOLD, 32); //18
        JLabel nameLabel = createLabel("Name", meal.getMealName(), labelFont);
        JLabel categoryLabel = createLabel("Category", meal.getCategory(), labelFont);
        JLabel ingredientsLabel = createLabel("Ingredients", meal.getIngredients(), labelFont);
        JLabel priceLabel = createLabel("Price", meal.getMealPrice() + " $", labelFont);
        JLabel timeLabel = createLabel("Time Needed", meal.getMinutesNeeded() + " minutes", labelFont);
        panel.add(nameLabel);
        panel.add(categoryLabel);
        panel.add(ingredientsLabel);
        panel.add(priceLabel);
        panel.add(timeLabel);
        return panel;
    }
    private JLabel createLabel(String title, String value, Font font) {
        JLabel label = new JLabel("<html>" + title + ":<br>" + value + "</html>");
        label.setFont(font);
        label.setForeground(Color.BLACK);
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); //10
        label.setMaximumSize(new Dimension(600, 200)); //340  200
        return label;
    }
    private JPanel createButtonPanel(ActionListener addAction, ActionListener deleteAction, ActionListener backAction) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setOpaque(false);
        JButton backButton = new JButton("🔙");
        backButton.setPreferredSize(new Dimension(50, 25)); //
        backButton.setBackground(new Color(211, 211, 211));
        backButton.addActionListener(backAction);
        panel.add(backButton);
        addButton = new JButton("➕");
        deleteButton = new JButton("➖");
        addButton.setBackground(new Color(211, 211, 211));
        deleteButton.setBackground(new Color(211, 211, 211));
        addButton.addActionListener(addAction);
        deleteButton.addActionListener(deleteAction);
        panel.add(addButton);
        panel.add(deleteButton);
        return panel;
    }
    private JLabel createPhotoLabel(String imagePath) {
        JLabel photoLabel = new JLabel();
        photoLabel.setBackground(new Color(238, 121, 50));
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setVerticalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(560, 790, Image.SCALE_SMOOTH); //280 395
            photoLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            photoLabel.setText("Image not found");
        }
        photoLabel.setBorder(new LineBorder(new Color(238, 121, 50), 25)); //10
        photoLabel.setOpaque(true);
        return photoLabel;
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }
    public void enableButtons(boolean enable) {
        addButton.setEnabled(enable);
        deleteButton.setEnabled(enable);
    }
}
