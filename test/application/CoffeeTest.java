package application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CoffeeTest {

    @Test
    void setSize() {
        Coffee testCoffee = new Coffee();
        // Test Case 1: set short size and check price
        Assertions.assertTrue(testCoffee.setSize(Coffee.sizes.SHORT));
        Assertions.assertTrue(testCoffee.getItemPrice() == Coffee.sizes.SHORT.getPrice());

        // Test Case 2: set tall size
        Assertions.assertTrue(testCoffee.setSize(Coffee.sizes.TALL));
        Assertions.assertTrue(testCoffee.getItemPrice() == Coffee.sizes.TALL.getPrice());

        // Test Case 3: set grande size
        Assertions.assertTrue(testCoffee.setSize(Coffee.sizes.GRANDE));
        Assertions.assertTrue(testCoffee.getItemPrice() == Coffee.sizes.GRANDE.getPrice());

        // Test Case 4: set venti size
        Assertions.assertTrue(testCoffee.setSize(Coffee.sizes.VENTI));
        Assertions.assertTrue(testCoffee.getItemPrice() == Coffee.sizes.VENTI.getPrice());
    }

    @Test
    void add() {
        Coffee testCoffee = new Coffee();
        double expectedPrice = 0;

        // Test Case 1: add cream and check price
        Assertions.assertTrue(testCoffee.add(Coffee.addIns.CREAM));
        expectedPrice += Coffee.ADD_IN_PRICE;
        Assertions.assertTrue(testCoffee.getItemPrice() == expectedPrice);

        // Test Case 2: add syrup and check price
        Assertions.assertTrue(testCoffee.add(Coffee.addIns.SYRUP));
        expectedPrice += Coffee.ADD_IN_PRICE;
        Assertions.assertTrue(testCoffee.getItemPrice() == expectedPrice);

        // Test Case 3: add milk and check price
        Assertions.assertTrue(testCoffee.add(Coffee.addIns.MILK));
        expectedPrice += Coffee.ADD_IN_PRICE;
        Assertions.assertTrue(testCoffee.getItemPrice() == expectedPrice);

        // Test Case 4: add caramel and check price
        Assertions.assertTrue(testCoffee.add(Coffee.addIns.CARAMEL));
        expectedPrice += Coffee.ADD_IN_PRICE;
        Assertions.assertTrue(testCoffee.getItemPrice() == expectedPrice);

        // Test Case 5: add invalid addIn and check price
        Assertions.assertFalse(testCoffee.add(new Object()));
        Assertions.assertTrue(testCoffee.getItemPrice() == expectedPrice);

        // Test Case 6: add whipped cream and check price
        Assertions.assertTrue(testCoffee.add(Coffee.addIns.WHIPPED_CREAM));
        expectedPrice += Coffee.ADD_IN_PRICE;
        Assertions.assertTrue(testCoffee.getItemPrice() == expectedPrice);
    }

    @Test
    void remove() {
        Coffee testCoffee = new Coffee();

        testCoffee.add(Coffee.addIns.CREAM);
        testCoffee.add(Coffee.addIns.SYRUP);
        testCoffee.add(Coffee.addIns.MILK);
        testCoffee.add(Coffee.addIns.CARAMEL);
        testCoffee.add(Coffee.addIns.WHIPPED_CREAM);

        double expectedPrice = Coffee.ADD_IN_PRICE * 5;

        // Test Case 1: remove cream and check price
        Assertions.assertTrue(testCoffee.remove(Coffee.addIns.CREAM));
        expectedPrice -= Coffee.ADD_IN_PRICE;
        Assertions.assertTrue(testCoffee.getItemPrice() == expectedPrice);

        // Test Case 2: remove cream again and check price
        Assertions.assertFalse(testCoffee.remove(Coffee.addIns.CREAM));
        Assertions.assertTrue(testCoffee.getItemPrice() == expectedPrice);

        // Test Case 3: remove invalid addIn and check price
        Assertions.assertFalse(testCoffee.remove(new Object()));
        Assertions.assertTrue(testCoffee.getItemPrice() == expectedPrice);

        // Test Case 4: remove syrup and check price
        Assertions.assertTrue(testCoffee.remove(Coffee.addIns.SYRUP));
        expectedPrice -= Coffee.ADD_IN_PRICE;
        Assertions.assertTrue(testCoffee.getItemPrice() == expectedPrice);

        // Test Case 5: remove milk and check price
        Assertions.assertTrue(testCoffee.remove(Coffee.addIns.MILK));
        expectedPrice -= Coffee.ADD_IN_PRICE;
        Assertions.assertTrue(testCoffee.getItemPrice() == expectedPrice);

        // Test Case 6: remove caramel and check price
        Assertions.assertTrue(testCoffee.remove(Coffee.addIns.CARAMEL));
        expectedPrice -= Coffee.ADD_IN_PRICE;
        Assertions.assertTrue(testCoffee.getItemPrice() == expectedPrice);

        // Test Case 7: remove whipped cream and check price
        Assertions.assertTrue(testCoffee.remove(Coffee.addIns.WHIPPED_CREAM));
        expectedPrice -= Coffee.ADD_IN_PRICE;
        Assertions.assertTrue(testCoffee.getItemPrice() == expectedPrice);
    }
}
