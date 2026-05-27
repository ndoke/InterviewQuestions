package com.algorithm.leetcode.dynamic.programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LongestUnequalAdjacentGroupsSubsequenceI {
     public List<String> getLongestSubsequenceRecursion(String[] words, int[] groups) {
         List<String> result = new ArrayList<>();
         recurse(words, groups, 0, -1, result);
         return result;
     }

     private void recurse(String[] words,
                          int[] groups,
                          int index,
                          int lastGroup,
                          List<String> result) {
         if (words.length == index) {
             return;
         }

         if (lastGroup == -1 || groups[index] != lastGroup) {
             result.add(words[index]);
             recurse(words, groups, index + 1, groups[index], result);
         } else {
             recurse(words, groups, index + 1, lastGroup, result);
         }
     }

    public List<String> getLongestSubsequence(String[] words, int[] groups) {
        int n = words.length;
        // dp[i] = length of longest alternating subsequence ending at i
        int[] dp = new int[n];
        int[] parent = new int[n];
        Arrays.fill(dp, 1);
        Arrays.fill(parent, -1);

        for (int i = 1; i < n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (groups[i] != groups[j] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }
        }

        // find index with max dp value
        int maxIdx = 0;
        for (int i = 1; i < n; i++) {
            if (dp[i] > dp[maxIdx]) {
                maxIdx = i;
            }
        }

        // reconstruct path
        LinkedList<String> result = new LinkedList<>();
        for (int i = maxIdx; i != -1; i = parent[i]) {
            result.addFirst(words[i]);
        }

        return result;
    }
}
