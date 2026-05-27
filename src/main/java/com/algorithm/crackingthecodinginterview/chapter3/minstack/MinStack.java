package com.algorithm.crackingthecodinginterview.chapter3.minstack;

import java.util.Stack;

public class MinStack<T> extends Stack<T> {
    private Stack<T> min;

    public MinStack() {
        min = new Stack<>();
    }

    @Override
    public T push(T item) {
        if (min.isEmpty() || min.peek() instanceof Number
                && (Integer) item < (Integer) min()) {
            min.push(item);
        }
        return super.push(item);
    }

    @Override
    public synchronized T pop() {
        T val = super.pop();
        if (val instanceof Number
                && !min.isEmpty()
                && val == min()) {
            min.pop();
        }
        return val;
    }

    public T min() {
        if (!min.isEmpty()) {
            return min.peek();
        }

        return null;
    }
}
