package application;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;

public class CoffeeTest {

    @Test
    public void setSize() {
        Coffee testCoffee = new Coffee();
        // Test Case 1: set short size and check price
        Assertions.assertTrue(testCoffee.setSize(CoffeeSize.SHORT));
        Assertions.assertEquals(CoffeeSize.SHORT.getPrice(), testCoffee.itemPrice());

        // Test Case 2: set tall size
        Assertions.assertTrue(testCoffee.setSize(CoffeeSize.TALL));
        Assertions.assertEquals(testCoffee.itemPrice(), CoffeeSize.TALL.getPrice());

        // Test Case 3: set grande size
        Assertions.assertTrue(testCoffee.setSize(CoffeeSize.GRANDE));
        Assertions.assertEquals(testCoffee.itemPrice(), CoffeeSize.GRANDE.getPrice());

        // Test Case 4: set venti size
        Assertions.assertTrue(testCoffee.setSize(CoffeeSize.VENTI));
        Assertions.assertEquals(testCoffee.itemPrice(), CoffeeSize.VENTI.getPrice());

        // Test Case 5: set invalid size, check price remains the same
        Assertions.assertFalse(testCoffee.setSize(new Object()));
        Assertions.assertEquals(testCoffee.itemPrice(), CoffeeSize.VENTI.getPrice());
    }

    @Test
    public void add() {
        Coffee testCoffee = new Coffee();
        double expectedPrice = 0;

        // Test Case 1: add cream and check price
        Assertions.assertTrue(testCoffee.add(CoffeeAddIn.CREAM));
        expectedPrice += Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 2: add syrup and check price
        Assertions.assertTrue(testCoffee.add(CoffeeAddIn.SYRUP));
        expectedPrice += Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 3: add milk and check price
        Assertions.assertTrue(testCoffee.add(CoffeeAddIn.MILK));
        expectedPrice += Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 4: add caramel and check price
        Assertions.assertTrue(testCoffee.add(CoffeeAddIn.CARAMEL));
        expectedPrice += Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 5: add invalid addIn and check price
        Assertions.assertFalse(testCoffee.add(new Object()));
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 6: add whipped cream and check price
        Assertions.assertTrue(testCoffee.add(CoffeeAddIn.WHIPPED_CREAM));
        expectedPrice += Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);
    }

    @Test
    public void remove() {
        Coffee testCoffee = new Coffee();

        testCoffee.add(CoffeeAddIn.CREAM);
        testCoffee.add(CoffeeAddIn.SYRUP);
        testCoffee.add(CoffeeAddIn.MILK);
        testCoffee.add(CoffeeAddIn.CARAMEL);
        testCoffee.add(CoffeeAddIn.WHIPPED_CREAM);

        double expectedPrice = Coffee.ADD_IN_PRICE * 5;

        // Test Case 1: remove cream and check price
        Assertions.assertTrue(testCoffee.remove(CoffeeAddIn.CREAM));
        expectedPrice -= Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 2: remove cream again and check price
        Assertions.assertFalse(testCoffee.remove(CoffeeAddIn.CREAM));
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 3: remove invalid addIn and check price
        Assertions.assertFalse(testCoffee.remove(new Object()));
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 4: remove syrup and check price
        Assertions.assertTrue(testCoffee.remove(CoffeeAddIn.SYRUP));
        expectedPrice -= Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 5: remove milk and check price
        Assertions.assertTrue(testCoffee.remove(CoffeeAddIn.MILK));
        expectedPrice -= Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 6: remove caramel and check price
        Assertions.assertTrue(testCoffee.remove(CoffeeAddIn.CARAMEL));
        expectedPrice -= Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 7: remove whipped cream and check price
        Assertions.assertTrue(testCoffee.remove(CoffeeAddIn.WHIPPED_CREAM));
        expectedPrice -= Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);
    }
}
