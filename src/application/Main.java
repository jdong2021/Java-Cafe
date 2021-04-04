package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Stage window = stage;
        Parent root;

        root = FXMLLoader.load(getClass().getResource("../view/mainmenu.fxml"));
        window.setTitle("Hello World");
        window.setScene(new Scene(root, 600,600));
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
