package com.algorithm.leetcode.dynamic.programming;

public class DecodeWays {
    /**
     * You have intercepted a secret message encoded as a string of numbers.
     * The message is decoded via the following mapping:
     *
     * "1" -> 'A'
     * "2" -> 'B'
     * ...
     * "25" -> 'Y'
     * "26" -> 'Z'
     *
     * However, while decoding the message, you realize that
     * there are many different ways you can decode the message
     * because some codes are contained in other codes ("2" and "5" vs "25").
     *
     * For example, "11106" can be decoded into:
     *
     * "AAJF" with the grouping (1, 1, 10, 6)
     * "KJF" with the grouping (11, 10, 6)
     * The grouping (1, 11, 06) is invalid because "06" is
     * not a valid code (only "6" is valid).
     * Note: there may be strings that are impossible to decode.
     *
     * Given a string s containing only digits, return the
     * number of ways to decode it. If the entire string
     * cannot be decoded in any valid way, return 0.
     *
     * The test cases are generated so that the answer
     * fits in a 32-bit integer.
     *
     *
     * Example 1:
     *
     * Input: s = "12"
     * Output: 2
     *
     * Explanation:
     * "12" could be decoded as "AB" (1 2) or "L" (12).
     *
     * Example 2:
     *
     * Input: s = "226"
     * Output: 3
     *
     * Explanation:
     * "226" could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
     *
     * Example 3:
     *
     * Input: s = "06"
     *
     * Output: 0
     *
     * Explanation:
     * "06" cannot be mapped to "F" because of the leading zero
     * ("6" is different from "06"). In this case, the string is
     * not a valid encoding, so return 0.
     *
     *
     * Constraints:
     *
     * 1 <= s.length <= 100
     * s contains only digits and may contain leading zero(s).
     */
    public int numDecodings(String s) {
        int n = s.length();

        // dp[i] = number of ways to decode s[0..i-1]
        int[] dp = new int[n + 1];

        // Base cases
        dp[0] = 1;                              // empty string: 1 way
        dp[1] = s.charAt(0) == '0' ? 0 : 1;    // single char: 0 if '0', else 1

        for (int i = 2; i <= n; i++) {
            int oneDigit = Integer.parseInt(s.substring(i - 1, i));   // s[i-1]
            int twoDigit = Integer.parseInt(s.substring(i - 2, i));   // s[i-2..i-1]

            // Take one digit: valid if digit is 1–9
            if (oneDigit >= 1) {
                dp[i] += dp[i - 1];
            }

            // Take two digits: valid if number is 10–26
            if (twoDigit >= 10 && twoDigit <= 26) {
                dp[i] += dp[i - 2];
            }
        }

        return dp[n];
    }

    public int numDecodingsSpaceOptimized(String s) {
        int n = s.length();

        // prev2 = dp[i-2], prev1 = dp[i-1]
        int prev2 = 1;
        int prev1 = s.charAt(0) == '0' ? 0 : 1;

        for (int i = 2; i <= n; i++) {
            int curr = 0;

            int oneDigit = Integer.parseInt(s.substring(i - 1, i));
            int twoDigit = Integer.parseInt(s.substring(i - 2, i));

            // Single digit decode: '1'–'9'
            if (oneDigit >= 1) {
                curr += prev1;
            }

            // Two digit decode: 10–26
            if (twoDigit >= 10 && twoDigit <= 26) {
                curr += prev2;
            }

            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }
}
