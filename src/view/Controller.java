package view;

import application.Order;
import application.StoreOrders;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private ListView<?> listView;

    @FXML
    private Button addOrderButton;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    private StoreOrders storeOrders;

    ListChangeListener<Order> testLister = change -> System.out.println("change!");

    public Controller() {
        storeOrders = new StoreOrders(testLister);
    }

    @FXML
    void onAddButtonClick(ActionEvent event) {
        Order newOrder = new Order();
        if(storeOrders.add(newOrder)) {
            System.out.println("Success");
        }
    }

}
