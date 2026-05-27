package com.algorithm.proratedbilling;

public enum Plans {
    BASIC(30),
    INTERMEDIATE(60),
    ADVANCED(120);

    private final int cost;

    Plans(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}
