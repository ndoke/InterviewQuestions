package com.algorithm.stripe.ratelimiter;

public class Counter {
    private int count = 0;
    private static Counter counter;
    static final int MAX_REQUESTS = 5;

    private Counter(int count) {
        this.count = count;
    }

    public static Counter getInstance() {
        if (counter == null) {
            counter = new Counter(0);
        }

        return counter;
    }

    public int getCount() {
        return count;
    }

    public void incrementCounter() {
        count++;
    }

    public void resetCount() {
        System.out.println("Request counter reset!");
        count = 0;
    }
}
