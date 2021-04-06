package application;

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

    DonutFlavor(String label, DonutType donutType) {
        this.label = label;
        this.donutType = donutType;
    }

    public String getLabel() {
        return label;
    }
    public double getPrice() {
        return donutType.getPrice();
    }
    public DonutType getDonutType() {
        return donutType;
    }

    @Override
    public String toString() {
        return label;
    }

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
