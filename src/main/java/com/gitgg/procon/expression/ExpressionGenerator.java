package com.gitgg.procon.expression;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ThreadLocalRandom;

@Log4j2
public class ExpressionGenerator {

    private static final char[] AVAILABLE_OPERATORS = {'+', '-', '*', '/'};

    //Not really needed but makes it easier to read for humans if numbers are shorter
    private static final int MAX_ABS_VALUE_OF_NUMBERS = (int) Math.pow(2, 16);

    public static String generate(int minLength, int maxLength) {
        int length = ThreadLocalRandom.current().nextInt(minLength, maxLength + 1);

        log.debug("Generating expression with length {}", length);
        return generate(length);
    }

    private static String generate(int length) {

        if (length <= 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(nextNumber());

        while (sb.length() < length) {
            char selected = AVAILABLE_OPERATORS[ThreadLocalRandom.current().nextInt(0, AVAILABLE_OPERATORS.length)];
            int num = nextNumber();

            if (num < 0 && selected == '-') {
                // -- gives +
                num *= -1;
                sb.append('+');
            } else if (num < 0 && selected == '+') {
                // +- gives -, we can skip adding operator
                //NOP
            } else if (num == 0 && selected == '/') {
                //division by zero
                sb.append(selected);
                num = 1;
            } else {
                sb.append(selected);
            }

            sb.append(num);
        }

        return cutToTheLength(sb, length);
    }

    private static String cutToTheLength(StringBuilder sb, int length) {
        if (lastCharIsNotDigit(sb, length)) {
            Integer num = ThreadLocalRandom.current().nextInt(1, 10);
            sb.insert(length - 1, num);
        }

        return sb.substring(0, length);
    }

    private static boolean lastCharIsNotDigit(StringBuilder sb, int length) {
        char last = sb.charAt(length - 1);
        return last < '0' || last > '9';
    }

    private static int nextNumber() {
        return ThreadLocalRandom.current().nextInt(-MAX_ABS_VALUE_OF_NUMBERS, MAX_ABS_VALUE_OF_NUMBERS);
    }


}
