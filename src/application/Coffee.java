package application;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Coffee class is a child class of MenuItem and represents a Coffee Object with additional instance variables
 * and methods unique to Coffee, implements Customizable interface
 *
 * @author Hugo De Moraes, Jonathan Dong
 */
public class Coffee extends MenuItem implements Customizable {
    private final Set<CoffeeAddIn> selectedAddIns = new HashSet<>();
    private CoffeeSize selectedSize;

    /**
     * Default Constructor for Coffee
     */
    public Coffee() {
        super();
    }

    /**
     * Overridden itemPrice method, calculates the price for a Coffee based on selected size and add-ins
     * @return a double representing the price
     */
    @Override
    public double itemPrice() {
        double itemPrice = 0;
        // if user selected size
        if(!Objects.isNull(selectedSize)) {
            // add to price
            itemPrice += selectedSize.getPrice();
        }
        // if user selected addIns
        if(!selectedAddIns.isEmpty()) {
            // addIns are all equal size
            // get number of addIns and multiply by addIn price
            itemPrice += ADD_IN_PRICE * selectedAddIns.size();
        }
        return itemPrice;
    }

    /**
     * Customizable interface method, given an object of type CoffeeAddIn
     * adds the object to this.selectedAddIns
     * @param obj Object
     * @return true if added successfully, false otherwise
     */
    public boolean add(Object obj) {
        // handle addIn
        if(obj instanceof CoffeeAddIn) {
            // cast to addIns enum
            CoffeeAddIn selectedAddIn = (CoffeeAddIn) obj;
            // if not already selected
            if(!this.selectedAddIns.contains(selectedAddIn)) {
                // add to set
                this.selectedAddIns.add(selectedAddIn);
                // add to price
                super.addToItemPrice(ADD_IN_PRICE);
            }
            return true;
        }
        return false;
    }

    /**
     * Customizable interface method, given an object of type CoffeeAddIn,
     * removes the object from this.selectedAddIns
     * @param obj Object
     * @return true if removed successfully, false otherwise
     */
    public boolean remove(Object obj) {
        if(obj instanceof CoffeeAddIn) {
            // cast to addIns enum
            CoffeeAddIn selectedAddIn = (CoffeeAddIn) obj;
            // if selected
            if(selectedAddIns.contains(selectedAddIn)) {
                // remove from set
                this.selectedAddIns.remove(selectedAddIn);
                // remove from menuItem price
                super.addToItemPrice(-ADD_IN_PRICE);
                return true;
            }
        }
        return false;
    }

    /**
     * Given an instance of CoffeeSize,
     * sets this.selectedSize to be the object
     * @param selectedSize CoffeeSize
     */
    public void setSize(CoffeeSize selectedSize) {
        // if null
        if(this.selectedSize == null) {
            // set new selected size
            this.selectedSize = selectedSize;
            // add newly selected size to menuItem price
            super.addToItemPrice(selectedSize.getPrice());
        }
        // if not already selected
        else if( this.selectedSize != selectedSize) {
            // remove previous size from menuItem price
            super.addToItemPrice(-this.selectedSize.getPrice());
            // set new selected size
            this.selectedSize = selectedSize;
            // add newly selected size to menuItem price
            super.addToItemPrice(selectedSize.getPrice());
        }
    }


    /**
     * Overridden toString method
     * @return formatted string representing Coffee object and it's data members
     */
    @Override
    public String toString() {
        return "Coffee - "
                + selectedSize.toString()
                + " - [ "
                + selectedAddIns.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "))
                + " ]";

    }
}
