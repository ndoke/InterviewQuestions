package com.algorithm.stripe.ratelimiter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RateLimiterApp {
    public static void main(String[] args) {
        Thread countClearer = new Thread(new CounterSetter(), "Counter setter");
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(countClearer, 0, 1, TimeUnit.MINUTES);

        RateLimiter rateLimiter = new RateLimiter();
        for (int i = 0; i < 10; i++) {
            try {
                rateLimiter.processRequest();
                System.out.println("Counter: " + Counter.getInstance().getCount());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        try {
            Thread.sleep(60_000);
        } catch (InterruptedException ignored) {

        }
        for (int i = 0; i < 10; i++) {
            try {
                rateLimiter.processRequest();
                System.out.println("Counter: " + Counter.getInstance().getCount());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
