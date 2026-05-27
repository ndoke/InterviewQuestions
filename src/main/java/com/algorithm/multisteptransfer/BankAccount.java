package com.algorithm.multisteptransfer;

public class BankAccount {
    private String id;
    private int balance;

    public BankAccount(String id) {
        this.id = id;
        this.balance = 0;
    }

    public BankAccount(String id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public void addAmount(int amount) {
        this.balance += amount;
    }

    public boolean deductAmount(int amount) {
        if (this.balance - amount < 0) {
            return false;
        }
        this.balance -= amount;
        return true;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                '}';
    }
}
