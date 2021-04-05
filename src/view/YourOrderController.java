package view;

import application.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class YourOrderController implements Initializable {
    private static StoreOrders myStoreOrders = new StoreOrders();
    private static final double SALES_TAX_PERCENT = 0.06625;


    public static StoreOrders getStoreOrders(){
        return myStoreOrders;
    }

    @FXML
    private ListView<String> myOrder;
    @FXML
    private TextField yourOrderSubtotal;
    @FXML
    private Button placeOrderBtn;
    @FXML
    private TextField salestax;
    @FXML
    private TextField totalTextField;
    @FXML
    private Button removeItemBtn;


   @FXML
   public void loadOrderInfo(){
       Order currentOrder = DonutController.getCurrentOrder();
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

       myOrder.getItems().clear();

       order.forEach((k,v) -> {
           myOrder.getItems().add(k.getLabel() + " " + v);
       });
   }

   @FXML
   public void loadSubtotalInfo(){
       yourOrderSubtotal.setText(Double.toString(DonutController.getCurrentSubtotal()));
   }

   @FXML
   public void loadStoreOrders() throws IOException {
       myStoreOrders.add(DonutController.getCurrentOrder());
       System.out.println("order added");





       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/storeorders.fxml"));
       Parent root1 = (Parent) fxmlLoader.load();
       Stage stage = new Stage();
       stage.setScene(new Scene(root1));
       stage.show();







   }
   @FXML
   private void calculatesalestax(){
        salestax.setText(Double.toString(RoundTo2Decimals(DonutController.getCurrentSubtotal() * SALES_TAX_PERCENT)));
   }

   @FXML
   private void calculatetotal(){
       double subtotalval = Double.parseDouble(yourOrderSubtotal.getText());
       double salestaxval = Double.parseDouble(salestax.getText());
       totalTextField.setText(Double.toString(RoundTo2Decimals(subtotalval+salestaxval)));
   }

    public double RoundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
    }

    @FXML
    private void removeItem(){
       //iterate thru order to find menuitem to remvoe
        Order currentOrder = DonutController.getCurrentOrder();


        // get index to split
        // get last occurrence of space character to separate label and amounr
        final int INDEX = myOrder.getSelectionModel().getSelectedItem().lastIndexOf(" ");
        final DonutFlavor FLAVOR_TO_REMOVE = DonutFlavor.getFlavorByLabel(myOrder.getSelectionModel().getSelectedItem().substring(0, INDEX));
        final int AMOUNT = Integer.parseInt(myOrder.getSelectionModel().getSelectedItem().substring(INDEX+1));
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

        loadOrderInfo();
        loadSubtotalInfo();
        calculatesalestax();
        calculatetotal();
//        for (MenuItem m : currentOrder.getCurrentOrder()) {
//            if (o.getOrderNumber().equals(currentOrderUUID)) {
//
            }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            loadOrderInfo();
            loadSubtotalInfo();
            calculatesalestax();
            calculatetotal();
    }
}
