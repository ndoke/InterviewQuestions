package com.algorithm.programming;

import java.util.Arrays;

public class Coins {
    public int countWays(int[] denoms, int cents) {
        int[] memo = new int[cents + 1];
        Arrays.fill(memo, -1);
        return countWaysHelper(denoms, cents, 0, memo);
    }

    private int countWaysHelper(int[] denoms,
                                int cents,
                                int index,
                                int[] memo) {
        if (index > denoms.length - 1) {
            return 1;
        }

        if (memo[index] != -1) {
            return memo[index];
        }

        int denomAmount = denoms[index];
        int ways = 0;
        for (int i = 0; cents >= denomAmount * i; i++) {
            int amountRemaining = cents - denomAmount * i;
            ways += countWaysHelper(denoms, amountRemaining, index + 1, memo);
        }

        memo[index] = ways;
        return memo[index];
    }

    public static void main(String[] args) {
        Coins coins = new Coins();
        System.out.println(coins.countWays(new int[]{25, 10, 5, 1}, 100));
    }
}
