package view;

import application.Donut;
import application.MenuItem;
import application.Order;
import application.StoreOrders;
import application.DonutFlavor;
import application.Coffee;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * StoreOrderController handles inputs from the store orders GUI and
 * defines all actions and helper methods relating to events triggered by the GUI
 * such as exporting store orders and removing an order from store orders
 *
 * @author Hugo De Moraes, Jonathan Dong
 */
public class StoreOrderController implements Initializable {
    static final StoreOrders storeOrders = new StoreOrders();
    private static final String ERROR_LOG = "Error";
    private static final String SELECT_FILE_LOG = "Please select valid file to export";
    private static final String EMPTY_STOREORDERS_LOG = "No Store Orders to export";
    private static final String SELECTION_ERROR_LOG = "No Order Selected ";

    private UUID currentOrderUUID;

    @FXML
    private ComboBox<String> orderNumbercombobox;

    @FXML
    private ListView<String> storeOrderListView;

    @FXML
    private TextField storeOrderTotal;

    @FXML
    private Button cancelOrderBtn;

    @FXML
    private Button exportBtn;

    /**
     * defautlt constructor for StoreOrderController class
     */
    public StoreOrderController() { }

    /**
     * dictates actions right after store Order GUI is created
     * @param url url
     * @param resourceBundle resourcebundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //populate order numbers
        loadOrdernumbers();
        storeOrderTotal.setEditable(false);
    }

    /**
     * Fills combobox with appropriate Order number values 
     */
    @FXML
    private void loadOrdernumbers() {
        for (Order o : storeOrders.getStoreOrders()) {
            orderNumbercombobox.getItems().add(o.getOrderNumber().toString());
        }
    }

    /**
     * Fills listview with appropriate contents of each order selected 
     */
    @FXML
    private void displayOrderContents() {
        if (orderNumbercombobox.getSelectionModel().getSelectedItem() == null){
            return;
        }
        currentOrderUUID = UUID.fromString(orderNumbercombobox.getSelectionModel().getSelectedItem());

        double currentOrderTotal =0;


        for (Order o : storeOrders.getStoreOrders()) {
            if (o.getOrderNumber().equals(currentOrderUUID)) {
                currentOrderTotal = o.getOrderFinalTotal();

                // initialize a new map
                HashMap<Object, Integer> order = new HashMap<>();

                for(MenuItem item : o.getOrder()) {
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
                    else if(item instanceof Coffee){
                        String key = item.toString();
                        if(order.containsKey(key)) {
                            order.put(key, order.get(key) + 1);
                        } else {
                            order.put(key, 1);
                        }
                    }
                }

                storeOrderListView.getItems().clear();

                order.forEach((k,v) -> {
                    storeOrderListView.getItems().add(k + " " + v);
                });
            }


        }


        updateTotal();


    }

    /**
     * updates textfield to show total of each order selected in Store Order GUI
     */
    private void updateTotal(){
        //update total textfield
        currentOrderUUID = UUID.fromString(orderNumbercombobox.getSelectionModel().getSelectedItem());

        for (Order o : storeOrders.getStoreOrders()) {
            if (o.getOrderNumber().equals(currentOrderUUID)) {
               double currentOrderTotal = o.getOrderFinalTotal();

                storeOrderTotal.setText(YourOrderController.RoundTo2Decimals(currentOrderTotal));
                o.setTotal((YourOrderController.RoundTo2Decimals(currentOrderTotal)));
            }
        }
    }

    /**
     * handles events when a user requests to cancel an Order from StoreOrders
     */
    @FXML
    private void cancelOrder(){

        // if no order is selected then we cannot cancel!
        if (orderNumbercombobox.getSelectionModel().getSelectedItem() == null){
            YourOrderController.displayAlert(ERROR_LOG, SELECTION_ERROR_LOG);
            return;
        }

        currentOrderUUID = UUID.fromString(orderNumbercombobox.getSelectionModel().getSelectedItem());

        for (Order o : storeOrders.getStoreOrders()) {
            if (o.getOrderNumber().equals(currentOrderUUID)) {

                Platform.runLater(()-> {
                        storeOrders.remove(o);

                            storeOrders.remove(o);
                            storeOrderTotal.setText("");
                            storeOrderListView.getItems().clear();
                       });
            }
        }


            orderNumbercombobox.getItems().removeAll(orderNumbercombobox.getSelectionModel().getSelectedItem());
    }

    /**
     * handles events when a user requests to export all orders from Store Orders
     */
    @FXML
    private void handleExport() {

        if (storeOrders.getStoreOrders().isEmpty()) {
            YourOrderController.displayAlert(ERROR_LOG, EMPTY_STOREORDERS_LOG);
            return;
        }


        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Target File for the Export");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        try {
            File targetFile = chooser.showSaveDialog(stage); //get the reference of the target file
            //write code to write to the file.
            if (targetFile == null) {
                //display alert that they must choose file
                YourOrderController.displayAlert(ERROR_LOG, SELECT_FILE_LOG);
                return;
            }

            FileWriter writer = new FileWriter(targetFile);
            //we wont allow user to export if store orders is blank


            StringBuilder str = new StringBuilder();
            for (Order o : storeOrders.getStoreOrders()) {


                HashMap<Object, Integer> order = new HashMap<>();
                str.append("Order num: ");
                str.append(o.getOrderNumber().toString());
                str.append(" ");
                for (MenuItem item : o.getOrder()) {

                    if (item instanceof Donut) {
                        // handle donut
                        DonutFlavor selectedFlavor = ((Donut) item).getFlavor();
                        if (order.containsKey(selectedFlavor)) {
                            order.put(selectedFlavor, order.get(selectedFlavor) + 1);
                        } else {
                            order.put(selectedFlavor, 1);
                        }
                    }
                    // handle coffee
                    else if (item instanceof Coffee) {
                        String key = item.toString();
                        if (order.containsKey(key)) {
                            order.put(key, order.get(key) + 1);
                        } else {
                            order.put(key, 1);
                        }
                    }

                }
                str.append("Order price: ");
                str.append(YourOrderController.RoundTo2Decimals(Double.parseDouble(o.getTotal())));
                //myOrder.getItems().clear();

                order.forEach((k, v) -> {
                    str.append(" ");
                    str.append(k + " " + v);


                });
                str.append("\n");
            }


            writer.write(str.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
