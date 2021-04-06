package view;

import application.MenuItem;
import application.Order;
import javafx.scene.control.TextField;
import java.text.DecimalFormat;

public class OrderController {
    protected static Order currentOrder;
    protected static Double subtotal;
    protected static DecimalFormat df2 = new DecimalFormat("##0.00");

    OrderController() {
        this.currentOrder = new Order();
    }

    protected void addListener(javafx.collections.ListChangeListener<MenuItem> listener) {
        this.currentOrder.addListener(listener);
    }

    public static Order getCurrentOrder(){
        return (currentOrder);
    }

    public static Double getCurrentSubtotal(){
        return (subtotal);
    }

    protected void adjustSubTotal(TextField subTotalField) {
        subtotal = currentOrder.getOrderSubtotal();
        subTotalField.setText((RoundTo2Decimals(subtotal)));
    }

    protected String RoundTo2Decimals(double val) {
        return (df2.format(val));
    }
}
