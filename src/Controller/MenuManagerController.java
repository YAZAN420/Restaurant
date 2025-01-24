package Controller;

import Model.MealModel;
import Model.PermissionModel;
import Model.UserModel;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MenuManagerController {
    private static Map<String, List<MealModel>> menu = new ConcurrentHashMap<>();
    static final Scanner scan = new Scanner(System.in);

    public void deleteFromMenu(String inCategory, String nameDelete) {
        UserModel.checkPermission(UserModel.getCurrentUser().getRole(), PermissionModel.DELETE_MEAL);
        inCategory = formatString(inCategory);
        nameDelete = formatString(nameDelete);
        MealModel meal = findMeal(inCategory, nameDelete);
        if (meal == null)
            return;
        menu.get(inCategory).remove(meal);
        MenuHandleController.saveMenu(menu);
    }

    public void addToMenu(String category, String mealName, String ingredients, Double mealPrice, int minutesNeeded)
            throws IOException, IllegalArgumentException {
        UserModel.checkPermission(UserModel.getCurrentUser().getRole(), PermissionModel.ADD_MEAL);
        category = formatString(category);
        mealName = formatString(mealName);
        if (!validMealAddition(mealName, category))
            throw new IllegalArgumentException("meal already exists !");
        if ((mealPrice) <= 0)
            throw new IllegalArgumentException("price can't be negative ");
        if ((minutesNeeded) <= 0)
            throw new IllegalArgumentException("minutes can't be negative");
        String imagePath = "photos\\" + mealName + ".jpg";
        menu.computeIfAbsent(category, k -> new ArrayList<>())
                .add(new MealModel(mealName, category, ingredients, mealPrice, minutesNeeded, imagePath));
        MenuHandleController.saveMenu(menu);
    }

    public void editFromMenu(String oldMealName, String oldCategory, String category, String mealName,
            String ingredients, Double mealPrice,
            int minutesNeeded, String path) throws IllegalArgumentException {
        UserModel.checkPermission(UserModel.getCurrentUser().getRole(), PermissionModel.EDIT_MEAL);
        oldCategory = formatString(oldCategory);
        oldMealName = formatString(oldMealName);
        try {
            MealModel meal = findMeal(oldCategory, oldMealName);
            if (meal == null)
                throw new IllegalArgumentException("invalid meal name");
            editName(meal, mealName);
            editIngredients(meal, ingredients);
            editPrice(meal, mealPrice);
            editMinutesNeeded(meal, minutesNeeded);
            editPhotoPath(meal, path);
            if (!oldCategory.equals(category)) {
                editCategory(meal, oldCategory, category);
            }
            MenuHandleController.saveMenu(menu);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void editCategory(MealModel meal, String oldCategory, String cat) throws IllegalArgumentException {
        if (validMealAddition(meal.getMealName(), formatString(cat))) {
            menu.get(oldCategory).remove(meal);
            meal.setCategory(formatString(cat));
            menu.computeIfAbsent(formatString(cat), k -> new ArrayList<>()).add(meal);
        } else
            throw new IllegalArgumentException("already exists there");
    }

    public void editName(MealModel meal, String name) throws IllegalArgumentException {
        if (!meal.getMealName().equals(name) && !validMealAddition(name, meal.getCategory())) {
            throw new IllegalArgumentException("meal already exists !");
        }
        meal.setMealName(formatString(name));
    }

    public void editIngredients(MealModel meal, String ingredients) {
        meal.setIngredients(ingredients);
    }

    public void editPrice(MealModel meal, Double mealPrice) throws IllegalArgumentException {
        try {
            if (mealPrice <= 0)
                throw new IllegalArgumentException("price should be positive ");
            meal.setMealPrice(mealPrice);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format! Please try again.");
        }
    }

    public void editMinutesNeeded(MealModel meal, int minutesNeeded) throws IllegalArgumentException {
        if (minutesNeeded <= 0)
            throw new IllegalArgumentException("minutes should be positive");
        meal.setMinutesNeeded(minutesNeeded);
    }

    public void editPhotoPath(MealModel meal, String path) throws IllegalArgumentException {
        meal.setImagePath(path);
    }

    private MealModel findMeal(String category, String mealName) {
        List<MealModel> meals = menu.get(category);
        if (meals == null) {
            return null;
        }
        return meals.stream().filter(meal -> meal.getMealName().equals(mealName)).findAny().orElse(null);
    }

    public MealModel callfindMeal(String category, String mealName) {
        return findMeal(category, mealName);
    }

    public boolean validMealAddition(String name, String category) {
        List<MealModel> meals = menu.get(category);
        return meals == null || meals.stream().noneMatch(meal -> meal.getMealName().equals(name));
    }

    private String formatString(String string) {
        return (string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase()).trim();
    }

    public static Map<String, List<MealModel>> getMenu() {
        return menu;
    }

    public static void setMenu(Map<String, List<MealModel>> menu) {
        MenuManagerController.menu = menu;
    }
}