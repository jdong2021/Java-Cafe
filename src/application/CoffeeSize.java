package application;

/**
 * CoffeeSize enum that contains tbe different supported sizes for Coffee
 * implements the PriceModifier interface
 *
 * @author Hugo De Moraes, Jonathan Dong
 */
public enum CoffeeSize implements PriceModifier {
    SHORT(1.99, "Short"),
    TALL(2.49, "Tall"),
    GRANDE(2.99, "Grande"),
    VENTI(3.49, "Venti");

    private final double price;
    private final String label;

    /**
     * Default constructor for CoffeeSize
     * @param price double
     * @param label String
     */
    CoffeeSize(double price, String label) {
        this.price = price;
        this.label = label;
    }

    /**
     * PriceModifier implementation
     * Getter method for price of a particular CoffeeSize
     * @return this.price
     */
    public double getPrice() {
        return price;
    }

    /**
     * PriceModifier implementation
     * Getter method for label of a particular CoffeeSize
     * @return this.label
     */
    public String getLabel() {
        return label;
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
     * Helper method that given a String label, returns the matching CoffeeSize
     * @param label String
     * @return returns one of the supported CoffeeSize objects if matched, else null
     */
    public static CoffeeSize getSizeByLabel(String label) {
        CoffeeSize selectedSize = null;
        for(CoffeeSize size : CoffeeSize.values()) {
            if(size.label.equals(label)) {
                selectedSize = size;
            }
        }
        return selectedSize;
    }
}
