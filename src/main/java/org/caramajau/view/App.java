package org.caramajau.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    private static final String FXML_FILE_PATH = "main_window.fxml";
    private static final String ICON_FILE_PATH = "clock_icon.png";
    @Override
    public void start(Stage primaryStage) {
        Parent root;
        FXMLLoader loader;

        // it can generate IOException so surround with try-catch
        try {
            // For Maven-JavaFX needs to be relative to where this class is.
            loader = new FXMLLoader(getClass().getResource(FXML_FILE_PATH));
            root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setTitle("Time Zone Checker");
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ICON_FILE_PATH)));
            primaryStage.getIcons().add(icon);
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
