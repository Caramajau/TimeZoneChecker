package org.caramajau.view.utility;

import java.util.List;

public class TextFieldHandler<T> {
    private final List<T> textFields;

    public TextFieldHandler(List<T> textFields) {
        this.textFields = textFields;
    }

    public T getNextTextField(T currentTextField) {
        return calculateNextTextField(currentTextField);
    }

    private T calculateNextTextField(T currentTextField) {
        int currentIndex = textFields.indexOf(currentTextField);

        if (currentIndex == textFields.size() - 1) {
            return currentTextField;
        } else {
            return textFields.get(currentIndex + 1);
        }

    }

    public T getPreviousTextField(T currentTextField) {
        return calculatePreviousTextField(currentTextField);
    }

    private T calculatePreviousTextField(T currentTextField) {
        int currentIndex = textFields.indexOf(currentTextField);

        if (currentIndex == 0) {
            return currentTextField;
        } else {
            return textFields.get(currentIndex - 1);
        }
    }
}
