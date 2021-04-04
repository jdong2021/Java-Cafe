package application;

import java.util.Objects;

public class Donut extends MenuItem {
    private DonutType.Flavor selectedDonut;

    public Donut() {
        super();
    }

    @Override
    public double itemPrice() {
        double itemPrice = 0;
        if(!Objects.isNull(selectedDonut)) {
            itemPrice += selectedDonut.getPrice();
        }
        return itemPrice;
    }

    public boolean setType(DonutType.Flavor selection) {
        // handle null
        if(Objects.isNull(selectedDonut)) {
            // set new selected size
            this.selectedDonut = selection;
            // add newly selected size to menuItem price
            super.addToItemPrice(selectedDonut.getPrice());
        }
        // only change if not the currently selected type
        else if(this.selectedDonut != selection) {
            // remove previous selection from menuItem price
            super.addToItemPrice(-this.selectedDonut.getPrice());
            // set new selected size
            this.selectedDonut = selection;
            // add newly selected size to menuItem price
            super.addToItemPrice(selectedDonut.getPrice());
        }
        return true;
    }
}
