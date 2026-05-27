package com.algorithm.programming;

public class MagicIndex {
    /**
     *     Magic Index: A magic index in an array A[ 1 ... n-1]
     *     is defined to be an index such that A[ i]
     *     i. Given a sorted array of distinct integers,
     *     write a method to find a magic index, if one exists, in
     *     array A.
     *     FOLLOW UP
     *     What if the values are not distinct?
     */
    public void magicIndexExists(int[] arr) {
        int magicIndexDistinct =
                magicIndexExistsHelper(0, arr.length - 1, arr);
        int magicIndexNotDistinct =
                magicIndexExistsNotDistinctHelper(0, arr.length - 1, arr);
    }

    private int magicIndexExistsNotDistinctHelper(int start, int end, int[] arr) {
        if (start > end) {
            return -1;
        }

        int mid = (start + end) / 2;
        if (arr[mid] == mid) {
            return mid;
        }

        int left = magicIndexExistsNotDistinctHelper(Math.min(mid - 1, arr[mid]), end, arr);
        if (left != -1) {
            return left;
        }

        return magicIndexExistsNotDistinctHelper(Math.max(mid + 1, arr[mid]), end, arr);
    }

    private int magicIndexExistsHelper(int start, int end, int[] arr) {
        if (start > end) {
            return -1;
        }
        int mid = (start + end) / 2;
        if (arr[mid] == mid) {
            return mid;
        } else if (arr[mid] > mid) {
            return magicIndexExistsHelper(mid + 1, end, arr);
        } else {
            return magicIndexExistsHelper(start, mid - 1, arr);
        }
    }
}
