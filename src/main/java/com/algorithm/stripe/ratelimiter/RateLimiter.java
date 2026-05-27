package com.algorithm.stripe.ratelimiter;

public class RateLimiter {
    public boolean shouldRequestBeProcessed() {
        Counter counter = Counter.getInstance();
        if (counter.getCount() >= Counter.MAX_REQUESTS) {
            return false;
        }

        counter.incrementCounter();
        return true;
    }

    public void processRequest() throws Exception {
        if (!shouldRequestBeProcessed()) {
            throw new Exception("Too Many Requests!");
        }

        System.out.println("Request processed.");
    }
}
