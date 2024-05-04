package org.caramajau.model.utility;

import java.util.List;

public class OrderedFieldHandler<T> {
    private final List<T> textFields;

    public OrderedFieldHandler(List<T> textFields) {
        this.textFields = textFields;
    }

    public T getNextTextField(T currentTextField) {
        return calculateTextField(currentTextField, 1);
    }

    public T getPreviousTextField(T currentTextField) {
        return calculateTextField(currentTextField, -1);
    }

    private T calculateTextField(T currentTextField, int indexStep) {
        int currentIndex = textFields.indexOf(currentTextField);
        int newIndex = currentIndex + indexStep;

        if (newIndex < 0) {
            newIndex = 0;
        } else if (newIndex >= textFields.size()) {
            newIndex = textFields.size() - 1;
        }

        return textFields.get(newIndex);
    }
}
