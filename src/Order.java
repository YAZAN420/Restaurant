import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public abstract class Order implements Serializable{

   private static List<Order> orders = new ArrayList<>();
   private final ArrayList<OrderStatusListener> listeners = new ArrayList<>();
    @Serial
    private static final long serialVersionUID = 1L;
    private ArrayList<Meal> orderList ;
    String dateOfOrder ;
    private double tip;
    private final int orderID;
    private String status;
    private boolean isCancelable;
    private String notes;
    private int time;
    static int counter=1;
    private transient OrderTimer orderTimer;
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
        OrderSystem.notifyListeners(listeners,this);
        this.orderTimer = new OrderTimer(this);
        this.orderTimer.startOrderProcessing(time);
    }
    public void cancelOrder() {
        if (isCancelable) {
            orderTimer.cancelOrder();
            setStatus("Canceled");
        }
    }
    public void setStatus(String newStatus) {
        this.status = newStatus;
        OrderSystem.notifyListeners(listeners,this);
    }
    public void setCancelable(boolean cancelable) {
        isCancelable = cancelable;
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
    public void setNotes(String notes) {this.notes = notes;}
    public void setTime(int time) {this.time = time;}
    public ArrayList<Meal> getOrderList() {return orderList;}
    public double getTip() {return tip;}
    public int getOrderID() {return orderID;}
    public String getStatus() {return status;}
    public boolean isCancelable() {return isCancelable;}
    public String getNotes() {return notes;}
    public int getTime() {return time;}
    public ArrayList<OrderStatusListener> getListeners() {return listeners;}
}
