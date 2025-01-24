import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MealDetailsView {
    Color colorOrange = Palette.AdditionalColor;
    Color colorBeige = Palette.BackGround; //Color.WHITE
    private JPanel mainPanel;
    private JButton addButton;
    private JButton deleteButton;
    private JButton removeButton;
    private JButton editButton;
    public JLabel photoLabel;
    public MealDetailsView(Meal meal, ActionListener addAction, ActionListener deleteAction,
                           ActionListener backAction,ActionListener removeAction,ActionListener editAction) {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setSize(new Dimension(680, 420));
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(colorBeige);
        JPanel topDetailsPanel = createDetailsPanel(meal);
        JPanel bottomButtonPanel = createButtonPanel(addAction, deleteAction, backAction,removeAction,editAction);
        CustomScrollPane scrollPane = new CustomScrollPane(topDetailsPanel,Color.LIGHT_GRAY,Color.DARK_GRAY);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        detailsPanel.add(scrollPane, BorderLayout.CENTER);
        detailsPanel.add(bottomButtonPanel, BorderLayout.SOUTH);
        photoLabel = createPhotoLabel(meal.getImagePath());
        mainPanel.add(detailsPanel, BorderLayout.CENTER);
        mainPanel.add(photoLabel, BorderLayout.EAST);
    }
    private JPanel createDetailsPanel(Meal meal) {
        JPanel panel = new JPanel();
        panel.setBackground(colorBeige);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(true);
        Font labelFont = new Font("Serif", Font.BOLD, 32);
        JPanel nameLabel = createPanel("Name", meal.getMealName(), labelFont);
        JPanel categoryLabel = createPanel("Category", meal.getCategory(), labelFont);
        JPanel ingredientsLabel = createPanel("Ingredients", meal.getIngredients(), labelFont);
        JPanel priceLabel = createPanel("Price", meal.getMealPrice() + " $", labelFont);
        JPanel timeLabel = createPanel("Time Needed", meal.getMinutesNeeded() + " minutes", labelFont);
        panel.add(nameLabel);
        panel.add(categoryLabel);
        panel.add(ingredientsLabel);
        panel.add(priceLabel);
        panel.add(timeLabel);
        return panel;
    }

    private JPanel createPanel(String title, String value, Font font) {

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBackground(colorBeige);
        labelPanel.setBorder(BorderFactory.createEmptyBorder(25, 80, 10, 20));
        labelPanel.setMaximumSize(new Dimension(800, 200));

        JLabel titleLabel = new JLabel("<html>" + title + ":<br></html>");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 38));
        titleLabel.setForeground(colorOrange);

        JLabel valueLabel = new JLabel("<html>" + value + "</html>");
        valueLabel.setFont(font);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 20));
        valueLabel.setForeground(Color.BLACK);

        labelPanel.add(titleLabel);
        labelPanel.add(valueLabel);

        return labelPanel;
    }

    private JPanel createButtonPanel(ActionListener addAction, ActionListener deleteAction, ActionListener backAction,ActionListener removeAction,ActionListener editAction) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setOpaque(false);
        JButton backButton = new JButton();

        ImageIcon backIcon = new ImageIcon("photos\\turn-back.png");
        Image img = backIcon.getImage();
        Image scaledImg = img.getScaledInstance(50, 40, Image.SCALE_SMOOTH);
        backIcon = new ImageIcon(scaledImg);
        backButton.setIcon(backIcon);

        backButton.setPreferredSize(new Dimension(100, 60));
        backButton.setBackground(new Color(211, 211, 211));
        backButton.addActionListener(backAction);
        panel.add(backButton);
        addButton = new JButton();
        deleteButton = new JButton();
        removeButton = new JButton();
        editButton = new JButton();
        ImageIcon removeIcon = new ImageIcon("photos\\recycle-bin.png");
        Image img4 = removeIcon.getImage();
        Image scaledImg4 = img4.getScaledInstance(40, 35, Image.SCALE_SMOOTH);
        removeIcon = new ImageIcon(scaledImg4);
        removeButton.setIcon(removeIcon);

        ImageIcon editIcon = new ImageIcon("photos\\update.png");
        Image img5 = editIcon.getImage();
        Image scaledImg5 = img5.getScaledInstance(40, 35, Image.SCALE_SMOOTH);
        editIcon = new ImageIcon(scaledImg5);
        editButton.setIcon(editIcon);

        ImageIcon addIcon = new ImageIcon("photos\\plus+.png");
        Image img2 = addIcon.getImage();
        Image scaledImg2 = img2.getScaledInstance(40, 35, Image.SCALE_SMOOTH);
        addIcon = new ImageIcon(scaledImg2);
        addButton.setIcon(addIcon);

        ImageIcon deleteIcon = new ImageIcon("photos\\minimize-sign.png");
        Image img3 = deleteIcon.getImage();
        Image scaledImg3 = img3.getScaledInstance(40, 35, Image.SCALE_SMOOTH);
        deleteIcon = new ImageIcon(scaledImg3);
        deleteButton.setIcon(deleteIcon);

        addButton.setPreferredSize(new Dimension(100, 60));
        editButton.setPreferredSize(new Dimension(100, 60));
        removeButton.setPreferredSize(new Dimension(100, 60));
        deleteButton.setPreferredSize(new Dimension(100, 60));
        removeButton.setBackground(new Color(211, 211, 211));
        addButton.setBackground(new Color(211, 211, 211));
        deleteButton.setBackground(new Color(211, 211, 211));
        editButton.setBackground(new Color(211, 211, 211));
        addButton.addActionListener(addAction);
        removeButton.addActionListener(removeAction);
        deleteButton.addActionListener(deleteAction);
        editButton.addActionListener(editAction);
        removeButton.setVisible(false);
        editButton.setVisible(false);
        if(User.getCurrentUser().getRole()!=Role.USER){
            removeButton.setVisible(true);
            editButton.setVisible(true);
        }
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(removeButton);
        panel.add(editButton);
        return panel;
    }
    private JLabel createPhotoLabel(String imagePath) {
        JLabel photoLabel = new JLabel();
        photoLabel.setBackground(colorOrange);
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setVerticalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(560, 790, Image.SCALE_SMOOTH);
            photoLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            photoLabel.setText("Image not found");
        }
        photoLabel.setBorder(new LineBorder(colorOrange, 25));
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
