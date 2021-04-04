package view;
import application.Donut;
import application.*;
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

import java.util.ResourceBundle;

public class orderingDonutsController implements Initializable {
    private Order tempCurrentOrder;
    private double subtotal =0 ;
    private Donut tempCurrentDonut;

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



    public orderingDonutsController(){

    }

    // method for when add button is pressed
    @FXML
    private void  addDonutToTemp(){
    // first we wanna make it show up on the other listview object, along with its quantity
      //  then we can add it to our temp order
        // then we can compute the sub total and display it
        // the add to order button will take this temp order and add it to store orders.
        String currentDonutType = donutTypeComboBox.getSelectionModel().getSelectedItem();
        String currentDonutFlavor = donutlistView.getSelectionModel().getSelectedItem();
        String currentDonutQuantity = donutQuantityComboBox.getSelectionModel().getSelectedItem();
        int currentQuantity = Integer.parseInt(currentDonutQuantity);

            if(currentDonutFlavor == null || currentDonutFlavor.isEmpty()){
                // gotta make a pop up letting user know that they need to select something
                displayAlert("Error With Order", "Please choose Donut Type");
                return;
            }


            donutPickedlistView.getItems().addAll(currentDonutFlavor + " " +currentDonutQuantity);

            if(currentDonutType.equals(Donut.types.YEAST.name())) {
               tempCurrentOrder = new Order();
               tempCurrentDonut = new Donut();
               tempCurrentDonut.setType(Donut.types.YEAST);
               tempCurrentOrder.addMultipleDonuts(tempCurrentDonut,currentQuantity);
               adjustSubtotalIncrease();

               subTotalField.setText(Double.toString(subtotal));
           }
           else if (currentDonutType.equals("Cake")){
               tempCurrentOrder = new Order();
               tempCurrentDonut = new Donut();
               tempCurrentDonut.setType(Donut.types.CAKE);
               tempCurrentOrder.addMultipleDonuts(tempCurrentDonut,currentQuantity);
               adjustSubtotalIncrease();

               subTotalField.setText(Double.toString(subtotal));
           }
           else if (currentDonutType.equals("Holes")){
               tempCurrentOrder = new Order();
               tempCurrentDonut = new Donut();
               tempCurrentDonut.setType(Donut.types.HOLES);

               tempCurrentOrder.addMultipleDonuts(tempCurrentDonut,currentQuantity);
               adjustSubtotalIncrease();
               subTotalField.setText(Double.toString(subtotal));
           }



    }

    @FXML
    private void removeDonutFromTemp(){
        // first remove it from displaying on listview object
        String donutToRemove = donutPickedlistView.getSelectionModel().getSelectedItem();

        adjustSubtotalDecrease();
        subTotalField.setText(Double.toString(subtotal));


        donutPickedlistView.getItems().remove(donutToRemove);

    }



    private double RoundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
    }


    private void adjustSubtotalIncrease(){

        String currentDonutQuantity = donutQuantityComboBox.getSelectionModel().getSelectedItem();
        int currentQuantity = Integer.parseInt(currentDonutQuantity);

        for(int i =0; i <currentQuantity; i++){
            subtotal = subtotal + tempCurrentDonut.itemPrice();
            subtotal = RoundTo2Decimals(subtotal);

        }
    }

    private void adjustSubtotalDecrease(){
        String donutToRemove = donutPickedlistView.getSelectionModel().getSelectedItem();
        String[] arrOfStr = donutToRemove.split(" ");
        String donutToRemoveFlavor = arrOfStr[0];
        int quantity = Integer.valueOf(arrOfStr[1]);

        //if its a cake donut
        if(donutToRemoveFlavor.equals("Glazed") || donutToRemoveFlavor.equals("Chocolate")
                || donutToRemoveFlavor.equals("Strawberry")){
            for(int i =0; i <quantity; i++){
                subtotal = subtotal - Donut.types.CAKE.getPrice();
                subtotal = RoundTo2Decimals(subtotal);

            }

        }
        else if (donutToRemoveFlavor.equals("Jelly") ||donutToRemoveFlavor.equals("VanillaFrosted") || donutToRemoveFlavor.equals("BostonCreme") ){
            for(int i =0; i <quantity; i++){
                subtotal = subtotal - Donut.types.YEAST.getPrice();
                subtotal = RoundTo2Decimals(subtotal);

            }

        }
        else if (donutToRemoveFlavor.equals("Powdered") ||donutToRemoveFlavor.equals("Cinnamon") || donutToRemoveFlavor.equals("Pumpkin") ){
            for(int i =0; i <quantity; i++){
                subtotal = subtotal - Donut.types.HOLES.getPrice();
                subtotal = RoundTo2Decimals(subtotal);

            }

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
       // loadDonutList();
        loadDonutType();
        loadDonutQuantity();
    }

    @FXML
    private void loadDonutType(){
        donutTypeComboBox.getItems().addAll("Yeast", "Cake", "Holes");
        donutTypeComboBox.setPromptText("Donut Type");
    }

    @FXML
    private void loadDonutQuantity(){
        donutQuantityComboBox.getItems().addAll("1","2","3", "4", "5", "6", "12");
        donutQuantityComboBox.setPromptText("Quantity");
    }

    @FXML
    private void handleSelectDonutType(ActionEvent event){
        if(donutTypeComboBox.getValue().equals("Cake")) {
            donutlistView.getItems().clear();
            donutlistView.getItems().addAll("Glazed", "Chocolate", "Strawberry");

        }
        else if(donutTypeComboBox.getValue().equals("Yeast")) {
            donutlistView.getItems().clear();
            donutlistView.getItems().addAll("Jelly", "VanillaFrosted", "BostonCreme");
        }
        else if(donutTypeComboBox.getValue().equals("Holes")) {
            donutlistView.getItems().clear();
            donutlistView.getItems().addAll("Powdered ", "Cinnamon", "Pumpkin");
        }
    }
}


