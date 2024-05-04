package org.caramajau.view.utility;

import javafx.scene.control.TextField;

import java.util.List;

public class TextFieldHandler {
    private final List<TextField> textFields;

    public TextFieldHandler(List<TextField> textFields) {
        this.textFields = textFields;
    }

    public TextField getNextTextField(TextField currentTextField) {
        return calculateNextTextField(currentTextField);
    }

    private TextField calculateNextTextField(TextField currentTextField) {
        int currentIndex = textFields.indexOf(currentTextField);

        if (currentIndex == textFields.size() - 1) {
            return currentTextField;
        } else {
            return textFields.get(currentIndex + 1);
        }

    }

    public TextField getPreviousTextField(TextField currentTextField) {
        return calculatePreviousTextField(currentTextField);
    }

    private TextField calculatePreviousTextField(TextField currentTextField) {
        int currentIndex = textFields.indexOf(currentTextField);

        if (currentIndex == 0) {
            return currentTextField;
        } else {
            return textFields.get(currentIndex - 1);
        }
    }
}
