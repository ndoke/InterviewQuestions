package com.algorithm.globallogic;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public int[] solution(int[] A, int[] B, int N) {
        // Handle edge cases
        if (A == null || B == null || N <= 0) {
            return new int[0];
        }

        int M = A.length;
        if (M == 0) {
            return new int[0];
        }

        if (B.length != M) {
            return new int[0];
        }

        // Validate inputs
        for (int i = 0; i < M; i++) {
            if (A[i] < 1 || A[i] > N || B[i] < 1 || B[i] > N || A[i] >= B[i]) {
                return new int[0];
            }
        }

        int[] result = new int[M];

        // dp[i] = minimum distance from city 1 to city i
        // This serves as our memoization array
        int[] dp = new int[N + 1];
        dp[1] = 0;
        for (int i = 2; i <= N; i++) {
            dp[i] = i - 1;
        }

        // Store all shortcut roads
        List<int[]> shortcuts = new ArrayList<int[]>();

        // Process each new road
        for (int k = 0; k < M; k++) {
            int from = A[k];
            int to = B[k];
            shortcuts.add(new int[]{from, to});

            // Recompute distances with memoization using all shortcuts
            recomputeWithMemoization(dp, N, shortcuts);

            result[k] = dp[N];
        }

        return result;
    }

    private void recomputeWithMemoization(int[] dp, int N, java.util.List<int[]> shortcuts) {
        // Use dynamic programming with memoization
        // Process cities in order from left to right
        for (int i = 2; i <= N; i++) {
            // Option 1: Come from previous city
            dp[i] = Math.min(dp[i], dp[i - 1] + 1);

            // Option 2: Use any shortcut ending at city i
            for (int[] road : shortcuts) {
                if (road[1] == i) {
                    dp[i] = Math.min(dp[i], dp[road[0]] + 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}