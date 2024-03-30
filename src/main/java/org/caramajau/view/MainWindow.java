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
import java.util.List;
import java.util.ResourceBundle;

public class MainWindow extends AnchorPane implements Initializable {
    private final Stage primaryStage;
    @FXML
    private ChoiceBox<String> timeChoiceBox;

    public MainWindow(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        primaryStage.setTitle("Time Zone Checker");

        List<String> allTimeZonesList = TimeZoneHandler.getAllTimeZoneAbbreviationsAsString();
        ObservableList<String> allTimeZonesObservableList = FXCollections.observableArrayList(allTimeZonesList);
        timeChoiceBox.setItems(allTimeZonesObservableList);
    }
}
