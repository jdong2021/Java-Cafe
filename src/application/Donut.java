package application;

import java.util.Objects;

/**
 * Donut class is a child class of MenuItem and represents a Donut Object with additional instance variables
 * and methods unique to Donut, implements Customizable interface
 *
 * @author Hugo De Moraes, Jonathan Dong
 */
public class Donut extends MenuItem {
    private DonutFlavor flavor;

    /**
     * Default Constructor for Donut
     */
    public Donut() {
        super();
    }

    /**
     * Overloaded Constructor for Donut
     * given an instance of DonutFlavor, sets this.flavor
     * @param flavor DonutFlavor
     */
    public Donut(DonutFlavor flavor) {
        super();
        this.flavor = flavor;
    }

    /**
     * Setter method that given an instance of DonutFlavor, sets this.flavor
     * @param selection DonutFlavor
     */
    public void setType(DonutFlavor selection) {
        // handle null
        if(Objects.isNull(flavor)) {
            // set new selected size
            this.flavor = selection;
            // add newly selected size to menuItem price
            super.addToItemPrice(flavor.getPrice());
        }
        // only change if not the currently selected type
        else if(this.flavor != selection) {
            // remove previous selection from menuItem price
            super.addToItemPrice(-this.flavor.getPrice());
            // set new selected size
            this.flavor = selection;
            // add newly selected size to menuItem price
            super.addToItemPrice(flavor.getPrice());
        }
    }

    /**
     * Getter method that returns this.flavor
     * @return DonutFlavor this.flavor
     */
    public DonutFlavor getFlavor() {
        return flavor;
    }

    /**
     * Overridden itemPrice method,
     * calculates and returns the price of the Donut based on selected flavor
     * @return price of the item
     */
    @Override
    public double itemPrice() {
        double itemPrice = 0;
        if(!Objects.isNull(flavor)) {
            itemPrice += flavor.getPrice();
        }
        return itemPrice;
    }
}
