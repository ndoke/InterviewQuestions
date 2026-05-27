package com.algorithm.citi;

class Transaction {
    int transactionId;
    int accountId;
    TransactionType type;
    double amount;     // Always positive in inputs
    long timestampSec; // Unix-style seconds (monotonic for tests)

    Transaction(int transactionId, int accountId, TransactionType type, double amount, long timestampSec) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.timestampSec = timestampSec;
    }
}
