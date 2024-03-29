package org.caramajau;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.caramajau.view.MainWindow;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Parent root;
        FXMLLoader loader;

        // it can generate IOException so surround with try-catch
        try {
            // For Maven-JavaFX needs to be relative to how the Main Class is.
            String filePath = "view/main_window.fxml";
            loader = new FXMLLoader(getClass().getResource(filePath));
            loader.setController(new MainWindow(primaryStage));
            root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}