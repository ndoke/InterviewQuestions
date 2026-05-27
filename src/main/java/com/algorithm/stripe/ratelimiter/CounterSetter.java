package com.algorithm.stripe.ratelimiter;

public class CounterSetter implements Runnable {
    @Override
    public void run() {
        System.out.println("Current time: " + System.currentTimeMillis());
//        if (System.currentTimeMillis() % 10_000 == 0) {
            Counter counter = Counter.getInstance();
            counter.resetCount();
            System.out.println("Counter reset! Count value: " + counter.getCount());
//        }
    }
}
