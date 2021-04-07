package application;

import javafx.collections.ListChangeListener;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

public class StoreOrdersTest {

    @Test
    public void testObservable() {
        StoreOrders testStoreOrders = new StoreOrders();
        Order testOrder1 = new Order();
        Assertions.assertTrue(testStoreOrders.add(testOrder1));
        Order testOrder2 = new Order();
        Assertions.assertTrue(testStoreOrders.add(testOrder2));
        Order testOrder3 = new Order();
        Assertions.assertTrue(testStoreOrders.add(testOrder3));
        Assertions.assertTrue(testStoreOrders.remove(testOrder3));
    }

    @Test
    public void add() {
        StoreOrders testStoreOrders = new StoreOrders();
        Order testOrder = new Order();
        testOrder.add(new Donut());

        // Test Case 1: accept Order objects
        Assertions.assertTrue(testStoreOrders.add(testOrder));

        // Test Case 2: do not accept duplicate order objects
        Assertions.assertFalse(testStoreOrders.add(testOrder));

        // Test Case 4: do not accept invalid objects
        Assertions.assertFalse(testStoreOrders.add(new Object()));
    }

    @Test
    public void remove() {
        StoreOrders testStoreOrders = new StoreOrders();
        Order testOrder = new Order();
        testOrder.add(new Donut());
        testStoreOrders.add(testOrder);

        // Test Case 1: remove Order objects
        Assertions.assertTrue(testStoreOrders.remove(testOrder));

        // Test Case 2: cannot remove objects not in list
        Assertions.assertFalse(testStoreOrders.remove(testOrder));

        // Test Case 3: cannot remove invalid objects
        Assertions.assertFalse(testStoreOrders.remove(new Object()));
    }
}
