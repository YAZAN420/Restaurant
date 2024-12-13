import java.util.ArrayList;
public class DineInOrder extends Order {
    int tableNumber;
    public DineInOrder(ArrayList<Meal> orderList, double tip, int tableNumber) throws CustomException {
        super(orderList, tip);
        this.tableNumber = tableNumber;

    }
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
    public int getTableNumber() {
        return tableNumber;
    }
    @Override
    public void billInfo() {
            System.out.println("Order Type: " + "DineIn Order");
            System.out.println("Table Number: " + this.tableNumber);
            System.out.println("Meals required : " + getOrderList().size());
            System.out.println("Order Details:");
            for (Meal m : getOrderList()) {
                System.out.println(m.getMealName() + ": " + m.getMealPrice() + "$");
            }
            System.out.println("Total Price : " + bill() + "$");
    }
}
