package application;

import java.util.ArrayList;
import java.util.List;

public class CoffeeEnumSizes {
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

    public static List<String> getNames(){
        ArrayList<String> nameList = new ArrayList<String>();
        for (sizes s : sizes.values()){
            nameList.add(s.name);
        }
        return nameList;
    }
}
