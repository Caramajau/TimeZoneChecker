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
    private TextField hourTextField;
    @FXML
    private TextField minuteTextField;
    @FXML
    private Button convertButton;

    private String selectedTimeZone = TimeZoneHandler.getNoSelectedTimeZoneString();
    private int selectedHour = TimeZoneHandler.getDefaultTime().getHour();
    private int selectedMinute = TimeZoneHandler.getDefaultTime().getMinute();
    private LocalDate selectedDate = TimeZoneHandler.getDefaultDate();

    private static final int MAX_TEXT_LENGTH = 2;
    private static final int MAX_HOUR = 23;
    private static final int MAX_MINUTE = 59;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> allTimeZonesList = TimeZoneHandler.getAllTimeZoneAbbreviationsAsString();
        ObservableList<String> allTimeZonesObservableList = FXCollections.observableArrayList(allTimeZonesList);

        timeChoiceBox.setItems(allTimeZonesObservableList);
        timeChoiceBox.setOnAction(event -> handleChoiceBoxAction());

        datePicker.setValue(selectedDate);

        convertButton.setDisable(true);
        String formatedHour = String.format("%02d", selectedHour);
        hourTextField.setPromptText(formatedHour);
        hourTextField.textProperty().addListener(
            (observable, oldText, newText) -> handleTextFieldChange(hourTextField, oldText, newText, MAX_HOUR)
        );
        String formatedMinute = String.format("%02d", selectedMinute);
        minuteTextField.setPromptText(formatedMinute);
        minuteTextField.textProperty().addListener(
                (observable, oldText, newText) -> handleTextFieldChange(minuteTextField, oldText, newText, MAX_MINUTE)
        );
    }

    private void handleTextFieldChange(TextField textField, String oldText, String newText, int numberLimit) {
        if (newText.length() > MAX_TEXT_LENGTH) {
            textField.setText(oldText);
        }
        if (!isAllDigit(newText)) {
            textField.setText(oldText);
        }
        if (newText.isEmpty()) {
            textField.setText((newText));
        } else if (Integer.parseInt(newText) > numberLimit) {
            textField.setText(String.valueOf(numberLimit));
        }
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
    private void handleHourTextField() {
        String selectedHourString = hourTextField.getText();

        boolean isAllDigit = isAllDigit(selectedHourString);

        if (isAllDigit && !selectedHourString.isEmpty()) {
            selectedHour = Integer.parseInt(selectedHourString);
        } else {
            selectedHour = 0;
        }
    }

    @FXML
    private void handleMinuteTextField() {
        String selectedMinuteString = minuteTextField.getText();
        boolean isAllDigit = isAllDigit(selectedMinuteString);

        if (isAllDigit && !selectedMinuteString.isEmpty()) {
            selectedMinute = Integer.parseInt(selectedMinuteString);
        } else {
            selectedMinute = 0;
        }
    }

    @FXML
    private void handleConvertButtonAction() {
        LocalTime selectedTime = LocalTime.of(selectedHour, selectedMinute);
        ZonedDateTime newDate = TimeZoneHandler.convertToCurrentTimeZone(selectedDate, selectedTime, selectedTimeZone);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd");
        String formattedDate = newDate.format(dateFormatter);
        convertedDateLabel.setText("New date: " + formattedDate);
    }

    private boolean isAllDigit(String stringToTest) {
        for (char c : stringToTest.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
