import java.io.Serial;
import java.io.Serializable;

public class Meal implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String mealName;
    private String category;
    private String ingredients;
    private double mealPrice;
    private int minutesNeeded;
    public Meal() {}
    public Meal(String mealName, String ingredients, double mealPrice, int minutesNeeded) {
        this.mealName = mealName;
        this.ingredients = ingredients;
        this.mealPrice = mealPrice;
        this.minutesNeeded = minutesNeeded;
    }
    @Override
    public String toString() {
        return "= " + mealName +
                ": " + ingredients +
                ": " + mealPrice +
                ":" + minutesNeeded + '\n';
    }
    public String getMealName() {
        return mealName;
    }
    public void setMealName(String mealName) {
        this.mealName = mealName;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String classification) {
        this.category = classification;
    }
    public String getIngredients() {
        return ingredients;
    }
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
    public double getMealPrice() {
        return mealPrice;
    }
    public void setMealPrice(double mealPrice) {
        this.mealPrice = mealPrice;
    }
    public int getMinutesNeeded() {
        return minutesNeeded;
    }
    public void setMinutesNeeded(int minutesNeeded) {
        this.minutesNeeded = minutesNeeded;
    }
}
