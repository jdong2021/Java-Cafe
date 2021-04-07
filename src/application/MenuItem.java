package application;

/**
 * MenuItem class represents an abstract data type MenuItem
 *
 * @author Hugo De Moraes, Jonathan Dong
 */
public class MenuItem {
    private double itemPrice;
    static final double ADD_IN_PRICE = 0.50;

    /**
     * Default Constructor for MenuItem
     */
    MenuItem() {
        itemPrice = 0;
    }

    /**
     * getter method for this.itemPrice
     * @return returns this.itemPrice
     */
    public double itemPrice() {
        return itemPrice;
    }

    /**
     * Given an amount, adds it to this.itemPrice
     * @param amount double
     */
    protected void addToItemPrice(double amount) {
        this.itemPrice += amount;
    }
}
