package application;

public enum DonutType implements PriceModifier {
        YEAST(1.39, "Yeast"),
        CAKE(1.59, "Cake"),
        HOLES(0.33, "Holes");

        private final double price;
        private final String label;

        DonutType(double price, String label) {
            this.price = price;
            this.label = label;
        }

        public double getPrice() {
            return price;
        }
        public String getLabel() {
            return label;
        }

        public static DonutType getTypeByLabel(String label) {
            DonutType selectedType = null;
            for(DonutType type : DonutType.values()) {
                if(type.label == label) {
                    selectedType = type;
                }
            }
            return selectedType;
        }
    }