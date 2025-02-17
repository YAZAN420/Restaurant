package Controller;

import Model.MealModel;
import Model.ReportModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ReportServiceController {
    public static Map<String, Integer> customerOrdersNumber = new HashMap<>();
    public static ReportModel currentReport = new ReportModel();

    @SuppressWarnings("static-access")
    public static void mealsCounterFromOrderToReport(List<MealModel> orderedMeal) {
        for (MealModel meal : orderedMeal)
            currentReport.mealsCounter.merge(meal.getMealName(), 1, Integer::sum);
        ReportHandleController.save2Map(ReportServiceController.currentReport.mealsCounter,
                "txtFiles/ReportMealsCounter.txt");
    }

    @SuppressWarnings("static-access")
    public static void mealsCounterReset() {
        for (Map.Entry<String, Integer> m : currentReport.mealsCounter.entrySet()) {
            currentReport.mealsCounter.put(m.getKey(), 0);
        }
    }

    @SuppressWarnings("static-access")
    public void giveReportInfo(String orderDate, List<MealModel> orderMeals, double bill) {
        if (isInSameReport(orderDate)) {
            this.mealsCounterFromOrderToReport(orderMeals);
            this.currentReport.setMostOrderedMeal(findMaxValue(this.currentReport.mealsCounter));
            this.currentReport.setDailyReturns(this.currentReport.getDailyReturns() + bill);
            this.currentReport.setDailyOrderCounter(this.currentReport.getDailyOrderCounter() + 1);
            this.currentReport.setMostFrequentCustomers(findMaxValue(customerOrdersNumber));
        } else {
            this.currentReport.setDate(orderDate);
            this.mealsCounterReset();
            this.mealsCounterFromOrderToReport(orderMeals);
            this.currentReport.setDailyReturns(bill);
            this.currentReport.setDailyOrderCounter(1);
        }
        ReportHandleController.saveReport();
    }

    public static List<String> findMaxValue(Map<String, Integer> counter) {
        if (!counter.isEmpty()) {
            int max = counter.values().stream().max(Integer::compare)
                    .orElseThrow(() -> new NoSuchElementException("Map is empty"));
            List<String> maxValue = counter.entrySet().stream().filter(k -> max == k.getValue()).map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            return maxValue;
        }
        return null;
    }

    public boolean isInSameReport(String orderDate) {
        if (currentReport.getDate().trim().equals(orderDate.trim())) {
            return true;
        }
        return false;
    }

    public static void updateCustomerOrdersNumber(String oldName, String newName) {
        if (customerOrdersNumber.containsKey(oldName)) {
            int val = customerOrdersNumber.remove(oldName);
            customerOrdersNumber.put(newName, val);
            ReportHandleController.save2Map(ReportServiceController.customerOrdersNumber,
                    "txtFiles/CustomerOrdersNumber.txt");

        }
    }
}
