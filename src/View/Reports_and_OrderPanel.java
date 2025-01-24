import javax.swing.*;
import java.awt.*;

public class Reports_and_OrderPanel  {
    static JPanel reports_and_OrderPanel;
    static OrderStatusView orderStatusView = new OrderStatusView();
    static ReportView reportView = new ReportView();
    static JPanel dateP = new JPanel();
    public Reports_and_OrderPanel() {
        reportView = new ReportView();
        orderStatusView = new OrderStatusView();
        reports_and_OrderPanel = new JPanel(new BorderLayout());
        reports_and_OrderPanel.setBackground(Color.LIGHT_GRAY);
        reports_and_OrderPanel.setLayout(new FlowLayout());
        dateP = new JPanel();
        dateP.setBackground(Color.WHITE);
        dateP.setPreferredSize(new Dimension(1920 - 385, 60));
        JLabel date = new JLabel(ReportService.currentReport.getDate());
        date.setFont(new Font("Georgia", Font.PLAIN, 36));
        date.setBackground(Color.WHITE);
        dateP.add(date, BorderLayout.CENTER);
        reports_and_OrderPanel.add(dateP);
        reports_and_OrderPanel.add(reportView.reportsView);
        reports_and_OrderPanel.add(orderStatusView.orderStatusPanel);
    }
    public JPanel getReports_and_OrderPanel(){
        return reports_and_OrderPanel;
    }
    public static void updateView(){
        reports_and_OrderPanel.remove(reportView.reportsView);
        reports_and_OrderPanel.remove(orderStatusView.orderStatusPanel);
        reportView = new ReportView();
        orderStatusView= new OrderStatusView();
        reports_and_OrderPanel.add(reportView.reportsView);
        reports_and_OrderPanel.add(orderStatusView.orderStatusPanel);
        reports_and_OrderPanel.revalidate();
        reports_and_OrderPanel.repaint();
    }
}
