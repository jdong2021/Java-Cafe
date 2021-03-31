package application;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StoreOrders implements Customizable {
    // private Map<UUID, Order> orders = new HashMap<>();
    // public ObservableMap<UUID, Order> observableOrders = FXCollections.observableHashMap();
    private ObservableMap<UUID, Order> orders = FXCollections.observableHashMap();

    StoreOrders() {
        // orders.addListener((MapChangeListener) change -> System.out.println("change! "));
    }

    StoreOrders(javafx.collections.MapChangeListener<UUID, Order> listener) {
        orders.addListener(listener);
    }

    public boolean add(Object obj) {
        // type check
        if(obj instanceof Order) {
            // cast
            Order NEW_ORDER = (Order) obj;
            // add to current order
            orders.put(NEW_ORDER.getOrderNumber(), NEW_ORDER);
            return true;
        }
        return false;
    }

    public boolean remove(Object obj) {
        // type check
        if(obj instanceof Order) {
            // cast
            final Order TARGET_ORDER = (Order) obj;
            // check if in current order
            if(orders.containsKey(TARGET_ORDER.getOrderNumber())) {
                // remove from current order
                orders.remove(TARGET_ORDER.getOrderNumber());
                return true;
            }
        }
        return false;
    }
}