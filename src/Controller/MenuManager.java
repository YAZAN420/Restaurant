import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MenuManager {
    private static Map<String, List<Meal>> menu = new ConcurrentHashMap<>();
    static final Scanner scan = new Scanner(System.in);
    public void deleteFromMenu(String inCategory , String nameDelete){
        User.checkPermission(User.getCurrentUser().getRole(), Permission.DELETE_MEAL);
        inCategory = formatString(inCategory);
        nameDelete = formatString(nameDelete);
        Meal meal = findMeal(inCategory,nameDelete);
        if(meal == null ) return;
        menu.get(inCategory).remove(meal);
        MenuHandle.saveMenu(menu);
    }
    public void addToMenu(String category,String mealName,String ingredients,Double mealPrice,int minutesNeeded) throws IOException,IllegalArgumentException {
        User.checkPermission(User.getCurrentUser().getRole(), Permission.ADD_MEAL);
        category = formatString(category);
        mealName = formatString(mealName);
            if (!validMealAddition(mealName,category))
                throw new IllegalArgumentException("meal already exists !");
            if ((mealPrice) < 0)
                throw new IllegalArgumentException("price can't be negative ");
            if ((minutesNeeded) < 0)
                throw new IllegalArgumentException("minutes can't be negative");
            String imagePath = "photos\\" + mealName + ".jpg";
            menu.computeIfAbsent(category, k -> new ArrayList<>()).add(new Meal(mealName,category, ingredients, mealPrice,minutesNeeded,imagePath));
            MenuHandle.saveMenu(menu);
    }
    public void editFromMenu(String oldMealName,String oldCategory,String category, String mealName, String ingredients, Double mealPrice,
            int minutesNeeded, String path) {
        User.checkPermission(User.getCurrentUser().getRole(), Permission.EDIT_MEAL);
        oldCategory = formatString(oldCategory);
        oldMealName = formatString(oldMealName);
        Meal meal = findMeal(oldCategory, oldMealName);
        try {
            if (meal == null)
                throw new IllegalArgumentException("invalid meal name");
            editName(meal, mealName);
            editIngredients(meal, ingredients);
            editPrice(meal, mealPrice);
            editMinutesNeeded(meal, minutesNeeded);
            editPhotoPath(meal, path);
            if(!oldCategory.equals(category)) { editCategory(meal,oldCategory, category);}
            MenuHandle.saveMenu(menu);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public void editCategory(Meal meal,String oldCategory , String cat) {
       if(validMealAddition(meal.getMealName(),formatString(cat))){
           menu.get(oldCategory).remove(meal);
           meal.setCategory(formatString(cat));
        menu.computeIfAbsent(formatString(cat), k -> new ArrayList<>()).add(meal);}
        else throw new IllegalArgumentException("already exists there");
    }
    public void editName(Meal meal,String name){
        meal.setMealName(formatString(name));
    }
    public void editIngredients(Meal meal,String ingredients){
        meal.setIngredients(ingredients);
    }
    public void editPrice(Meal meal,Double mealPrice) throws IllegalArgumentException{
        try{
            if(mealPrice <=0 ) throw new IllegalArgumentException("price should be > 0");
            meal.setMealPrice(mealPrice);}
        catch (NumberFormatException e) {
            System.err.println("Invalid number format! Please try again.");}
    }

    public void editMinutesNeeded(Meal meal, int minutesNeeded) throws IllegalArgumentException {
        if (minutesNeeded <= 0)
            throw new IllegalArgumentException("minutes should be > 0");
        meal.setMinutesNeeded(minutesNeeded);
    }
    public void editPhotoPath(Meal meal,String path) throws IllegalArgumentException{
        meal.setImagePath(path);
    }
    private Meal findMeal(String category, String mealName) {
        List<Meal> meals = menu.get(category);
        if (meals == null) {
            return null;
        }
        return meals.stream().filter(meal -> meal.getMealName().equals(mealName)).findAny().orElse(null);
    }
    public Meal callfindMeal(String category, String mealName) {
        return findMeal(category,mealName);
    }
    public boolean validMealAddition(String name,String category){
        List<Meal> meals = menu.get(category);
        return meals == null || meals.stream().noneMatch(meal -> meal.getMealName().equals(name));
    }
    private String formatString(String string) {
        return (string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase()).trim();
    }
    public static Map<String, List<Meal>> getMenu() {
        return menu;
    }
    public static void setMenu(Map<String, List<Meal>> menu) {
        MenuManager.menu = menu;
    }
}