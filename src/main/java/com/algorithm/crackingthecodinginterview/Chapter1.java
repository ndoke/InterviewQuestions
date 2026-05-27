package com.algorithm.crackingthecodinginterview;

import java.util.Arrays;

public class Chapter1 {
    public boolean isUniqueBrute(String s) {
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length(); j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isUniqueSorted(String s) {
        char[] c = s.toCharArray();
        Arrays.sort(c);
        for (int i = 1; i < c.length; i++) {
            if (c[i] == c[i - 1]) {
                return false;
            }
        }

        return true;
    }

    public boolean isUniqueOptimal(String s) {
        int checker = 0;
        for (int i = 0; i < s.length(); i++) {
            int val = 1 << (s.charAt(i) - 'a');
            if ((checker & val) != 0) {
                return false;
            }
            checker |= val;
        }

        return true;
    }

    public boolean checkPermutation(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }

        int[] register = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            int index = s1.charAt(i) - 'a';
            register[index]++;
        }

        for (int i = 0; i < s2.length(); i++) {
            int index = s2.charAt(i) - 'a';
            register[index]--;
            if (register[index] < 0) {
                return false;
            }
        }

        return true;
    }

    public String urlIfy(String s) {
        int spaces = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                spaces++;
            }
        }

        char[] result = new char[s.length() + 2 * spaces];
        int rIndex = result.length - 1;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ' ') {
                result[rIndex] = '0';
                result[rIndex - 1] = '2';
                result[rIndex - 2] = '%';
                rIndex -= 3;
            } else {
                result[rIndex] = s.charAt(i);
                rIndex--;
            }
        }

        return new String(result);
    }

    public boolean palindromePerm(String s) {
        int checker = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != ' ') {
                int val = 1 << (c - 'a');
                if ((checker & val) != 0) {
                    checker &= ~val;
                } else {
                    checker |= val;
                }
            }
        }

        return checker == 0 || (checker & (checker - 1)) == 0;
    }

    public boolean oneAway(String s1, String s2) {
        String longer = s1.length() > s2.length() ? s1 : s2;
        String shorter = s1.length() > s2.length() ? s2 : s1;
        int longPointer = 0, shortPointer = 0;
        boolean diffFound = false;
        while (longPointer < longer.length() && shortPointer < shorter.length()) {
            char l = longer.charAt(longPointer);
            char s = shorter.charAt(shortPointer);
            if (l != s) {
                if (diffFound) {
                    return false;
                }

                diffFound = true;
                if (longer.length() == shorter.length()) {
                    shortPointer++;
                }
            } else {
                shortPointer++;
            }
            longPointer++;
        }

        return true;
    }

    public String stringCompression(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        int count = 1;
        for (int i = 1; i < input.length(); i++) {
            char current = input.charAt(i);
            char previous = input.charAt(i - 1);
            if (current != previous) {
                result.append(previous);
                result.append(count);
                count = 1;
            } else {
                count++;
            }
        }

        result.append(input.charAt(input.length() - 1));
        result.append(count);

        return result.toString();
    }

    public void rotateMatrix(int[][] grid) {
        int n = grid.length;
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;
            for (int i = first; i < last; i++) {
                int offset = i - first;
                int top = grid[first][i];
                grid[first][i] = grid[last - offset][first];
                grid[last - offset][first] = grid[last][last - offset];
                grid[last][last - offset] = grid[i][last];
                grid[i][last] = top;
            }
        }
    }

    public void zeroMatrix(int[][] matrix) {
        boolean firstRowZero = false;
        for (int col = 0; col < matrix[0].length; col++) {
            if (matrix[0][col] == 0) {
                firstRowZero = true;
                break;
            }
        }

        boolean firstColZero = false;
        for (int row = 0; row < matrix.length; row++) {
            if (matrix[row][0] == 0) {
                firstColZero = true;
                break;
            }
        }

        for (int row = 1; row < matrix.length; row++) {
            for (int col = 1; col < matrix[row].length; col++) {
                if (matrix[row][col] == 0) {
                    matrix[row][0] = 0;
                    matrix[0][col] = 0;
                }
            }
        }

        for (int row = 0; row < matrix.length; row++) {
            if (matrix[row][0] == 0) {
                Arrays.fill(matrix[row], 0);
            }
        }

        for (int col = 0; col < matrix[0].length; col++) {
            if (matrix[0][col] == 0) {
                makeColZero(matrix, col);
            }
        }

        if (firstRowZero) {
            Arrays.fill(matrix[0], 0);
        }

        if (firstColZero) {
            makeColZero(matrix, 0);
        }
    }

    private void makeColZero(int[][] matrix, int col) {
        for (int row = 0; row < matrix.length; row++) {
            matrix[row][col] = 0;
        }
    }
}
