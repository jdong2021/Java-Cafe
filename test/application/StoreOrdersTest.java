package application;

import javafx.collections.MapChangeListener;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

import java.util.UUID;

public class StoreOrdersTest {

    @Test
    public void testObservable() {
        StoreOrders testStoreOrders = new StoreOrders();
        Order testOrder1 = new Order();
        System.out.println("1");
        Assertions.assertTrue(testStoreOrders.add(testOrder1));
        Order testOrder2 = new Order();
        System.out.println("2");
        Assertions.assertTrue(testStoreOrders.add(testOrder2));
        Order testOrder3 = new Order();
        System.out.println("3");
        Assertions.assertTrue(testStoreOrders.add(testOrder3));
        System.out.println("4");
        Assertions.assertTrue(testStoreOrders.remove(testOrder3));
    }

    @Test
    public void testObservableListener() {

        MapChangeListener<UUID, Order> testLister = new MapChangeListener<UUID, Order>() {
            @Override
            public void onChanged(MapChangeListener.Change change) {
                System.out.println("change! ");
            }
        };

        StoreOrders testStoreOrders = new StoreOrders(testLister);
        StoreOrders testStoreOrdersTwo = new StoreOrders((MapChangeListener) change -> System.out.println("change! "));

        Order testOrder1 = new Order();
        System.out.println("1");
        Assertions.assertTrue(testStoreOrders.add(testOrder1));
        Order testOrder2 = new Order();
        System.out.println("2");
        Assertions.assertTrue(testStoreOrders.add(testOrder2));
        Order testOrder3 = new Order();
        System.out.println("3");
        Assertions.assertTrue(testStoreOrders.add(testOrder3));
        System.out.println("4");
        Assertions.assertTrue(testStoreOrders.remove(testOrder3));
    }
}
