package application;

/**
 * CoffeeAddIn enum that contains the different types of supported AddIns for Coffee
 *
 * @author Hugo De Moraes, Jonathan Dong
 */
public enum CoffeeAddIn {
    CREAM("Cream"),
    SYRUP("Syrup"),
    MILK("Milk"),
    CARAMEL("Caramel"),
    WHIPPED_CREAM("Whipped Cream");

    private final String label;

    /**
     * Default constructor for CoffeeAddIn
     * @param label String
     */
    CoffeeAddIn(String label) {
        this.label = label;
    }

    /**
     * Overridden toString
     * @return returns this.label
     */
    @Override
    public String toString() {
        return label;
    }

    /**
     * Getter method for this.label
     * @return this.label
     */
    public String getLabel() {
        return label;
    }
}
