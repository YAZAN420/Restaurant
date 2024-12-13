import java.util.ArrayList;
public class DeliveryOrder extends Order {
    String address;
    public DeliveryOrder(ArrayList<Meal> orderList, double tip, String address) throws CustomException {
        super(orderList, tip);
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
