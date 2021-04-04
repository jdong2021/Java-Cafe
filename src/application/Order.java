package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.UUID;

public class Order implements Customizable {
    private final UUID orderNumber;
    private ObservableList<MenuItem> currentOrder = FXCollections.observableArrayList();

    public Order() {
        orderNumber = UUID.randomUUID();
    }

    public Order(javafx.collections.ListChangeListener<MenuItem> listener) {
        orderNumber = UUID.randomUUID();
        currentOrder.addListener(listener);
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
            // cannot add the exact same object again
            if(!currentOrder.contains(NEW_MENU_ITEM)) {
                // add to current order
                currentOrder.add(NEW_MENU_ITEM);
                return true;
            }
        }
        return false;
    }

    public boolean remove(Object obj) {
        // type check
        if(obj instanceof MenuItem) {
            // cast
            final MenuItem TARGET_MENU_ITEM = (MenuItem) obj;
            // check if in current order
            if(currentOrder.contains(TARGET_MENU_ITEM)) {
                // remove from current order
                currentOrder.remove(TARGET_MENU_ITEM);
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