package com.algorithm.leetcode.dynamic.programming;

public class UniqueBinarySearchTrees {
    /**
     * Given an integer n, return the number of
     * structurally unique BST's (binary search trees)
     * which has exactly n nodes of unique values from 1 to n.
     *
     * Example 1:
     *
     * Input: n = 3
     * Output: 5
     *
     * Example 2:
     *
     * Input: n = 1
     * Output: 1
     *
     *
     * Constraints:
     * 1 <= n <= 19
     */
    public int numTrees(int n) {
        // dp[i] = number of unique BSTs with i nodes
        int[] dp = new int[n + 1];

        // Base cases
        dp[0] = 1; // empty tree
        dp[1] = 1; // single node tree

        // Fill dp[2] through dp[n]
        for (int nodes = 2; nodes <= n; nodes++) {
            // Try every value k as the root
            for (int k = 1; k <= nodes; k++) {
                int leftTrees  = dp[k - 1];          // nodes in left subtree
                int rightTrees = dp[nodes - k];       // nodes in right subtree
                dp[nodes] += leftTrees * rightTrees;
            }
        }

        return dp[n];
    }
}
