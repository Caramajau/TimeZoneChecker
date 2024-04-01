package org.caramajau.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.caramajau.model.TimeFormatConverter;
import org.caramajau.model.TimeZoneHandler;
import org.caramajau.model.TimeZoneOffsets;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindow extends AnchorPane implements Initializable {
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
    private TextField timeTextField;
    @FXML
    private Button convertButton;
    private static final String NO_SELECTED_TIME_ZONE_STRING = "NaN";
    // Note to self: "-1" is a valid time zone
    // Probably means numbers are fine in general
    // Could probably make the model simpler.
    private String selectedTimeZone = NO_SELECTED_TIME_ZONE_STRING;
    private LocalTime selectedTime = LocalTime.of(0, 0);
    private LocalDate selectedDate = LocalDate.now();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    private void handleTimeTextField() {
        String selectedTimeString = timeTextField.getText();
        try {
            // Doesn't seem to necessary, but can throw exception when
            // it is not in a valid format according to me.
            String validFormatTimeString = TimeFormatConverter.convertToValidFormat(selectedTimeString);
            selectedTime = LocalTime.parse(validFormatTimeString);
        } catch (IllegalArgumentException e) {
            convertedDateLabel.setText("Write 2 or 4 numbers!");
        }
    }

    @FXML
    private void handleConvertButtonAction() {
        if (!selectedTimeZone.equals(NO_SELECTED_TIME_ZONE_STRING)) {
            ZonedDateTime newDate = TimeZoneHandler.convertToCurrentTimeZone(selectedDate, selectedTime, selectedTimeZone);
            convertedDateLabel.setText("New date: " + newDate);
        } else {
            convertedDateLabel.setText("Select a time zone!");
        }

    }
}
