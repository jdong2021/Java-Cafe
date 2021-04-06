package view;
import application.*;
import application.MenuItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
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

    public CoffeeController() {
        super();
        super.addListener( change -> {
            adjustSubTotal(subTotalField);
        });
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
        final int CUR_AMOUNT = currentOrder.getCurrentOrder().size();

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
        adjustSubTotal(subTotalField);
    }

    @FXML
    private void onAddInSelected(ActionEvent e) {
        // adjust addIns
        handleAddIn();
        // adjust subTotal
        adjustSubTotal(subTotalField);
    }

    @FXML
    private void onSizeSelected(ActionEvent e) {
        // adjust sizes
        handleSize();
        // adjust subTotal
        adjustSubTotal(subTotalField);
    }

    private void addToOrder(int curAmount, int selectedAmount) {
        // get amount to add
        final int AMOUNT_TO_ADD = selectedAmount - curAmount;
        // for amount, add
        for(int i = 0; i < AMOUNT_TO_ADD; i++) {
            currentOrder.add(new Coffee());
        }
    }

    private void removeFromOrder(int curAmount, int selectedAMount) {
        // get array of objects to remove
        Object[] objectsToDelete = currentOrder.getCurrentOrder().toArray();
        objectsToDelete = Arrays.copyOfRange(objectsToDelete, selectedAMount, curAmount);
        // for each object, delete
        for( Object coffee : objectsToDelete) {
            currentOrder.remove(coffee);
        }
    }

    private void handleAddIn() {
        // only do when Coffee object exists
        if(!currentOrder.isEmpty()) {

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
        currentOrder.getCurrentOrder().forEach((item) -> {
            Coffee coffee = (Coffee) item;
            coffee.add(addIn);
        });
    }

    private void removeMany(CoffeeAddIn addIn) {
        currentOrder.getCurrentOrder().forEach((item) -> {
            Coffee coffee = (Coffee) item;
            coffee.remove(addIn);
        });
    }

    private void handleSize() {
        // only do when Coffee object exists
        if(!currentOrder.isEmpty()) {
            // get selection
            final CoffeeSize SELECTED_SIZE = CoffeeSize.getSizeByLabel(coffeeSizeComboBox.getSelectionModel().getSelectedItem());
            // apply to each item in order
            currentOrder.getCurrentOrder().forEach((item) -> {
                Coffee coffee = (Coffee) item;
                coffee.setSize(SELECTED_SIZE);
            });
        }
    }
}
