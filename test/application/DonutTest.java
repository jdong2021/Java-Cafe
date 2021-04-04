package application;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;

public class DonutTest {

    @Test
    public void setType() {
        Donut testDonut = new Donut();

        System.out.println("Hello");
        // testDonut.setType(DonutType.Flavor.BOSTON_CREME);
        // Test Case 1: set Yeast type and check price
        Assertions.assertTrue(testDonut.setType(DonutType.Flavor.BOSTON_CREME));
        Assertions.assertTrue(testDonut.itemPrice() == DonutType.Flavor.BOSTON_CREME.getPrice());

        System.out.println("World");
        // Test Case 2: set cake type and check price
        Assertions.assertTrue(testDonut.setType(DonutType.Flavor.CHOCOLATE));
        Assertions.assertTrue(testDonut.itemPrice() == DonutType.Flavor.CHOCOLATE.getPrice());

        // Test Case 3: set holes type and check price
        Assertions.assertTrue(testDonut.setType(DonutType.Flavor.POWDERED));
        Assertions.assertTrue(testDonut.itemPrice() == DonutType.Flavor.POWDERED.getPrice());
    }
}
