package com.algorithm.verily;

import java.util.*;

public class LargestRectangleFromPoints {

    /**
     * Finds the area of the largest axis-aligned rectangle
     * that can be formed from the given set of 2D points.
     *
     * Time Complexity:  O(n^2) average, where n = number of points
     *                   For each pair of points with the same Y, we do O(1) HashMap ops.
     *                   Total pairs across all Y-groups = O(n^2) in worst case.
     * Space Complexity: O(n) for the HashSet + O(n) for the pair map
     *
     * @param points 2D array of [x, y] coordinates
     * @return area of the largest rectangle, or 0 if none exists
     */
    public static long largestRectangleArea(int[][] points) {
        if (points == null || points.length < 4) return 0;

        // Step 1: Store all points in a HashSet for O(1) lookup
        Set<Long> pointSet = new HashSet<>();
        for (int[] p : points) {
            pointSet.add(encode(p[0], p[1]));
        }

        // Step 2: Group points by Y-coordinate
        Map<Integer, List<Integer>> byY = new HashMap<>();
        for (int[] p : points) {
            byY.computeIfAbsent(p[1], k -> new ArrayList<>()).add(p[0]);
        }

        // Step 3: Sort Y-levels so we process bottom-to-top
        List<Integer> yLevels = new ArrayList<>(byY.keySet());
        Collections.sort(yLevels);

        // Step 4: For each pair of X-values at each Y-level,
        //         record the last Y we saw this X-pair at.
        //         If we see it again at a higher Y, we found a rectangle.
        // Key: encoded (x1, x2) pair  →  Value: last Y seen
        Map<Long, Integer> lastSeenY = new HashMap<>();
        long maxArea = 0;

        for (int y : yLevels) {
            List<Integer> xs = byY.get(y);
            Collections.sort(xs); // ensure canonical (x1 < x2) pairs
            int size = xs.size();

            for (int i = 0; i < size; i++) {
                for (int j = i + 1; j < size; j++) {
                    int x1 = xs.get(i);
                    int x2 = xs.get(j);

                    long pairKey = encode(x1, x2);

                    if (lastSeenY.containsKey(pairKey)) {
                        int prevY = lastSeenY.get(pairKey);
                        long width  = (long) Math.abs(x2 - x1);
                        long height = (long) Math.abs(y - prevY);
                        maxArea = Math.max(maxArea, width * height);
                    }

                    // Always update to current (highest so far) Y for this pair
                    lastSeenY.put(pairKey, y);
                }
            }
        }

        return maxArea;
    }

    /**
     * Encodes two integers into a single long for use as a HashMap key.
     * Safe for coordinate values in the range [-10^6, 10^6].
     */
    private static long encode(int a, int b) {
        return ((long) a << 32) | (b & 0xFFFFFFFFL);
    }

    // ─── Demo / Test ────────────────────────────────────────────────────────────

    public static void main(String[] args) {

        // Test 1: Classic rectangle
        int[][] points1 = {{0,0},{4,0},{0,3},{4,3}};
        System.out.println("Test 1: " + largestRectangleArea(points1));  // Expected: 12

        // Test 2: Multiple rectangles — pick the largest
        int[][] points2 = {
                {0,0},{1,0},{2,0},
                {0,1},{1,1},{2,1},
                {0,3},{2,3}
        };
        System.out.println("Test 2: " + largestRectangleArea(points2));  // Expected: 6

        // Test 3: Negative coordinates
        int[][] points3 = {{-3,-2},{3,-2},{-3,4},{3,4},{0,0}};
        System.out.println("Test 3: " + largestRectangleArea(points3));  // Expected: 36

        // Test 4: No rectangle possible
        int[][] points4 = {{0,0},{1,1},{2,2},{3,3}};
        System.out.println("Test 4: " + largestRectangleArea(points4));  // Expected: 0

        // Test 5: Large area with many distractors
        int[][] points5 = {
                {0,0},{10,0},{0,5},{10,5},
                {1,1},{2,2},{3,3},{4,4},   // noise
                {0,3},{10,3}               // inner rectangle: 10x3=30
        };
        System.out.println("Test 5: " + largestRectangleArea(points5));  // Expected: 50
    }
}
