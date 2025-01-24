import javax.swing.*;
import java.awt.*;

public class ProfileView extends JPopupMenu {

    public ProfileView() {
        super();
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(340,320));

        JPanel pic = new JPanel(new BorderLayout());
        pic.setBackground(Color.WHITE);
        pic.add(ProfileEditView.createRoundedPhotoLabel(User.getCurrentUser().getPhotoPath()));
        JPanel emptyP = new JPanel(); emptyP.setPreferredSize(new Dimension(95,140)); emptyP.setBackground(Color.WHITE);
        JPanel emptyP2 = new JPanel(); emptyP2.setPreferredSize(new Dimension(95,140)); emptyP2.setBackground(Color.WHITE);
        pic.add(emptyP,BorderLayout.WEST); pic.add(emptyP2,BorderLayout.EAST);

        pic.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        add(pic);
        JLabel userName = new JLabel(User.getCurrentUser().getName(),SwingConstants.CENTER);
        userName.setFont(new Font("Georgia",Font.PLAIN,20)); userName.setBorder(BorderFactory.createEmptyBorder(15,0,10,0));
        JLabel userEmail = new JLabel("Email : " + User.getCurrentUser().getEmail(),SwingConstants.CENTER);
        userEmail.setFont(new Font("Georgia",Font.PLAIN,20));userEmail.setBorder(BorderFactory.createEmptyBorder(5,0,15,0));
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(340,160));
        panel.add(userName,BorderLayout.NORTH);
        panel.add(userEmail,BorderLayout.CENTER);

        JPanel twoButtons = new JPanel();
        RoundButton logout = new RoundButton(13,"Log out ");
        logout.setFont(new Font("Arial",Font.PLAIN,20));
        logout.setBackground(Palette.Red);
        logout.setForeground(Color.white);
        logout.setPreferredSize(new Dimension(160,30));
        logout.addActionListener(e-> {
            setVisible(false);
            User.setCurrentUser(null);
            MainPanel.mainContent.removeAll();
            LoginRegisterView.mainPanel.setVisible(false);
            LoginRegisterView.loginPanel.setVisible(true);
        });
        twoButtons.add(logout);
        RoundButton editProfile = new RoundButton(13,"edit profile ");
        editProfile.setFont(new Font("Arial",Font.PLAIN,20));
        editProfile.setBackground(Palette.green);
        editProfile.setForeground(Color.white);
        editProfile.setPreferredSize(new Dimension(160,30));
        editProfile.addActionListener(e-> {
            setVisible(false);
            MainPanel.mainContent.removeAll();
            MainPanel.sideCategoryPanel.getSideMenu().setVisible(false);
            MainPanel.mainContent.add(new ProfileEditView());
            MainPanel.mainContent.repaint();
            MainPanel.mainContent.revalidate();
        });
        twoButtons.add(editProfile);

        panel.add(twoButtons,BorderLayout.SOUTH);

        add(panel,BorderLayout.SOUTH);
    }
    @Override
    public void show(Component invoker, int x ,int y){
        int newX = x- 60;
        int newY = y;
        super.show(invoker,newX,newY);
    }
}
