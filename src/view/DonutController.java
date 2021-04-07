package view;
import application.Donut;
import application.MenuItem;
import application.DonutFlavor;
import application.DonutType;
import application.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * DonutController class handles inputs from the ordering donuts GUI and defines all actions and helper methods relating to events triggered by the GUI
 *
 * @author Hugo De Moraes, Jonathan Dong
 */
public class DonutController extends OrderController implements Initializable {
    private final String[] AVAILABLE_QUANTITIES = { "1","2","3", "4", "5", "6", "12" };
    private final String MISSING_SELECTION = "Please select a donut";
    private final String MISSING_QUANITITY = "Please select a quantity";
    private final String ERROR = "Error";
    private final String EMPTY_LIST = "List is empty";
    private final String QUANTITY_LABEL = "QUANTITY";

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

    /**
     * default constructor for DonutController, initializes currentOrder to be a new Order object and triggers adjustSubTotal and adjustCurrentOrderList
     * when Observable List for Order is changed
     */
    public DonutController(){
        super();
        tempOrder = new Order();
        tempOrder.addListener(change -> {
            adjustSubTotal();
            adjustCurrentOrderList();
        });
    }

    /**
     * adjusts subtotal whenever a menuitem is added or removed
     */
    private void adjustSubTotal() {
        subtotal = tempOrder.getOrderSubtotal();
        subTotalField.setText((YourOrderController.RoundTo2Decimals(subtotal))); }

    /**
     * adjusts order list whenever a menuitem is added or removed
     */
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
            tempOrder.add(new Donut(SELECTED_FLAVOR));
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

    /**
     * dictates actions after donut GUI is created
     * @param url url
     * @param resourceBundle resourcebundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDonutType();
        loadDonutQuantity();
        subTotalField.setEditable(false);
    }

    /**
     * pulls up the your order GUI after user presses Add to Order button
     * @throws IOException exception to be thrown
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
        donutQuantityComboBox.setPromptText(QUANTITY_LABEL);
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


