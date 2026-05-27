package com.algorithm.capital.one;

import java.util.Date;

class Offer {
    private int offerId;
    private Date offerStartDate;
    private int offerValidity;
    private OfferDirection offerDirection;
    private OfferCycleInclusion offerCycleInclusion;

    public Offer(int offerId, Date offerStartDate, int offerValidity, OfferDirection offerDirection, OfferCycleInclusion offerCycleInclusion) {
        this.offerId = offerId;
        this.offerStartDate = offerStartDate;
        this.offerValidity = offerValidity;
        this.offerDirection = offerDirection;
        this.offerCycleInclusion = offerCycleInclusion;
    }

    public Date getOfferStartDate() {
        return offerStartDate;
    }

    public OfferDirection getOfferDirection() {
        return offerDirection;
    }

    public int getOfferValidity() {
        return offerValidity;
    }

    public OfferCycleInclusion getOfferCycleInclusion() {
        return offerCycleInclusion;
    }
}
