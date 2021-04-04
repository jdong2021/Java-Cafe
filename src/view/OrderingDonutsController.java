package view;
import application.Donut;
import application.*;
import application.MenuItem;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class OrderingDonutsController implements Initializable {
    private Order currentOrder;
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
    private TextField subTotalField;


    public OrderingDonutsController(){
        currentOrder = new Order(change -> {
            adjustSubTotal();
            adjustCurrentOrderList();
        });
    }

    private void adjustSubTotal() {
        subTotalField.setText(Double.toString(RoundTo2Decimals(currentOrder.getOrderSubtotal())));
    }

    private void adjustCurrentOrderList() {
        // initialize a new map
        HashMap<DonutType.Flavor, Integer> order = new HashMap<>();

        // reduce items in order to flavor selection and amounts
        for(application.MenuItem item : currentOrder.getCurrentOrder()) {
            DonutType.Flavor selectedFlavor = ((Donut) item).getSelectedDonut();
            if(order.containsKey(selectedFlavor)) {
                order.put(selectedFlavor, order.get(selectedFlavor) + 1);
            } else {
                order.put(selectedFlavor, 1);
            }
        }

        donutPickedlistView.getItems().clear();

        order.forEach((k,v) -> {
            donutPickedlistView.getItems().add(k.getLabel() + " " + v);
        });
    }

    @FXML
    private void addDonutToTemp() {
        // if empty
        if(donutlistView.getSelectionModel().isEmpty()) {
            displayAlert(ERROR, EMPTY_LIST);
            return;
        }

        // if no donut was selected
        if(donutlistView.getSelectionModel().getSelectedItem() == null) {
            displayAlert(ERROR, MISSING_SELECTION);
            return;
        }

        // if no quantity was selected
        if(donutQuantityComboBox.getSelectionModel().getSelectedItem() == null) {
            displayAlert(ERROR, MISSING_QUANITITY);
            return;
        }

        // match the UI string to the enum
        final DonutType.Flavor SELECTED_FLAVOR = DonutType.Flavor.getFlavorByLabel(donutlistView.getSelectionModel().getSelectedItem());
        // get amount
        final int SELECTED_AMOUNT = Integer.parseInt(donutQuantityComboBox.getSelectionModel().getSelectedItem());


        // add to order
        for(int i = 0; i < SELECTED_AMOUNT; i++) {
            currentOrder.add(new Donut(SELECTED_FLAVOR));
        }
    }

    @FXML
    private void removeDonutFromTemp(){
        // if empty
        if(donutPickedlistView.getSelectionModel().isEmpty()) {
            displayAlert(ERROR, EMPTY_LIST);
            return;
        }

        // if no donut was selected
        if(donutPickedlistView.getSelectionModel().getSelectedItem() == null) {
            displayAlert(ERROR, MISSING_SELECTION);
            return;
        }

        // get index to split
        // get last occurrence of space character to separate label and amounr
        final int INDEX = donutPickedlistView.getSelectionModel().getSelectedItem().lastIndexOf(" ");
        final DonutType.Flavor FLAVOR_TO_REMOVE = DonutType.Flavor.getFlavorByLabel(donutPickedlistView.getSelectionModel().getSelectedItem().substring(0, INDEX));
        final int AMOUNT = Integer.parseInt(donutPickedlistView.getSelectionModel().getSelectedItem().substring(INDEX+1));
        ArrayList<Donut> donutsToDelete = new ArrayList<>();

        // for each item in current order
        currentOrder.getCurrentOrder().forEach((item) -> {
            Donut donut = (Donut) item;
            // get donuts that match selected flavor to delete
            if(donut.getSelectedDonut() == FLAVOR_TO_REMOVE) {
                donutsToDelete.add(donut);
            }
        });

        // for each item to delete
        for(int i = 0; i < AMOUNT; i++) {
            // delete
            currentOrder.remove(donutsToDelete.get(i));
        }
    }

    private double RoundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
    }

    private static void displayAlert(String title, String message){
        Stage alertWindow = new Stage();
        alertWindow.setTitle(title);
        alertWindow.setMinWidth(300);
        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close Window");
        closeButton.setOnAction(e -> alertWindow.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        alertWindow.setScene(scene);
        alertWindow.showAndWait();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // loadDonutList();
        loadDonutType();
        loadDonutQuantity();
    }

    @FXML
    private void loadDonutType(){
        for(DonutType.Type type : DonutType.Type.values()) {
            donutTypeComboBox.getItems().add(type.getDisplayableType());
        }
    }

    @FXML
    private void loadDonutQuantity(){
        for(String amount : AVAILABLE_QUANTITIES) {
            donutQuantityComboBox.getItems().add(amount);
        }
        donutQuantityComboBox.setPromptText("Quantity");
    }

    @FXML
    private void handleSelectDonutType(ActionEvent event){
        final DonutType.Type SELECTED_DONUT_TYPE = DonutType.Type.getTypeByLabel(donutTypeComboBox.getValue());

        donutlistView.getItems().clear();

        for(DonutType.Flavor flavor : DonutType.Flavor.values()) {
            if(flavor.getDonutType() == SELECTED_DONUT_TYPE) {
                donutlistView.getItems().add(flavor.getLabel());
            }
        }
    }
}


