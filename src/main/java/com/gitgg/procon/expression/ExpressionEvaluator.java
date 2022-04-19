package com.gitgg.procon.expression;


import com.udojava.evalex.Expression;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;

@Log4j2
public class ExpressionEvaluator {

    public static BigDecimal evaluate(String expression) {

        try {
            Expression parsedExpression = new Expression(expression);
            parsedExpression.setPrecision(7);
            return parsedExpression.eval();
        } catch (ArithmeticException ex) {
            log.error("Expression: " + expression, ex);
        }
        return null;
    }

}
