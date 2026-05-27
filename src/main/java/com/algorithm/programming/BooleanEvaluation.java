package com.algorithm.programming;

public class BooleanEvaluation {
    public int eval(String expression, boolean result) {
        return evalHelper(expression, result);
    }

    private int evalHelper(String expression, boolean result) {
        if (expression.isEmpty()) {
            return 0;
        } else if (expression.length() == 1) {
            boolean actualResult = expression.charAt(0) == 1;
            return actualResult == result ? 1 : 0;
        }

        int ways = 0;
        for (int i = 1; i < expression.length(); i = i + 2) {
            String left = expression.substring(0, i);
            char c = expression.charAt(i);
            String right = expression.substring(i);

            int leftTrue = evalHelper(left, true);
            int leftFalse = evalHelper(left, false);
            int rightTrue = evalHelper(right, true);
            int rightFalse = evalHelper(right, false);

            int total = (leftTrue + leftFalse) * (rightTrue + rightFalse);

            int totalTrue= 0;
            if (c == '^') {
                totalTrue = leftFalse * rightTrue + leftTrue * rightFalse;
            } else if (c == '&') {
                totalTrue = leftTrue * rightTrue;
            } else if (c == '|') {
                totalTrue = leftTrue * rightFalse + leftFalse * rightTrue;
            }

            int subWays = result ? totalTrue : total - totalTrue;
            ways += subWays;
        }

        return ways;
    }
}
