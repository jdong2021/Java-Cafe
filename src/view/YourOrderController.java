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

/**
 *YourOrderController class handles inputs from the your order GUI and defines all actions and helper methods relating to events triggered by the GUI
 *
 * @author Hugo De Moraes, Jonathan Dong
 */
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

    /**
     * default constructor for YourOrderController, initializes yourOrder to be the current Order and adds a listener to ensure
     * that subtotalinfo, salestax, and total are changes whenever the order is changed
     */
    public YourOrderController() {
        yourOrder = OrderController.getCurrentOrder();
        yourOrder.addListener(change -> {
            loadOrderInfo();
            loadSubtotalInfo();

            calculatesalestax();
            calculatetotal();
        });
    }

    /**
     * populates the listview with appropriate order details
     */
   @FXML
   public void loadOrderInfo(){
       //Order currentOrder = OrderController.getCurrentOrder();

       // initialize a new map
       HashMap<Object, Integer> order = new HashMap<>();

       for(MenuItem item : yourOrder.getOrder()) {
           if(item instanceof Donut) {
               // handle donut
               String key = "Donut " + ((Donut) item).getFlavor();
               if(order.containsKey(key)) {
                   order.put(key, order.get(key) + 1);
               } else {
                   order.put(key, 1);
               }
           }

           // handle coffee
           else if(item instanceof Coffee){
               String key = item.toString();
               if(order.containsKey(key)) {
                   order.put(key, order.get(key) + 1);
               } else {
                   order.put(key, 1);
               }
           }
       }

       myOrder.getItems().clear();

       order.forEach((k,v) -> {
           myOrder.getItems().add(k + " " + v);
       });
   }

    /**
     * populates the subtotal textfield with appropriate subtotal amount
     */
   @FXML
   public void loadSubtotalInfo(){
       yourOrderSubtotal.setText(RoundTo2Decimals(yourOrder.getOrderSubtotal()));
   }

    /**
     * handles action when user wants to add their order to the Store Orders
     * @throws IOException exception to be thrown if fxml page has error
     */
   @FXML
   public void loadStoreOrders() throws IOException {
       // yourOrder.add(OrderController.getCurrentOrder());
       // OrderController.getCurrentOrder().setTotal(totalTextField.getText());

       if(totalTextField.getText().equals("0.00") || totalTextField.getText().equals("")){
           final String ERROR_TITLE = "ERROR";
           final String ERROR_MESSAGE = "Cannot place empty order";
           displayAlert(ERROR_TITLE, ERROR_MESSAGE);
           return;
       }
       StoreOrderController.storeOrders.add(yourOrder);
       OrderController.setNewOrder();


       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/storeorders.fxml"));
       Parent root1 = (Parent) fxmlLoader.load();
       Stage stage = new Stage();
       stage.setScene(new Scene(root1));
       stage.show();

       stage = (Stage) placeOrderBtn.getScene().getWindow();
       stage.close();
   }

    /**
     * calculates sales tax for order and updates textfield
     */
   @FXML
   private void calculatesalestax(){
        salestax.setText(RoundTo2Decimals(yourOrder.getOrderSubtotal() * SALES_TAX_PERCENT));
   }

    /**
     * calculates order total and updates textfield
     */
   @FXML
   private void calculatetotal(){
        double subtotalval = Double.parseDouble(yourOrderSubtotal.getText());
        double salestaxval = Double.parseDouble(salestax.getText());
        totalTextField.setText((RoundTo2Decimals(subtotalval+salestaxval)));
   }

    /**
     * returns a formatted String to 2 decimal places given a double
     * @param val double to be formatted
     * @return string representation of double to 2 decimal places
     */
    public static String RoundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("##0.00");
        return (df2.format(val));
    }

    /**
     * removes a menuitem from your order
     */
    @FXML
    private void removeItem(){
        //if there is no selected item to remove
        if (myOrder.getSelectionModel().getSelectedItem() == null){
            final String ERROR_TITLE = "Error";
            final String ERROR_MESSAGE = "No item Selected";
            displayAlert(ERROR_TITLE, ERROR_MESSAGE);
            return;
        }


        final int ITEM_INDEX = myOrder.getSelectionModel().getSelectedItem().indexOf(" ");
        String MENU_ITEM_TYPE = myOrder.getSelectionModel().getSelectedItem().substring(0, ITEM_INDEX);

        final String DONUT = "Donut";
        final String COFFEE = "Coffee";

        if(MENU_ITEM_TYPE.equals(COFFEE)) {
            // get the string Coffee alsjdlfgjas;kdfj;asdjf amount
            final int TARGET_INDEX = myOrder.getSelectionModel().getSelectedItem().lastIndexOf(" ");
            final String KEY = myOrder.getSelectionModel().getSelectedItem().substring(0, TARGET_INDEX);

            ArrayList<Coffee> coffeeToDelete = new ArrayList<>();

            yourOrder.getOrder().forEach(item -> {
                if(item instanceof Coffee) {
                    Coffee coffee = (Coffee) item;
                    if(coffee.toString().equals(KEY)) {
                        coffeeToDelete.add(coffee);
                    }
                }
            });

            // delete key
            coffeeToDelete.forEach(coffee -> {
                yourOrder.remove(coffee);
            });
        }
        else if(MENU_ITEM_TYPE.equals(DONUT)) {
            // get index to split
            // get last occurrence of space character to separate label and amounr
            final int TARGET_INDEX = myOrder.getSelectionModel().getSelectedItem().lastIndexOf(" ");
            final DonutFlavor FLAVOR_TO_REMOVE = DonutFlavor.getFlavorByLabel(myOrder.getSelectionModel().getSelectedItem().substring(ITEM_INDEX+1, TARGET_INDEX));
            final int AMOUNT = Integer.parseInt(myOrder.getSelectionModel().getSelectedItem().substring(TARGET_INDEX+1));
            ArrayList<Donut> donutsToDelete = new ArrayList<>();

            // for each item in current order
            yourOrder.getOrder().forEach((item) -> {
                if(item instanceof Donut) {
                    Donut donut = (Donut) item;
                    // get donuts that match selected flavor to delete
                    if(donut.getFlavor() == FLAVOR_TO_REMOVE) {
                        donutsToDelete.add(donut);
                    }
                }
            });

            // for each item to delete
            for(int i = 0; i < AMOUNT; i++) {
                // delete
                yourOrder.remove(donutsToDelete.get(i));
            }
        }

        // this block is for donut orders






   }

    /**
     * displays an alert message with a title and messages
     * @param title title of alert box
     * @param message message of alert box
     */
    public static void displayAlert(String title, String message){
        Stage alertWindow = new Stage();
        alertWindow.setTitle(title);
        alertWindow.setMinWidth(300);
        Label label = new Label();
        label.setText(message);
        final String BUTTON_LABEL = "Close Window";
        Button closeButton = new Button(BUTTON_LABEL);
        closeButton.setOnAction(e -> alertWindow.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        alertWindow.setScene(scene);
        alertWindow.showAndWait();

    }

    /**
     * dictates actions right after your order GUI is created
     * @param url url
     * @param resourceBundle resourcebundle
     */
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
