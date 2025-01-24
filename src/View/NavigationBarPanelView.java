package View;

import Model.RoleModel;
import Model.UserModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class NavigationBarPanelView {
    private final JPanel navBar;
    private final JPanel logoPanel;

    public NavigationBarPanelView() {
        navBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        navBar.setBackground(PaletteView.MainColor);
        Dimension fixedSize = new Dimension(Integer.MAX_VALUE, 59);
        navBar.setPreferredSize(fixedSize);

        logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(PaletteView.MainColor);
        int width = 390;
        if (UserModel.getCurrentUser().getRole() != RoleModel.ADMIN)
            width = 450;
        logoPanel.setPreferredSize(new Dimension(width, 40));
        JLabel ResNameL = new JLabel("Dish Dash", SwingConstants.CENTER);
        ResNameL.setForeground(PaletteView.AdditionalColor.brighter());
        ResNameL.setFont(new Font(PaletteView.font, Font.PLAIN, 30));
        logoPanel.add(ResNameL, BorderLayout.WEST);
        Image im = new ImageIcon("photos/Layer 3.png").getImage().getScaledInstance(33, 30, Image.SCALE_SMOOTH);
        JLabel logoPic = new JLabel(new ImageIcon(im), SwingConstants.CENTER);
        logoPanel.add(logoPic);
        logoPanel.add(new JLabel("                                       "));
        navBar.add(logoPanel);

        ArrayList<String> buttons = new ArrayList<>();
        buttons.add("Home");
        buttons.add("Cart");
        if (UserModel.getCurrentUser().getRole() != RoleModel.USER) {
            buttons.add("Reports & OrderStatus");
        } else
            buttons.add("Orders status");
        if (UserModel.getCurrentUser().getRole() == RoleModel.ADMIN) {
            buttons.add("Users");
        }
        buttons.add(UserModel.getCurrentUser().getName());
        for (String text : buttons) {
            JButton button = createButton(text);
            button.addActionListener(new ActionListener() {
                @SuppressWarnings("static-access")
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (button.getText() == "Home") {
                        // button.setForeground(Color.cyan);
                        MainView.mainContent.removeAll();
                        MainView.mainContent.add(MainView.menuContentPanel1, BorderLayout.CENTER);
                        MainView.menuContentPanel1.setSelectedCategory("Pizza");
                        MainView.sideCategoryPanel.getSideMenu().setVisible(true);
                        MainView.mainContent.revalidate();
                        MainView.mainContent.repaint();
                    } else if (button.getText() == "Cart") {
                        SideCategoryView.categoryList.clearSelection();
                        MainView.mainContent.removeAll();
                        MainView.mainContent.add(MainView.cartPanel, BorderLayout.CENTER);
                        MainView.sideCategoryPanel.getSideMenu().setVisible(false);
                        MainView.mainContent.revalidate();
                        MainView.mainContent.repaint();
                    } else if (button.getText() == "Reports & OrderStatus") {
                        SideCategoryView.categoryList.clearSelection();
                        MainView.mainContent.removeAll();
                        MainView.sideCategoryPanel.getSideMenu().setVisible(false);
                        MainView.mainContent.add(new ReportsAndOrderView().reports_and_OrderPanel,
                                BorderLayout.CENTER);
                        MainView.mainContent.revalidate();
                        MainView.mainContent.repaint();
                    } else if (button.getText() == "Users") {
                        SideCategoryView.categoryList.clearSelection();
                        MainView.mainContent.removeAll();
                        MainView.sideCategoryPanel.getSideMenu().setVisible(false);
                        MainView.mainContent.add(new ChangeRoleView().changeRoleView, BorderLayout.CENTER);
                        MainView.mainContent.revalidate();
                        MainView.mainContent.repaint();
                    } else if (button.getText() == "Orders status") {
                        SideCategoryView.categoryList.clearSelection();
                        MainView.mainContent.removeAll();
                        MainView.sideCategoryPanel.getSideMenu().setVisible(false);
                        MainView.mainContent.add(new UserOrderView().OrderPanel, BorderLayout.CENTER);
                        MainView.mainContent.revalidate();
                        MainView.mainContent.repaint();
                    } else {

                        new ProfileView().show(button, 20, 45);
                        MainView.mainContent.revalidate();
                        MainView.mainContent.repaint();
                    }
                }
            });
            navBar.add(button);
        }
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setPreferredSize(new Dimension(35, 35));
        p.add(createRoundedPhotoLabel(UserModel.getCurrentUser().getPhotoPath()), BorderLayout.CENTER);
        JPanel empty = new JPanel(new BorderLayout());
        int width2 = 300;
        if (UserModel.getCurrentUser().getRole() == RoleModel.EMPLOYEE)
            width2 = 350;
        else if (UserModel.getCurrentUser().getRole() == RoleModel.USER)
            width2 = 450;
        empty.setPreferredSize(new Dimension(width2, 30));
        empty.setBackground(PaletteView.MainColor);

        RoundButtonView Xbutton = new RoundButtonView(0, "X", true);
        Xbutton.setBackground(PaletteView.MainColor);
        Xbutton.setFont(new Font("Arial", Font.BOLD, 40));
        Xbutton.setBorder(null);
        Xbutton.setPreferredSize(new Dimension(26, 26));
        Xbutton.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        Xbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrameView.frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                MainFrameView.frame.dispose();
            }
        });
        empty.add(Xbutton, BorderLayout.EAST);

        navBar.add(p);
        navBar.add(empty);
    }

    public void reRender(String oldName) {
        Arrays.stream(navBar.getComponents())
                .filter(component -> (component instanceof JButton) && ((JButton) component).getText().equals(oldName))
                .findAny()
                .ifPresent(component -> {
                    JButton button = (JButton) component;
                    button.setText(UserModel.getCurrentUser().getName());
                });
        Arrays.stream(navBar.getComponents())
                .filter(component -> (component instanceof JPanel && component != logoPanel))
                .findAny()
                .ifPresent(component -> {
                    ((JPanel) component).removeAll();
                    ((JPanel) component).add(createRoundedPhotoLabel(UserModel.getCurrentUser().getPhotoPath()));
                    component.revalidate();
                    component.repaint();
                });

        ;
        navBar.revalidate();
        navBar.repaint();
    }

    public JPanel getPanel() {
        return navBar;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(PaletteView.font, Font.BOLD, 20));
        button.setBackground(PaletteView.MainColor);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
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

        photoLabel.setBackground(PaletteView.MainColor);
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setVerticalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH);
            photoLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            photoLabel.setText("Image not found");
        }
        photoLabel.setPreferredSize(new Dimension(55, 55));
        photoLabel.setOpaque(false);
        return photoLabel;
    }
}
