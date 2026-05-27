package com.algorithm.crackingthecodinginterview;

import com.algorithm.crackingthecodinginterview.chapter3.minstack.MinStack;
import com.algorithm.crackingthecodinginterview.chapter3.multiplestacksinone.EmptyStackException;
import com.algorithm.crackingthecodinginterview.chapter3.multiplestacksinone.FullStackException;
import com.algorithm.crackingthecodinginterview.chapter3.multiplestacksinone.MultipleStacksInOne;
import com.algorithm.crackingthecodinginterview.chapter3.multiplestacksinone.OnlyNStacksException;
import com.algorithm.crackingthecodinginterview.chapter3.queueviastacks.QueueViaStacks;
import com.algorithm.crackingthecodinginterview.chapter3.sortstack.SortStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Stack;

public class Chapter3Test {
    @Test
    public void multipleInOne() throws FullStackException, OnlyNStacksException, EmptyStackException {
        MultipleStacksInOne multipleStacksInOne = new MultipleStacksInOne(3, 5);
        multipleStacksInOne.push(2, 9);
        Assertions.assertEquals(9, multipleStacksInOne.peek(2));
        try {
            multipleStacksInOne.peek(1);
            Assertions.fail();
        } catch (EmptyStackException esx) {
            System.out.println(esx.getMessage());
        }
        Assertions.assertEquals(9, multipleStacksInOne.pop(2));
        try {
            multipleStacksInOne.peek(2);
            Assertions.fail();
        } catch (EmptyStackException esx) {
            System.out.println(esx.getMessage());
        }
        multipleStacksInOne.push(2, 1);
        multipleStacksInOne.push(2, 2);
        multipleStacksInOne.push(2, 3);
        try {
            multipleStacksInOne.push(2, 4);
            Assertions.fail();
        } catch (FullStackException esx) {
            System.out.println(esx.getMessage());
        }
    }

    @Test
    public void minStack() {
        MinStack<Integer> minStack = new MinStack<>();
        minStack.push(5);
        Assertions.assertEquals(5, minStack.min());
        minStack.push(6);
        Assertions.assertEquals(5, minStack.min());
        minStack.push(3);
        Assertions.assertEquals(3, minStack.min());
        minStack.push(7);
        Assertions.assertEquals(3, minStack.min());
        minStack.push(2);
        Assertions.assertEquals(2, minStack.min());
        minStack.pop();
        Assertions.assertEquals(3, minStack.min());
        minStack.pop();
        minStack.pop();
        Assertions.assertEquals(5, minStack.min());
        minStack.pop();
        minStack.pop();
        Assertions.assertNull(minStack.min());
    }

    @Test
    public void queueVaStacks() {
        QueueViaStacks queueViaStacks = new QueueViaStacks();
        queueViaStacks.add(2);
        queueViaStacks.add(13);
        queueViaStacks.add(-7);
        Assertions.assertEquals(2, queueViaStacks.poll());
        queueViaStacks.add(25);
        queueViaStacks.add(-110);
        queueViaStacks.add(0);
        Assertions.assertEquals(13, queueViaStacks.poll());
        Assertions.assertEquals(-7, queueViaStacks.peek());
        queueViaStacks.add(19);
        queueViaStacks.add(29);
        queueViaStacks.add(3);
        Assertions.assertEquals(-7, queueViaStacks.poll());
    }

    @Test
    public void sortStack() {
        Stack<Integer> stk = populateStack();
        SortStack<Integer> sortStack = new SortStack<>();
        System.out.println("Before sorting: " + stk);
        System.out.println("After sorting Desc: " + sortStack.sortStackDesc(stk));
        stk = populateStack();
        System.out.println("After sorting Asc: " + sortStack.sortStackAsc(stk));
    }

    private static Stack<Integer> populateStack() {
        Stack<Integer> stk = new Stack<>();
        stk.push(2);
        stk.push(13);
        stk.push(-7);
        stk.push(25);
        stk.push(-110);
        stk.push(0);
        stk.push(19);
        stk.push(29);
        stk.push(3);
        return stk;
    }
}
