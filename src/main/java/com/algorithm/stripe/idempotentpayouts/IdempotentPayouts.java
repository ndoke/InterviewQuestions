package com.algorithm.stripe.idempotentpayouts;

import static com.algorithm.stripe.idempotentpayouts.Bankdata.accountsData;
import static com.algorithm.stripe.idempotentpayouts.Bankdata.processedRequests;

public class IdempotentPayouts {
    public boolean performIdempotentPayout(Request paymentProcessingRequest) {
        String requestKey = paymentProcessingRequest.getKey();

        if (processedRequests.containsKey(requestKey)) {
            System.out.println("Request already processed");
            return true;
        }

        String accountId = paymentProcessingRequest.getAccountId();
        int transaction = paymentProcessingRequest.getTransaction();

        if (accountsData.containsKey(accountId)) {
            AccountInfo accountInfo = accountsData.get(accountId);
            int balance = accountInfo.getBalance();
            if (transaction < 0 && balance < -transaction) {
                System.out.println("Insufficient balance");
                return false;
            }
            accountInfo.setBalance(balance + transaction);
            processedRequests.put(requestKey, paymentProcessingRequest);
            System.out.println("Transaction succeeded!");
            return true;
        } else {
            System.out.println("Account does not exist");
            return false;
        }
    }
}
