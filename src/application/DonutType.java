package application;

/**
 * DonutType enum that contains tbe different supported types for Donut
 * implements the PriceModifier interface
 *
 * @author Hugo De Moraes, Jonathan Dong
 */
public enum DonutType implements PriceModifier {
        YEAST(1.39, "Yeast"),
        CAKE(1.59, "Cake"),
        HOLES(0.33, "Holes");

        private final double price;
        private final String label;

        /**
        * Default constructor for DonutType
        * @param price double
        * @param label String
         */
        DonutType(double price, String label) {
            this.price = price;
            this.label = label;
        }

        /**
        * PriceModifier implementation
        * Getter method for price of a particular DonutType
        * @return this.price
        */
        public double getPrice() {
            return price;
        }

        /**
        * PriceModifier implementation
        * Getter method for label of a particular DonutType
        * @return this.label
        */
        public String getLabel() {
            return label;
        }

        /**
        * Helper method that given a String label, returns the matching DonutType
        * @param label String
        * @return returns one of the supported DonutType objects if matched, else null
         */
        public static DonutType getTypeByLabel(String label) {
            DonutType selectedType = null;
            for(DonutType type : DonutType.values()) {
                if(type.label.equals(label)) {
                    selectedType = type;
                }
            }
            return selectedType;
        }
    }