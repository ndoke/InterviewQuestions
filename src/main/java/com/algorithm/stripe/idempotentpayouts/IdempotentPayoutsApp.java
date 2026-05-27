package com.algorithm.stripe.idempotentpayouts;

public class IdempotentPayoutsApp {
    public static void main(String[] args) {
        IdempotentPayouts idempotentPayouts = new IdempotentPayouts();
        System.out.println("Accounts data: " + Bankdata.accountsData);
        System.out.println("Requests processed: " + Bankdata.processedRequests);
        System.out.println("Number of requests processed: " + Bankdata.processedRequests.size());
        idempotentPayouts.performIdempotentPayout(
                new Request("abc-123", 100, "id_101"));
        System.out.println("Accounts data: " + Bankdata.accountsData);
        System.out.println("Requests processed: " + Bankdata.processedRequests);
        System.out.println("Number of requests processed: " + Bankdata.processedRequests.size());
        idempotentPayouts.performIdempotentPayout(
                new Request("abc-123", 100, "id_101"));
        System.out.println("Accounts data: " + Bankdata.accountsData);
        System.out.println("Requests processed: " + Bankdata.processedRequests);
        System.out.println("Number of requests processed: " + Bankdata.processedRequests.size());
        idempotentPayouts.performIdempotentPayout(
                new Request("abc-124", -100, "id_101"));
        System.out.println("Accounts data: " + Bankdata.accountsData);
        System.out.println("Requests processed: " + Bankdata.processedRequests);
        System.out.println("Number of requests processed: " + Bankdata.processedRequests.size());
        idempotentPayouts.performIdempotentPayout(
                new Request("abc-125", -100, "id_101"));
        System.out.println("Accounts data: " + Bankdata.accountsData);
        System.out.println("Requests processed: " + Bankdata.processedRequests);
        System.out.println("Number of requests processed: " + Bankdata.processedRequests.size());
        idempotentPayouts.performIdempotentPayout(
                new Request("abc-126", -100, "id_101"));
        System.out.println("Accounts data: " + Bankdata.accountsData);
        System.out.println("Requests processed: " + Bankdata.processedRequests);
        System.out.println("Number of requests processed: " + Bankdata.processedRequests.size());
    }
}
