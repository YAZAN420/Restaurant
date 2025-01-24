package Controller;

import java.io.*;
import java.util.Map;

public class ReportHandleController {
    @SuppressWarnings("static-access")
    public static void loadReport() {
        try (BufferedReader br = new BufferedReader(new FileReader("txtFiles/Reports.txt"))) {
            String line = br.readLine();
            String[] data = line.split(":");
            ReportServiceController.currentReport.setDate(data[0].trim());
            ReportServiceController.currentReport.setDailyOrderCounter(Integer.parseInt(data[1].trim()));
            ReportServiceController.currentReport.setDailyReturns(Double.parseDouble(data[2].trim()));
            ReportHandleController.load2Map(ReportServiceController.customerOrdersNumber,
                    "txtFiles/CustomerOrdersNumber.txt");
            ReportHandleController.load2Map(ReportServiceController.currentReport.mealsCounter,
                    "txtFiles/ReportMealsCounter.txt");
            ReportServiceController.currentReport.setMostOrderedMeal(
                    ReportServiceController.findMaxValue(ReportServiceController.currentReport.mealsCounter));
            ReportServiceController.currentReport.setMostFrequentCustomers(
                    ReportServiceController.findMaxValue(ReportServiceController.customerOrdersNumber));
            saveReport();
        } catch (FileNotFoundException e) {
            System.err.println("file not found " + e.getMessage());
        } catch (IOException e) {
            System.err.println("io exception " + e.getMessage());
        }
    }

    public static void saveReport() {
        try (FileWriter fw = new FileWriter("txtFiles/Reports.txt")) {
            fw.write(ReportServiceController.currentReport.getDate());
            fw.write(" : ");
            fw.write(String.valueOf(ReportServiceController.currentReport.getDailyOrderCounter()));
            fw.write(" : ");
            fw.write(String.valueOf(ReportServiceController.currentReport.getDailyReturns()));
            fw.write(" : ");
            fw.write(String.valueOf(ReportServiceController.currentReport.getMostOrderedMeal()));
            fw.write(" :");
            fw.write(String.valueOf(ReportServiceController.currentReport.getMostFrequentCustomers()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            ;
        }
    }

    public static void load2Map(Map<String, Integer> map, String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("=");
                map.computeIfAbsent(data[0].trim(), k -> Integer.parseInt(data[1].trim()));
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("An error occurred while reading/writing files: " + e.getMessage());
        }
    }

    public static void save2Map(Map<String, Integer> map, String fileName) {
        try (FileWriter fw = new FileWriter(fileName)) {
            for (Map.Entry<String, Integer> m : map.entrySet()) {
                fw.write(m.toString() + "\n");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
