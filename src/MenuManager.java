import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
public class MenuManager {
    private static Map<String, List<Meal>> menu = new ConcurrentHashMap<>();
    static final Scanner scan = new Scanner(System.in);
    public void deleteFromMenu(String inCategory , String nameDelete){
        inCategory=formatCategory(inCategory);
        Meal meal = findMeal(inCategory,nameDelete);
        if(meal == null ) return;
        menu.get(inCategory).remove(meal);
        MenuHandle.saveMenu(menu);
    }
    public void addToMenu () throws IOException {
        System.out.println("category:");
        String category = scan.nextLine();
        category=formatCategory(category);
        String mealName , ingredients ;
        Double mealPrice;
        int minutesNeeded;
        System.out.println("name: ");
        try {
            if (!validMealAddition(mealName = scan.nextLine(), category))
                throw new IllegalArgumentException("meal already exists !");
            System.out.println("ingredients: ");
            ingredients = scan.nextLine();
            System.out.println("price: >0");
            if ((mealPrice = scan.nextDouble()) <= 0)
                throw new IllegalArgumentException("price cant be <= 0 ");
            System.out.println("minutes needed: >0");
            if ((minutesNeeded = scan.nextInt()) < 0)
                throw new IllegalArgumentException("minutes cant be <0");
            scan.nextLine();
            menu.computeIfAbsent(category, k -> new ArrayList<>()).add(new Meal(mealName, ingredients, mealPrice,minutesNeeded));
            MenuHandle.saveMenu(menu);
        } catch (IllegalArgumentException e) {
            System.err.println("error: "+ e.getMessage());
        }
    }
    public void editFromMenu(){
        System.out.println("choose category");
        String category = scan.nextLine();
        category=formatCategory(category);
        System.out.println("choose name");
        String name = scan.nextLine();
            Meal meal = findMeal(category, name);
            try {
                if (meal == null) throw new IllegalArgumentException("invalid meal name");
            System.out.println("choose to edit: name / ingredients / price / minutes ");
            String choice = scan.nextLine();
            if (choice.equals("name")) editName(meal);
            else if (choice.equals("ingredients")) editIngredients(meal);
            else if (choice.equals("price")) editPrice(meal);
            else if (choice.equals("minutes")) editMinutesNeeded(meal);
            else System.err.println("Invalid choice! Please try again.");}
            catch (IllegalArgumentException e) {
               System.err.println("something wrong "+e.getMessage());
            }
    }
    public void editName(Meal meal){
        System.out.println("choose new name");
        String name=scan.nextLine();
        meal.setMealName(name);
        MenuHandle.saveMenu(menu);
    }
    public void editIngredients(Meal meal){
        System.out.println("type new ingredients");
        String ing=scan.nextLine();
        meal.setIngredients(ing);
        MenuHandle.saveMenu(menu);
    }
    public void editPrice(Meal meal) throws IllegalArgumentException{
        System.out.println("choose new price > 0");
        try{
        double price= scan.nextDouble();
        if(price <=0 ) throw new IllegalArgumentException("price should be > 0");
        meal.setMealPrice(price);}
        catch (NumberFormatException e) {
            System.err.println("Invalid number format! Please try again.");}

        MenuHandle.saveMenu(menu);
    }
    public void editMinutesNeeded(Meal meal) throws IllegalArgumentException{
        System.out.println("choose minutes needed");
        int min = scan.nextInt();
        if(min <= 0 ) throw new IllegalArgumentException("minutes should be > 0");
        meal.setMinutesNeeded(min);
        MenuHandle.saveMenu(menu);
    }

    private Meal findMeal(String category, String mealName) {
        category = formatCategory(category);
        List<Meal> meals = menu.get(category);
        if (meals == null) {
            System.out.println("Category not found: " + category);
            return null;
        }
        return meals.stream().filter(meal -> meal.getMealName().equals(mealName)).findAny().orElse(null);
    }
    public boolean validMealAddition(String name,String category){
            List<Meal> meals = menu.get(category);
            return meals == null || meals.stream().noneMatch(meal -> meal.getMealName().equals(name));
        }
    private String formatCategory(String category) {
        return (category.substring(0, 1).toUpperCase() + category.substring(1).toLowerCase()).trim();
    }
    public static Map<String, List<Meal>> getMenu() {
        return menu;
    }
    public static void setMenu(Map<String, List<Meal>> menu) {
        MenuManager.menu = menu;
    }
}