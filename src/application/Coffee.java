package application;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Coffee extends MenuItem implements Customizable {
    private Set<addIns> selectedAddIns = new HashSet();
    private sizes selectedSize;

    public enum sizes {
        SHORT(1.99, "Short"),
        TALL(2.49, "Tall"),
        GRANDE(2.99, "Grande"),
        VENTI(3.49, "Venti");

        private final double price;
        private final String name;

        sizes(double price, String name) {
            this.price = price;
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public String toString() {
            return name;
        }
    }

    public enum addIns {
        CREAM("Cream"),
        SYRUP("Syrup"),
        MILK("Milk"),
        CARAMEL("Caramel"),
        WHIPPED_CREAM("Whipped Cream");

        private final String name;

        addIns(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    Coffee() {
        super();
    }

    @Override
    public double itemPrice() {
        double itemPrice = 0;
        // if user selected size
        if(!Objects.isNull(selectedSize)) {
            // add to price
            itemPrice += selectedSize.price;
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
        if(obj instanceof addIns ) {
            // cast to addIns enum
            addIns selectedAddIn = (addIns) obj;
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
        if(obj instanceof addIns ) {
            // cast to addIns enum
            addIns selectedAddIn = (addIns) obj;
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
        if(obj instanceof sizes) {
            // cast to sizes enum
            sizes selectedSize = (sizes) obj;
            // if null
            if(this.selectedSize == null) {
                // set new selected size
                this.selectedSize = selectedSize;
                // add newly selected size to menuItem price
                super.addToItemPrice(selectedSize.price);
            }
            // if not already selected
            else if( this.selectedSize != selectedSize) {
                // remove previous size from menuItem price
                super.addToItemPrice(-this.selectedSize.price);
                // set new selected size
                this.selectedSize = selectedSize;
                // add newly selected size to menuItem price
                super.addToItemPrice(selectedSize.price);
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
