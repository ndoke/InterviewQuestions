package com.algorithm.crackingthecodinginterview;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Chapter2 {
    public void deleteDups(LinkedListNode input) {
        LinkedListNode previous = null;
        Set<Integer> data = new HashSet<>();
        while (input != null) {
            if (data.contains(input.getVal())) {
                previous.setNext(input.getNext());
            } else {
                data.add(input.getVal());
                previous = input;
            }
            input = input.getNext();
        }
    }

    public LinkedListNode kToLast(LinkedListNode input, int k) {
        LinkedListNode rep = input;
        for (int i = 0; i < k; i++) {
            rep = rep.getNext();
        }

        while (rep != null) {
            input = input.getNext();
            rep = rep.getNext();
        }

        return input;
    }

    public LinkedListNode addListsReverse(LinkedListNode l1, LinkedListNode l2) {
        return addListsReverseHelper(l1, l2, 0);
    }

    private LinkedListNode addListsReverseHelper(LinkedListNode l1, LinkedListNode l2, int carry) {
        if (l1 == null && l2 == null && carry == 0) {
            return null;
        }

        int val = carry;

        if (l1 != null) {
            val += l1.getVal();
        }

        if (l2 != null) {
            val += l2.getVal();
        }

        LinkedListNode next = addListsReverseHelper(l1 == null ? null : l1.getNext(),
                l2 == null ? null : l2.getNext(),
                val / 10);

        return new LinkedListNode(val % 10, next);
    }

    class PartialSum {
        LinkedListNode sum;
        int carry;

        PartialSum() {
            this.sum = null;
            this.carry = 0;
        }

        PartialSum(LinkedListNode sum, int carry) {
            this.sum = sum;
            this.carry = carry;
        }
    }

    public LinkedListNode addListsInOrder(LinkedListNode l1, LinkedListNode l2) {
        int len1 = listLength(l1);
        int len2 = listLength(l2);

        if (len1 > len2) {
            l2 = appendZeros(l2, len1 - len2);
        } else if (len2 > len1) {
            l1 = appendZeros(l1, len2 - len1);
        }

        PartialSum partialSum = addListsInOrderHelper(l1, l2);

        if (partialSum.carry == 0) {
            return partialSum.sum;
        }

        return new LinkedListNode(partialSum.carry, partialSum.sum);
    }

    private LinkedListNode appendZeros(LinkedListNode l, int zeros) {
        while (zeros > 0) {
            l = new LinkedListNode(0, l);
            zeros--;
        }

        return l;
    }

    private int listLength(LinkedListNode l) {
        int len = 0;
        while (l != null) {
            l = l.getNext();
            len++;
        }

        return len;
    }

    private PartialSum addListsInOrderHelper(LinkedListNode l1, LinkedListNode l2) {
        if (l1 == null && l2 == null) {
            return new PartialSum();
        }

        PartialSum next = addListsInOrderHelper(l1.getNext(), l2.getNext());

        int val = l1.getVal() + l2.getVal() + next.carry;

        next.sum = insertBefore(next.sum, val % 10);
        next.carry = val / 10;

        return next;
    }

    private LinkedListNode insertBefore(LinkedListNode sum, int val) {
        return new LinkedListNode(val, sum);
    }

    public boolean isPalindrome(LinkedListNode l) {
        Stack<Integer> stk = new Stack<>();
        int len = listLength(l);

        for (int i = 0; i < len / 2; i++) {
            stk.push(l.getVal());
            l = l.getNext();
        }

        if ((len % 2) != 0) {
            l = l.getNext();
        }

        while (!stk.isEmpty()) {
            if (stk.pop() != l.getVal()) {
                return false;
            }
            l = l.getNext();
        }

        return true;
    }
}
