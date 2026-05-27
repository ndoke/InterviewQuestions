package com.algorithm.stripe.idempotentpayouts;

public class AccountInfo {
    private String accountId;
    private int balance;

    public AccountInfo(String accountId, int balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "accountId='" + accountId + '\'' +
                ", balance=" + balance +
                '}';
    }
}
