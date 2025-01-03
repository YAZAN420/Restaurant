import java.io.Serial;
import java.util.ArrayList;
import java.io.Serializable;

public class DineInOrder extends Order implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    int tableNumber;

    public DineInOrder(ArrayList<Meal> orderList, double tip, int orderID, String status, boolean isCancelable, int tableNumber, String notes, int time) throws CustomException {
        super(orderList, tip, orderID, status, isCancelable, notes, time);
        setTableNumber(tableNumber);
    }
    public void setTableNumber(int tableNumber) throws CustomException {
        if (tableNumber <= 0 || tableNumber > 150)
            throw new CustomException("Error: Table number is wrong, please enter number between 1 to 150 ");
        else
            this.tableNumber = tableNumber;
    }
    public int getTableNumber() {
        return tableNumber;
    }

}
