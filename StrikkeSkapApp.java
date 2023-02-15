package StrikkeSkap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StrikkeSkapApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("StrikkeSkap");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("StrikkeSkap.fxml"))));
        primaryStage.show();
    }
}
