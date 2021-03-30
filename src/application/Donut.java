package application;

public class Donut extends MenuItem implements Customizable {
    static enum Types {
        YEAST(1.39), CAKE(1.59), HOLES(0.33);

        private final double value;
        Types(double value) {
            this.value = value;
        }

        public double getPrice() {
            return value;
        }
    }

    Donut(Types type) {
        super(type.getPrice());
    }

    public boolean add(Object obj) {
        return true;
    }
    public boolean remove(Object obj) {
        return true;
    }
}
