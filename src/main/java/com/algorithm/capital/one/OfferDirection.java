package com.algorithm.capital.one;

enum OfferDirection {
    FORWARD("Forward"),
    BACKWARD("Backward");

    private String direction;

    private OfferDirection(String direction) {
        this.direction = direction;
    }
}
