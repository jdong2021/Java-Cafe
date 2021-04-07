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
     * initializes
     */
    public static void setNewOrder() {
        currentOrder = new Order();
    }

    public static Order getCurrentOrder(){
        return currentOrder;
    }

    protected String RoundTo2Decimals(double val) {
        return (df2.format(val));
    }
}
