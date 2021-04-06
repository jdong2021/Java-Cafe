package application;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Coffee extends MenuItem implements Customizable {
    private Set<CoffeeAddIn> selectedAddIns = new HashSet();
    private CoffeeSize selectedSize;

    public Coffee() {
        super();
    }

    @Override
    public double itemPrice() {
        double itemPrice = 0;
        // if user selected size
        if(!Objects.isNull(selectedSize)) {
            // add to price
            itemPrice += selectedSize.getPrice();
        }
        // if user selected addIns
        if(!Objects.isNull(selectedAddIns)) {
            // addIns are all equal size
            // get number of addIns and multiply by addIn price
            itemPrice += ADD_IN_PRICE * selectedAddIns.size();
        }
        return itemPrice;
    }

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

    public boolean setSize(Object obj) {
        // handle size
        if(obj instanceof CoffeeSize) {
            // cast to sizes enum
            CoffeeSize selectedSize = (CoffeeSize) obj;
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
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Coffee{" +
                "selectedAddIns=" + selectedAddIns +
                ", selectedSize=" + selectedSize +
                '}';
    }
}
