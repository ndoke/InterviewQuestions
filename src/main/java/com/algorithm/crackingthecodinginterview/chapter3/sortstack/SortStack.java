package com.algorithm.crackingthecodinginterview.chapter3.sortstack;

import java.util.Stack;

public class SortStack<T extends Comparable<? super T>> {
    public Stack<T> sortStackDesc(Stack<T> stk) {
        Stack<T> temp = new Stack<>();

        while (!stk.isEmpty()) {
            T val = stk.pop();
            while (!temp.isEmpty() && temp.peek().compareTo(val) < 0) {
                stk.push(temp.pop());
            }
            temp.push(val);
        }

        return temp;
    }

    public Stack<T> sortStackAsc(Stack<T> stk) {
        Stack<T> temp = new Stack<>();

        while (!stk.isEmpty()) {
            T val = stk.pop();
            while (!temp.isEmpty() && temp.peek().compareTo(val) > 0) {
                stk.push(temp.pop());
            }
            temp.push(val);
        }

        return temp;
    }
}
