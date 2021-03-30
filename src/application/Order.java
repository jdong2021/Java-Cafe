package application;

public class Order extends MenuItem implements Customizable {

    Order() {
        super();
    }

    public boolean add(Object obj) {
        return true;
    }
    public boolean remove(Object obj) {
        return true;
    }
}