package Model;

import java.io.Serial;
import java.util.ArrayList;
import java.io.Serializable;

@SuppressWarnings("unused")
public class DeliveryOrderModel extends OrderModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    String address;

    public DeliveryOrderModel(ArrayList<MealModel> orderList, double tip, int orderID, String status,
            boolean isCancelable, String address, String notes, int time, String nameUser) {
        super(orderList, tip, orderID, status, isCancelable, notes, time, nameUser);
        this.address = address;

    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

}
