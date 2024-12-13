import java.util.ArrayList;
public abstract class Order {
    private ArrayList<Meal> orderList = new ArrayList<>();
    private double tip;
    static int counter=1;
    public Order(ArrayList<Meal> orderList, double tip) throws CustomException {
        setOrderList(orderList);
        setTip(tip);
        counter++;
    }
    public double bill(){
        double fullPrice = 0;
        for(Meal m : orderList){
            fullPrice += m.getMealPrice();
        }
        return Math.round((fullPrice + tip) * 100.0) / 100.0;
    }
    public abstract void billInfo();
    public void setOrderList(ArrayList<Meal> orderList) throws CustomException {
        if(orderList != null && !orderList.isEmpty())
        this.orderList = orderList;
        else
        throw new CustomException("The order list is invalid or empty.");
    }
    public void setTip(double tip) throws CustomException {
        if(tip>=0)
        this.tip = tip;
        else
        throw new CustomException("The tip amount must be a positive number.");    }
    public ArrayList<Meal> getOrderList() {
        return orderList;
    }
    public double getTip() {
        return tip;
    }
}
