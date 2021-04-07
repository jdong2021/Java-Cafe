package view;
// import application.*;
import application.Coffee;
import application.CoffeeAddIn;
import application.CoffeeSize;
import application.Order;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
// import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;


/**
 * CoffeeController class handles inputs from the ordering Coffee GUI and defines all actions and helper methods relating
 * events triggered by the GUI
 */
public class CoffeeController extends OrderController implements Initializable {
    private final String[] AVAILABLE_QUANTITIES = { "1","2","3","4" };
    private final String SIZE_LABEL = "Size";
    private final String QUANTITY_LABEL = "Quantity";
    private final String ERROR_LOG = "ERROR";
    private final String SIZE_QUANTITY_LOG = "Please select size and quantity";

    @FXML
    private ComboBox<String> coffeeSizeComboBox;

    @FXML
    private ComboBox<String> coffeeQuantityComboBox;

    @FXML
    private TextField subTotalField;

    @FXML
    private CheckBox creamCheckBox;

    @FXML
    private CheckBox milkCheckBox;

    @FXML
    private CheckBox syrupCheckBox;

    @FXML
    private CheckBox caramelCheckBox;

    @FXML
    private CheckBox whippedCreamCheckBox;

    @FXML
    private Button addToOrderBtn;

    private Order tempOrder;

    public CoffeeController() {
        super();
        tempOrder = new Order();
        tempOrder.addListener(change -> {
            adjustSubTotal();
        });
    }

    private void adjustSubTotal() {
        subtotal = tempOrder.getOrderSubtotal();
        subTotalField.setText((RoundTo2Decimals(subtotal)));
    }

    /**
     * inserts valid coffee sizes into coffee sizes combobox
     */
    @FXML
    private void loadCoffeeSize(){
        for(CoffeeSize size : CoffeeSize.values()) {
            coffeeSizeComboBox.getItems().add(size.getLabel());
        }
        coffeeSizeComboBox.setPromptText(SIZE_LABEL);
    }

    /**
     * inserts valid coffee quantities into coffee quantity combobox
     */
    @FXML
    private void loadCoffeeQuantity(){
        for(String amount : AVAILABLE_QUANTITIES) {
            coffeeQuantityComboBox.getItems().add(amount);
        }
        coffeeQuantityComboBox.setPromptText(QUANTITY_LABEL);
    }

    /**
     * dictates actions right after Coffee GUI is created
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCoffeeSize();
        loadCoffeeQuantity();
    }

    /**
     * takes in info on how many coffees ordered and keeps track of current amount in order
     */
    @FXML
    private void onQuantitySelected() {
        // get amount selected and current amount in order
        final int SELECTED_AMOUNT = Integer.parseInt(coffeeQuantityComboBox.getSelectionModel().getSelectedItem());
        final int CUR_AMOUNT = tempOrder.getOrder().size();

        // case: do nothing
        if(SELECTED_AMOUNT == CUR_AMOUNT) {
            return;
        }

        // case: need to delete objects
        if(CUR_AMOUNT > SELECTED_AMOUNT) {
            removeFromOrder(CUR_AMOUNT, SELECTED_AMOUNT);
        }
        // case: need to add objects
        else if(CUR_AMOUNT < SELECTED_AMOUNT) {
            addToOrder(CUR_AMOUNT, SELECTED_AMOUNT);
        }

        // check if sizes need to be set
        handleSize();
        // check if addIns need to be set
        handleAddIn();
        // adjust subTotal
        adjustSubTotal();
    }

    /**
     * handles user input regarding add ins for coffee
     * @param e ActionEvent
     */
    @FXML
    private void onAddInSelected(ActionEvent e) {
        // adjust addIns
        handleAddIn();
        // adjust subTotal
        adjustSubTotal();
    }

    /**
     * handles user input regarding size of Coffee
     * @param e ActionEvent
     */
    @FXML
    private void onSizeSelected(ActionEvent e) {
        // adjust sizes
        handleSize();
        // adjust subTotal
        adjustSubTotal();
    }

    /**
     *loads yourOrder GUI after placing adding coffee to your order
     * @throws IOException
     */
    @FXML
    private void loadyourOrder() throws IOException {
        if(coffeeQuantityComboBox.getSelectionModel().getSelectedItem() == null ||
                coffeeSizeComboBox.getSelectionModel().getSelectedItem() == null){

            YourOrderController.displayAlert(ERROR_LOG, SIZE_QUANTITY_LOG);
            return;
        }

        // add temp order to current order
        tempOrder.getOrder().forEach(item -> {
            currentOrder.add(item);
        });

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/yourorder.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();

        stage = (Stage) addToOrderBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * computes amount to add to subtotal
     * @param curAmount integer current amount
     * @param selectedAmount integer selected amount
     */
    private void addToOrder(int curAmount, int selectedAmount) {
        // get amount to add
        final int AMOUNT_TO_ADD = selectedAmount - curAmount;
        // for amount, add
        for(int i = 0; i < AMOUNT_TO_ADD; i++) {
            tempOrder.add(new Coffee());
        }
    }

    /**
     * removes a coffee object from order
     * @param curAmount integer current amount
     * @param  selectedAmount selected amount
     */
    private void removeFromOrder(int curAmount, int selectedAmount) {
        // get array of objects to remove
        Object[] objectsToDelete = tempOrder.getOrder().toArray();
        objectsToDelete = Arrays.copyOfRange(objectsToDelete, selectedAmount, curAmount);
        // for each object, delete
        for( Object coffee : objectsToDelete) {
            tempOrder.remove(coffee);
        }
    }

    /**
     * deals with addIns entered by GUI
     */
    private void handleAddIn() {
        // only do when Coffee object exists
        if(!tempOrder.isEmpty()) {

            // handle cream
            if(creamCheckBox.isSelected()) {
                addMany(CoffeeAddIn.CREAM);
            } else {
                removeMany(CoffeeAddIn.CREAM);
            }

            // handle milk
            if(milkCheckBox.isSelected()) {
                addMany(CoffeeAddIn.MILK);
            } else {
                removeMany(CoffeeAddIn.MILK);
            }

            // handle syrup
            if(syrupCheckBox.isSelected()) {
                addMany(CoffeeAddIn.SYRUP);
            } else {
                removeMany(CoffeeAddIn.SYRUP);
            }

            // handle caramel
            if(caramelCheckBox.isSelected()) {
                addMany(CoffeeAddIn.CARAMEL);
            } else {
                removeMany(CoffeeAddIn.CARAMEL);
            }

            // handle whipped cream
            if(whippedCreamCheckBox.isSelected()) {
                addMany(CoffeeAddIn.WHIPPED_CREAM);
            } else {
                removeMany(CoffeeAddIn.WHIPPED_CREAM);
            }
        }
    }

    /**
     *deals with adding in multiple addins
     * @param addIn CoffeeAddIn to add in
     */
    private void addMany(CoffeeAddIn addIn) {
        tempOrder.getOrder().forEach((item) -> {
            Coffee coffee = (Coffee) item;
            coffee.add(addIn);
        });
    }

    /**
     * deals with removing multiple addins
     * @param addIn CoffeeAddIn to remove
     */
    private void removeMany(CoffeeAddIn addIn) {
        tempOrder.getOrder().forEach((item) -> {
            Coffee coffee = (Coffee) item;
            coffee.remove(addIn);
        });
    }

    /**
     * handles user info regarding size of coffees
     */
    private void handleSize() {
        // only do when Coffee object exists
        if(!tempOrder.isEmpty()) {
            // get selection
            final CoffeeSize SELECTED_SIZE = CoffeeSize.getSizeByLabel(coffeeSizeComboBox.getSelectionModel().getSelectedItem());
            // apply to each item in order
            tempOrder.getOrder().forEach((item) -> {
                Coffee coffee = (Coffee) item;
                coffee.setSize(SELECTED_SIZE);
            });
        }
    }
}
