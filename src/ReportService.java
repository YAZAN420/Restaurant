import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ReportService{
    static Map <String,Integer> customerOrdersNumber = new HashMap<>();//BACK
    static Report currentReport = new Report();//BACK
    @SuppressWarnings("static-access")
    public static void mealsCounterFromOrderToReport(List <Meal> orderedMeal){
        for(Meal meal : orderedMeal)
            currentReport.mealsCounter.merge(meal.getMealName(), 1, Integer::sum);
        ReportHandle.save2Map(ReportService.currentReport.mealsCounter,"ReportMealsCounter.txt");
    }
    @SuppressWarnings("static-access")
    public static void mealsCounterReset(){
        for(Map.Entry <String,Integer> m : currentReport.mealsCounter.entrySet()){
            currentReport.mealsCounter.put(m.getKey(),0);
        }
    }
    @SuppressWarnings("static-access")
    public void giveReportInfo(String orderDate, List<Meal> orderMeals, double bill) {
        if (isInSameReport(orderDate)){
            this.mealsCounterFromOrderToReport(orderMeals);
            this.currentReport.setMostOrderedMeal(findMaxValue(this.currentReport.mealsCounter));
            this.currentReport.setDailyReturns(this.currentReport.getDailyReturns()+bill);
            this.currentReport.setDailyOrderCounter(this.currentReport.getDailyOrderCounter()+1);
            this.currentReport.setMostFrequentCustomers(findMaxValue(customerOrdersNumber));
        }else {
            this.currentReport.setDate(orderDate);
            this.mealsCounterReset();
            this.mealsCounterFromOrderToReport(orderMeals);
            this.currentReport.setDailyReturns(bill);
            this.currentReport.setDailyOrderCounter(1);
        }
        ReportHandle.saveReport();
    }
    public List<String> findMaxValue(Map<String,Integer> counter){//mealsCounter or customerOrderNumber
        if (!counter.isEmpty()){
            int max = counter.values().stream().max(Integer::compare).orElseThrow(() -> new NoSuchElementException("Map is empty"));
            List<String>maxMeals = counter.entrySet().stream().filter(k -> max==k.getValue()).map(Map.Entry::getKey).collect(Collectors.toList());
            return maxMeals;
        }
        else System.err.println("meals counter is empty");
        return null; // exception
    }
    public boolean isInSameReport(String orderDate) {
        if (currentReport.getDate().trim().equals(orderDate.trim())) {
            return true;
        }
        return false;
    }
}
