package com.algorithm.crackingthecodinginterview;

public class LinkedListNode {
    private int val;
    private LinkedListNode next;

    public LinkedListNode(int val, LinkedListNode next) {
        this.val = val;
        this.next = next;
    }

    public int getVal() {
        return val;
    }

    public LinkedListNode getNext() {
        return next;
    }

    public void setNext(LinkedListNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "LinkedListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }
}
