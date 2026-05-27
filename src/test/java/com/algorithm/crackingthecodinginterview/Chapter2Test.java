package com.algorithm.crackingthecodinginterview;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Chapter2Test {
    private static Chapter2 chapter2;

    @BeforeAll
    public static void setUp() {
        chapter2 = new Chapter2();
    }

    @Test
    public void deleteDups() {
        LinkedListNode fourth = new LinkedListNode(4, null);
        LinkedListNode thirdDup = new LinkedListNode(3, fourth);
        LinkedListNode third = new LinkedListNode(3, thirdDup);
        LinkedListNode second = new LinkedListNode(2, third);
        LinkedListNode first = new LinkedListNode(1, second);
        System.out.println(first);
        chapter2.deleteDups(first);
        System.out.println(first);
    }

    @Test
    public void kToLast() {
        LinkedListNode tenth = new LinkedListNode(10, null);
        LinkedListNode ninth = new LinkedListNode(9, tenth);
        LinkedListNode eighth = new LinkedListNode(8, ninth);
        LinkedListNode seventh = new LinkedListNode(7, eighth);
        LinkedListNode sixth = new LinkedListNode(6, seventh);
        LinkedListNode fifth = new LinkedListNode(5, sixth);
        LinkedListNode fourth = new LinkedListNode(4, fifth);
        LinkedListNode third = new LinkedListNode(3, fourth);
        LinkedListNode second = new LinkedListNode(2, third);
        LinkedListNode first = new LinkedListNode(1, second);
        System.out.println(first);
        LinkedListNode result = chapter2.kToLast(first, 3);
        System.out.println(result);
    }

    @Test
    public void addListsReverse() {
        LinkedListNode l1 =
                new LinkedListNode(7, new LinkedListNode(1, new LinkedListNode(6, null)));
        LinkedListNode l2 =
                new LinkedListNode(5, new LinkedListNode(9, new LinkedListNode(2, new LinkedListNode(1, null))));
        System.out.println(chapter2.addListsReverse(l1, l2));
    }

    @Test
    public void addListsInOrder() {
        LinkedListNode l1 =
                new LinkedListNode(6, new LinkedListNode(1, new LinkedListNode(7, null)));
        LinkedListNode l2 =
                new LinkedListNode(1, new LinkedListNode(2, new LinkedListNode(9, new LinkedListNode(5, null))));
        System.out.println(chapter2.addListsInOrder(l1, l2));
    }

    @Test
    public void isPalindromeOdd() {
        LinkedListNode seventh = new LinkedListNode(1, null);
        LinkedListNode sixth = new LinkedListNode(2, seventh);
        LinkedListNode fifth = new LinkedListNode(3, sixth);
        LinkedListNode fourth = new LinkedListNode(4, fifth);
        LinkedListNode third = new LinkedListNode(3, fourth);
        LinkedListNode second = new LinkedListNode(2, third);
        LinkedListNode first = new LinkedListNode(1, second);
        Assertions.assertTrue(chapter2.isPalindrome(first));
    }

    @Test
    public void isPalindromeEven() {
        LinkedListNode seventh = new LinkedListNode(1, null);
        LinkedListNode sixth = new LinkedListNode(2, seventh);
        LinkedListNode fifth = new LinkedListNode(3, sixth);
        LinkedListNode third = new LinkedListNode(3, fifth);
        LinkedListNode second = new LinkedListNode(2, third);
        LinkedListNode first = new LinkedListNode(1, second);
        Assertions.assertTrue(chapter2.isPalindrome(first));
    }

    @Test
    public void isNotPalindromeOdd() {
        LinkedListNode seventh = new LinkedListNode(1, null);
        LinkedListNode sixth = new LinkedListNode(2, seventh);
        LinkedListNode fifth = new LinkedListNode(5, sixth);
        LinkedListNode fourth = new LinkedListNode(4, fifth);
        LinkedListNode third = new LinkedListNode(3, fourth);
        LinkedListNode second = new LinkedListNode(2, third);
        LinkedListNode first = new LinkedListNode(1, second);
        Assertions.assertFalse(chapter2.isPalindrome(first));
    }

    @Test
    public void isNotPalindromeEven() {
        LinkedListNode seventh = new LinkedListNode(1, null);
        LinkedListNode sixth = new LinkedListNode(2, seventh);
        LinkedListNode fifth = new LinkedListNode(4, sixth);
        LinkedListNode third = new LinkedListNode(3, fifth);
        LinkedListNode second = new LinkedListNode(2, third);
        LinkedListNode first = new LinkedListNode(1, second);
        Assertions.assertFalse(chapter2.isPalindrome(first));
    }
}
