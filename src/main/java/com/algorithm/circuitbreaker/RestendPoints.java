package com.algorithm.circuitbreaker;

public class RestendPoints {
    private int maxRetries;

    public RestendPoints() {
        maxRetries = CircuitBreakerRegistry.THRESHOLD;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public boolean someGetRequest(boolean shouldFail) {
        setMaxRetries(CircuitBreakerRegistry.THRESHOLD);
        boolean daoResult = false;
        while (maxRetries > 0) {
            daoResult = someDaoRequest(shouldFail);
            if (daoResult) {
                break;
            }
            System.out.println("Request failed, retrying..! Retry num: "
                    + (CircuitBreakerRegistry.THRESHOLD - maxRetries + 1));
            maxRetries--;
            if (maxRetries == 0) {
                System.out.println("Request failed. No more retries left.");
                return daoResult;
            }
        }

        System.out.println("Request passed!");
        return daoResult;
    }

    private boolean someDaoRequest(boolean shouldFail) {
        return shouldFail;
    }
}
