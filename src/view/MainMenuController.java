package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * MainMenu Controller handles inputs from GUI regarding ordering donuts, ordering coffee, viewing their current order, and viewing store orders
 *
 * @author Hugo De Moraes, Jonathan Dong
 */
public class MainMenuController implements Initializable {

    @FXML
    private Button orderDonutBtn;

    @FXML
    private Button orderCoffeeBtn;

    @FXML
    private Button yourOrderBtn;

    @FXML
    private Button storeOrdersBtn;

    /**
     * Constructor for MainMenu Controller
     */
    public MainMenuController(){

    }

    /**
     * loadOrderDonuts loads up the GUI for ordering donuts
     * @throws IOException exception to be thrown if fxml not found
     */
    @FXML
    private void loadOrderDonuts() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/orderingdonuts.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }
    /**
     * loadOrderCoffee loads up the GUI for ordering coffee
     * @throws IOException exception to be thrown if fxml not found
     */
    @FXML
    private void loadOrderCoffee() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/orderingcoffee.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }
    /**
     * loadYourOrder loads up the GUI for the current order. If user already placed their order, the the GUI will be blank
     * @throws IOException exception to be thrown if fxml not found
     */
    @FXML
    private void loadYourOrder() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/yourorder.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }
    /**
     * loadStoreOrders loads up the GUI for all store Orders. Users have the option to export store orders as well
     * @throws IOException exception to be thrown if fxml not found
     */
    @FXML
    private void loadStoreOrders() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/storeorders.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }

    /**
     * dictates actions after main menu GUI is created
     * @param url url
     * @param resourceBundle resourcebundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
