import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;

public class NavigationBarPanel{
    private final JPanel navBar;

    public NavigationBarPanel() {
        navBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        navBar.setBackground(Palette.MainColor);
        Dimension fixedSize = new Dimension(Integer.MAX_VALUE, 55); 
        navBar.setPreferredSize(fixedSize);
        ArrayList<String> buttons = new ArrayList<>();
        buttons.add("Home");
        buttons.add("Cart");
        if (User.getCurrentUser().getRole() != Role.USER) {
            buttons.add("Reports & OrderStatus");
        } else
            buttons.add("Orders status");
        if (User.getCurrentUser().getRole() == Role.ADMIN) {
            buttons.add("Users");
        }
        buttons.add(User.getCurrentUser().getName());
        for (String text : buttons) {
            JButton button = createButton(text);
            button.addActionListener(new ActionListener() {
                @SuppressWarnings("static-access")
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (button.getText() == "Home") {
//                        button.setForeground(Color.cyan);
                        MainPanel.mainContent.removeAll();
                        MainPanel.mainContent.add(MainPanel.menuContentPanel1, BorderLayout.CENTER);
                        MainPanel.menuContentPanel1.setSelectedCategory("Pizza");
                        MainPanel.sideCategoryPanel.getSideMenu().setVisible(true);
                        MainPanel.mainContent.revalidate();
                        MainPanel.mainContent.repaint();
                    } else if (button.getText() == "Cart") {
                        SideCategoryPanel.categoryList.clearSelection();
                        MainPanel.mainContent.removeAll();
                        MainPanel.mainContent.add(MainPanel.cartPanel, BorderLayout.CENTER);
                        MainPanel.sideCategoryPanel.getSideMenu().setVisible(false);
                        MainPanel.mainContent.revalidate();
                        MainPanel.mainContent.repaint();
                    } else if (button.getText() == "Reports & OrderStatus") {
                        SideCategoryPanel.categoryList.clearSelection();
                        MainPanel.mainContent.removeAll();
                        MainPanel.sideCategoryPanel.getSideMenu().setVisible(false);
                        MainPanel.mainContent.add(new Reports_and_OrderPanel().reports_and_OrderPanel,
                                BorderLayout.CENTER);
                        MainPanel.mainContent.revalidate();
                        MainPanel.mainContent.repaint();
                    } else if (button.getText() == "Users") {
                        SideCategoryPanel.categoryList.clearSelection();
                        MainPanel.mainContent.removeAll();
                        MainPanel.sideCategoryPanel.getSideMenu().setVisible(false);
                        MainPanel.mainContent.add(new ChangeRoleView().csp, BorderLayout.CENTER);
                        MainPanel.mainContent.revalidate();
                        MainPanel.mainContent.repaint();
                    } else if (button.getText() == "Orders status") {
                        SideCategoryPanel.categoryList.clearSelection();
                        MainPanel.mainContent.removeAll();
                        MainPanel.sideCategoryPanel.getSideMenu().setVisible(false);
                        MainPanel.mainContent.add(new UserOrderPanel().OrderPanel, BorderLayout.CENTER);
                        MainPanel.mainContent.revalidate();
                        MainPanel.mainContent.repaint();
                    } else {

                        new ProfileView().show(button, 20, 45);
                        MainPanel.mainContent.revalidate();
                        MainPanel.mainContent.repaint();
                    }
                }
            });
            navBar.add(button);
        }
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setPreferredSize(new Dimension(35, 35));
        p.add(createRoundedPhotoLabel(User.getCurrentUser().getPhotoPath()), BorderLayout.CENTER);

        navBar.add(p);
    }

    public void reRender(String oldName) {
        Arrays.stream(navBar.getComponents())
                .filter(component -> (component instanceof JButton) && ((JButton) component).getText().equals(oldName))
                .findAny()
                .ifPresent(component -> {
                    JButton button = (JButton) component;
                    button.setText(User.getCurrentUser().getName());
                });
        Arrays.stream(navBar.getComponents()).filter(component -> (component instanceof JPanel))
                .findAny()
                .ifPresent(component -> {
                    ((JPanel)component).removeAll();
                    ((JPanel)component).add(createRoundedPhotoLabel(User.getCurrentUser().getPhotoPath()));
                    component.revalidate();
                    component.repaint();
                });

;
        navBar.revalidate();
        navBar.repaint();
    }
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Georgia", Font.BOLD, 20));
        button.setBackground(Palette.MainColor);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }
    public JPanel getPanel() {
        return navBar;
    }
    public static JLabel createRoundedPhotoLabel(String imagePath) {
        JLabel photoLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Shape circle = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
                g2d.setClip(circle);
                ImageIcon icon = (ImageIcon) getIcon();
                if (icon != null) {
                    g2d.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
                g2d.dispose();
            }

        };

        photoLabel.setBackground(new Color(66, 109, 103));
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setVerticalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(150,150, Image.SCALE_SMOOTH);
            photoLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            photoLabel.setText("Image not found");
        }
        photoLabel.setPreferredSize(new Dimension(50,50));
        photoLabel.setOpaque(false);
        return photoLabel;
    }
}
