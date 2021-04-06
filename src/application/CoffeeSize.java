package application;

public enum CoffeeSize implements PriceModifier {
    SHORT(1.99, "Short"),
    TALL(2.49, "Tall"),
    GRANDE(2.99, "Grande"),
    VENTI(3.49, "Venti");

    private final double price;
    private final String label;

    CoffeeSize(double price, String label) {
        this.price = price;
        this.label = label;
    }

    public double getPrice() {
        return price;
    }
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

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
