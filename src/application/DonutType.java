package application;

public final class DonutType {

    public interface DonutTypeInterface {
        String getDisplayableType();
    }

    public enum Type implements DonutTypeInterface {
        YEAST(1.39, "Yeast"),
        CAKE(1.59, "Cake"),
        HOLES(0.33, "Holes");

        private final double price;
        private final String label;
        Type(double price, String label) {
            this.price = price;
            this.label = label;
        }

        public double getPrice() {
            return price;
        }

        public String getDisplayableType() {
            return label;
        }

        public static Type getTypeByLabel(String label) {
            Type selectedType = null;
            for(Type type : Type.values()) {
                if(type.label == label) {
                    selectedType = type;
                }
            }
            return selectedType;
        }
    }

    public enum Flavor implements DonutTypeInterface {
        GLAZED("Glazed" , Type.CAKE),
        CHOCOLATE("Chocolate", Type.CAKE),
        STRAWBERRY("Strawberry", Type.CAKE),
        JELLY("Jelly", Type.YEAST),
        VANILLA("Vanilla", Type.YEAST),
        BOSTON_CREME("Boston Creme", Type.YEAST),
        POWDERED("Powdered", Type.HOLES),
        CINNAMON("Cinnamon", Type.HOLES),
        PUMPKIN("Pumpkin", Type.HOLES);

        private final String label;
        private final Type donutType;

        Flavor(String label, Type donutType) {
            this.label = label;
            this.donutType = donutType;
        }

        public String getLabel() {
            return label;
        }
        public double getPrice() {
            return donutType.getPrice();
        }

        public Type getDonutType() {
            return donutType;
        }

        public String getDisplayableType() {
            return donutType.getDisplayableType();
        }

        public static Flavor getFlavorByLabel(String label) {
            Flavor selectedFlavor = null;
            for(Flavor flavor : Flavor.values()) {
                if(flavor.label.equals(label)) {
                    selectedFlavor = flavor;
                }
            }
            return selectedFlavor;
        }
    }
}
