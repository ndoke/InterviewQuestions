package com.algorithm.clrs;

import java.util.Arrays;

public class AddBinary {
    public int[] addBinary(int[] bin1, int[] bin2) {
        int n = Math.max(bin1.length, bin2.length);
        int[] result = new int[n + 1];
        Arrays.fill(result, 0);
        int n1 = bin1.length - 1, n2 = bin2.length - 1, resInd = result.length - 1;
        int carry = 0, sum = 0;
        while (n1 >= 0 && n2 >= 0) {
            sum = bin1[n1] + bin2[n2] + carry;
            switch (sum) {
                case 3:
                    result[resInd] = 1;
                    carry = 1;
                    break;
                case 2:
                    result[resInd] = 0;
                    carry = 1;
                    break;
                case 1:
                    result[resInd] = 1;
                    carry = 0;
                    break;
                default:
                    result[resInd] = 0;
                    carry = 0;
                    break;
            }
            resInd--;
            n1--;
            n2--;
        }

        if (carry == 1) {
            result[0] = 1;
        }

        return result;
    }

    public static void main(String[] args) {
        AddBinary addBinary = new AddBinary();
        // 10 + 31 = 41
        int[] result = addBinary.addBinary(new int[]{1, 0, 1, 0}, new int[]{1, 1, 1, 1, 1});
        System.out.println(Arrays.toString(result));
        // 15 + 23 = 38
        result = addBinary.addBinary(new int[]{1, 1, 1, 1}, new int[]{1, 0, 1, 1, 1});
        System.out.println(Arrays.toString(result));
    }
}
