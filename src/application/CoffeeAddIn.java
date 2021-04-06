package application;

public enum CoffeeAddIn {
    CREAM("Cream"),
    SYRUP("Syrup"),
    MILK("Milk"),
    CARAMEL("Caramel"),
    WHIPPED_CREAM("Whipped Cream");

    private final String label;
    CoffeeAddIn(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
