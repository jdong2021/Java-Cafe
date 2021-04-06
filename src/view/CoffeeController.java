package view;
import application.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.net.URL;

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
    private void addCoffeeToTemp() {
        final int SELECTED_AMOUNT = Integer.parseInt(coffeeQuantityComboBox.getSelectionModel().getSelectedItem());
        for(int i = 0; i < SELECTED_AMOUNT; i++) {
            currentOrder.add(new Coffee());
        }
        handleSize();
        handleAddIn();
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

    @FXML
    private void onSizeSelected(ActionEvent e) {
        handleSize();
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

    @FXML
    void onAddInSelected(ActionEvent e) {
        handleAddIn();
    }
}
