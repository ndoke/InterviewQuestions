package com.algorithm.crackingthecodinginterview;

import java.util.*;

public class CCI {
    /**
     *     Volume of Histogram: Imagine a histogram (bar graph).
     *     Design an algorithm to compute the
     *     volume of water it could hold if someone poured water
     *     across the top. You can assume that each
     *     histogram bar has width 1.
     *
     *     EXAMPLE
     *     Input: {0, 0, 4, 0, 0, 6, 0, 0, 3, 0, 5, 0, 1, 0, 0, 0}
     *     Output:26
     */
    public int volumeOfHistogram(int[] histogram) {
        // find left max
        int[] leftMax = new int[histogram.length];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < histogram.length; i++) {
            max = Math.max(histogram[i], max);
            leftMax[i] = max;
        }

        // find right max
        int[] rightMax = new int[histogram.length];
        max = Integer.MIN_VALUE;
        for (int i = histogram.length - 1; i >= 0; i--) {
            max = Math.max(histogram[i], max);
            rightMax[i] = max;
        }

        // find min of the two
        for (int i = 0; i < rightMax.length; i++) {
            rightMax[i] = Math.min(leftMax[i], rightMax[i]);
        }

        // delta between min of the two and actual value
        // sum up the deltas
        int sum = 0;
        for (int i = 0; i < rightMax.length; i++) {
            sum += Math.abs(rightMax[i] - histogram[i]);
        }

        return sum;
    }

    /**
     *    Shortest Supersequence: You are given two arrays,
     *     one shorter (with all distinct elements) and one
     *     longer. Find the shortest subarray in the longer
     *     array that contains all the elements in the shorter
     *     array. The items can appear in any order.
     *
     *     EXAMPLE
     *     Input:
     *     {1, 5, 9}
     *     {7, 5, 9, 0, 2, 1, 3, 5, 7, 9, 1, 1, 5, 8, 8, 9, 7}
     *     Output:[7, 10]
     */
    public int shortestSubsequence(int[] longer, int[] shorter) {
        int[][] table = new int[shorter.length + 1][longer.length];
        for (int row = 0; row < table.length - 1; row++) {
            int val = shorter[row];
            int index = -1;
            for (int col = table[row].length - 1; col >= 0; col--) {
                if (longer[col] == val) {
                    index = col;
                }
                table[row][col] = index;
            }
        }

        int minWindow = Integer.MAX_VALUE;
        for (int col = 0; col < table[0].length; col++) {
            int max = Integer.MIN_VALUE;
            for (int row = 0; row < table.length - 1; row++) {
                if (table[row][col] == -1) {
                    max = -1;
                    break;
                }
                max = Math.max(max, table[row][col]);
            }
            table[table.length - 1][col] = max == -1 ? -1 : max - col + 1;
            if (max != -1) {
                minWindow = Math.min(minWindow, table[table.length - 1][col]);
            }
        }

        return minWindow;
    }

    /**
     *     The Masseuse: A popular masseuse receives a sequence
     *     of back-to-back appointment requests
     *     and is debating which ones to accept. She needs a
     *     15-minute break between appointments and
     *     therefore she cannot accept any adjacent requests.
     *     Given a sequence of back-to-back appoint
     *     ment requests (all multiples of 15 minutes, none overlap, and none can be moved),
     *     find the optimal (highest total booked minutes) set the masseuse can honor.
     *     Return the number of minutes.
     *
     *     EXAMPLE
     *     Input: {30, 15, 60, 75, 45, 15, 15, 45}
     *     Output: 180 minutes ({30, 60, 45, 45}).
     */
    public int masseuseSchedule(int[] timings) {
        int oneAway = 0;
        int twoAway = 0;
        for (int i = timings.length - 1; i >= 0; i--) {
            int withCurrent = timings[i] + twoAway;
            int withoutCurrent = oneAway;
            int current = Math.max(withCurrent, withoutCurrent);
            twoAway = oneAway;
            oneAway = current;
        }

        return oneAway;
    }

    /**
     * Bonus: Also returns WHICH appointments were selected.
     * Time:  O(n)
     * Space: O(n) — only for result reconstruction
     */
    public int[] selectedMasseuseSchedule(int[] appointments) {
        if (appointments == null || appointments.length == 0) return new int[]{};

        int n = appointments.length;
        int[] dp = new int[n + 1];

        dp[0] = 0;
        dp[1] = appointments[0];

        for (int i = 2; i <= n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + appointments[i - 1]);
        }

        // Backtrack to find selected appointments
        List<Integer> selected = new ArrayList<>();
        int i = n;
        while (i >= 1) {
            if (i == 1 || dp[i] != dp[i - 1]) {
                // This appointment was taken
                selected.add(appointments[i - 1]);
                i -= 2; // Skip adjacent
            } else {
                i -= 1;
            }
        }

        Collections.reverse(selected);
        return selected.stream().mapToInt(x -> x).toArray();
    }

    private class People {
        int birthYear;
        int deathYear;

        public int getBirthYear() {
            return birthYear;
        }

        public int getDeathYear() {
            return deathYear;
        }
    }

    /**
     *     Living People: Given a list of people with their
     *     birth and death years, implement a method to
     *     compute the year with the most number of people alive.
     *     You may assume that all people were born
     *     between 1900 and 2000 (inclusive). If a person
     *     was alive during any portion of that year, they should
     *     be included in that year's count. For example,
     *     Person (birth= 1908, death= 1909) is included in the
     *     counts for both 1908 and 1909.
     */
    public int livingPeople(People[] people) {
        int start = 1900;
        int end = 2000;
        int[] yearOffsets = new int[end - start + 1];
        for (People p : people) {
            int birthYearOffset = p.getBirthYear() - 1900;
            int deathYearOffset = p.getDeathYear() - 1900;
            yearOffsets[birthYearOffset]++;
            yearOffsets[deathYearOffset]--;
        }

        int alive = 0;
        int maxAlive = Integer.MIN_VALUE;
        int maxAliveYear = -1;
        for (int i = 0; i < yearOffsets.length; i++) {
            alive += yearOffsets[i];
            if (alive > maxAlive) {
                maxAlive = alive;
                maxAliveYear = i + 1900;
            }
        }

        return maxAliveYear;
    }

    private class Result {
        int hits;
        int pseudoHits;

        public Result(int hits, int pseudoHits) {
            this.hits = hits;
            this.pseudoHits = pseudoHits;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "hits=" + hits +
                    ", pseudoHits=" + pseudoHits +
                    '}';
        }
    }

    /**
     *     Master Mind: The Game of Master Mind is played as follows:
     *     The computer has four slots, and each slot will contain a ball that
     *     is red (R), yellow (Y), green (G) or blue (B).
     *     For example, the computer might have RGGB
     *     (Slot #1 is red, Slots #2 and #3 are green, Slot #4 is blue).
     *     You, the user, are trying to guess the solution.
     *     You might, for example, guess YRGB.
     *     When you guess the correct color for the correct slot,
     *     you get a "hit:' If you guess a color that exists
     *     but is in the wrong slot, you get a
     *         "pseudo-hit:' Note that a slot that is a hit can never count as a pseudo-hit.
     *     For example, if the actual solution is RGBY and you guess GGRR,
     *     you have one hit and one pseudo hit
     *     Write a method that, given a guess and a solution,
     *     returns the number of hits and pseudo-hits.
     */
    public Result masterMind(String guess, String answer) {
        if (guess.length() != answer.length()) {
            return new Result(0, 0);
        }

        int[] guessRegister = new int[26];
        for (char g : guess.toCharArray()) {
            guessRegister[g - 'A']++;
        }

        int hits = 0;
        int pseudoHits = 0;
        for (int i = 0; i < answer.length(); i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                hits++;
            } else {
                char a = answer.charAt(i);
                if (guessRegister[a - 'A'] > 0) {
                    guessRegister[a - 'A']--;
                    pseudoHits++;
                }
            }
        }

        return new Result(hits, pseudoHits);
    }

    /**
     *     Sub Sort: Given an array of integers, write a method to
     *     find indices m and n such that if you sorted
     *     elements m through n, the entire array would be sorted.
     *     Minimize n - m (that is, find the smallest such sequence).
     *
     *     EXAMPLE
     *     Input: 1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19
     *     Output: (3, 9)
     */
    public int[] subSort(int[] a) {
        int left = -1, right = -1;
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1]) {
                left = i;
                break;
            }
        }

        for (int i = a.length - 2; i >= 0; i--) {
            if (a[i + 1] < a[i]) {
                right = i;
                break;
            }
        }

        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int i = left; i <= right; i++) {
            min = Math.min(min, a[i]);
            max = Math.max(max, a[i]);
        }

        int[] result = new int[2];
        for (int i = left - 1; i >= 0; i--) {
            if (a[i] == min) {
                result[0] = i;
                break;
            } else if (a[i] < min) {
                result[0] = i + 1;
                break;
            }
        }

        for (int i = right + 1; i < a.length; i++) {
            if (a[i] == max) {
                result[1] = i;
                break;
            } else if (a[i] > max) {
                result[1] = i - 1;
                break;
            }
        }

        return result;
    }

    /**
     *     Contiguous Sequence: You are given an array of
     *     integers (both positive and negative). Find the
     *     contiguous sequence with the largest sum. Return the sum.
     *
     *     EXAMPLE
     *     Input: 2, -8, 3, -2, 4, -10
     *     Output: 5 ( i.e. , { 3, -2, 4} )
     */
    public int maxContiguousSubSequence(int[] a) {
        int i = 0;
        int sum = 0, maxSum = Integer.MIN_VALUE;
        for (int a_ : a) {
            sum += a_;
            if (sum < 0) {
                sum = 0;
            } else {
                maxSum = Math.max(maxSum, sum);
            }
        }

        return maxSum;
    }

    /**
     *     Pond Sizes: You have an integer matrix representing a
     *     plot of land, where the value at that location
     *     represents the height above sea level. A value of zero
     *     indicates water. A pond is a region of water
     *     connected vertically, horizontally, or diagonally.
     *     The size of the pond is the total number of
     *     connected water cells. Write a method to compute the
     *     sizes of all ponds in the matrix.
     *     EXAMPLE
     *     Input:
     *             0 2 1 0
     *             0 1 0 1
     *             1 1 0 1
     *             0 1 0 1
     *     Output: 2, 4, 1 (in any order)
     */
    public List<Integer> pondSize(int[][] pond) {
        boolean[][] visited = new boolean[pond.length][pond[0].length];
        List<Integer> result = new ArrayList<>();
        for (int row = 0; row < pond.length; row++) {
            for (int col = 0; col < pond[row].length; col++) {
                int size = getPondSizeHelper(pond, row, col, visited);
                if (size > 0) {
                    result.add(size);
                }
            }
        }

        return result;
    }

    private int getPondSizeHelper(int[][] pond, int row, int col, boolean[][] visited) {
        if (row < 0
                || row > pond.length - 1
                || col < 0
                || col > pond[row].length - 1
                || pond[row][col] != 0
                || visited[row][col]) {
            return 0;
        }

        int pondSize = 1;
        visited[row][col] = true;
        for (int rc = row - 1; rc <= row + 1; rc++) {
            for (int cc = col - 1; cc <= col + 1; cc++) {
                pondSize += getPondSizeHelper(pond, rc, cc, visited);
            }
        }

        return pondSize;
    }
}
