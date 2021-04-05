package application;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;

public class CoffeeTest {

    @Test
    public void setSize() {
        Coffee testCoffee = new Coffee();
        // Test Case 1: set short size and check price
        Assertions.assertTrue(testCoffee.setSize(CoffeeSizes.SHORT));
        Assertions.assertEquals(CoffeeSizes.SHORT.getPrice(), testCoffee.itemPrice());

        // Test Case 2: set tall size
        Assertions.assertTrue(testCoffee.setSize(CoffeeSizes.TALL));
        Assertions.assertEquals(testCoffee.itemPrice(), CoffeeSizes.TALL.getPrice());

        // Test Case 3: set grande size
        Assertions.assertTrue(testCoffee.setSize(CoffeeSizes.GRANDE));
        Assertions.assertEquals(testCoffee.itemPrice(), CoffeeSizes.GRANDE.getPrice());

        // Test Case 4: set venti size
        Assertions.assertTrue(testCoffee.setSize(CoffeeSizes.VENTI));
        Assertions.assertEquals(testCoffee.itemPrice(), CoffeeSizes.VENTI.getPrice());

        // Test Case 5: set invalid size, check price remains the same
        Assertions.assertFalse(testCoffee.setSize(new Object()));
        Assertions.assertEquals(testCoffee.itemPrice(), CoffeeSizes.VENTI.getPrice());
    }

    @Test
    public void add() {
        Coffee testCoffee = new Coffee();
        double expectedPrice = 0;

        // Test Case 1: add cream and check price
        Assertions.assertTrue(testCoffee.add(CoffeeAddIns.CREAM));
        expectedPrice += Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 2: add syrup and check price
        Assertions.assertTrue(testCoffee.add(CoffeeAddIns.SYRUP));
        expectedPrice += Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 3: add milk and check price
        Assertions.assertTrue(testCoffee.add(CoffeeAddIns.MILK));
        expectedPrice += Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 4: add caramel and check price
        Assertions.assertTrue(testCoffee.add(CoffeeAddIns.CARAMEL));
        expectedPrice += Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 5: add invalid addIn and check price
        Assertions.assertFalse(testCoffee.add(new Object()));
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 6: add whipped cream and check price
        Assertions.assertTrue(testCoffee.add(CoffeeAddIns.WHIPPED_CREAM));
        expectedPrice += Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);
    }

    @Test
    public void remove() {
        Coffee testCoffee = new Coffee();

        testCoffee.add(CoffeeAddIns.CREAM);
        testCoffee.add(CoffeeAddIns.SYRUP);
        testCoffee.add(CoffeeAddIns.MILK);
        testCoffee.add(CoffeeAddIns.CARAMEL);
        testCoffee.add(CoffeeAddIns.WHIPPED_CREAM);

        double expectedPrice = Coffee.ADD_IN_PRICE * 5;

        // Test Case 1: remove cream and check price
        Assertions.assertTrue(testCoffee.remove(CoffeeAddIns.CREAM));
        expectedPrice -= Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 2: remove cream again and check price
        Assertions.assertFalse(testCoffee.remove(CoffeeAddIns.CREAM));
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 3: remove invalid addIn and check price
        Assertions.assertFalse(testCoffee.remove(new Object()));
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 4: remove syrup and check price
        Assertions.assertTrue(testCoffee.remove(CoffeeAddIns.SYRUP));
        expectedPrice -= Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 5: remove milk and check price
        Assertions.assertTrue(testCoffee.remove(CoffeeAddIns.MILK));
        expectedPrice -= Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 6: remove caramel and check price
        Assertions.assertTrue(testCoffee.remove(CoffeeAddIns.CARAMEL));
        expectedPrice -= Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);

        // Test Case 7: remove whipped cream and check price
        Assertions.assertTrue(testCoffee.remove(CoffeeAddIns.WHIPPED_CREAM));
        expectedPrice -= Coffee.ADD_IN_PRICE;
        Assertions.assertEquals(testCoffee.itemPrice(), expectedPrice);
    }
}
