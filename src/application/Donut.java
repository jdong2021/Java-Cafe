package application;

public class Donut extends MenuItem {
    private types selectedType;
    public enum types {
        YEAST(1.39, "Yeast"), CAKE(1.59, "Cake"), HOLES(0.33, "Holes");

        private final double price;
        private final String name;
        types(double price, String name) {
            this.price = price;
            this.name = name;
        }

        public double getPrice() {
            return price;
        }
    }

    Donut() {
        super();
    }

    public boolean setType(Object obj) {
        // handle size
        if(obj instanceof types) {
            // cast to sizes enum
            types selectedType = (types) obj;
            // if null
            if(this.selectedType == null) {
                // set new selected size
                this.selectedType = selectedType;
                // add newly selected size to menuItem price
                super.addToItemPrice(selectedType.price);
            }
            // if not already selected
            else if(this.selectedType != selectedType) {
                // remove previous size from menuItem price
                super.addToItemPrice(-this.selectedType.price);
                // set new selected size
                this.selectedType = selectedType;
                // add newly selected size to menuItem price
                super.addToItemPrice(selectedType.price);
            }
            return true;
        }
        return false;
    }
}
