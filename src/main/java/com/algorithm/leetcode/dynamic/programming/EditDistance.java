package com.algorithm.leetcode.dynamic.programming;

public class EditDistance {
    /**
     * Given two strings word1 and word2, return the
     * minimum number of operations required to convert
     * word1 to word2.
     *
     * You have the following three operations permitted
     * on a word:
     *
     * Insert a character
     * Delete a character
     * Replace a character
     *
     *
     * Example 1:
     *
     * Input: word1 = "horse", word2 = "ros"
     * Output: 3
     * Explanation:
     * horse -> rorse (replace 'h' with 'r')
     * rorse -> rose (remove 'r')
     * rose -> ros (remove 'e')
     *
     * Example 2:
     *
     * Input: word1 = "intention", word2 = "execution"
     * Output: 5
     * Explanation:
     * intention -> inention (remove 't')
     * inention -> enention (replace 'i' with 'e')
     * enention -> exention (replace 'n' with 'x')
     * exention -> exection (replace 'n' with 'c')
     * exection -> execution (insert 'u')
     *
     *
     * Constraints:
     *
     * 0 <= word1.length, word2.length <= 500
     * word1 and word2 consist of lowercase English letters.
     */
    public int minDistanceMatrix(String word1, String word2) {
        int m = word1.length(), n = word2.length();

        // dp[i][j] = min operations to convert word1[0..i-1] to word2[0..j-1]
        int[][] dp = new int[m + 1][n + 1];

        // Base cases: converting to/from empty string
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i; // delete all chars
        }

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j; // insert all chars
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // Characters match — no operation needed
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(
                            dp[i - 1][j - 1],  // replace
                            Math.min(
                                    dp[i - 1][j],  // delete from word1
                                    dp[i][j - 1]   // insert into word1
                            )
                    );
                }
            }
        }

        return dp[m][n];
    }

    public int minDistanceArray(String word1, String word2) {
        int m = word1.length(), n = word2.length();

        // Only keep the previous row; 'prev' tracks dp[i-1][j-1] (diagonal)
        int[] dp = new int[n + 1];

        // Base case: empty word1 → insert all chars of word2
        for (int j = 0; j <= n; j++) {
            dp[j] = j;
        }

        for (int i = 1; i <= m; i++) {
            int prev = dp[0]; // holds dp[i-1][j-1] before overwrite
            dp[0] = i;        // base case: empty word2 → delete i chars

            for (int j = 1; j <= n; j++) {
                int temp = dp[j]; // save before overwrite (becomes next 'prev')

                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[j] = prev;  // characters match → take diagonal
                } else {
                    dp[j] = 1 + Math.min(
                            prev,      // replace  (diagonal)
                            Math.min(
                                    dp[j], // delete   (cell above)
                                    dp[j - 1] // insert (cell to left)
                            )
                    );
                }
                prev = temp; // slide diagonal forward
            }
        }

        return dp[n];
    }
}
