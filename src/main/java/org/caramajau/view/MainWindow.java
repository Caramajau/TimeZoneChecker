package org.caramajau.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.caramajau.model.TimeZoneHandler;
import org.caramajau.model.TimeZoneOffsets;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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
    private TextField timeTextField;
    @FXML
    private Button convertButton;
    private static final String NO_SELECTED_TIME_ZONE_STRING = "NaN";
    // Note to self: "-1" is a valid time zone
    // Probably means numbers are fine in general
    // Could probably make the model simpler.
    private String selectedTimeZone = NO_SELECTED_TIME_ZONE_STRING;
    private LocalTime selectedTime = LocalTime.of(0, 0);
    private LocalDate selectedDate = LocalDate.ofEpochDay(0);

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
    private void handleTimeTextField() {
        String selectedTimeString = timeTextField.getText();
        String validFormatTimeString = convertToValidFormat(selectedTimeString);
        selectedTime = LocalTime.parse(selectedTimeString);
    }

    private String convertToValidFormat(String selectedTimeString) {
        String timeNumbersString = findTimeNumbersInString(selectedTimeString);
        return createValidFormat(timeNumbersString);

    }

    private static String findTimeNumbersInString(String selectedTimeString) {
        StringBuilder timeNumbersBuilder = new StringBuilder();
        for (char c : selectedTimeString.toCharArray()) {
            if (Character.isDigit(c)) {
                timeNumbersBuilder.append(c);
                if (timeNumbersBuilder.length() == 4) {
                    break;
                }
            }
        }
        return timeNumbersBuilder.toString();
    }

    private static String createValidFormat(String timeNumbersString) {
        return switch (timeNumbersString.length()) {
            case 2 -> timeNumbersString + ":00";
            case 4 -> timeNumbersString.substring(0, 2) + ":" + timeNumbersString.substring(2, 4);
            default -> throw new IllegalArgumentException("Invalid time format");
        };
    }

    @FXML
    private void handleConvertButtonAction() {
        if (!selectedTimeZone.equals(NO_SELECTED_TIME_ZONE_STRING)) {
            ZonedDateTime newDate = TimeZoneHandler.convertToCurrentTimeZone(selectedDate, selectedTimeZone);
            convertedDateLabel.setText("New date: " + newDate);
        } else {
            convertedDateLabel.setText("Select a time zone!");
        }

    }
}
