package com.algorithm.stripe.currencybalancer;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * At Stripe we keep track of where the money is and move money between
 * bank accounts to make sure their balances are not below some threshold.
 * This is for operational and regulatory reasons, e.g. we should have
 * enough funds to pay out to our users, and we are legally required to
 * separate our users' funds from our own. This interview question is a
 * simplified version of a real-world problem we have here.
 * Let's say there are at most 500 bank accounts, some of their balances
 * are above 100 and some are below. How do you move money between them
 * so that they all have at least 100?
 * Just to be clear we are not looking for the optimal solution,
 * but a working one.
 *
 * Example input:
 *
 * AU: 80
 * US: 140
 * MX: 110
 * SG: 120
 * FR: 70
 *
 * Output:
 *
 * from: US, to: AU, amount: 20
 * from: US, to: FR, amount: 20
 * from: MX, to: FR, amount: 10
 * Potential follow ups/parts (in no specific order):
 *         1. (Practical) If this code will be used to move millions of
 *         dollars in production, how would you change it? Specifically,
 *         we just eyeballed that our end goal of each balance >= 100 is met,
 *         how would you check that in reality? What should we do if the check fails?
 *         2. (Algorithmic) Do it in the minimum number of moves.
 *         For the input data in the original prompt:
 *          from: US, to: FR, amount: 30
 *          from: SG, to: AU, amount: 20
 */
public class CurrencyBalancer {
    public void balanceCurrency(List<CurrencyDetails> currencyDetails) {
        PriorityQueue<CurrencyDetails> currencyMinHeap =
                new PriorityQueue<>(Comparator.comparingInt(CurrencyDetails::getValue));
        PriorityQueue<CurrencyDetails> currencyMaxHeap =
                new PriorityQueue<>(Comparator.comparingInt(CurrencyDetails::getValue).reversed());
        for (CurrencyDetails currencyDetail : currencyDetails) {
            currencyMaxHeap.add(currencyDetail);
            currencyMinHeap.add(currencyDetail);
        }

        while (true) {
            if (Objects.requireNonNull(currencyMaxHeap.peek()).getValue() < 100
                    || Objects.requireNonNull(currencyMinHeap.peek()).getValue() >= 100) {
                break;
            }
            CurrencyDetails maxCurrencyDetail = currencyMaxHeap.poll();
            CurrencyDetails minCurrencyDetail = currencyMinHeap.poll();
            int max = Objects.requireNonNull(maxCurrencyDetail).getValue();
            int min = Objects.requireNonNull(minCurrencyDetail).getValue();
            int deduction = Math.min(100 - min, max - 100);
            maxCurrencyDetail.setValue(max - deduction);
            minCurrencyDetail.setValue(min + deduction);
            currencyMaxHeap.add(maxCurrencyDetail);
            currencyMinHeap.add(minCurrencyDetail);
            System.out.println("from: " + maxCurrencyDetail.getName()
                    + " to: " + minCurrencyDetail.getName()
                    + " amount: " + deduction);
        }
    }
}
