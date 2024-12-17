import java.io.Serial;
import java.util.ArrayList;
import java.io.Serializable;

public class DeliveryOrder extends Order implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    String address;

    public DeliveryOrder(ArrayList<Meal> orderList, double tip, int orderID, String status, boolean isCancelable, String address) throws CustomException {
        super(orderList, tip, orderID, status, isCancelable);
        this.address = address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }
    @Override
    public void billInfo() {
        System.out.println("Order Type: " + "Delivery Order");
        System.out.println("Address: " + this.address);
        System.out.println("Meals required : " + getOrderList().size());
        System.out.println("Order Details:");
        for (Meal m : getOrderList()) {
            System.out.println(m.getMealName() + ": " + m.getMealPrice() + "$");
        }
        System.out.println("Total Price : " + bill() + "$");
    }
}
