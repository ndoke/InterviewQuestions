package com.algorithm.programming;

import java.util.ArrayList;
import java.util.List;

public class Parens {
    public List<String> getParens(int n) {
        char[] c = new char[n * 2];
        List<String> result = new ArrayList<>();
        getParensHelper(n, n, c, 0, result);
        return result;
    }

    private void getParensHelper(int leftRem,
                                 int rightRem,
                                 char[] c,
                                 int index,
                                 List<String> result) {
        if (leftRem < 0 || rightRem < 0) {
            return;
        }

        if (leftRem == 0 && rightRem == 0) {
            result.add(new String(c));
            return;
        }

        c[index] = '(';
        getParensHelper(leftRem - 1, rightRem, c, index + 1, result);

        c[index] = ')';
        getParensHelper(leftRem, rightRem - 1, c, index + 1, result);
    }

    public static void main(String[] args) {
        Parens parens = new Parens();
        System.out.println(parens.getParens(3));
        System.out.println(parens.getParens(5));
    }
}
