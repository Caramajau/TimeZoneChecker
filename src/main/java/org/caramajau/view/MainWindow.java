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
import org.caramajau.view.utility.OrderedFieldHandler;

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

    private OrderedFieldHandler<TextField> textFieldHandler;

    private static final int MAX_TEXT_LENGTH = 2;
    private static final int MAX_HOUR = 23;
    private static final int MAX_MINUTE = 59;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<TextField> textFields = new ArrayList<>();
        textFields.add(hourTextField);
        textFields.add(minuteTextField);
        textFieldHandler = new OrderedFieldHandler<>(textFields);

        List<String> allTimeZonesList = TimeZoneHandler.getAllTimeZoneAbbreviationsAsString();
        ObservableList<String> allTimeZonesObservableList = FXCollections.observableArrayList(allTimeZonesList);

        timeChoiceBox.setItems(allTimeZonesObservableList);
        timeChoiceBox.setOnAction(event -> handleChoiceBoxAction());

        datePicker.setValue(selectedDate);

        convertButton.setDisable(true);
        initializeTimeTextField(selectedHour, hourTextField, MAX_HOUR);
        initializeTimeTextField(selectedMinute, minuteTextField, MAX_MINUTE);
    }

    private void initializeTimeTextField(int selectedHour, TextField timeTextField, int maxTime) {
        String formattedHour = String.format("%02d", selectedHour);
        timeTextField.setPromptText(formattedHour);
        timeTextField.textProperty().addListener(
            (observable, oldText, newText) -> handleTextFieldChange(timeTextField, oldText, newText, maxTime)
        );
    }

    private void handleTextFieldChange(TextField textField, String oldText, String newText, int numberLimit) {
        if (!isAllDigit(newText)) {
            textField.setText(oldText);

        } else if (newText.length() > MAX_TEXT_LENGTH) {
            handleMaxTextLengthExceeded(textField, oldText, newText);

        } else if (newText.isEmpty()) {
            handleEmptyText(textField, newText);

        } else if (Integer.parseInt(newText) > numberLimit) {
            textField.setText(String.valueOf(numberLimit));
        }
    }

    private void handleMaxTextLengthExceeded(TextField textField, String oldText, String newText) {
        textField.setText(oldText);
        TextField nextTextField = textFieldHandler.getNextTextField(textField);
        nextTextField.requestFocus();

        // send the new character to the next text field and only if it can.
        if (nextTextField.getText().length() < MAX_TEXT_LENGTH) {
            nextTextField.setText(String.valueOf(newText.charAt(newText.length() - 1)));
        }
        // Move the cursor so that it is at the end.
        nextTextField.end();
    }

    private void handleEmptyText(TextField textField, String newText) {
        textField.setText(newText);
        TextField previousTextField = textFieldHandler.getPreviousTextField(textField);
        previousTextField.requestFocus();
        previousTextField.end();
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
