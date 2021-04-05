package view;

import application.Order;
import application.StoreOrders;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class mainmenuController implements Initializable {

    @FXML
    private Button orderDonutBtn;

    public mainmenuController(){
        storeOrders = new StoreOrders();
    }
    private StoreOrders storeOrders;

    public static void loadMainMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("../view/mainmenu.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    private void loadOrderDonuts() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/orderingdonuts.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    private void loadOrderCoffee() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/orderingcoffee.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
