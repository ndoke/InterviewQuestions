package com.algorithm.leetcode.dynamic.programming;

import java.util.ArrayList;
import java.util.List;

public class UniqueBinarySearchTreesII {
    /**
     * Given an integer n, return all the structurally unique
     * BST's (binary search trees), which has exactly n nodes
     * of unique values from 1 to n. Return the answer in any order.
     *
     *
     * Example 1:
     *
     * Input: n = 3
     * Output: [[1,null,2,null,3],[1,null,3,2],[2,1,3],[3,1,null,null,2],[3,2,null,1]]
     *
     * Example 2:
     *
     * Input: n = 1
     * Output: [[1]]
     *
     *
     * Constraints:
     * 1 <= n <= 8
     */
    public List<TreeNode> generateTrees(int n) {

        // dp[i][j] = all unique BSTs using values i..j
        // Using (n+2) to safely handle index dp[i][i-1] (empty range)
        List<TreeNode>[][] dp = new List[n + 2][n + 2];

        // Base case 1: empty ranges → single null tree
        for (int i = 1; i <= n + 1; i++) {
            dp[i][i - 1] = new ArrayList<>();
            dp[i][i - 1].add(null);
        }

        // Base case 2: single node ranges → one leaf each
        for (int i = 1; i <= n; i++) {
            dp[i][i] = new ArrayList<>();
            dp[i][i].add(new TreeNode(i));
        }

        // Fill by increasing length (len = 2 up to n)
        for (int len = 2; len <= n; len++) {
            for (int start = 1; start <= n - len + 1; start++) {
                int end = start + len - 1;
                dp[start][end] = new ArrayList<>();

                // Try every k in [start,end] as root
                for (int k = start; k <= end; k++) {
                    List<TreeNode> leftTrees  = dp[start][k - 1]; // precomputed ✓
                    List<TreeNode> rightTrees = dp[k + 1][end];   // precomputed ✓

                    for (TreeNode left : leftTrees) {
                        for (TreeNode right : rightTrees) {
                            TreeNode root = new TreeNode(k);
                            root.left  = left;
                            root.right = right;
                            dp[start][end].add(root);
                        }
                    }
                }
            }
        }

        return dp[1][n];
    }
}
