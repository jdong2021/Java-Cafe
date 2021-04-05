package view;

import application.StoreOrders;

public class Context {
    private final static Context instance = new Context();

    public static Context getInstance() {
        return instance;
    }

    private StoreOrders storeOrders = new StoreOrders();

    public StoreOrders getStoreOrders() {
        return storeOrders;
    }
}
