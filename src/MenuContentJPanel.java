import javax.swing.*;
import java.awt.*;
public class MenuContentJPanel extends JPanel {
    static String selectedCategory = "Pizza";
    int row,height ,empty_squares;
    JPanel mealContainer ;
    static JScrollPane dsp;
    public MenuContentJPanel(String selectedCategory){
        setLayout(new BorderLayout());
        mealContainer = new JPanel();
        row = setCol();
        height = 300*setCol();
        mealContainer.setPreferredSize(new Dimension(300, height));
        if(row == 1) row =2;
        mealContainer.setLayout(new GridLayout(row,4,5,5));
        mealContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        dsp = new JScrollPane(mealContainer);
        dsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        dsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        add(dsp, BorderLayout.CENTER);
        setSelectedCategory(selectedCategory);
    }
    public void createSquarePanels(){
        mealContainer.removeAll();
        if(selectedCategory != null) {
            java.util.List<Meal> meals = MenuManager.getMenu().get(selectedCategory);
            if (meals == null) return;
            for (Meal m : meals) {
                MealPanel mealPanel = new MealPanel(m);
                mealContainer.add(mealPanel);
            }
            empty_squares = row * 4 - meals.size();
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
        height = 300*setCol();
        mealContainer.setPreferredSize(new Dimension(350, height));
        mealContainer.setLayout(new GridLayout(row,4,10,10));
        createSquarePanels();
    }
    public int setCol(){
        if(selectedCategory== null || (!MenuManager.getMenu().containsKey(selectedCategory))) return 1;
        int size = MenuManager.getMenu().get(selectedCategory).size();
        return (size/5+1);
    }
}

