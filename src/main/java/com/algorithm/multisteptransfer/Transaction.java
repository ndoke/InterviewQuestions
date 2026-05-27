package com.algorithm.multisteptransfer;

public class Transaction {
    private int amount;
    private TransactionType transactionType;
    private BankAccount account;

    public Transaction(int amount,
                       TransactionType transactionType,
                       BankAccount account) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.account = account;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }
}
