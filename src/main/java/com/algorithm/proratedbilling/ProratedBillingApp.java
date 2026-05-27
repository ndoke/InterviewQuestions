package com.algorithm.proratedbilling;

public class ProratedBillingApp {
    public static void main(String[] args) {
        ProratedBilling proratedBilling = new ProratedBilling();
        System.out.println(proratedBilling.calculateProratedBill(20, Plans.BASIC, Plans.INTERMEDIATE));
    }
}
