package application;

import java.util.Objects;

public class Donut extends MenuItem {
    private DonutFlavor flavor;

    public Donut() {
        super();
    }

    public Donut(DonutFlavor flavor) {
        this.flavor = flavor;
    }

    public boolean setType(DonutFlavor selection) {
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
        return true;
    }

    public DonutFlavor getFlavor() {
        return flavor;
    }



    @Override
    public double itemPrice() {
        double itemPrice = 0;
        if(!Objects.isNull(flavor)) {
            itemPrice += flavor.getPrice();
        }
        return itemPrice;
    }
}
