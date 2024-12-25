import java.time.LocalDate;
import java.util.*;

public class Report {
     private String date = String.valueOf(LocalDate.now());
     private double dailyReturns = 0 ;
     private int dailyOrderCounter = 0 ;
     private List <String> mostOrderedMeal ;
     private List <String> mostFrequentCustomers;
     static Map<String, Integer> mealsCounter = new HashMap<>() ;

    public Report() {}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDailyReturns() {
        return dailyReturns;
    }

    public void setDailyReturns(double dailyReturns) {
        this.dailyReturns = dailyReturns;
    }

    public int getDailyOrderCounter() {
        return dailyOrderCounter;
    }

    public void setDailyOrderCounter(int dailyOrderCounter) {
        this.dailyOrderCounter = dailyOrderCounter;
    }

    public Map<String, Integer> getMealsCounter() {
        return mealsCounter;
    }

    public void setMealsCounter(Map<String, Integer> mealsCounter) {
        Report.mealsCounter = mealsCounter;
    }

    public List<String> getMostOrderedMeal() {
        return mostOrderedMeal;
    }

    public void setMostOrderedMeal(List<String> mostOrderedMeal) {
        this.mostOrderedMeal = mostOrderedMeal;
    }

    public List<String> getMostFrequentCustomers() {
        return mostFrequentCustomers;
    }

    public void setMostFrequentCustomers(List<String> mostFrequentCustomers) {
        this.mostFrequentCustomers = mostFrequentCustomers;
    }
}

