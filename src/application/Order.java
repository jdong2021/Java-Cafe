package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.UUID;

public class Order implements Customizable {
    private final UUID orderNumber;
    private final ObservableList<MenuItem> currentOrder = FXCollections.observableArrayList();
    private static final double TAX_MULTIPLIER = 1.06625;
    private String total = "";

    public Order() {
        orderNumber = UUID.randomUUID();
    }

    public void addListener(javafx.collections.ListChangeListener<MenuItem> listener) {
        currentOrder.addListener(listener);
    }

    public double getOrderSubtotal() {
        double subTotal = 0;
        // map over each item type in order
        for(MenuItem item : currentOrder) {
            subTotal += item.itemPrice();
        }
        return subTotal;
    }

    public double getOrderFinalTotal(){
        double subTotal = getOrderSubtotal();
        return (subTotal*TAX_MULTIPLIER);
    }

    // adding menuitem to order
    public boolean add(Object obj) {
        // type check
        if(obj instanceof MenuItem) {
            // cast
            final MenuItem MENU_ITEM = (MenuItem) obj;
            // cannot add the exact same object again
            if(!currentOrder.contains(MENU_ITEM)) {
                // add to current order
                currentOrder.add(MENU_ITEM);
                return true;
            }
        }
        return false;
    }

    public boolean remove(Object obj) {
        // type check
        if(obj instanceof MenuItem) {
            // cast
            final MenuItem MENU_ITEM = (MenuItem) obj;
            // check if in current order
            if(currentOrder.contains(MENU_ITEM)) {
                // delete
                currentOrder.remove(MENU_ITEM);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", currentOrder=" + currentOrder +
                '}';
    }
    public String getTotal(){
        return this.total;
    }

    public void setTotal(String val){
        this.total = val;
    }

    public ObservableList<MenuItem> getOrder() { return currentOrder; }

    public UUID getOrderNumber() {
        return orderNumber;
    }

    public boolean isEmpty() { return currentOrder.isEmpty(); }
}