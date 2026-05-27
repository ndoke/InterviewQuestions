package com.algorithm.crackingthecodinginterview;

import java.util.Arrays;

public class CCIApp {
    public static void main(String[] args) {
        int[] histogram = new int[]{0, 0, 4, 0, 0, 6, 0, 0, 3, 0, 8, 0, 2, 0, 5, 2, 0, 3, 0, 0};
        CCI cci = new CCI();
        int vol = cci.volumeOfHistogram(histogram);
        System.out.println(vol);

        int minWindow = cci.shortestSubsequence(new int[]{7, 5, 9, 0, 2, 1, 3, 5, 7, 9, 1, 1, 5, 8, 8, 9, 7},
                new int[]{1, 5, 9});
        System.out.println(minWindow);

        int[] timings = new int[]{30, 15, 60, 75, 45, 15, 15, 45};
        System.out.println(cci.masseuseSchedule(timings));
        System.out.println(Arrays.toString(cci.selectedMasseuseSchedule(timings)));

        System.out.println(cci.masterMind("GGRR", "RGBY"));

        System.out.println(Arrays.toString(cci.subSort(new int[]{1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19})));

        System.out.println(cci.maxContiguousSubSequence(new int[]{2, -8, 3, -2, 4, -10}));

        int[][] pond = new int[][]{
                new int[]{0, 2, 1, 0},
                new int[]{0, 1, 0, 1},
                new int[]{1, 1, 0, 1},
                new int[]{0, 1, 0, 1},
        };
        System.out.println(cci.pondSize(pond));
    }
}
