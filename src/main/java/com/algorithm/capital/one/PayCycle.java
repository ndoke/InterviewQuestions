package com.algorithm.capital.one;

import java.util.Date;

class PayCycle {
    private long cycleNum;
    private Date startDate;
    private Date endDate;
    private int pastDueIndicator;

    public PayCycle(long cycleNum, Date startDate, Date endDate, int pastDueIndicator) {
        this.cycleNum = cycleNum;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pastDueIndicator = pastDueIndicator;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setPastDueIndicator(int pastDueIndicator) {
        this.pastDueIndicator = pastDueIndicator;
    }

    @Override
    public String toString() {
        return "CycleNum: " + cycleNum + " PastDueIndicator: " + pastDueIndicator;
    }
}
