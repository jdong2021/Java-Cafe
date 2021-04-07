package application;

/**
 * DonutFlavor enum that contains the different supported flavors for each DonutType
 *
 * @author Hugo De Moraes, Jonathan Dong
 */
public enum DonutFlavor {
    GLAZED("Glazed" , DonutType.CAKE),
    CHOCOLATE("Chocolate", DonutType.CAKE),
    STRAWBERRY("Strawberry", DonutType.CAKE),
    JELLY("Jelly", DonutType.YEAST),
    VANILLA("Vanilla", DonutType.YEAST),
    BOSTON_CREME("Boston Creme", DonutType.YEAST),
    POWDERED("Powdered", DonutType.HOLES),
    CINNAMON("Cinnamon", DonutType.HOLES),
    PUMPKIN("Pumpkin", DonutType.HOLES);

    private final String label;
    private final DonutType donutType;

    /**
     * Default constructor for DonutFlavor
     * @param label String
     * @param donutType DonutType
     */
    DonutFlavor(String label, DonutType donutType) {
        this.label = label;
        this.donutType = donutType;
    }

    /**
     * Getter method for this.label
     * @return this.label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Getter method for price of this.donutType
     * @return calls and returns getPrice of this.donutType
     */
    public double getPrice() {
        return donutType.getPrice();
    }

    /**
     * Getter method for this.donutType
     * @return this.donutType
     */
    public DonutType getDonutType() {
        return donutType;
    }

    /**
     * Overridden toString method
     * @return this.label
     */
    @Override
    public String toString() {
        return label;
    }

    /**
     * Helper method that given a String label, returns the matching DonutFlavor
     * @param label String
     * @return returns one of the supported DonutFlavor objects if matched, else null
     */
    public static DonutFlavor getFlavorByLabel(String label) {
        DonutFlavor selectedFlavor = null;
        for(DonutFlavor flavor : DonutFlavor.values()) {
            if(flavor.label.equals(label)) {
                selectedFlavor = flavor;
            }
        }
        return selectedFlavor;
    }
}
