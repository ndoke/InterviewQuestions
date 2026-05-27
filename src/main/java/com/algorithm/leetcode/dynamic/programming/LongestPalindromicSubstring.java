package com.algorithm.leetcode.dynamic.programming;

import java.util.Arrays;

public class LongestPalindromicSubstring {
    /**
     *     Given a string s, return the longest palindromic
     *     substring in s.
     *
     *
     *     Example 1:
     *
     *     Input: s = "babad"
     *     Output: "bab"
     *     Explanation: "aba" is also a valid answer.
     *
     *     Example 2:
     *
     *     Input: s = "cbbd"
     *     Output: "bb"
     *
     *
     *     Constraints:
     *     - 1 <= s.length <= 1000
     *     - s consist of only digits and English letters.
     */
    public String longestPalindromeDynamicProgramming(String s) {
        int n = s.length();
        // dp[i][j] = true if s[i..j] is a palindrome
        boolean[][] dp = new boolean[n][n];
        int start = 0, maxLen = 1;

        // every single char is a palindrome
        for (int i = 0; i < n; i++){
            dp[i][i] = true;
        }

        // check substrings of length 2
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                start = i;
                maxLen = 2;
            }
        }

        // check substrings of length 3 and more
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    if (len > maxLen) {
                        start = i;
                        maxLen = len;
                    }
                }
            }
        }

        return s.substring(start, start + maxLen);
    }

    private int[][] memo;
    private String s;

    public String longestPalindromeRecursion(String s) {
        int n = s.length();
        this.s = s;
        this.memo = new int[n][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        int start = 0, maxLen = 1;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (isPalindrome(i, j) == 1 && j - i + 1 > maxLen) {
                    start = i;
                    maxLen = j - i + 1;
                }
            }
        }

        return s.substring(start, start + maxLen);
    }

    // returns 1 if s[i..j] is palindrome, 0 otherwise
    private int isPalindrome(int i, int j) {
        if (i >= j) {
            return 1;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        memo[i][j] = (s.charAt(i) == s.charAt(j)) ? isPalindrome(i + 1, j - 1) : 0;
        return memo[i][j];
    }
}
