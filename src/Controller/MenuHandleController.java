package Controller;

import Model.MealModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class MenuHandleController {
    private static final File menuFile = new File("txtFiles/menuFile.txt");

    public static Map<String, List<MealModel>> loadMenu() {
        if (!menuFile.exists() || menuFile.length() == 0)
            return new ConcurrentHashMap<>();
        Map<String, List<MealModel>> menu = new ConcurrentHashMap<>();
        try (Scanner fileRead = new Scanner(menuFile)) {
            String category = null, mealName, ingredients, imagePath;
            Double mealPrice;
            int minutesNeeded;
            while (fileRead.hasNextLine()) {
                String line = fileRead.nextLine().trim();
                if (line.startsWith("*")) {
                    category = line.substring(2).trim();
                    menu.computeIfAbsent(category, k -> new ArrayList<>());
                } else if (line.startsWith("=")) {
                    String[] menuParts = line.split(":", 5);
                    if (menuParts.length == 5) {
                        mealName = menuParts[0].substring(2).trim();
                        ingredients = menuParts[1].trim();
                        mealPrice = Double.parseDouble(menuParts[2].trim());
                        minutesNeeded = Integer.parseInt(menuParts[3].trim());
                        imagePath = menuParts[4].trim();
                        menu.get(category).add(
                                new MealModel(mealName, category, ingredients, mealPrice, minutesNeeded, imagePath));
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Menu file could not be found: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.err.println("Error parsing number in menu file. Check format: " + ex.getMessage());
        }
        return menu;
    }

    public static void saveMenu(Map<String, List<MealModel>> menu) {
        try (FileWriter fw = new FileWriter(menuFile)) {
            for (Map.Entry<String, List<MealModel>> entry : menu.entrySet()) {
                fw.write("* " + entry.getKey() + '\n');
                for (MealModel m : entry.getValue()) {
                    fw.write(m.toString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing menu to file: " + e.getMessage());
        }
    }
}
