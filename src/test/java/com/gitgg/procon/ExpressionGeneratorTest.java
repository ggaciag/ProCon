package com.gitgg.procon;

import com.gitgg.procon.expression.ExpressionGenerator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionGeneratorTest {

    @Test
    public void canGenerateRandomExpressionOfGivenLength() {
        //Given
        List<Integer> lengths = Arrays.asList(0, 1, 2, 3, 4, 5, 100, 1000);

        //When

        List<String> expressions = lengths.stream()
                .map(it -> ExpressionGenerator.generate(it, it))
                .toList();

        //Then
        for (int i = 0; i < lengths.size(); i++) {
            assertEquals(lengths.get(i), expressions.get(i).length());
        }
    }

    @Test
    public void generatedExpressionsHaveOnlyRequiredChars() {
        //Given
        int length = 10_000;
        //When
        String expression = ExpressionGenerator.generate(length, length);
        //Then
        assertTrue(expression.matches("[\\d-+/*]+"));
        assertEquals(length, expression.length());
    }

    @Test
    public void thereAreNoOperatorsNextToEachOther() {
        // *- /- are Ok as those are operations on negative numbers
        //Given
        int length = 10_000;
        //When
        String expression = ExpressionGenerator.generate(length, length);
        //Then
        assertFalse(expression.matches(".*[-+/*][+/*].*"));
        assertFalse(expression.matches(".*[-+][-+/*].*"));
        assertEquals(length, expression.length());
    }

    @Test
    public void expressionLengthIsBetweenMinMax() {
        //Given
        int min = 100;
        int max = 200;

        for (int i = 0; i < 100; i++){
            //When
            String expression = ExpressionGenerator.generate(min, max);
            //Then
            assertTrue(expression.length() <= max);
            assertTrue(expression.length() >= min);
        }
    }

}