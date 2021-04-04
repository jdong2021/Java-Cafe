package application;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(value=Suite.class)
@Suite.SuiteClasses(value={
        CoffeeTest.class,
        DonutTest.class,
        OrderTest.class,
        StoreOrdersTest.class,
})
public class TestSuite {
    // This class remains empty, it is used only as a holder for the above annotations
}
