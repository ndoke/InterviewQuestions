package com.algorithm.circuitbreaker;

public class CircuitBreakerApp {
    public static void main(String[] args) {
        RestendPoints restendPoints = new RestendPoints();
        restendPoints.someGetRequest(true);
        restendPoints.someGetRequest(false);
    }
}
