package com.algorithm.multisteptransfer;

import java.util.Arrays;
import java.util.List;

public class MultiStepTransferApp {
    public static void main(String[] args) {
        BankAccount accountA = new BankAccount("id_101", 50);
        BankAccount accountB = new BankAccount("id_102", 100);
        BankAccount accountC = new BankAccount("id_103");
        List<Transaction> transactions =
                Arrays.asList(
                        new Transaction(30, TransactionType.ADD, accountA),
                        new Transaction(20, TransactionType.SUBTRACT, accountB),
                        new Transaction(30, TransactionType.SUBTRACT, accountC)
                );
        boolean success = MultiStepTransfer.processTransactions(transactions);
        System.out.println(success);
        System.out.println(accountA);
        System.out.println(accountB);
        System.out.println(accountC);
        System.out.println("/////////////////");
        transactions =
                Arrays.asList(
                        new Transaction(30, TransactionType.ADD, accountA),
                        new Transaction(20, TransactionType.SUBTRACT, accountB),
                        new Transaction(30, TransactionType.ADD, accountC)
                );
        success = MultiStepTransfer.processTransactions(transactions);
        System.out.println(success);
        System.out.println(accountA);
        System.out.println(accountB);
        System.out.println(accountC);
        System.out.println("/////////////////");
        transactions =
                Arrays.asList(
                        new Transaction(30, TransactionType.ADD, accountA),
                        new Transaction(100, TransactionType.SUBTRACT, accountB),
                        new Transaction(30, TransactionType.SUBTRACT, accountC)
                );
        success = MultiStepTransfer.processTransactions(transactions);
        System.out.println(success);
        System.out.println(accountA);
        System.out.println(accountB);
        System.out.println(accountC);
        System.out.println("/////////////////");
    }
}
