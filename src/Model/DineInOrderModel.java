package Model;

import Controller.CustomExceptionController;
import View.MainFrameView;

import java.io.Serial;
import java.util.ArrayList;
import java.io.Serializable;

@SuppressWarnings("unused")
public class DineInOrderModel extends OrderModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    int tableNumber;

    public DineInOrderModel(ArrayList<MealModel> orderList, double tip, int orderID, String status,
            boolean isCancelable,
            int tableNumber, String notes, int time, String nameUser) {
        super(orderList, tip, orderID, status, isCancelable, notes, time, nameUser);
        try {
            setTableNumber(tableNumber);
        } catch (CustomExceptionController e) {
            MainFrameView.notificationView.showNotification(e.getMessage(), true);
        }

    }

    public void setTableNumber(int tableNumber) throws CustomExceptionController {
        if (tableNumber <= 0 || tableNumber > 100)
            throw new CustomExceptionController("Error: Table number is wrong, please enter number between 1 to 150 ");
        else
            this.tableNumber = tableNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }

}
