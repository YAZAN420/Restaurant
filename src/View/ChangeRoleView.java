package View;

import Model.RoleModel;
import Model.UserModel;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.max;

public class ChangeRoleView {
    CustomScrollView csp;
    JPanel changeRoleView;
    Color blue = new Color(17, 77, 157);// 24, 119, 242)

    public ChangeRoleView() {
        int size = UserModel.getUsers().size();
        changeRoleView = new JPanel();
        changeRoleView.setPreferredSize(new Dimension(1920, 1080));

        JPanel titleP = new JPanel(new BorderLayout());
        titleP.setPreferredSize(new Dimension(1800, 100));

        JLabel title = new JLabel("Change Users' Roles And Permissions", SwingConstants.CENTER);
        title.setFont(new Font(PaletteView.font, Font.BOLD, 32));
        title.setForeground(new Color(54, 54, 54));
        titleP.setBackground(Color.WHITE);
        titleP.add(title);
        changeRoleView.add(titleP);

        changeRoleView.add(userPanel("User", "Email", "Current role", "New role", 31, Color.LIGHT_GRAY));
        int i = 0;
        JPanel panel = new JPanel(new GridLayout(max(size, 6), 1));
        panel.setPreferredSize(new Dimension(1450,size*100 -30));
        for (UserModel user : UserModel.getUsers()) {
            String newRole = String.valueOf(user.getRole());
            if (user.getRole() == RoleModel.ADMIN) {
                newRole = "ADMIN";
                panel.add(userPanel(user.getName(), user.getEmail(), String.valueOf(user.getRole()), newRole,
                        24, (i % 2 == 0) ? Color.WHITE : Color.LIGHT_GRAY));
                i++;
            }
        }
        for (UserModel user : UserModel.getUsers()) {
            String newRole = String.valueOf(user.getRole());
            if (user.getRole() == RoleModel.EMPLOYEE) {
                panel.add(userPanel(user.getName(), user.getEmail(), String.valueOf(user.getRole()), newRole,
                        24, (i % 2 == 0) ? Color.WHITE : Color.LIGHT_GRAY));
                i++;
            }
        }
        for (UserModel user : UserModel.getUsers()) {
            String newRole = String.valueOf(user.getRole());
            if (user.getRole() == RoleModel.USER) {
                panel.add(userPanel(user.getName(), user.getEmail(), String.valueOf(user.getRole()), newRole,
                        24, (i % 2 == 0) ? Color.WHITE : Color.LIGHT_GRAY));
                i++;
            }
        }
        changeRoleView.setBorder(BorderFactory.createLineBorder(Color.WHITE, 30, true));
        csp = new CustomScrollView(panel, Color.LIGHT_GRAY, Color.DARK_GRAY);
//        csp.setPreferredSize(1);
        csp.setPreferredSize(new Dimension(1475,562));
        csp.setBorder(null);
        changeRoleView.add(csp);
    }

    public JPanel userPanel(String name, String email, String curRole, String newRole, int fontSize, Color color) {
        JPanel panel = new JPanel(new GridLayout(1, 4));
        panel.setPreferredSize(new Dimension(1475, 100));
        panel.setBackground(color);
        JLabel nameP = new JLabel(name, SwingConstants.CENTER);
        nameP.setFont(new Font(PaletteView.font, 0, fontSize));
        try {
            String pic = UserModel.findUserByName(name).getPhotoPath();
            JPanel picP = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
            picP.setPreferredSize(new Dimension(80, 80));
            picP.setBackground(color);
            picP.setBorder(BorderFactory.createEmptyBorder(20, 60, 0, 0));
            picP.add(NavigationBarPanelView.createRoundedPhotoLabel(pic));
            panel.add(picP);
            picP.add(nameP);
            panel.add(picP);
        } catch (Exception e) {
            panel.add(nameP);
        }

        JLabel emailP = new JLabel(email, SwingConstants.CENTER);
        emailP.setFont(new Font(PaletteView.font, 0, fontSize));
        panel.add(emailP);

        JLabel curRoleP = new JLabel(curRole=="USER"?"CUSTOMER":curRole, SwingConstants.CENTER);
        curRoleP.setFont(new Font(PaletteView.font, 0, fontSize));
        if (curRole == "ADMIN")
            curRoleP.setForeground(PaletteView.Red);
        else if (curRole == "EMPLOYEE")
            curRoleP.setForeground(PaletteView.green);
        if (curRole == "USER")
            curRoleP.setForeground(blue);
        panel.add(curRoleP);

        if (newRole != "EMPLOYEE" && newRole != "USER") {
            JLabel newRoleP = new JLabel(newRole, SwingConstants.CENTER);
            newRoleP.setFont(new Font(PaletteView.font, 0, fontSize));
            panel.add(newRoleP);
        } else {
            JPanel newRoleP = new JPanel(new GridLayout(1, 2, 10, 10));
            newRoleP.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
            newRoleP.setBackground(color);
            newRoleP.setPreferredSize(new Dimension((1080) / 4, 100));
            if (newRole == "EMPLOYEE") {
                newRoleP.add(createButton("CUSTOMER", blue, name), BorderLayout.CENTER);
                newRoleP.add(createButton("ADMIN", PaletteView.Red, name), BorderLayout.CENTER);
            } else if (newRole == "USER") {
                newRoleP.add(createButton("EMPLOYEE", PaletteView.green, name), BorderLayout.CENTER);
                newRoleP.add(createButton("ADMIN", PaletteView.Red, name));
            }
            panel.add(newRoleP);
        }
        return panel;
    }

    public RoundButtonView createButton(String text, Color color, String name) {
        RoundButtonView button = new RoundButtonView(12, text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font(PaletteView.font, Font.PLAIN, 15));
        button.setPreferredSize(new Dimension(50, 30));
        button.addActionListener(e -> {
            String msg = "Do you want to set " + name + "\n as " + text + " ?";
            JLabel msgLable = new JLabel(msg); msgLable.setFont(new Font(PaletteView.font,Font.BOLD,15));
            JOptionPane p = new JOptionPane(msgLable,JOptionPane.QUESTION_MESSAGE,JOptionPane.YES_NO_OPTION,
                    new ImageIcon(new ImageIcon("photos/AreYouSure!.png").getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH)));

            JDialog dialog = p.createDialog("Question");
            dialog.setFont(new Font(PaletteView.font,Font.BOLD,10));
            dialog.setVisible(true);

            Object selectedValue = p.getValue();
            if (selectedValue instanceof Integer) {
            int res = (Integer) selectedValue;
//
            switch (res) {
                case JOptionPane.YES_OPTION: {
                    UserModel curUser = UserModel.findUserByName(name);
                    curUser.changeRole(curUser, curUser.stringToRole(text));
                    MainView.mainContent.removeAll();
                    MainView.mainContent.add(new ChangeRoleView().changeRoleView, BorderLayout.CENTER);
                    MainView.mainContent.revalidate();
                    MainView.mainContent.repaint();
                }

            }}
        });

        return button;
    }
}
