package application;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Coffee extends MenuItem implements Customizable {
    private final Set<CoffeeAddIn> selectedAddIns = new HashSet<>();

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
        if(!selectedAddIns.isEmpty()) {
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

    public CoffeeSize getSelectedSize() {
        return selectedSize;
    }

    public Set<CoffeeAddIn> getSelectedAddIns() {
        return selectedAddIns;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Coffee)) {
            return false;
        }

        // typecast o to Employee so that we can compare data members
        Coffee c = (Coffee) o;

        if(selectedSize != c.getSelectedSize()) {
            return false;
        }

        return selectedAddIns.equals(c.getSelectedAddIns());
    }

    @Override
    public String toString() {
        return "Coffee - "
                + selectedSize.toString()
                + " - "
                + selectedAddIns.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

    }
}
