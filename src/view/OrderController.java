package view;

import application.MenuItem;
import application.Order;
import java.text.DecimalFormat;

public class OrderController {
    protected static Order currentOrder = new Order();
    protected static Double subtotal;
    protected static DecimalFormat df2 = new DecimalFormat("##0.00");

    OrderController() { }

    protected void addListener(javafx.collections.ListChangeListener<MenuItem> listener) {
        this.currentOrder.addListener(listener);
    }

    public static Order getCurrentOrder(){
        return currentOrder;
    }

    public static Double getCurrentSubtotal(){
        return subtotal;
    }

    protected String RoundTo2Decimals(double val) {
        return (df2.format(val));
    }
}
