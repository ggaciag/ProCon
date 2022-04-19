package com.gitgg.procon;

import com.gitgg.procon.expression.ExpressionEvaluator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExpressionEvaluatorTest {

    @Test
    public void canEvaluateSimpleExpressions() {
        //Given
        Map<String, Integer> testCases = new HashMap<>();
        testCases.put("5+5", 10);
        testCases.put("5-5", 0);
        testCases.put("5*5", 25);
        testCases.put("5/5", 1);
        testCases.put("1+2-3*10", -27);
        testCases.put("5*1-4/2", 3);


        for (Map.Entry<String, Integer> entry : testCases.entrySet()) {
            //When
            BigDecimal result = ExpressionEvaluator.evaluate(entry.getKey());
            //Then
            assertTrue(new BigDecimal(entry.getValue()).compareTo(result) == 0);
        }
    }

    @Test
    public void canHandleCorrectOrderOfOperations() {
        //Given
        Map<String, Integer> testCases = new HashMap<>();
        testCases.put("2*2+2", 6);
        testCases.put("2+2*2", 6);
        testCases.put("2/2+2", 3);
        testCases.put("2+2/2", 3);
        testCases.put("3-3+3*3+3/3", 10);
        testCases.put("5*10/2", 25);
        testCases.put("10/2*5", 25);

        for (Map.Entry<String, Integer> entry : testCases.entrySet()) {
            //When
            BigDecimal result = ExpressionEvaluator.evaluate(entry.getKey());
            //Then
            assertTrue(new BigDecimal(entry.getValue()).compareTo(result) == 0);
        }
    }

    @Test
    public void canHandleBigNumbers() {
        //Given
        MathContext mc = MathContext.DECIMAL32;
        int max = Integer.MAX_VALUE;
        BigDecimal maxIntegerValue = new BigDecimal(max, mc);
        BigDecimal expectedResult = maxIntegerValue.pow(5, mc);

        String expression = String.format("%d*%d*%d*%d*%d", max, max, max, max, max);
        //When
        BigDecimal result = ExpressionEvaluator.evaluate(expression);
        //Then
        assertTrue(expectedResult.compareTo(result) == 0);
    }

    @Test
    public void canHandleFractions() {
        //Given
        Map<String, BigDecimal> testCases = new HashMap<>();
        testCases.put("1/3", new BigDecimal(1).divide(new BigDecimal(3), 7, RoundingMode.HALF_EVEN));
        testCases.put("1/2+3/4", new BigDecimal("1.25"));
        testCases.put("1/2*1/2", new BigDecimal("0.25"));

        for (Map.Entry<String, BigDecimal> entry : testCases.entrySet()) {
            //When
            BigDecimal result = ExpressionEvaluator.evaluate(entry.getKey());
            //Then
            assertTrue(entry.getValue().compareTo(result) == 0);
        }
    }
}