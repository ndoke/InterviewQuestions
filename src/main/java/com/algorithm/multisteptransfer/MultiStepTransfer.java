package com.algorithm.multisteptransfer;

import java.util.List;

public class MultiStepTransfer {
    public static boolean processTransactions(List<Transaction> transactions) {
        boolean success = true;
        int i = 0;
        while (i < transactions.size() && success) {
            Transaction transaction = transactions.get(i);
            int amount = transaction.getAmount();
            BankAccount account = transaction.getAccount();
            TransactionType transactionType = transaction.getTransactionType();

            switch (transactionType) {
                case ADD:
                    account.addAmount(amount);
                    break;
                case SUBTRACT:
                    success = account.deductAmount(amount);
                    break;
            }
            i++;
        }

        if (success) {
            return true;
        } else {
            i -= 2;
            while (i >= 0) {
                Transaction transaction = transactions.get(i);
                TransactionType transactionType = transaction.getTransactionType();
                BankAccount bankAccount = transaction.getAccount();
                int amount = transaction.getAmount();
                switch (transactionType) {
                    case ADD:
                        bankAccount.deductAmount(amount);
                        break;
                    case SUBTRACT:
                        bankAccount.addAmount(amount);
                        break;
                }
                i--;
            }
        }

        return false;
    }
}
