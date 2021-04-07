package view;

import application.Order;
import java.text.DecimalFormat;

public class OrderController {
    protected static Order currentOrder = new Order();
    protected static Double subtotal;
    protected static DecimalFormat df2 = new DecimalFormat("##0.00");

    OrderController() { }

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
