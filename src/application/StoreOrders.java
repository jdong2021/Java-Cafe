package application;

import java.util.HashMap;
import java.util.UUID;

public class StoreOrders implements Customizable {
    private HashMap<UUID, Order> orders = new HashMap();

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