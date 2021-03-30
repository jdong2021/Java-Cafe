package application;

public class MenuItem {
    private double itemPrice;

    MenuItem() {
        itemPrice = 0;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void addToItemPrice(double amount) {
        this.itemPrice += amount;
    }
}
