package application;

import java.util.HashMap;
import java.util.UUID;

public class Order implements Customizable {
    private final UUID orderNumber;
    private HashMap<UUID, MenuItem> currentOrder = new HashMap();

    public Order() {
        orderNumber = UUID.randomUUID();
    }


    // maybe i should make a add method along with a quantity?
    //adding multiple menuitems to order
    public void addMultipleDonuts(Object obj, int quantity){
        for(int i =0; i <quantity;i++){
            add(obj);
        }
    }

    //removing multiple menuitems to order
    public void removeMultipleDonuts(Object obj, int quantity){
        for(int i =0; i < quantity; i++){
            remove(obj);
        }
    }

    // adding menuitem to order
    public boolean add(Object obj) {
        // type check
        if(obj instanceof MenuItem) {
            // cast
            final MenuItem NEW_MENU_ITEM = (MenuItem) obj;
            // add to current order
            currentOrder.put(NEW_MENU_ITEM.getUuid(), NEW_MENU_ITEM);
            return true;
        }
        return false;
    }

    public boolean remove(Object obj) {
        // type check
        if(obj instanceof MenuItem) {
            // cast
            final MenuItem TARGET_MENU_ITEM = (MenuItem) obj;
            // check if in current order
            if(currentOrder.containsKey(TARGET_MENU_ITEM.getUuid())) {
                // remove from current order
                currentOrder.remove(TARGET_MENU_ITEM.getUuid());
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

    public UUID getOrderNumber() {
        return orderNumber;
    }
}