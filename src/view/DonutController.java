package view;
import application.Donut;
import application.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * DonutController class handles inputs from the ordering donuts GUI and defines all actions and helper methods relating to events triggered by the GUI
 *
 * @author Hugo De Moraes, Jonathan Dong
 */
public class DonutController implements Initializable {
    private static Order currentOrder;
    private static Double subtotal= 0.00;
    private final String[] AVAILABLE_QUANTITIES = { "1","2","3", "4", "5", "6", "12" };
    private final String MISSING_SELECTION = "Please select a donut";
    private final String MISSING_QUANITITY = "Please select a quantity";
    private final String ERROR = "Error";
    private final String EMPTY_LIST = "List is empty";

    @FXML
    private ListView<String> donutPickedlistView;
    @FXML
    private ListView<String> donutlistView;

    @FXML
    private ComboBox<String> donutTypeComboBox;

    @FXML
    private ComboBox<String> donutQuantityComboBox;

    @FXML
    private Button addDonutBtn;

    @FXML
    private Button removeDonutBtn;

    @FXML
    private Button addtoOrderBtn;

    @FXML
    private TextField subTotalField;

    /**
     * default constructor for DonutController, initializes currentOrder to be a new Order object and triggers adjustSubTotal and adjustCurrentOrderList
     * when Observable List for Order is changed
     */
    public DonutController(){
        currentOrder = new Order(change -> {
            adjustSubTotal();
            adjustCurrentOrderList();
        });
    }

    /**
     * getter method for currentOrder attribute
     * @return currentOrder Order
     */
    public static Order getCurrentOrder(){
        return (currentOrder);
    }

    /**
     * getter method for the current Order's subtotal
     * @return double value for subtotal
     */
    public static Double getCurrentSubtotal(){
        return (subtotal);
    }

    /**
     * adjusts subtotal whenever a menuitem is added or removed
     */
    private void adjustSubTotal() {
        subtotal = currentOrder.getOrderSubtotal();
        subTotalField.setText((YourOrderController.RoundTo2Decimals(currentOrder.getOrderSubtotal())));
    }

    /**
     * adjusts order list whenever a menuitem is added or removed
     */
    private void adjustCurrentOrderList() {
        // initialize a new map
        HashMap<DonutFlavor, Integer> order = new HashMap<>();

        // reduce items in order to flavor selection and amounts
        for(application.MenuItem item : currentOrder.getCurrentOrder()) {
            DonutFlavor selectedFlavor = ((Donut) item).getFlavor();
            if(order.containsKey(selectedFlavor)) {
                order.put(selectedFlavor, order.get(selectedFlavor) + 1);
            } else {
                order.put(selectedFlavor, 1);
            }
        }

        donutPickedlistView.getItems().clear();

        order.forEach((donut, amount) -> {
            donutPickedlistView.getItems().add(donut.getLabel() + " " + amount);
        });
    }

    /**
     * handles actions when adding a Donut to the current temporary order
     */
    @FXML
    private void addDonutToTemp() {
        // if empty
        if(donutlistView.getSelectionModel().isEmpty()) {
            YourOrderController.displayAlert(ERROR, EMPTY_LIST);
            return;
        }

        // if no donut was selected
        if(donutlistView.getSelectionModel().getSelectedItem() == null) {
            YourOrderController.displayAlert(ERROR, MISSING_SELECTION);
            return;
        }

        // if no quantity was selected
        if(donutQuantityComboBox.getSelectionModel().getSelectedItem() == null) {
            YourOrderController.displayAlert(ERROR, MISSING_QUANITITY);
            return;
        }

        // match the UI string to the enum
        final DonutFlavor SELECTED_FLAVOR = DonutFlavor.getFlavorByLabel(donutlistView.getSelectionModel().getSelectedItem());
        // get amount
        final int SELECTED_AMOUNT = Integer.parseInt(donutQuantityComboBox.getSelectionModel().getSelectedItem());

        // add to order
        for(int i = 0; i < SELECTED_AMOUNT; i++) {
            currentOrder.add(new Donut(SELECTED_FLAVOR));
        }
    }

    /**
     * handles actions when user removes donut from current temp order
     */
    @FXML
    private void removeDonutFromTemp(){
        // if empty
        if(donutPickedlistView.getSelectionModel().isEmpty()) {
            YourOrderController.displayAlert(ERROR, EMPTY_LIST);
            return;
        }

        // if no donut was selected
        if(donutPickedlistView.getSelectionModel().getSelectedItem() == null) {
            YourOrderController.displayAlert(ERROR, MISSING_SELECTION);
            return;
        }

        // get index to split
        // get last occurrence of space character to separate label and amounr
        final int INDEX = donutPickedlistView.getSelectionModel().getSelectedItem().lastIndexOf(" ");
        final DonutFlavor FLAVOR_TO_REMOVE = DonutFlavor.getFlavorByLabel(donutPickedlistView.getSelectionModel().getSelectedItem().substring(0, INDEX));
        final int AMOUNT = Integer.parseInt(donutPickedlistView.getSelectionModel().getSelectedItem().substring(INDEX+1));
        ArrayList<Donut> donutsToDelete = new ArrayList<>();

        // for each item in current order
        currentOrder.getCurrentOrder().forEach((item) -> {
            Donut donut = (Donut) item;
            // get donuts that match selected flavor to delete
            if(donut.getFlavor() == FLAVOR_TO_REMOVE) {
                donutsToDelete.add(donut);
            }
        });

        // for each item to delete
        for(int i = 0; i < AMOUNT; i++) {
            // delete
            currentOrder.remove(donutsToDelete.get(i));
        }
    }

    /**
     * returns a formatted string to two decimal places
     * @param val double to be formatted
     * @return a properly formatted string
     */
//    public String RoundTo2Decimals(double val) {
//        DecimalFormat df2 = new DecimalFormat("##0.00");
//        return (df2.format(val));
//    }

    /**
     * displays an alert popup with a given title and message
     * @param title
     * @param message
     */
//    private static void displayAlert(String title, String message){
//        Stage alertWindow = new Stage();
//        alertWindow.setTitle(title);
//        alertWindow.setMinWidth(300);
//        Label label = new Label();
//        label.setText(message);
//        Button closeButton = new Button("Close Window");
//        closeButton.setOnAction(e -> alertWindow.close());
//
//        VBox layout = new VBox(10);
//        layout.getChildren().addAll(label,closeButton);
//        layout.setAlignment(Pos.CENTER);
//
//        Scene scene = new Scene(layout);
//        alertWindow.setScene(scene);
//        alertWindow.showAndWait();
//
//    }

    /**
     * dictates actions after donut GUI is created
     * @param url url
     * @param resourceBundle resourcebundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // loadDonutList();
        loadDonutType();
        loadDonutQuantity();
        subTotalField.setEditable(false);
    }

    /**
     * pulls up the your order GUI after user presses Add to Order button
     * @throws IOException
     */
    @FXML
    private void loadyourOrder() throws IOException {

        // if empty
        if(donutlistView.getSelectionModel().isEmpty()) {
            YourOrderController.displayAlert(ERROR, EMPTY_LIST);
            return;
        }

        // if no donut was selected
        if(donutlistView.getSelectionModel().getSelectedItem() == null) {
            YourOrderController.displayAlert(ERROR, MISSING_SELECTION);
            return;
        }

        // if no quantity was selected
        if(donutQuantityComboBox.getSelectionModel().getSelectedItem() == null) {
            YourOrderController.displayAlert(ERROR, MISSING_QUANITITY);
            return;
        }


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/yourorder.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();

        stage = (Stage) addtoOrderBtn.getScene().getWindow();
        stage.close();

    }

    /**
     * populates the donutTypes in donutTypeComboBox
     */
    @FXML
    private void loadDonutType(){
        for(DonutType type : DonutType.values()) {
            donutTypeComboBox.getItems().add(type.getLabel());
        }
    }

    /**
     * populates the donut quantities in donutQuantityComboBox
     */
    @FXML
    private void loadDonutQuantity(){
        for(String amount : AVAILABLE_QUANTITIES) {
            donutQuantityComboBox.getItems().add(amount);
        }
        donutQuantityComboBox.setPromptText("Quantity");
    }

    /**
     * displays appropriate donut flavors after donut type has been selected
     * @param event
     */
    @FXML
    private void handleSelectDonutType(ActionEvent event){

        final DonutType SELECTED_DONUT_TYPE = DonutType.getTypeByLabel(donutTypeComboBox.getValue());

        donutlistView.getItems().clear();

        for(DonutFlavor flavor : DonutFlavor.values()) {
            if(flavor.getDonutType() == SELECTED_DONUT_TYPE) {
                donutlistView.getItems().add(flavor.getLabel());
            }
        }
    }


}


