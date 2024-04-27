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

    private String selectedTimeZone = TimeZoneHandler.getNoSelectedTimeZoneString();
    private LocalTime selectedTime = TimeZoneHandler.getDefaultTime();
    private LocalDate selectedDate = TimeZoneHandler.getDefaultDate();

    private static final int MAX_TEXT_LENGTH = 5;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> allTimeZonesList = TimeZoneHandler.getAllTimeZoneAbbreviationsAsString();
        ObservableList<String> allTimeZonesObservableList = FXCollections.observableArrayList(allTimeZonesList);

        timeChoiceBox.setItems(allTimeZonesObservableList);
        timeChoiceBox.setOnAction(event -> handleChoiceBoxAction());

        timeTextField.setPromptText(selectedTime.toString());

        datePicker.setValue(selectedDate);

        convertButton.setDisable(true);

        timeTextField.textProperty().addListener(
            (observable, oldText, newText)-> {
                if (newText.length() > MAX_TEXT_LENGTH) {
                    timeTextField.setText(oldText);
                }
                if (stringContainsInvalidCharacter(newText)) {
                    timeTextField.setText(oldText);
                }
            }
        );
    }

    private boolean stringContainsInvalidCharacter(String stringToTest) {
        for (char c : stringToTest.toCharArray()) {
            if (!Character.isDigit(c) && c != ':') {
                return true;
            }
        }
        return false;
    }

    // For some reason the onAction doesn't exist in SceneBuilder
    private void handleChoiceBoxAction() {
        // Can only convert after having set to a valid time zone.
        if (selectedTimeZone.equals(TimeZoneHandler.getNoSelectedTimeZoneString())) {
            convertButton.setDisable(false);
        }
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
        ZonedDateTime newDate = TimeZoneHandler.convertToCurrentTimeZone(selectedDate, selectedTime, selectedTimeZone);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd");
        String formattedDate = newDate.format(dateFormatter);
        convertedDateLabel.setText("New date: " + formattedDate);
    }
}
