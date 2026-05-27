package com.algorithm.capital.one;

import java.util.List;

class Customer {
    private long customerId;
    private List<PayCycle> payCycles;

    public Customer(long customerId, List<PayCycle> payCycles) {
        this.customerId = customerId;
        this.payCycles = payCycles;
    }

    public List<PayCycle> getPayCycles() {
        return payCycles;
    }
}
