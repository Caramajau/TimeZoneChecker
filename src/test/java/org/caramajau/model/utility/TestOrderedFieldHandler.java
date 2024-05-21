package org.caramajau.model.utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestOrderedFieldHandler {
    private OrderedFieldHandler<String> orderedFieldHandler;
    private final static String FIRST_FIELD = "A";
    private final static String SECOND_FIELD = "B";
    private final static String THIRD_FIELD = "C";
    private final static String FOURTH_FIELD = "D";
    private final static String NOT_EXISTING_FIELD = "E";

    @BeforeEach
    void setUp() {
        List<String> fields = Arrays.asList(FIRST_FIELD, SECOND_FIELD, THIRD_FIELD, FOURTH_FIELD);
        orderedFieldHandler = new OrderedFieldHandler<>(fields);
    }

    // Tests for getNextField
    @Test
    void testGetNextFieldGivenFirstFieldReturnsSecondField() {
        String nextField = orderedFieldHandler.getNextField(FIRST_FIELD);
        assertEquals(SECOND_FIELD, nextField);
    }
    @Test
    void testGetNextFieldGivenSecondFieldReturnsThirdField() {
        String nextField = orderedFieldHandler.getNextField(SECOND_FIELD);
        assertEquals(THIRD_FIELD, nextField);
    }
    @Test
    void testGetNextFieldGivenThirdFieldReturnsFourthField() {
        String nextField = orderedFieldHandler.getNextField(THIRD_FIELD);
        assertEquals(FOURTH_FIELD, nextField);
    }
    @Test
    void testGetNextFieldGivenFourthFieldReturnsFourthField() {
        String nextField = orderedFieldHandler.getNextField(FOURTH_FIELD);
        assertEquals(FOURTH_FIELD, nextField);
    }
    @Test
    void testGetNextFieldGivenNotExistingFieldReturnsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> orderedFieldHandler.getNextField(NOT_EXISTING_FIELD));
    }

    // Tests for getPreviousField
    @Test
    void testGetPreviousFieldGivenFirstFieldReturnsFirstField() {
        String previousField = orderedFieldHandler.getPreviousField(FIRST_FIELD);
        assertEquals(FIRST_FIELD, previousField);
    }
    @Test
    void testGetPreviousFieldGivenSecondFieldReturnsFirstField() {
        String previousField = orderedFieldHandler.getPreviousField(SECOND_FIELD);
        assertEquals(FIRST_FIELD, previousField);
    }
    @Test
    void testGetPreviousFieldGivenThirdFieldReturnsSecondField() {
        String previousField = orderedFieldHandler.getPreviousField(THIRD_FIELD);
        assertEquals(SECOND_FIELD, previousField);
    }
    @Test
    void testGetPreviousFieldGivenFourthFieldReturnsThirdField() {
        String previousField = orderedFieldHandler.getPreviousField(FOURTH_FIELD);
        assertEquals(THIRD_FIELD, previousField);
    }
    @Test
    void testGetPreviousFieldGivenNotExistingFieldReturnsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> orderedFieldHandler.getPreviousField(NOT_EXISTING_FIELD));
    }
}
