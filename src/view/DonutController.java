package view;
import application.Donut;
import application.*;
import application.MenuItem;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DonutController extends OrderController implements Initializable {
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
    private Button addToOrderBtn;

    @FXML
    private TextField subTotalField;

    private Order tempOrder;

    public DonutController(){
        super();
        tempOrder = new Order();
        tempOrder.addListener(change -> {
            adjustSubTotal();
            adjustCurrentOrderList();
        });
    }

    private void adjustSubTotal() {
        subtotal = tempOrder.getOrderSubtotal();
        subTotalField.setText((RoundTo2Decimals(subtotal)));
    }

    private void adjustCurrentOrderList() {
        // initialize a new map
        HashMap<DonutFlavor, Integer> order = new HashMap<>();

        // reduce items in order to flavor selection and amounts
        for(MenuItem item : tempOrder.getOrder()) {
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
        final DonutFlavor SELECTED_FLAVOR = DonutFlavor.getFlavorByLabel(donutlistView.getSelectionModel().getSelectedItem());
        // get amount
        final int SELECTED_AMOUNT = Integer.parseInt(donutQuantityComboBox.getSelectionModel().getSelectedItem());

        // add to order
        for(int i = 0; i < SELECTED_AMOUNT; i++) {
            tempOrder.add(new Donut(SELECTED_FLAVOR));
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
        final DonutFlavor FLAVOR_TO_REMOVE = DonutFlavor.getFlavorByLabel(donutPickedlistView.getSelectionModel().getSelectedItem().substring(0, INDEX));
        final int AMOUNT = Integer.parseInt(donutPickedlistView.getSelectionModel().getSelectedItem().substring(INDEX+1));
        ArrayList<Donut> donutsToDelete = new ArrayList<>();

        // for each item in current order
        tempOrder.getOrder().forEach((item) -> {
            Donut donut = (Donut) item;
            // get donuts that match selected flavor to delete
            if(donut.getFlavor() == FLAVOR_TO_REMOVE) {
                donutsToDelete.add(donut);
            }
        });

        // for each item to delete
        for(int i = 0; i < AMOUNT; i++) {
            // delete
            tempOrder.remove(donutsToDelete.get(i));
        }
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
        loadDonutType();
        loadDonutQuantity();
    }


    @FXML
    private void loadyourOrder() throws IOException {

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

        // add temp order to current order
        tempOrder.getOrder().forEach(item -> currentOrder.add(item));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/yourorder.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();

        stage = (Stage) addToOrderBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void loadDonutType(){
        for(DonutType type : DonutType.values()) {
            donutTypeComboBox.getItems().add(type.getLabel());
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
        final DonutType SELECTED_DONUT_TYPE = DonutType.getTypeByLabel(donutTypeComboBox.getValue());
        donutlistView.getItems().clear();

        for(DonutFlavor flavor : DonutFlavor.values()) {
            if(flavor.getDonutType() == SELECTED_DONUT_TYPE) {
                donutlistView.getItems().add(flavor.getLabel());
            }
        }
    }
}


