import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.io.Serializable;

public abstract class Order implements Serializable{
   private static List<Order> orders = new ArrayList<>();
    @Serial
    private static final long serialVersionUID = 1L;
    private ArrayList<Meal> orderList ;
    private double tip;
    private final int orderID;
    private String status;
    private boolean isCancelable;
    private String notes;
    private int time;
    static int counter=1;
    public Order(ArrayList<Meal> orderList, double tip, int orderID, String status, boolean isCancelable, String notes, int time) throws CustomException {
        setOrderList(orderList);
        setTip(tip);
        this.orderID = orderID;
        this.status = status;
        this.isCancelable = isCancelable;
        this.notes = notes;
        this.time = time;
        counter++;
        orders.add(this);
        notifyListeners();
        startOrderProcessing();
    }
    private final ArrayList<OrderStatusListener> listeners = new ArrayList<>();
    public void addListener(OrderStatusListener listener) {
        listeners.add(listener);
    }
    public void setStatus(String newStatus) {
        this.status = newStatus;
        notifyListeners();
    }
    private void notifyListeners() {
        for (OrderStatusListener listener : listeners) {
            listener.onStatusChanged(this, status);
        }
    }
    public void setCancelable(boolean cancelable) {
        isCancelable = cancelable;
    }
    private transient final Timer timer = new Timer();
    private void startOrderProcessing() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isCancelable = false;
                setStatus("Prepared");
            }
        }, time*1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isCancelable = false;
                setStatus("Delivered");
            }
        }, time + 10000);
    }
    public void cancelOrder() {
        if (isCancelable) {
            timer.cancel();
            setStatus("Canceled");
        }
    }
    public double bill(){
        double fullPrice = 0;
        for(Meal m : orderList){
            fullPrice += m.getMealPrice();
        }
        return Math.round((fullPrice + tip) * 100.0) / 100.0;
    }
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
        throw new CustomException("The tip amount must be a positive number.");
    }
    public ArrayList<Meal> getOrderList() {return orderList;}
    public double getTip() {return tip;}
    public int getOrderID() {return orderID;}
    public String getStatus() {return status;}
    public boolean isCancelable() {return isCancelable;}
    public void setNotes(String notes) {this.notes = notes;}
    public String getNotes() {return notes;}
}
