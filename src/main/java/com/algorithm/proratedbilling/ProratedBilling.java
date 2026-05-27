package com.algorithm.proratedbilling;

public class ProratedBilling {
    private static final int DAYS_IN_A_MONTH = 30;

    public int calculateProratedBill(int day, Plans olderPlan, Plans newPlan) {
        int billPaid = olderPlan.getCost();
        int actualBill = (day * olderPlan.getCost()
                + (DAYS_IN_A_MONTH - day) * newPlan.getCost()) / DAYS_IN_A_MONTH;

        return actualBill - billPaid;
    }
}
