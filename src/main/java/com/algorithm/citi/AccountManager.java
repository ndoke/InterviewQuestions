package com.algorithm.citi;

import java.util.*;

class AccountManager {
    Map<Integer, Account> accounts = new HashMap<>();
    List<Transaction> transactions = new ArrayList<>();

    void addAccount(Account account) {
        accounts.put(account.accountId, account);
    }

    void addTransaction(Transaction tx) {
        // Assume input transactions always refer to valid accounts for this question.
        transactions.add(tx);
    }

    // Returns the current balance for the given accountId.
    double getBalance(int accountId) {
        double balance = 0.0;
        for (Transaction tx : transactions) {
            if (tx.accountId == accountId) {
                if (tx.type == TransactionType.CREDIT) {
                    balance += tx.amount;
                } else if (tx.type == TransactionType.DEBIT) {
                    balance -= tx.amount;
                }
            }
        }
        return balance;
    }

    Map<Integer, Double> getAverageTransactionAmountByAccount() {
        Map<Integer, Double> result = new HashMap<>();
        Map<Integer, Integer> counts = new HashMap<>();
        for (Transaction transaction : transactions) {
            int accountId = transaction.accountId;
            double amount = transaction.amount;
            double totalAmount = result.getOrDefault(accountId, 0.0d);
            result.put(accountId, totalAmount + amount);
            int totalNumberOfTransactions = counts.getOrDefault(accountId, 0);
            counts.put(accountId, totalNumberOfTransactions + 1);
        }

        for (Integer accountId : result.keySet()) {
            double amount = result.get(accountId);
            int transactions = counts.get(accountId);
            result.put(accountId, amount / transactions);
        }

        return result;
    }

    Map<Integer, Double> getTransactionFees() {
        Collections.sort(transactions, (a, b) -> {
            return Long.compare(a.timestampSec, b.timestampSec);
        });

        Map<Integer, Double> fees = new HashMap<>();
        Map<Integer, Integer> counts = new HashMap<>();
        for (Transaction transaction : transactions) {
            int accountId = transaction.accountId;
            int totalNumberOfTransactions = counts.getOrDefault(accountId, 0);
            if (totalNumberOfTransactions >= 3) {
                double totalFee = fees.getOrDefault(accountId, 0.0d);
                switch (transaction.type) {
                    case CREDIT:
                        totalFee += 1;
                        break;
                    case DEBIT:
                        totalFee += 2;
                        break;
                }
                fees.put(accountId, totalFee);
            } else {
                counts.put(accountId, totalNumberOfTransactions + 1);
            }
        }

        return fees;
    }

    public List<Integer> getSuspiciousAccounts() {
        //TODO implement this later
        return Collections.emptyList();
    }
}
