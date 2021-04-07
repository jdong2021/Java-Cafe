package view;

import application.Order;
import java.text.DecimalFormat;

/**
 * OrderController is extended by DonutController and Coffee Controller and allows commmunication between the two controller classes
 */
public class OrderController {
    protected static Order currentOrder = new Order();
    protected static Double subtotal;
    protected static DecimalFormat df2 = new DecimalFormat("##0.00");

    OrderController() { }

    /**
     * initializes current order to be a new order
     */
    public static void setNewOrder() {
        currentOrder = new Order();
    }

    /**
     * returns currentOrder attribute
     * @return order currentorder
     */
    public static Order getCurrentOrder(){
        return currentOrder;
    }

    /**
     * returns a formatted string to 2 decimals
     * @param val double to format to string
     * @return formatted string
     */
    protected String RoundTo2Decimals(double val) {
        return (df2.format(val));
    }
}
