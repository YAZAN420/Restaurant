import javax.swing.*;
import java.awt.*;

import static java.lang.Math.max;

public class ChangeRoleView {
    CustomScrollPane csp;
    JPanel changeRoleView;
    public ChangeRoleView(){
        int size = User.getUsers().size();
        changeRoleView = new JPanel(new GridLayout(max(size+2,8),1));
        changeRoleView.setPreferredSize(new Dimension(1080,(size+2)*100));

        JPanel titleP = new JPanel(new BorderLayout());
        titleP.setPreferredSize(new Dimension(1080,100));

        JLabel title = new JLabel("Change Users' Roles And Permissions",SwingConstants.CENTER);
        title.setFont(new Font("Georgia",Font.BOLD,32));
        titleP.setBackground(Color.WHITE);
        titleP.add(title);
        changeRoleView.add(titleP);

        changeRoleView.add(userPanel("User","Email","Current role","New role",31,Color.LIGHT_GRAY));
        int i =0 ;
        for(User user : User.getUsers()){
            String newRole=String.valueOf(user.getRole());
            if(user.getRole()==Role.ADMIN){newRole = "ADMIN";
                changeRoleView.add(userPanel(user.getName(),user.getEmail(),String.valueOf(user.getRole()),newRole,24,(i%2==0)?Color.WHITE:Color.LIGHT_GRAY));
                i++;}}
        for(User user : User.getUsers()){
            String newRole=String.valueOf(user.getRole());
            if(user.getRole()==Role.EMPLOYEE){
                changeRoleView.add(userPanel(user.getName(),user.getEmail(),String.valueOf(user.getRole()),newRole,24,(i%2==0)?Color.WHITE:Color.LIGHT_GRAY));
                i++;}}
        for(User user : User.getUsers()){
            String newRole=String.valueOf(user.getRole());
            if(user.getRole()==Role.USER){
                changeRoleView.add(userPanel(user.getName(),user.getEmail(),String.valueOf(user.getRole()),newRole,24,(i%2==0)?Color.WHITE:Color.LIGHT_GRAY));
                i++;}}
        changeRoleView.setBorder(BorderFactory.createLineBorder(Color.WHITE,30,true));
        csp= new CustomScrollPane(changeRoleView,Color.DARK_GRAY,Color.LIGHT_GRAY);

    }
    public JPanel userPanel(String name,String email, String curRole,String newRole,int fontSize,Color color){
        JPanel panel = new JPanel(new GridLayout(1,4));
        panel.setPreferredSize(new Dimension(1080,100));
        panel.setBackground(color);
        JLabel nameP = new JLabel(name,SwingConstants.CENTER);
        nameP.setFont(new Font("Georgia",0,fontSize));
        try {
            String pic = User.findUserByName(name).getPhotoPath();
            JPanel picP = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0));
            picP.setPreferredSize(new Dimension(80,80)); picP.setBackground(color);picP.setBorder(BorderFactory.createEmptyBorder(20,60,0,0));
            picP.add(NavigationBarPanel.createRoundedPhotoLabel(pic)); panel.add(picP);
            picP.add(nameP);
            panel.add(picP);}
        catch (Exception e) {
            panel.add(nameP);
        }


        JLabel emailP = new JLabel(email,SwingConstants.CENTER);
        emailP.setFont(new Font("Georgia",0 ,fontSize));
        panel.add(emailP);

        JLabel curRoleP = new JLabel(curRole,SwingConstants.CENTER);
        curRoleP.setFont(new Font("Georgia",0,fontSize));
        if(curRole=="ADMIN") curRoleP.setForeground(Palette.Red);
        else if(curRole=="EMPLOYEE") curRoleP.setForeground(Palette.green);
        if(curRole=="USER") curRoleP.setForeground(Color.BLUE);
        panel.add(curRoleP);

        if(newRole !="EMPLOYEE" && newRole!="USER"){
            JLabel newRoleP = new JLabel(newRole,SwingConstants.CENTER);
            newRoleP.setFont(new Font("Georgia",0,fontSize));
            panel.add(newRoleP);
        }
        else{
            JPanel newRoleP = new JPanel(new GridLayout(1,2,10,10));
            newRoleP.setBorder(BorderFactory.createEmptyBorder(30,80,30,80));
            newRoleP.setBackground(color);
            newRoleP.setPreferredSize(new Dimension((1080)/4,100));
            if(newRole == "EMPLOYEE"){
                newRoleP.add(createButton("USER",Color.BLUE,name),BorderLayout.CENTER);
                newRoleP.add(createButton("ADMIN",Palette.Red,name),BorderLayout.CENTER);
            }
            else if(newRole =="USER"){
                newRoleP.add(createButton("EMPLOYEE",Palette.green,name),BorderLayout.CENTER);
                newRoleP.add(createButton("ADMIN",Palette.Red,name));
            }
            panel.add(newRoleP);
        }
        return panel;
    }
    public RoundButton createButton(String text,Color color,String name) {
        RoundButton button = new RoundButton(12,text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Sans Serif",Font.PLAIN,15));
        button.setPreferredSize(new Dimension(50,30));
        button.addActionListener(e->{
            String msg= "Do you want to set "+name+" as "+text +" ?";
            int res = JOptionPane.showConfirmDialog(null,msg, "Question",JOptionPane.YES_NO_OPTION);
            switch (res){
                case JOptionPane.YES_OPTION:{
                    User curUser = User.findUserByName(name);
                    curUser.changeRole(curUser,curUser.stringToRole(text));
                    MainPanel.mainContent.removeAll();
                    MainPanel.mainContent.add(new ChangeRoleView().csp,BorderLayout.CENTER);
                    MainPanel.mainContent.revalidate();
                    MainPanel.mainContent.repaint();
                }
            }
        });

        return button;
    }
}
