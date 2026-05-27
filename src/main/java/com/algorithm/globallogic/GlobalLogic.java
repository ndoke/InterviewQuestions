package com.algorithm.globallogic;

public class GlobalLogic {
    public static String compressString(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        // aabbbcdddde
        StringBuilder result = new StringBuilder();
        int count = 1;
        for (int i = 1; i < s.length(); i++) {
            char current = s.charAt(i);
            char previous = s.charAt(i - 1);
            if (current != previous) {
                result.append(previous);
                result.append(count);
            } else {
                count++;
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(compressString("aabbbcdddde"));
    }
}

// SELECT * FROM (SELECT * FROM employee ORDER BY salary DESC LIMIT 2) ORDER BY salary ASC LIMIT 1;
