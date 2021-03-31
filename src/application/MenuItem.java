package application;

import java.util.UUID;

public class MenuItem {
    private double itemPrice;
    private final UUID uuid;

    MenuItem() {
        itemPrice = 0;
        uuid = UUID.randomUUID();
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void addToItemPrice(double amount) {
        this.itemPrice += amount;
    }

    public UUID getUuid() {
        return uuid;
    }
}
