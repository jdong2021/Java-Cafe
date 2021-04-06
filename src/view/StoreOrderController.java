package view;

import application.*;
import application.MenuItem;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

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
    @FXML
    private Button exportBtn;

    public StoreOrderController() {
 //       thisstoresorders = new StoreOrders(change -> {
  //
 //     });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadOrdernumbers();
        //populate order numbers

        storeOrderTotal.setEditable(false);

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

                storeOrderTotal.setText(RoundTo2Decimals(currentOrderTotal));
                o.setTotal((RoundTo2Decimals(currentOrderTotal)));
            }
        }
    }

    @FXML
    private void cancelOrder(){
        //StoreOrders thisstoresorders = YourOrderController.getStoreOrders();
        // if no order is selected then we cannot cancel!
        if (orderNumbercombobox.getSelectionModel().getSelectedItem() == null){
            displayAlert("Error", "No Order Selected ");
            return;
        }

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

    @FXML
    private void handleExport(){
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
               displayAlert("Error exporting", "Please select valid file to export");
               return;
            }

            FileWriter writer = new FileWriter(targetFile);
            // loop through all people in company
//            if (isEmpty()) {
//                return IoFields.EMPTY_DB_LOG;
//
//            }

            String output = "";
            for (application.Order o : thisstoresorders.getOrders()) {


                   double currentOrderTotal = o.getOrderFinalTotal();

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
                        output = output + "Order Number: " + o.getOrderNumber().toString()+ " ";





                    Iterator it = order.entrySet().iterator();
                    while(it.hasNext()){
                        Map.Entry pair = (Map.Entry) it.next();
                        output = output  +(pair.getKey().toString() + " " + pair.getValue().toString() + " ");
                        it.remove();
                    }
                    output = output + " " +"Total:"+ RoundTo2Decimals(Double.parseDouble(o.getTotal())) + "\n";



            }
















            writer.write(output);
            writer.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
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
    public String RoundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("##0.00");
        return (df2.format(val));
    }
}
