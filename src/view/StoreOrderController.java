package view;

import application.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.UUID;

public class StoreOrderController implements Initializable {
    private StoreOrders thisstoresorders= YourOrderController.getStoreOrders();

    private UUID currentOrderUUID;
    @FXML
    private ComboBox<String> orderNumbercombobox;

    @FXML
    private ListView<String> storeOrderListView;
    @FXML
    private TextField storeOrderTotal;
    @FXML
    private Button cancelOrderBtn;

    public StoreOrderController() {
 //       thisstoresorders = new StoreOrders(change -> {
  //
 //     });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadOrdernumbers();
        //populate order numbers

    }

    @FXML
    private void loadOrdernumbers() {
        //StoreOrders thisstoresorders = YourOrderController.getStoreOrders();
        for (application.Order o : thisstoresorders.getOrders()) {
            orderNumbercombobox.getItems().add(o.getOrderNumber().toString());
        }
    }

    @FXML
    private void displayOrderContents() {
        if (orderNumbercombobox.getSelectionModel().getSelectedItem() == null){
            return;
        }
        currentOrderUUID = UUID.fromString(orderNumbercombobox.getSelectionModel().getSelectedItem());
      //  StoreOrders thisstoresorders = YourOrderController.getStoreOrders();
        double currentOrderTotal =0;


        for (application.Order o : thisstoresorders.getOrders()) {
            if (o.getOrderNumber().equals(currentOrderUUID)) {
                currentOrderTotal = o.getOrderFinalTotal();

                // initialize a new map
                HashMap<DonutFlavor, Integer> order = new HashMap<>();

                // reduce items in order to flavor selection and amounts
                for (MenuItem item : o.getCurrentOrder()) {
                    DonutFlavor selectedFlavor = ((Donut) item).getFlavor();
                    if (order.containsKey(selectedFlavor)) {
                        order.put(selectedFlavor, order.get(selectedFlavor) + 1);
                    } else {
                        order.put(selectedFlavor, 1);
                    }
                }

                storeOrderListView.getItems().clear();

                order.forEach((k, v) -> {
                    storeOrderListView.getItems().add(k.getLabel() + " " + v);
                });
            }


        }


        updateTotal();


    }

    private void updateTotal(){
        //update total textfield
        currentOrderUUID = UUID.fromString(orderNumbercombobox.getSelectionModel().getSelectedItem());

        for (application.Order o : thisstoresorders.getOrders()) {
            if (o.getOrderNumber().equals(currentOrderUUID)) {
               double currentOrderTotal = o.getOrderFinalTotal();

                storeOrderTotal.setText(Double.toString(RoundTo2Decimals(currentOrderTotal)));
            }
        }
    }

    @FXML
    private void cancelOrder(){
        //StoreOrders thisstoresorders = YourOrderController.getStoreOrders();
        currentOrderUUID = UUID.fromString(orderNumbercombobox.getSelectionModel().getSelectedItem());

        for (application.Order o : thisstoresorders.getOrders()) {
            if (o.getOrderNumber().equals(currentOrderUUID)) {

                Platform.runLater(()-> {
               // System.out.println("removing order");
                            thisstoresorders.remove(o);
                            storeOrderTotal.setText("");
                            storeOrderListView.getItems().clear();
                       });

                //for testing purposes only


            }
        }


        for (application.Order o : thisstoresorders.getOrders()) {
            System.out.println(o.getOrderNumber().toString());
        }

      //  Platform.runLater(()-> {
          //  orderNumbercombobox.getSelectionModel().clearSelection();
            orderNumbercombobox.getItems().removeAll(orderNumbercombobox.getSelectionModel().getSelectedItem());
           // loadOrdernumbers();

  //      });




    }


    public double RoundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
    }
}