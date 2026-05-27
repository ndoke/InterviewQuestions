package com.algorithm.programming;

import java.util.ArrayList;
import java.util.List;

public class PermutationsWithoutDups {
    public List<String> permuteString(String input) {
        List<String> result = new ArrayList<>();
        permuteStringHelper(input, "", input.length(), result);
        return result;
    }

    private void permuteStringHelper(String input,
                                     String prefix,
                                     int n,
                                     List<String> result) {
        if (prefix.length() == n) {
            result.add(prefix);
            return;
        }

        for (int i = 0; i < input.length(); i++) {
            String left = input.substring(0, i);
            char c = input.charAt(i);
            String right = input.substring(i + 1);
            permuteStringHelper(left + right, prefix + c, n, result);
        }
    }

    public static void main(String[] args) {
        PermutationsWithoutDups permutationsWithoutDups = new PermutationsWithoutDups();
        System.out.println(permutationsWithoutDups.permuteString("abc"));
        System.out.println(permutationsWithoutDups.permuteString("as0d5"));
    }
}
