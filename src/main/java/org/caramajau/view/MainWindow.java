package org.caramajau.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.caramajau.model.TimeZoneHandler;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class MainWindow extends AnchorPane implements Initializable {
    private final Stage primaryStage;
    @FXML
    ChoiceBox<String> timeChoiceBox;

    public MainWindow(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        primaryStage.setTitle("Time Zone Checker");
        Set<String> allTimeZonesSet = TimeZoneHandler.getAllTimeZones();
        ObservableList<String> allTimeZonesList = FXCollections.observableArrayList(allTimeZonesSet);
        timeChoiceBox.setItems(allTimeZonesList);
    }
}
