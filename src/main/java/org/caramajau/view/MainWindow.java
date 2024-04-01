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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindow extends AnchorPane implements Initializable {
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
    private LocalTime selectedTime = TimeZoneHandler.getDefaultTime();
    private LocalDate selectedDate = TimeZoneHandler.getDefaultDate();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> allTimeZonesList = TimeZoneHandler.getAllTimeZoneAbbreviationsAsString();
        ObservableList<String> allTimeZonesObservableList = FXCollections.observableArrayList(allTimeZonesList);
        timeChoiceBox.setItems(allTimeZonesObservableList);
        timeChoiceBox.setOnAction(event -> handleChoiceBoxAction());
        timeTextField.setPromptText(selectedTime.toString());
        datePicker.setValue(selectedDate);
    }

    // For some reason the onAction doesn't exist in SceneBuilder
    private void handleChoiceBoxAction() {
        String selectedTimeZoneAbbreviation = timeChoiceBox.getValue();
        TimeZoneOffsets selectedTimeZoneOffset = TimeZoneOffsets.fromString(selectedTimeZoneAbbreviation);
        selectedTimeZone = TimeZoneHandler.getTimeZoneBasedOnOffset(selectedTimeZoneOffset);
    }

    @FXML
    private void handleDatePickAction() {
        selectedDate = datePicker.getValue();
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

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd");
            String formattedDate = newDate.format(dateFormatter);
            convertedDateLabel.setText("New date: " + formattedDate);
        } else {
            convertedDateLabel.setText("Select a time zone!");
        }
    }
}
