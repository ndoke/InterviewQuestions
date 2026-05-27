package com.algorithm.crackingthecodinginterview.chapter3.multiplestacksinone;

import java.util.Arrays;

public class MultipleStacksInOne {
    private final int numStacks;
    private final int capacity;
    private final int[] stk;
    private final int[] pointers;

    public MultipleStacksInOne(int capacity, int numStacks) {
        this.capacity = capacity;
        this.numStacks = numStacks;
        stk = new int[this.capacity * this.numStacks];
        pointers = new int[this.numStacks];
        Arrays.fill(pointers, -1);
    }

    public void push(int stkNum, int val) throws OnlyNStacksException, FullStackException {
        if (stkNum >= this.numStacks) {
            throw new OnlyNStacksException("Stack number " + stkNum + " does not exist.");
        }

        if (pointers[stkNum] >= this.capacity - 1) {
            throw new FullStackException("Stack number " + stkNum + " is full.");
        }

        stk[++pointers[stkNum] + this.capacity * stkNum] = val;
    }

    public int pop(int stkNum) throws EmptyStackException, OnlyNStacksException {
        if (stkNum >= this.numStacks) {
            throw new OnlyNStacksException("Stack number " + stkNum + " does not exist.");
        }

        if (pointers[stkNum] < 0) {
            throw new EmptyStackException("Stack number " + stkNum + " is empty.");
        }

        int val = stk[pointers[stkNum] + this.capacity * stkNum];
        pointers[stkNum]--;
        return val;
    }

    public int peek(int stkNum) throws EmptyStackException, OnlyNStacksException {
        if (stkNum >= this.numStacks) {
            throw new OnlyNStacksException("Stack number " + stkNum + " does not exist.");
        }

        if (pointers[stkNum] < 0) {
            throw new EmptyStackException("Stack number " + stkNum + " is empty.");
        }

        return stk[pointers[stkNum] + this.capacity * stkNum];
    }
}
