package view;
import application.CoffeeEnumSizes;
import application.*;
import application.Coffee;
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
import java.util.List;
import java.util.ResourceBundle;

public class CoffeeController implements Initializable {
    private final String[] AVAILABLE_QUANTITIES = { "1","2","3", "4" };


    @FXML
    private ComboBox<String> coffeeTypeComboBox;

    @FXML
    private ComboBox<String> coffeeQuantityComboBox;

    @FXML
    private void loadCoffeeSize(){

       List<String> mylist = CoffeeEnumSizes.getNames();
        for(String s : mylist) {
            coffeeTypeComboBox.getItems().add(s);
        }
        coffeeTypeComboBox.setPromptText("Size");
    }

    @FXML
    private void loadCoffeeQuantity(){
        for(String amount : AVAILABLE_QUANTITIES) {
            coffeeQuantityComboBox.getItems().add(amount);
        }
        coffeeQuantityComboBox.setPromptText("Quantity");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCoffeeSize();
        loadCoffeeQuantity();
    }



}
