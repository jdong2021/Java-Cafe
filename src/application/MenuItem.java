package application;

public class MenuItem {
    private double itemPrice;
    static final double ADD_IN_PRICE = 0.50;

    MenuItem() {
        itemPrice = 0;
    }

    public double itemPrice() {
        return itemPrice;
    }

    protected void addToItemPrice(double amount) {
        this.itemPrice += amount;
    }
}
