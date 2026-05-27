package com.algorithm.capital.one;

enum OfferCycleInclusion {
    INCLUDE("Include"),
    EXCLUDE("Exclude");

    private String inclusionStatus;

    private OfferCycleInclusion(String inclusionStatus) {
        this.inclusionStatus = inclusionStatus;
    }
}
