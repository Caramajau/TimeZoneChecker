package org.caramajau.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        Parent root;
        FXMLLoader loader;

        // it can generate IOException so surround with try-catch
        try {
            // For Maven-JavaFX needs to be relative to where this class is.
            String filePath = "main_window.fxml";
            loader = new FXMLLoader(getClass().getResource(filePath));
            root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setTitle("Time Zone Checker");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startView() {
        launch();
    }

    public void startView(String[] args) {
        launch(args);
    }
}
