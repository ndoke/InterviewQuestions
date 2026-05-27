package com.algorithm.stripe.currencybalancer;

import java.util.Arrays;
import java.util.List;

public class CurrencyBalancerApp {
    public static void main(String[] args) {
        CurrencyBalancer currencyBalancer = new CurrencyBalancer();
        List<CurrencyDetails> currencyDetails =
                Arrays.asList(
                        new CurrencyDetails(CurrencyName.AU, 80),
                        new CurrencyDetails(CurrencyName.US, 140),
                        new CurrencyDetails(CurrencyName.MX, 110),
                        new CurrencyDetails(CurrencyName.SG, 120),
                        new CurrencyDetails(CurrencyName.FR, 70));
        currencyBalancer.balanceCurrency(currencyDetails);
    }
}
