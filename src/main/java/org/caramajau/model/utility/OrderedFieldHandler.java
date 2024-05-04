package org.caramajau.model.utility;

import java.util.List;

public class OrderedFieldHandler<T> {
    private final List<T> fields;

    public OrderedFieldHandler(List<T> fields) {
        this.fields = fields;
    }

    public T getNextField(T currentField) {
        return calculateField(currentField, 1);
    }

    public T getPreviousField(T currentField) {
        return calculateField(currentField, -1);
    }

    private T calculateField(T currentField, int indexStep) {
        int currentIndex = fields.indexOf(currentField);
        int newIndex = currentIndex + indexStep;

        if (newIndex < 0) {
            newIndex = 0;
        } else if (newIndex >= fields.size()) {
            newIndex = fields.size() - 1;
        }

        return fields.get(newIndex);
    }
}
