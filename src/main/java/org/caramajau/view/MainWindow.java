package org.caramajau.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.caramajau.model.TimeZoneHandler;
import org.caramajau.model.TimeZoneOffsets;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindow extends AnchorPane implements Initializable {
    private final Stage primaryStage;
    @FXML
    private Label timeZoneLabel;
    @FXML
    private Label selectedDateLabel;
    @FXML
    private Label convertedDateLabel;
    @FXML
    private ChoiceBox<String> timeChoiceBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button convertButton;

    private String selectedTimeZone;
    private LocalDate selectedDate;

    public MainWindow(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        primaryStage.setTitle("Time Zone Checker");

        List<String> allTimeZonesList = TimeZoneHandler.getAllTimeZoneAbbreviationsAsString();
        ObservableList<String> allTimeZonesObservableList = FXCollections.observableArrayList(allTimeZonesList);
        timeChoiceBox.setItems(allTimeZonesObservableList);
        timeChoiceBox.setOnAction(event -> handleChoiceBoxAction());
    }

    // For some reason the onAction doesn't exist in SceneBuilder
    private void handleChoiceBoxAction() {
        String selectedTimeZoneAbbreviation = timeChoiceBox.getValue();
        TimeZoneOffsets selectedTimeZoneOffset = TimeZoneOffsets.fromString(selectedTimeZoneAbbreviation);
        selectedTimeZone = TimeZoneHandler.getTimeZoneBasedOnOffset(selectedTimeZoneOffset);
        timeZoneLabel.setText("Selected Time Zone: " + selectedTimeZoneAbbreviation);
    }

    @FXML
    private void handleDatePickAction() {
        selectedDate = datePicker.getValue();
        selectedDateLabel.setText("Selected Date: " + selectedDate);
    }

    @FXML
    private void handleConvertButtonAction() {
        ZonedDateTime newDate = TimeZoneHandler.convertToCurrentTimeZone(selectedDate, selectedTimeZone);
        convertedDateLabel.setText("New date: " + newDate);
    }
}
