package com.algorithm.programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PowerSet {
    /**
     * Power Set: Write a method to return all subsets of a set.
     */
    public List<List<Integer>> powerSet(List<Integer> l) {
        List<List<Integer>> result = new ArrayList<>();
        return powerSetRecursive(l, result, 0);
    }

    private List<List<Integer>> powerSetRecursive(List<Integer> l,
                                                  List<List<Integer>> result,
                                                  int index) {
        if (index == l.size()) {
            result.add(new ArrayList<>());
        } else {
            Integer val = l.get(index);
            List<List<Integer>> nextResult = powerSetRecursive(l, result, index + 1);
            List<List<Integer>> allSubSets = new ArrayList<>();
            for (List<Integer> r : nextResult) {
                List<Integer> temp = new ArrayList<>();
                temp.add(val);
                temp.addAll(r);
                allSubSets.add(temp);
            }
            result.addAll(allSubSets);
        }

        return result;
    }

    private List<List<Integer>> powerSetCombinatorics(List<Integer> l) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < Math.pow(2, l.size()); i++) {
            powerSetCombinatoricsHelper(l, i, result);
        }

        return result;
    }

    private void powerSetCombinatoricsHelper(List<Integer> l,
                                             int i,
                                             List<List<Integer>> result) {
        int index = 0;
        List<Integer> temp = new ArrayList<>();
        while (i > 0) {
            if ((i & 1) != 0) {
                temp.add(l.get(index));
            }
            i = i >> 1;
            index++;
        }
        result.add(temp);
    }

    public static void main(String[] args) {
        PowerSet powerSet = new PowerSet();
        System.out.println(powerSet.powerSet(Arrays.asList(1, 3, 7)));
        System.out.println(powerSet.powerSet(Arrays.asList(4, 3, 7, -1, -1)));
        System.out.println(powerSet.powerSetCombinatorics(Arrays.asList(1, 3, 7)));
        System.out.println(powerSet.powerSet(Arrays.asList(4, 3, 7, -1, -1)));
    }
}
