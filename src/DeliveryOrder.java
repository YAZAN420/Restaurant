import java.io.Serial;
import java.util.ArrayList;
import java.io.Serializable;

public class DeliveryOrder extends Order implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    String address;

    public DeliveryOrder(ArrayList<Meal> orderList, double tip, int orderID, String status, boolean isCancelable, String address, String notes, int time) throws CustomException {
        super(orderList, tip, orderID, status, isCancelable, notes, time);
        this.address = address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }

}
