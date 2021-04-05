package application;

public enum CoffeeAddIns {
    CREAM("Cream"),
    SYRUP("Syrup"),
    MILK("Milk"),
    CARAMEL("Caramel"),
    WHIPPED_CREAM("Whipped Cream");

    private final String label;

    CoffeeAddIns(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
