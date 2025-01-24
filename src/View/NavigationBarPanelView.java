package View;

import Model.RoleModel;
import Model.UserModel;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
public class NavigationBarPanelView {
    private final JPanel navBar;
    private final JPanel logoPanel;
    private static Clip clip;
    JWindow transparentW ;
    long second;
    JFrame introFrame;
    public NavigationBarPanelView() {
        navBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        navBar.setBackground(PaletteView.MainColor);
        Dimension fixedSize = new Dimension(Integer.MAX_VALUE, 59);//55->60
        navBar.setPreferredSize(fixedSize);

        logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(PaletteView.MainColor);

        try {
            intro();
        } catch (Exception ex) {
            System.out.println("interrupted");
        }
        JButton ResNameL= new JButton("Dish Dash"); ResNameL.setBackground(PaletteView.MainColor); ResNameL.setBorder(null);
        ResNameL.setForeground(PaletteView.AdditionalColor.brighter());
        ResNameL.setFont(new Font(PaletteView.font,Font.PLAIN,30));
        ResNameL.addActionListener(e -> {
            transparentW.setVisible(true);
            introFrame.setVisible(true);

            if(clip!= null ){ if(clip.isRunning()) clip.stop(); // Ensure the clip is stopped before resetting
            clip.setMicrosecondPosition(0); // Reset to the beginning
            second = 0;}
        });
        logoPanel.add(ResNameL);
        Image  im = new ImageIcon("photos/Layer 3.png").getImage().getScaledInstance(33,30,Image.SCALE_SMOOTH);
        JLabel logoPic  = new JLabel( new ImageIcon(im),SwingConstants.CENTER); logoPanel.add(logoPic);
        navBar.add(logoPanel);
        navBar.add(Box.createRigidArea(new Dimension(200,0)));
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
        navBar.add(p);

        int width2 = 248; if(UserModel.getCurrentUser().getRole()==RoleModel.EMPLOYEE ) width2 = 345;
        else if(UserModel.getCurrentUser().getRole() == RoleModel.USER) width2 =452;

        navBar.add(Box.createRigidArea(new Dimension(width2,0)));

        RoundButtonView Xbutton = new RoundButtonView(0,"X",true); Xbutton.setBackground(PaletteView.MainColor); Xbutton.setFont(new Font("Arial",Font.BOLD,40));
        Xbutton.setBorder(null); Xbutton.setPreferredSize(new Dimension(26,26)); Xbutton.setBorder(BorderFactory.createEmptyBorder(0,0,10,10));
        Xbutton.addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                      System.exit(0);
                  }
              });
        navBar.add(Xbutton, BorderLayout.EAST);
    }


    public void reRender(String oldName) {
        Arrays.stream(navBar.getComponents())
                .filter(component -> (component instanceof JButton) && ((JButton) component).getText().equals(oldName))
                .findAny()
                .ifPresent(component -> {
                    JButton button = (JButton) component;
                    button.setText(UserModel.getCurrentUser().getName());
                });
        Arrays.stream(navBar.getComponents()).filter(component -> (component instanceof JPanel && !(component.equals(logoPanel))))
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

    public void intro() throws Exception {
        introFrame = new JFrame();
        introFrame.setLayout(new BorderLayout());
        introFrame.setPreferredSize(new Dimension(750, 500));
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.setUndecorated(true);
        introFrame.pack();
        introFrame.setLocationRelativeTo(null);

        transparentW = new JWindow();
        transparentW.setBackground(new Color(0,0,0,1));
        transparentW.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        transparentW.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                Rectangle frameBounds = introFrame.getBounds();

                if (!frameBounds.contains(e.getPoint())) {
                    introFrame.setVisible(false);
                    transparentW.setVisible(false);
                    if(clip!=null && clip.isRunning()) clip.stop();
                }
            }
        });

        JPanel fullP = new JPanel(new BorderLayout());
        fullP.setPreferredSize(new Dimension(introFrame.getWidth(),introFrame.getHeight()));
        fullP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                introFrame.setVisible(false);
                transparentW.setVisible(false);
                if(clip !=null && clip.isRunning()) clip.stop();
            }
        });

        JLabel gusteau = new JLabel(new ImageIcon(new ImageIcon("photos/hhhh.jpg").getImage().getScaledInstance(750,400,Image.SCALE_SMOOTH)));
        fullP.add(gusteau,BorderLayout.NORTH);

        ImageIcon imageIconR = new ImageIcon(new ImageIcon("photos/right.jpg").getImage().getScaledInstance(310,100,Image.SCALE_SMOOTH));
        ImageIcon imageIconL = new ImageIcon(new ImageIcon("photos/left.jpg").getImage().getScaledInstance(310,100,Image.SCALE_SMOOTH));

        JLabel l = new JLabel(imageIconL);
        JLabel r = new JLabel(imageIconR);
        JPanel downP =new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        downP.add(l);

        ImageIcon imageIconPause = new ImageIcon(new ImageIcon("photos/pause.jpg").getImage().getScaledInstance(150,150,Image.SCALE_SMOOTH));
        ImageIcon imageIconPlay = new ImageIcon(new ImageIcon("photos/play.jpg" ).getImage().getScaledInstance(150,150,Image.SCALE_SMOOTH));
        JButton button = new JButton(imageIconPlay);
        button.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        button.setPreferredSize(new Dimension(125,100));
        button.setBackground(new Color(233, 220, 194));
        button.addActionListener((e)-> {
            File audioFile = new File("photos/chefVoice.wav");
            try {

                if (clip == null) {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                    clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP && !clip.isRunning()) {

                            button.setIcon(imageIconPlay);
                            button.revalidate();
                            button.repaint();
                        }
                    });
                }
                if (!clip.isRunning()) {
                    button.setIcon(imageIconPause);button.revalidate();button.repaint();

                    if (second >= clip.getMicrosecondLength() || clip.getMicrosecondPosition() >= clip.getMicrosecondLength()) {
                        clip.stop();
                        clip.setMicrosecondPosition(0);
                        second = 0;
                    }
                    clip.setMicrosecondPosition(second);

                    clip.start();
                    if (second >= clip.getMicrosecondLength() || clip.getMicrosecondPosition() >= clip.getMicrosecondLength()) {
                        button.setIcon(imageIconPause);
                        button.revalidate();
                        button.repaint();

                    }
                } else {
                    button.setIcon(imageIconPlay);button.revalidate();button.repaint();

                    second = clip.getMicrosecondPosition();
                    clip.stop();
                }
            } catch (Exception eq) {
                eq.printStackTrace();
            }
        });
        downP.add(button); downP.add(r);
        fullP.add(downP);
        fullP.setBorder(BorderFactory.createDashedBorder(Color.BLACK,2,5,2,true));

        introFrame.add(fullP,BorderLayout.NORTH);
        introFrame.setVisible(false);
        transparentW.setVisible(false);

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
