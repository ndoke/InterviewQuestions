package com.algorithm.leetcode;

import java.util.*;

public class Solution {
    public String[] sortPeople(String[] names, int[] heights) {
        PriorityQueue<Person> people =
                new PriorityQueue<>(Comparator.comparingInt(Person::getHeight));
        for (int i = 0; i < names.length; i++) {
            Person p = new Person(names[i], heights[i]);
            people.add(p);
        }

        String[] result = new String[names.length];
        int index = 0;
        for (Person p : people) {
            result[index] = p.getName();
        }

        return result;
    }

    public int deleteGreatestValue(int[][] grid) {
        for (int[] g : grid) {
            Arrays.sort(g);
        }

        int sum = 0;
        for (int col = grid[0].length - 1; col >= 0; col--) {
            int max = Integer.MIN_VALUE;
            for (int row = 0; row < grid.length; row++) {
                max = Math.max(max, grid[row][col]);
            }
            sum += max;
        }

        return sum;
    }
}
