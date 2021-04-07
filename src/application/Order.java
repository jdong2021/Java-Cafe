package application;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import java.util.UUID;

/**
 * Order class is a container class that is designed to hold MenuItem objects and any subclasses of MenuItem.
 *
 * @author Hugo De Moraes, Jonathan Dong
 */
public class Order implements Customizable {
    private final UUID orderNumber = UUID.randomUUID();
    private final ObservableList<MenuItem> order = FXCollections.observableArrayList();
    private static final double TAX_MULTIPLIER = 1.06625;
    private String total = "";

    /**
     * Default Constructor for Order object
     */
    public Order() {
    }

    /**
     * Helper method that attaches a listener to this.order
     * @param listener ListChangeListener<MenuItem>
     */
    public void addListener(ListChangeListener<MenuItem> listener) {
        order.addListener(listener);
    }

    /**
     * Getter method that calculates and returns the order subtotal
     * @return subtotal for all items in order
     */
    public double getOrderSubtotal() {
        double subTotal = 0;
        // map over each item type in order
        for(MenuItem item : order) {
            subTotal += item.itemPrice();
        }
        return subTotal;
    }

    /**
     * Getter method that calculates and returns the order total with taxes
     * @return total for all items in order and tax
     */
    public double getOrderFinalTotal(){
        double subTotal = getOrderSubtotal();
        return (subTotal*TAX_MULTIPLIER);
    }

    /**
     * Customizable interface method, given an object of type MenuItem
     * adds the object to this.order
     * @param obj Object
     * @return true if added successfully, false otherwise
     */
    public boolean add(Object obj) {
        // type check
        if(obj instanceof MenuItem) {
            // cast
            final MenuItem MENU_ITEM = (MenuItem) obj;
            // cannot add the exact same object again
            if(!order.contains(MENU_ITEM)) {
                // add to current order
                order.add(MENU_ITEM);
                return true;
            }
        }
        return false;
    }

    /**
     * Customizable interface method, given an object of type MenuItem
     * removes the object from this.order
     * @param obj Object
     * @return true if removed successfully, false otherwise
     */
    public boolean remove(Object obj) {
        // type check
        if(obj instanceof MenuItem) {
            // cast
            final MenuItem MENU_ITEM = (MenuItem) obj;
            // check if in current order
            if(order.contains(MENU_ITEM)) {
                // delete
                order.remove(MENU_ITEM);
                return true;
            }
        }
        return false;
    }

    /**
     * Overridden toString method
     * @return
     */
    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", order=" + order +
                '}';
    }

    /**
     * Getter method
     * @return this.total
     */
    public String getTotal(){
        return this.total;
    }

    /**
     * Setter method for this.total
     * @param val
     */
    public void setTotal(String val){
        this.total = val;
    }

    /**
     * Getter method
     * @return this.order
     */
    public ObservableList<MenuItem> getOrder() { return order; }

    /**
     * Getter method
     * @return this.orderNumber
     */
    public UUID getOrderNumber() {
        return orderNumber;
    }

    /**
     * Helper method
     * @return true if this.order.isEmpty(), false otherwise
     */
    public boolean isEmpty() { return order.isEmpty(); }
}