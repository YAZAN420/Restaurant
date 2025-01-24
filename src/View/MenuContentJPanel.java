import javax.swing.*;
import java.awt.*;
public class MenuContentJPanel extends JPanel {
    static String selectedCategory = "Pizza";
    int row,height ,empty_squares,col=5;
    JPanel mealContainer ;
    static CustomScrollPane dsp;
    public MenuContentJPanel(String selectedCategory){
        setLayout(new BorderLayout());
        mealContainer = new JPanel();
        row = setCol();
        height = 400*setCol();
        mealContainer.setPreferredSize(new Dimension(350, height));
        if(row == 1) row =2;
        mealContainer.setLayout(new GridLayout(row,col,5,5));
        mealContainer.setBorder(BorderFactory.createEmptyBorder(40, 15, 35, 25));
        dsp = new CustomScrollPane(mealContainer, new Color(67, 86, 38),new Color(85, 107, 47, 69));
        dsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        dsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        add(dsp, BorderLayout.CENTER);
        setSelectedCategory(selectedCategory);
    }
    public void createSquarePanels(){
        mealContainer.removeAll();
        mealContainer.setBackground(Palette.BackGround);

        if(selectedCategory != null) {
            java.util.List<Meal> meals = MenuManager.getMenu().get(selectedCategory);
            if (meals == null) return;
            for (Meal m : meals) {
                MealPanel mealPanel = new MealPanel(m);
                mealContainer.add(mealPanel);
            }
            empty_squares = row * col  - meals.size();
            if(User.getCurrentUser().getRole()!=Role.USER)
            {if(empty_squares>0) {empty_squares--; mealContainer.add(new MealPanel("photos/add-item-grey1.png"));}}
            for (int i = 0; i < empty_squares; i++) {
                mealContainer.add(new MealPanel());
            }

        }
        revalidate();
        repaint();
    }
    @SuppressWarnings("static-access")
    public void setSelectedCategory(String category){
        this.selectedCategory = category;
        row = setCol();
        if(row==1) row= 2;
        height = 400*setCol();
        mealContainer.setPreferredSize(new Dimension(350, height));
        mealContainer.setLayout(new GridLayout(row,col,10,10));
        createSquarePanels();
    }
    public static String getSelectedCategory() {
        return selectedCategory;
    }
    public int setCol(){
        if(selectedCategory== null || (!MenuManager.getMenu().containsKey(selectedCategory))) return 1;
        int size = MenuManager.getMenu().get(selectedCategory).size();
        if(User.getCurrentUser().getRole()!= Role.USER) size+=1;
        int res = size/col; if(size%col!=0) res++;
        return res;
    }
}

