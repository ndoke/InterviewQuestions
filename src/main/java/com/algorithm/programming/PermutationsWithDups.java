package com.algorithm.programming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermutationsWithDups {
    public List<String> permuteStringDups(String s) {
        Map<Character, Integer> count = new HashMap<>();
        populateCounts(s, count);
        List<String> result = new ArrayList<>();
        permuteStringDupsHelper(s.length(), count, "", result);
        return result;
    }

    private void permuteStringDupsHelper(int remaining,
                                         Map<Character, Integer> count,
                                         String prefix,
                                         List<String> result) {
        if (remaining == 0) {
            result.add(prefix);
            return;
        }

        for (Character c : count.keySet()) {
            int cnt = count.get(c);
            if (cnt > 0) {
                count.put(c, cnt - 1);
                permuteStringDupsHelper(remaining - 1, count, prefix + c, result);
                count.put(c, cnt);
            }
        }
    }

    private void populateCounts(String s, Map<Character, Integer> count) {
        for (char c : s.toCharArray()) {
            count.put(c, count.getOrDefault(c, 0) + 1);
        }
    }

    public static void main(String[] args) {
        PermutationsWithDups permutationsWithDups = new PermutationsWithDups();
        System.out.println(permutationsWithDups.permuteStringDups("aabccddd"));
    }
}
