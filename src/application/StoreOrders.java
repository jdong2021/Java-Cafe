package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StoreOrders implements Customizable {
    private ObservableList<Order> orders = FXCollections.observableArrayList();

    public StoreOrders() { }

    public StoreOrders(javafx.collections.ListChangeListener<Order> listener) {
        orders.addListener(listener);
    }

    public boolean add(Object obj) {
        // type check
        if(obj instanceof Order) {
            // cast
            Order NEW_ORDER = (Order) obj;
            // if not already added
            if(!orders.contains(NEW_ORDER)) {
                // add to current order
                orders.add(NEW_ORDER);
                return true;
            }
        }
        return false;
    }

    public boolean remove(Object obj) {
        // type check
        if(obj instanceof Order) {
            // cast
            final Order TARGET_ORDER = (Order) obj;
            // check if in current order
            if(orders.contains(TARGET_ORDER)) {
                // remove from current order
                orders.remove(TARGET_ORDER);
                return true;
            }
        }
        return false;
    }

    public ObservableList<Order> getOrders() {
        return orders;
    }
}