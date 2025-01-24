package View;

import javax.swing.*;
import java.awt.*;
import Controller.*;

public class ReportsAndOrderView {
    static JPanel reports_and_OrderPanel;
    public static OrderStatusView orderStatusView = new OrderStatusView();
    static ReportView reportView = new ReportView();
    static JPanel dateP = new JPanel();
    static String lastDateShown ;

    public ReportsAndOrderView() {
        reportView = new ReportView();
        orderStatusView = new OrderStatusView();
        reports_and_OrderPanel = new JPanel(new BorderLayout());
        reports_and_OrderPanel.setBackground(Color.LIGHT_GRAY);
        reports_and_OrderPanel.setLayout(new FlowLayout());
        dateP = new JPanel();
        dateP.setBackground(Color.WHITE);
        dateP.setPreferredSize(new Dimension(1920 - 385, 60));
        lastDateShown = ReportServiceController.currentReport.getDate();
        JLabel date = new JLabel(lastDateShown);
        date.setFont(new Font(PaletteView.font, Font.PLAIN, 36));
        date.setBackground(Color.WHITE);
        dateP.add(date, BorderLayout.CENTER);
        reports_and_OrderPanel.add(dateP);
        reports_and_OrderPanel.add(reportView.reportsView);
        reports_and_OrderPanel.add(orderStatusView.orderStatusPanel);
    }

    public JPanel getReports_and_OrderPanel() {
        return reports_and_OrderPanel;
    }

    public static void updateView() {
        reports_and_OrderPanel.remove(reportView.reportsView);
        reports_and_OrderPanel.remove(orderStatusView.orderStatusPanel);
        if(!lastDateShown.equals( ReportServiceController.currentReport.getDate())) {
            lastDateShown =  ReportServiceController.currentReport.getDate();
            dateP.removeAll();
            JLabel date = new JLabel(lastDateShown);
            date.setFont(new Font(PaletteView.font, Font.PLAIN, 36));
            date.setBackground(Color.WHITE);
            dateP.add(date, BorderLayout.CENTER);
        }
        reportView = new ReportView();
        orderStatusView = new OrderStatusView();
        reports_and_OrderPanel.add(reportView.reportsView);
        reports_and_OrderPanel.add(orderStatusView.orderStatusPanel);
        reports_and_OrderPanel.revalidate();
        reports_and_OrderPanel.repaint();
    }
}
