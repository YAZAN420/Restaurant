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
        mainPanel.setSize(680, 450);

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(new Color(253, 227, 201, 255));

        JPanel topDetailsPanel = createDetailsPanel(meal);

        JPanel bottomButtonPanel = createButtonPanel(addAction, deleteAction, backAction);

        detailsPanel.add(topDetailsPanel, BorderLayout.CENTER);
        detailsPanel.add(bottomButtonPanel, BorderLayout.SOUTH);

        JLabel photoLabel = createPhotoLabel(meal.getImagePath());

        mainPanel.add(detailsPanel, BorderLayout.CENTER);
        mainPanel.add(photoLabel, BorderLayout.EAST);
    }

    private JPanel createDetailsPanel(Meal meal) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        Font labelFont = new Font("Serif", Font.BOLD, 18);

        JLabel nameLabel = createLabel("Name", meal.getMealName(), labelFont);
        JLabel categoryLabel = createLabel("Category", meal.getCategory(), labelFont);
        JLabel ingredientsLabel = createLabel("Ingredients", meal.getIngredients(), labelFont);
        JLabel priceLabel = createLabel("Price", meal.getMealPrice() + " $", labelFont);
        JLabel timeLabel = createLabel("Time Needed", meal.getMinutesNeeded() + " minutes", labelFont);

        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        categoryLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ingredientsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        priceLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        timeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

    private JPanel createButtonPanel(ActionListener addAction, ActionListener deleteAction, ActionListener backAction) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setOpaque(false);

        JButton backButton = new JButton("🔙");
        backButton.setPreferredSize(new Dimension(50, 25));
        backButton.setBackground(new Color(211, 211, 211));
        backButton.addActionListener(backAction);
        panel.add(backButton);

        addButton = new JButton("➕");
        deleteButton = new JButton("➖");

        addButton.setEnabled(false);
        deleteButton.setEnabled(false);
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
            Image scaledImage = icon.getImage().getScaledInstance(280, 390, Image.SCALE_SMOOTH);
            photoLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            photoLabel.setText("Image not found");
        }

        photoLabel.setBorder(new LineBorder(new Color(238, 121, 50), 10));
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
