package org.caramajau.view.mainwindow;

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
import org.caramajau.model.timezonehandling.TimeZoneHandler;
import org.caramajau.model.timezonehandling.TimeZoneOffsets;
import org.caramajau.model.utility.DigitInStringChecker;
import org.caramajau.view.utility.TextFieldHandler;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    private TextFieldHandler textFieldHandler;

    private static final int MAX_TEXT_LENGTH = 2;
    private static final int MAX_HOUR = 23;
    private static final int MAX_MINUTE = 59;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textFieldHandler = createTextFieldHandler();

        List<String> allTimeZonesList = TimeZoneHandler.getAllTimeZoneAbbreviationsAsString();
        ObservableList<String> allTimeZonesObservableList = FXCollections.observableArrayList(allTimeZonesList);

        timeChoiceBox.setItems(allTimeZonesObservableList);
        timeChoiceBox.setOnAction(event -> handleChoiceBoxAction());

        datePicker.setValue(selectedDate);

        convertButton.setDisable(true);
        initializeTimeTextField(selectedHour, hourTextField, MAX_HOUR);
        initializeTimeTextField(selectedMinute, minuteTextField, MAX_MINUTE);
    }

    private TextFieldHandler createTextFieldHandler() {
        List<TextField> textFields = new ArrayList<>();
        textFields.add(hourTextField);
        textFields.add(minuteTextField);
        return new TextFieldHandler(textFields);
    }

    private void initializeTimeTextField(int selectedHour, TextField timeTextField, int maxTime) {
        String formattedHour = String.format("%02d", selectedHour);
        timeTextField.setPromptText(formattedHour);
        timeTextField.textProperty().addListener(
            (observable, oldText, newText) -> textFieldHandler.handleTextFieldChange(timeTextField, oldText, newText, MAX_TEXT_LENGTH, maxTime)
        );
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

        boolean isAllDigit = DigitInStringChecker.isAllDigit(selectedHourString);

        if (isAllDigit && !selectedHourString.isEmpty()) {
            selectedHour = Integer.parseInt(selectedHourString);
        } else {
            selectedHour = 0;
        }
    }

    @FXML
    private void handleMinuteTextField() {
        String selectedMinuteString = minuteTextField.getText();
        boolean isAllDigit = DigitInStringChecker.isAllDigit(selectedMinuteString);

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
}
