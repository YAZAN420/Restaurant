import java.io.*;
import java.util.Map;
public class ReportHandle {
    @SuppressWarnings("static-access")
    public static void loadReport() {
        try (BufferedReader br = new BufferedReader(new FileReader("Reports.txt"))) {
            String line = br.readLine();
            String[] data = line.split(":");
            ReportService.currentReport.setDate(data[0].trim());
            ReportService.currentReport.setDailyOrderCounter(Integer.parseInt(data[1].trim()));
            ReportService.currentReport.setDailyReturns(Double.parseDouble(data[2].trim()));
            ReportHandle.load2Map(ReportService.currentReport.mealsCounter,"ReportMealsCounter.txt");
        } catch (FileNotFoundException e) {
          System.err.println("file not found "+e.getMessage());
        } catch (IOException e) {
            System.err.println("io exception "+e.getMessage());
        }
    }
    public static void saveReport() {
        try (FileWriter fw = new FileWriter("Reports.txt")) {
            fw.write(ReportService.currentReport.getDate());
            fw.write(" : ");
            fw.write(String.valueOf(ReportService.currentReport.getDailyOrderCounter()));
            fw.write(" : ");
            fw.write(String.valueOf(ReportService.currentReport.getDailyReturns()));
            fw.write(" : ");
            fw.write(String.valueOf(ReportService.currentReport.getMostOrderedMeal()));
            fw.write(" :");
            fw.write(String.valueOf(ReportService.currentReport.getMostFrequentCustomers()));
        } catch (IOException e) {
            System.err.println(e.getMessage());;
        }
    }

    public static void load2Map(Map<String, Integer> map, String fileName) {
        //ReportService.customerOrdersNumber
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
            // Handle further
        }
    }
    public static void save2Map(Map<String,Integer> map,String fileName) {     // here
        try (FileWriter fw = new FileWriter(fileName)) {
            for (Map.Entry<String, Integer> m : map.entrySet()) {
                fw.write(m.toString()+"\n");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
