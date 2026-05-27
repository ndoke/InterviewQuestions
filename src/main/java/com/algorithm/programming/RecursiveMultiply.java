package com.algorithm.programming;

public class RecursiveMultiply {
    /**
     *     Recursive Multiply: Write a recursive function to
     *     multiply two positive integers without using
     *     the * operator (or / operator). You can use addition,
     *     subtraction, and bit shifting, but you should
     *     minimize the number of those operations.
     */
    public int recursiveMultiply(int num1, int num2) {
        int smaller = Math.max(num1, num2);
        int greater = Math.min(num1, num2);
        return recursiveMultiplyHelper(smaller, greater);
    }

    private int recursiveMultiplyHelper(int smaller, int greater) {
        if (smaller == 0) {
            return 0;
        } else if (smaller == 1) {
            return greater;
        }

        int halfProd = recursiveMultiplyHelper(smaller >> 1, greater);
        if ((smaller % 2) == 0) {
            return halfProd + halfProd;
        }

        return halfProd + halfProd + greater;
    }

    public static void main(String[] args) {
        RecursiveMultiply recursiveMultiply = new RecursiveMultiply();
        System.out.println(recursiveMultiply.recursiveMultiply(1, 0));
        System.out.println(recursiveMultiply.recursiveMultiply(1, 5));
        System.out.println(recursiveMultiply.recursiveMultiply(3, 5));
        System.out.println(recursiveMultiply.recursiveMultiply(3, 8));
        System.out.println(recursiveMultiply.recursiveMultiply(4, 8));
    }
}
