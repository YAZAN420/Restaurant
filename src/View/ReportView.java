import javax.swing.*;
import java.awt.*;

public class ReportView{
    JPanel reportsView;
    public ReportView(){
        ReportHandle.loadReport();
        reportsView = new JPanel();
        reportsView.setBackground(Color.lightGray);
        reportsView.setPreferredSize(new Dimension(1920-385,150));
        reportsView.setLayout(new GridLayout(1,4));

        JPanel Reports= new JPanel();
        Reports.setPreferredSize(new Dimension(1920-385,60));

        reportsView.add(partOfReport("Number of today's orders : ",String.valueOf(ReportService.currentReport.getDailyOrderCounter())));
        reportsView.add(partOfReport("Today's returns : ",String.format("%.2f",ReportService.currentReport.getDailyReturns())));
        String s = String.valueOf(ReportService.currentReport.getMostOrderedMeal());
        reportsView.add(partOfReport("Most Ordered Meal : ",s.substring(1,s.length()-1)));
        s = String.valueOf(ReportService.currentReport.getMostFrequentCustomers());
        reportsView.add(partOfReport("Most Frequent Customer : ",s.substring(1,s.length()-1)));
    }
    public Component partOfReport(String contentTitle,String content){

        roundPanel panel = new roundPanel(new GridLayout(2,1));
        JLabel labelTitle = new JLabel(contentTitle,SwingConstants.CENTER);
        JLabel labelContent= new JLabel(content,SwingConstants.CENTER);

        labelTitle.setFont(new Font("Georgia",Font.BOLD,23));
        labelTitle.setBackground(Color.LIGHT_GRAY);

        labelContent.setFont(new Font("Georgia",Font.BOLD,20));
        panel.repaint();

        panel.add(labelTitle);
        panel.add(labelContent);
        CustomScrollPane csp = new CustomScrollPane(panel,Color.LIGHT_GRAY,Color.DARK_GRAY);
        csp.setBorder(null);
        return csp;
    }
    static class roundPanel extends JPanel{
        public roundPanel(LayoutManager L){
            super(L);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.WHITE);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.fillRoundRect(7,2,getWidth()-15,145,13,13);
        }
    }

}
