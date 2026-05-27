package com.algorithm.crackingthecodinginterview.chapter3.queueviastacks;

import java.util.Stack;

public class QueueViaStacks {
    private final Stack<Integer> oldest;
    private final Stack<Integer> newest;

    public QueueViaStacks() {
        this.oldest = new Stack<>();
        this.newest = new Stack<>();
    }

    public void add(int n) {
        newest.add(n);
    }

    public int poll() {
        shiftStacks();
        return oldest.pop();
    }

    public int peek() {
        shiftStacks();
        return oldest.peek();
    }

    private void shiftStacks() {
        if (oldest.isEmpty()) {
            while (!newest.isEmpty()) {
                oldest.add(newest.pop());
            }
        }
    }
}
