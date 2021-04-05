package application;

public class CoffeeEnumAdins {
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




}
