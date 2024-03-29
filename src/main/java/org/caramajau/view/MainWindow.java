package org.caramajau.view;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow extends AnchorPane implements Initializable {
    private final Stage primaryStage;

    public MainWindow(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        primaryStage.setTitle("Time Zone Checker");
    }
}
