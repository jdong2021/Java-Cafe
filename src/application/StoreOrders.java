package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * StoreOrder class is a container class that is designed to hold Order objects
 * Implements Customizable interface
 *
 * @author Hugo De Moraes, Jonathan Dong
 */
public class StoreOrders implements Customizable {
    private ObservableList<Order> storeOrders = FXCollections.observableArrayList();

    /**
     * Default Constructor for StoreOrders
     */
    public StoreOrders() { }

    /**
     * Customizable interface method, given an object of type Order
     * adds the object to this.order
     * @param obj Object
     * @return true if added successfully, false otherwise
     */
    public boolean add(Object obj) {
        // type check
        if(obj instanceof Order) {
            // cast
            Order NEW_ORDER = (Order) obj;
            // if not already added
            if(!storeOrders.contains(NEW_ORDER)) {
                // add to current order
                storeOrders.add(NEW_ORDER);
                return true;
            }
        }
        return false;
    }

    /**
     * Customizable interface method, given an object of type Order
     * removes the object from this.order
     * @param obj Object
     * @return true if removed successfully, false otherwise
     */
    public boolean remove(Object obj) {
        // type check
        if(obj instanceof Order) {
            // cast
            final Order TARGET_ORDER = (Order) obj;
            // check if in current order
            if(storeOrders.contains(TARGET_ORDER)) {
                // remove from current order
                storeOrders.remove(TARGET_ORDER);
                return true;
            }
        }
        return false;
    }

    /**
     * Getter method
     * @return this.storeOrders
     */
    public ObservableList<Order> getStoreOrders() {
        return storeOrders;
    }
}