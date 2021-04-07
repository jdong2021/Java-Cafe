package view;

import application.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class YourOrderController implements Initializable {
    private static final double SALES_TAX_PERCENT = 0.06625;
    Order yourOrder;

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

    public YourOrderController() {
        yourOrder = OrderController.getCurrentOrder();
    }

   @FXML
   public void loadOrderInfo(){
       Order currentOrder = OrderController.getCurrentOrder();

       // initialize a new map
       HashMap<Object, Integer> order = new HashMap<>();

       for(MenuItem item : currentOrder.getOrder()) {
           if(item instanceof Donut) {
               // handle donut
               DonutFlavor selectedFlavor = ((Donut) item).getFlavor();
               if(order.containsKey(selectedFlavor)) {
                   order.put(selectedFlavor, order.get(selectedFlavor) + 1);
               } else {
                   order.put(selectedFlavor, 1);
               }
           }
           // handle coffee
           else {
               if(order.containsKey(item)) {
                   order.put(item, order.get(item) + 1);
               } else {
                   order.put(item, 1);
               }
           }
       }

//       // check if donut
//       if(currentOrder.getCurrentOrder().get(0) instanceof Donut) {
//           // reduce items in order to flavor selection and amounts
//           for(MenuItem item : currentOrder.getCurrentOrder()) {
//               DonutFlavor selectedFlavor = ((Donut) item).getFlavor();
//               if(order.containsKey(selectedFlavor)) {
//                   order.put(selectedFlavor, order.get(selectedFlavor) + 1);
//               } else {
//                   order.put(selectedFlavor, 1);
//               }
//           }
//       }
//       // else is coffee
//       else {
//           for(MenuItem item : currentOrder.getCurrentOrder()) {
//               if(order.containsKey(item)) {
//                   order.put(item, order.get(item) + 1);
//               } else {
//                   order.put(item, 1);
//               }
//           }
//       }

       myOrder.getItems().clear();

       order.forEach((k,v) -> {
           myOrder.getItems().add(k + " " + v);
       });
   }

   @FXML
   public void loadSubtotalInfo(){
       // yourOrderSubtotal.setText(RoundTo2Decimals(OrderController.getCurrentSubtotal()));
   }

   @FXML
   public void loadStoreOrders() throws IOException {
       // yourOrder.add(OrderController.getCurrentOrder());
       // OrderController.getCurrentOrder().setTotal(totalTextField.getText());
       StoreOrderController.storeOrders.add(yourOrder);


       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/storeorders.fxml"));
       Parent root1 = (Parent) fxmlLoader.load();
       Stage stage = new Stage();
       stage.setScene(new Scene(root1));
       stage.show();

       stage = (Stage) placeOrderBtn.getScene().getWindow();
       stage.close();
   }

   @FXML
   private void calculatesalestax(){
        salestax.setText(RoundTo2Decimals(OrderController.getCurrentSubtotal() * SALES_TAX_PERCENT));
   }

   @FXML
   private void calculatetotal(){
       // double subtotalval = Double.parseDouble(yourOrderSubtotal.getText());
       // double salestaxval = Double.parseDouble(salestax.getText());
       // totalTextField.setText((RoundTo2Decimals(subtotalval+salestaxval)));
   }

    public static String RoundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("##0.00");
        return (df2.format(val));
    }

    @FXML
    private void removeItem(){
       //iterate thru order to find menuitem to remvoe
        Order currentOrder = OrderController.getCurrentOrder();

        //if there is no selected item to remove
        if (myOrder.getSelectionModel().getSelectedItem() == null){
            displayAlert("Error", "No item Selected ");
            return;
        }

        // get index to split
        // get last occurrence of space character to separate label and amounr
        final int INDEX = myOrder.getSelectionModel().getSelectedItem().lastIndexOf(" ");
        final DonutFlavor FLAVOR_TO_REMOVE = DonutFlavor.getFlavorByLabel(myOrder.getSelectionModel().getSelectedItem().substring(0, INDEX));
        final int AMOUNT = Integer.parseInt(myOrder.getSelectionModel().getSelectedItem().substring(INDEX+1));
        ArrayList<Donut> donutsToDelete = new ArrayList<>();

        // for each item in current order
        currentOrder.getOrder().forEach((item) -> {
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
   }

    public static void displayAlert(String title, String message){
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
       if(yourOrder != null) {
           loadOrderInfo();
           loadSubtotalInfo();
           calculatesalestax();
           calculatetotal();
       }

        yourOrderSubtotal.setEditable(false);
        salestax.setEditable(false);
        totalTextField.setEditable(false);
    }
}
