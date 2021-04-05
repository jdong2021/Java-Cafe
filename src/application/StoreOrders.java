package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Arrays;

public class StoreOrders implements Customizable {
    private ObservableList<Order> orders = FXCollections.observableArrayList();
    private static int endingtotal;

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

    public void printAllStoreOrders(){
        System.out.println(Arrays.asList(this));
    }

//    public static void main (String args[]){
//        StoreOrders mystoreorders = new StoreOrders();
//
//        //create my first order
//        Order o1 = new Order();
//        //this order will contain a tall coffee with cream
//        Coffee c1 = new Coffee();
//        c1.setSize(Coffee.sizes.TALL);
//        c1.add(Coffee.addIns.CREAM);
//        //now add said coffee to order
//        o1.add(c1);
//        //now add my first order to storeordres
//        mystoreorders.add(o1);
//        mystoreorders.orders.get(o1.getOrderNumber());
//    }
}