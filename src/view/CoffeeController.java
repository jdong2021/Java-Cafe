package view;
import application.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import java.util.Arrays;
import java.util.ResourceBundle;

public class CoffeeController extends OrderController implements Initializable {
    private final String[] AVAILABLE_QUANTITIES = { "1","2","3","4" };
    private final String SIZE_LABEL = "Size";
    private final String QUANTITY_LABEL = "Quantity";

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

    @FXML
    private void loadCoffeeSize(){
        for(CoffeeSize size : CoffeeSize.values()) {
            coffeeSizeComboBox.getItems().add(size.getLabel());
        }
        coffeeSizeComboBox.setPromptText(SIZE_LABEL);
    }

    @FXML
    private void loadCoffeeQuantity(){
        for(String amount : AVAILABLE_QUANTITIES) {
            coffeeQuantityComboBox.getItems().add(amount);
        }
        coffeeQuantityComboBox.setPromptText(QUANTITY_LABEL);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCoffeeSize();
        loadCoffeeQuantity();
    }

    @FXML
    private void onQuantitySelected() {
        // get amount selected and current amount in order
        final int SELECTED_AMOUNT = Integer.parseInt(coffeeQuantityComboBox.getSelectionModel().getSelectedItem());
        final int CUR_AMOUNT = tempOrder.getOrder().size();

        System.out.println(SELECTED_AMOUNT);
        System.out.println(CUR_AMOUNT);

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

    @FXML
    private void onAddInSelected(ActionEvent e) {
        // adjust addIns
        handleAddIn();
        // adjust subTotal
        adjustSubTotal();
    }

    @FXML
    private void onSizeSelected(ActionEvent e) {
        // adjust sizes
        handleSize();
        // adjust subTotal
        adjustSubTotal();
    }

    @FXML
    private void loadyourOrder() throws IOException {

        // if empty
//        if(donutlistView.getSelectionModel().isEmpty()) {
//            displayAlert(ERROR, EMPTY_LIST);
//            return;
//        }
//
//        // if no donut was selected
//        if(donutlistView.getSelectionModel().getSelectedItem() == null) {
//            displayAlert(ERROR, MISSING_SELECTION);
//            return;
//        }
//
//        // if no quantity was selected
//        if(donutQuantityComboBox.getSelectionModel().getSelectedItem() == null) {
//            displayAlert(ERROR, MISSING_QUANITITY);
//            return;
//        }

        // add temp order to current order
        tempOrder.getOrder().forEach(item -> {
            System.out.println("Adding");
            currentOrder.add(item);
        });

        currentOrder.getOrder().forEach(item -> {
            System.out.println("Added");
        });

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/yourorder.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();

        stage = (Stage) addToOrderBtn.getScene().getWindow();
        stage.close();
    }

    private void addToOrder(int curAmount, int selectedAmount) {
        // get amount to add
        final int AMOUNT_TO_ADD = selectedAmount - curAmount;
        // for amount, add
        System.out.println("Adding " + AMOUNT_TO_ADD);
        for(int i = 0; i < AMOUNT_TO_ADD; i++) {
            System.out.println("Adding to temp order");
            tempOrder.add(new Coffee());
        }
        tempOrder.getOrder().forEach(item -> System.out.println("Added to temp order"));
    }

    private void removeFromOrder(int curAmount, int selectedAMount) {
        // get array of objects to remove
        Object[] objectsToDelete = tempOrder.getOrder().toArray();
        objectsToDelete = Arrays.copyOfRange(objectsToDelete, selectedAMount, curAmount);
        // for each object, delete
        for( Object coffee : objectsToDelete) {
            tempOrder.remove(coffee);
        }
    }

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

    private void addMany(CoffeeAddIn addIn) {
        tempOrder.getOrder().forEach((item) -> {
            Coffee coffee = (Coffee) item;
            coffee.add(addIn);
        });
    }

    private void removeMany(CoffeeAddIn addIn) {
        tempOrder.getOrder().forEach((item) -> {
            Coffee coffee = (Coffee) item;
            coffee.remove(addIn);
        });
    }

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
