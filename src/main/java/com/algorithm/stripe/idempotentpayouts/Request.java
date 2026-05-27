package com.algorithm.stripe.idempotentpayouts;

public class Request {
    private String key;
    private int transaction;
    private String accountId;

    public Request(String key, int transaction, String accountId) {
        this.key = key;
        this.transaction = transaction;
        this.accountId = accountId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getTransaction() {
        return transaction;
    }

    public void setTransaction(int transaction) {
        this.transaction = transaction;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Request{" +
                "key='" + key + '\'' +
                ", transaction=" + transaction +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}
