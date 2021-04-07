package application;

/**
 * A public interface to enforce getPrice and getLabel methods on different classes
 *
 * @author Hugo De Moraes, Jonathan Dong
 */
public interface PriceModifier {
    /**
     * getPrice method signature
     * @return double
     */
    double getPrice();

    /**
     * getLabel method signature
     * @return String
     */
    String getLabel();
}
