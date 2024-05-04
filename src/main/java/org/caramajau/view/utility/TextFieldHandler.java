package org.caramajau.view.utility;

import javafx.scene.control.TextField;
import org.caramajau.model.utility.DigitInStringChecker;
import org.caramajau.model.utility.OrderedFieldHandler;

import java.util.List;

public class TextFieldHandler {
    private final OrderedFieldHandler<TextField> textFieldOrderedFieldHandler;

    public TextFieldHandler(List<TextField> textFields) {
        textFieldOrderedFieldHandler = new OrderedFieldHandler<>(textFields);
    }

    public void handleTextFieldChange(TextField textField, String oldText, String newText, int textLimit, int numberLimit) {
        if (!DigitInStringChecker.isAllDigit(newText)) {
            textField.setText(oldText);

        } else if (newText.length() > textLimit) {
            handleMaxTextLengthExceeded(textField, oldText, newText, textLimit);

        } else if (newText.isEmpty()) {
            handleEmptyText(textField, newText);

        } else if (Integer.parseInt(newText) > numberLimit) {
            textField.setText(String.valueOf(numberLimit));
        }
    }

    private void handleMaxTextLengthExceeded(TextField textField, String oldText, String newText, int textLimit) {
        textField.setText(oldText);
        TextField nextTextField = textFieldOrderedFieldHandler.getNextField(textField);
        nextTextField.requestFocus();

        // send the new character to the next text field and only if it can.
        if (nextTextField.getText().length() < textLimit) {
            nextTextField.setText(String.valueOf(newText.charAt(newText.length() - 1)));
        }
        // Move the cursor so that it is at the end.
        nextTextField.end();
    }

    private void handleEmptyText(TextField textField, String newText) {
        textField.setText(newText);
        TextField previousTextField = textFieldOrderedFieldHandler.getPreviousField(textField);
        previousTextField.requestFocus();
        previousTextField.end();
    }
}
