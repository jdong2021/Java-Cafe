package application;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;

public class DonutTest {

    @Test
    public void setType() {
        Donut testDonut = new Donut();

        // Test Case 1: set Yeast type and check price
        Assertions.assertTrue(testDonut.setType(Donut.types.YEAST));
        Assertions.assertTrue(testDonut.itemPrice() == Donut.types.YEAST.getPrice());

        // Test Case 2: set cake type and check price
        Assertions.assertTrue(testDonut.setType(Donut.types.CAKE));
        Assertions.assertTrue(testDonut.itemPrice() == Donut.types.CAKE.getPrice());

        // Test Case 3: set holes type and check price
        Assertions.assertTrue(testDonut.setType(Donut.types.HOLES));
        Assertions.assertTrue(testDonut.itemPrice() == Donut.types.HOLES.getPrice());

        // Test Case 4: set invalid type, check price remains the same
        Assertions.assertFalse(testDonut.setType(new Object()));
        Assertions.assertTrue(testDonut.itemPrice() == Donut.types.HOLES.getPrice());
    }
}
