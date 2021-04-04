package application;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class OrderTest {

    @Test
    public void add() {
        Order testOrder = new Order();
        MenuItem testMenuItem = new MenuItem();
        Coffee testCoffee = new Coffee();
        Donut testDonut = new Donut();

        // Test Case 1: accept MenuItem objects
        Assertions.assertTrue(testOrder.add(testMenuItem));

        // Test Case 2: accept Coffee objects
        Assertions.assertTrue(testOrder.add(testCoffee));

        // Test Case 3: accept Donut objects
        Assertions.assertTrue(testOrder.add(testDonut));

        // Test Case 4: do not accept duplicate objects
        Assertions.assertFalse(testOrder.add(testMenuItem));

        // Test Case 5: do not accept invalid objects
        Assertions.assertFalse(testOrder.add(new Object()));
    }

    @Test
    public void remove() {
        Order testOrder = new Order();
        MenuItem testMenuItem = new MenuItem();
        Coffee testCoffee = new Coffee();
        Donut testDonut = new Donut();

        testOrder.add(testMenuItem);
        testOrder.add(testCoffee);
        testOrder.add(testDonut);

        // Test Case 1: remove MenuItem objects
        Assertions.assertTrue(testOrder.remove(testMenuItem));

        // Test Case 2: remove Coffee objects
        Assertions.assertTrue(testOrder.remove(testCoffee));

        // Test Case 3: remove Donut objects
        Assertions.assertTrue(testOrder.remove(testDonut));

        // Test Case 4: cannot remove objects if not in list
        Assertions.assertFalse(testOrder.remove(testDonut));

        // Test Case 5: cannot remove invalid objects
        Assertions.assertFalse(testOrder.remove(new Object()));
    }
}
