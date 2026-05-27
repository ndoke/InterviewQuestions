package com.algorithm.crackingthecodinginterview;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class Chapter1Test {
    private static Chapter1 chapter1;

    @BeforeAll
    public static void setUp() {
        chapter1 = new Chapter1();
    }

    @Test
    public void isUniqueTest() {
        Assertions.assertTrue(chapter1.isUniqueBrute("abcde"));
        Assertions.assertFalse(chapter1.isUniqueBrute("abcdb"));
        Assertions.assertTrue(chapter1.isUniqueSorted("abcde"));
        Assertions.assertFalse(chapter1.isUniqueSorted("abcdb"));
        Assertions.assertTrue(chapter1.isUniqueOptimal("abcde"));
        Assertions.assertFalse(chapter1.isUniqueOptimal("abcdb"));
    }

    @Test
    public void checkPermutation() {
        Assertions.assertTrue(chapter1.checkPermutation("abc", "acb"));
        Assertions.assertFalse(chapter1.checkPermutation("abc", "ace"));
        Assertions.assertFalse(chapter1.checkPermutation("abcf", "abc"));
    }

    @Test
    public void urlIfy() {
        Assertions.assertEquals("a%20b%20c", chapter1.urlIfy("a b c"));
        Assertions.assertEquals("This%20is%20a%20beautiful%20day%20%20",
                chapter1.urlIfy("This is a beautiful day  "));
    }

    @Test
    public void palindromePerm() {
        Assertions.assertTrue(chapter1.palindromePerm("abcab"));
        Assertions.assertTrue(chapter1.palindromePerm("abc ab"));
        Assertions.assertFalse(chapter1.palindromePerm("abca"));
        Assertions.assertFalse(chapter1.palindromePerm(" a bca"));
        Assertions.assertTrue(chapter1.palindromePerm("abab"));
        Assertions.assertTrue(chapter1.palindromePerm("aba b"));
        Assertions.assertTrue(chapter1.palindromePerm(""));
    }

    @Test
    public void oneAway() {
        Assertions.assertTrue(chapter1.oneAway("pale", "ple"));
        Assertions.assertTrue(chapter1.oneAway("pales", "pale"));
        Assertions.assertTrue(chapter1.oneAway("pale", "bale"));
        Assertions.assertFalse(chapter1.oneAway("pale", "bae"));
    }

    @Test
    public void stringCompression() {
        Assertions.assertEquals("a2b1c5a1", chapter1.stringCompression("aabccccca"));
        Assertions.assertEquals("a2b1c5a3", chapter1.stringCompression("aabcccccaaa"));
        Assertions.assertEquals("a1b1c1d1", chapter1.stringCompression("abcd"));
        Assertions.assertEquals("", chapter1.stringCompression(""));
    }

    @Test
    public void rotateMatrix() {
        int[][] grid = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        chapter1.rotateMatrix(grid);
        for (int[] g : grid) {
            System.out.println(Arrays.toString(g));
        }
    }

    @Test
    public void zeroMatrix() {
        int[][] grid = {
                {1, 2, 3, 4},
                {5, 0, 7, 8},
                {0, 10, 11, 0},
                {13, 14, 15, 16}
        };
        chapter1.zeroMatrix(grid);
        for (int[] g : grid) {
            System.out.println(Arrays.toString(g));
        }
    }
}
