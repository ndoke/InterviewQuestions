package com.algorithm.citi;

import org.junit.Assert;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
    	/*
        We are developing a payment transaction monitoring system that tracks accounts and their transactions.
        The system can compute each account's current balance and basic statistics.

        Definitions:
        * An "account" has a unique accountId and an owner name.
        * A "transaction" represents money moving in or out of an account.
          - CREDIT increases the account balance.
          - DEBIT decreases the account balance.
        * "AccountManager" manages accounts and transactions and provides balance-related methods.

        To begin with, we present you with two tasks:
        1-1) Read through and understand the code below. Please take as much time as necessary, and feel free to run it.
        1-2) The test for AccountManager is not passing due to a bug in the code.
             Make the necessary changes to AccountManager to fix the bug.
    */

    /*
        We are extending our payment transaction monitoring system to support
        basic analytics over transactions.

        For this task, we want to calculate the average transaction amount per account.

        2) Implement the function getAverageTransactionAmountByAccount in AccountManager that returns
        the average transaction amount for each account.

        Requirements:
        - The result should associate each accountId with the average amount of its transactions.
        - Both CREDIT and DEBIT transactions should be considered.
        - Transaction amounts should be treated as absolute values when calculating averages.
        - Accounts with no transactions should not appear in the result.
        - Transactions always refer to valid accounts.

        To assist you in testing this new function, we have provided the
        testGetAverageTransactionAmountByAccount test.
    */

    /*
        We are extending our payment transaction monitoring system to calculate
        transaction fees based on business rules.

        Each account is charged transaction fees as follows:

        - The first 3 transactions for an account are FREE.
        - From the 4th transaction onward:
          - CREDIT transactions cost a flat fee of $1.
          - DEBIT transactions cost a flat fee of $2.

        Transactions must be processed in chronological order (by timestamp).
        Fees are applied per account independently.

        3) Implement getTransactionFees() in AccountManager. The function should
        return a map associating each accountId with the total transaction
        fees charged to that account.

        To assist you in testing this new function, we have provided the
        testGetTransactionFees function.
    */

    /*
        We are adding suspicious activity detection to our payment transaction monitoring system.

        A suspicious account is defined as follows:

        - Consider only DEBIT transactions.
        - A DEBIT transaction is considered "large" if amount >= 50.
        - An account is suspicious if it has 3 or more large DEBIT transactions within ANY 60-second window.

        Notes:
        - Transactions are not guaranteed to be inserted in chronological order.
        - You must process transactions in chronological order (by timestampSec) per account.
        - The window is inclusive of endpoints: a transaction at t and another at t+60 are in the same 60-second window.


        4) Implement getSuspiciousAccounts() in AccountManager. The function should
         return a sorted list of accountIds that have 3 or more large DEBIT
        transactions (amount >= 50) within any 60-second window.
    */
        testGetBalance_basic();
        testGetBalance_multipleAccounts();
        testGetAverageTransactionAmountByAccount();
        testGetTransactionFees();
        testGetSuspiciousAccounts();
        
        
        /*
        We are building a program to manage a food delivery platform. The platform has multiple restaurants,
        customers place orders, and those orders move through statuses:
        PLACED → PREPARING → OUT_FOR_DELIVERY → DELIVERED, or CANCELED.

        Definitions:
        * An "order" has: orderId, restaurantId, customerId, orderValue, distanceKm, status.
        * "OrderManager" manages orders and provides order statistics.

        To begin with, we present you with two tasks:
        1-1) Read through and understand the code below. Feel free to run it.
        1-2) The test for OrderManager is not passing due to a bug in the code.
             Make the necessary changes to OrderManager to fix the bug.
        */
        /*
        We are updating our system to include delivery session information for orders.

        We introduce a Delivery class:
        - Each Delivery has a unique deliveryId
        - startMinute and endMinute represent minutes from the start of the day (same day)
        - duration = endMinute - startMinute

        Add two functions to OrderManager:

        2.1) addDelivery(orderId, delivery):
             Associate a delivery with an order. One order could have multiple deliveries. If the order does not exist, ignore.

        2.2) getAverageDeliveryTimeByRestaurant():
             Compute the average delivery duration (minutes) per restaurantId.
             Count ALL deliveries for that restaurant (across orders).
             Return: Map<Integer, Double> restaurantId -> averageDuration.

        To assist you in testing these new functions, we have provided the `testGetAverageDeliveryTimeByRestaurant` and `assertAlmost` functions.

        */
        /*
        We want to know the delivery fee for each order.

        A delivery fee is computed in three steps:

        * Step 1: Round the order's distance UP to the nearest whole km.
          (1.0 => 1, 1.2 => 2, 2.1 => 3)

        * Step 2: Calculate the base fee:
          2 for the first km,
          +1 per extra km.
          (Example: 1 km => 2, 2 km => 3, 3 km => 4, etc.)

        * Step 3: Apply order value discount:
          - order value >= 50  =>  fee = 0 (free delivery)
          - order value >= 30  =>  fee is 50% off
          - Otherwise  =>  use the base fee

        Example: Order with distance 2.1 km and orderValue 35.0
          roundedKm = 3  =>  base = 4  =>  50% off  =>  fee = 2.0

        Add a function getDeliveryFees in OrderManager that calculates and returns a map from orderId to the corresponding fee.

        To test this new function, the testGetDeliveryFees function will be added.
        */

        testOrderManager();
        testGetAverageDeliveryTimeByRestaurant();
        testGetDeliveryFees();

        System.out.println("All tests passed.");
    }

    private static void assertAlmost1(double expected, double actual, double eps) {
        Assert.assertTrue("Expected " + expected + " but got " + actual, Math.abs(expected - actual) <= eps);
    }

    public static void testGetBalance_basic() {
        System.out.println("Running testGetBalance_basic");
        AccountManager mgr = new AccountManager();
        mgr.addAccount(new Account(1, "Alice"));

        mgr.addTransaction(new Transaction(101, 1, TransactionType.CREDIT, 100.0, 1000));
        mgr.addTransaction(new Transaction(102, 1, TransactionType.DEBIT, 30.0, 1010));
        mgr.addTransaction(new Transaction(103, 1, TransactionType.DEBIT, 20.0, 1020));
        mgr.addTransaction(new Transaction(104, 1, TransactionType.CREDIT, 10.0, 1030));

        // Expected balance: 100 - 30 - 20 + 10 = 60
        assertAlmost1(60.0, mgr.getBalance(1), 0.0001);

        mgr.addTransaction(new Transaction(105, 1, TransactionType.DEBIT, 70.0, 1045));
        assertAlmost1(-10.0, mgr.getBalance(1), 0.0001);
    }

    public static void testGetBalance_multipleAccounts() {
        System.out.println("Running testGetBalance_multipleAccounts");
        AccountManager mgr = new AccountManager();
        mgr.addAccount(new Account(1, "Alice"));
        mgr.addAccount(new Account(2, "Bob"));

        mgr.addTransaction(new Transaction(201, 1, TransactionType.CREDIT, 50.0, 2000));
        mgr.addTransaction(new Transaction(202, 2, TransactionType.CREDIT, 80.0, 2005));
        mgr.addTransaction(new Transaction(203, 1, TransactionType.DEBIT, 10.0, 2010));
        mgr.addTransaction(new Transaction(204, 2, TransactionType.DEBIT, 5.5, 2015));
        mgr.addTransaction(new Transaction(205, 2, TransactionType.DEBIT, 14.5, 2020));

        // Account 1: 50 - 10 = 40
        assertAlmost1(40.0, mgr.getBalance(1), 0.0001);
        // Account 2: 80 - 5.5 - 14.5 = 60
        assertAlmost1(60.0, mgr.getBalance(2), 0.0001);
    }

    public static void testGetAverageTransactionAmountByAccount() {
        System.out.println("Running testGetAverageTransactionAmountByAccount");
        AccountManager mgr = new AccountManager();

        mgr.addAccount(new Account(1, "Alice"));
        mgr.addAccount(new Account(2, "Bob"));
        mgr.addAccount(new Account(3, "Charlie")); // no transactions

        // Account 1: 100, 30, 20, 10 => avg = 160/4 = 40
        mgr.addTransaction(new Transaction(101, 1, TransactionType.CREDIT, 100.0, 1000));
        mgr.addTransaction(new Transaction(102, 1, TransactionType.DEBIT, 30.0, 1010));
        mgr.addTransaction(new Transaction(103, 1, TransactionType.DEBIT, 20.0, 1020));
        mgr.addTransaction(new Transaction(104, 1, TransactionType.CREDIT, 10.0, 1030));

        // Account 2: 80, 5.5, 14.5 => avg = 100/3 = 33.333...
        mgr.addTransaction(new Transaction(201, 2, TransactionType.CREDIT, 80.0, 2005));
        mgr.addTransaction(new Transaction(202, 2, TransactionType.DEBIT, 5.5, 2015));
        mgr.addTransaction(new Transaction(203, 2, TransactionType.DEBIT, 14.5, 2020));

        Map<Integer, Double> avg = mgr.getAverageTransactionAmountByAccount();

        assertAlmost1(40.0, avg.get(1), 0.0001);
        assertAlmost1(33.3333, avg.get(2), 0.0001);

        // Account 3 has no transactions -> should not be present
        Assert.assertFalse(avg.containsKey(3));
    }

    public static void testGetTransactionFees() {
        System.out.println("Running testGetTransactionFees");
        AccountManager mgr = new AccountManager();

        mgr.addAccount(new Account(1, "Alice"));
        mgr.addAccount(new Account(2, "Bob"));
        mgr.addAccount(new Account(3, "Jane"));

        // Account 1: 5 transactions
        mgr.addTransaction(new Transaction(1, 1, TransactionType.CREDIT, 100.0, 1000));
        mgr.addTransaction(new Transaction(2, 1, TransactionType.DEBIT, 20.0, 1010));
        mgr.addTransaction(new Transaction(3, 1, TransactionType.CREDIT, 10.0, 1020));
        mgr.addTransaction(new Transaction(4, 1, TransactionType.DEBIT, 5.0, 1030));  // fee: $2
        mgr.addTransaction(new Transaction(5, 1, TransactionType.CREDIT, 7.0, 1040)); // fee: $1

        // Account 2: 4 transactions
        mgr.addTransaction(new Transaction(6, 2, TransactionType.DEBIT, 50.0, 2000));
        mgr.addTransaction(new Transaction(7, 2, TransactionType.DEBIT, 10.0, 2010));
        mgr.addTransaction(new Transaction(8, 2, TransactionType.CREDIT, 20.0, 2020));
        mgr.addTransaction(new Transaction(9, 2, TransactionType.DEBIT, 5.0, 2030)); // fee: $2

        // Account 3: 4 transactions
        mgr.addTransaction(new Transaction(26, 3, TransactionType.DEBIT, 50.0, 2000));
        mgr.addTransaction(new Transaction(27, 3, TransactionType.DEBIT, 10.0, 2010));
        mgr.addTransaction(new Transaction(28, 3, TransactionType.CREDIT, 20.0, 2020)); // should be 4th → $1
        mgr.addTransaction(new Transaction(29, 3, TransactionType.DEBIT, 5.0, 2005));

        Map<Integer, Double> fees = mgr.getTransactionFees();

        // Account 1: $2 + $1 = $3
        assertAlmost1(3.0, fees.get(1), 0.0001);

        // Account 2: $2
        assertAlmost1(2.0, fees.get(2), 0.0001);

        // Account 3: 4th transaction (chronologically) is CREDIT → $1
        assertAlmost1(1.0, fees.get(3), 0.0001);
    }

    public static void testGetSuspiciousAccounts() {
        System.out.println("Running testGetSuspiciousAccounts");

        AccountManager mgr = new AccountManager();
        mgr.addAccount(new Account(1, "Alice"));
        mgr.addAccount(new Account(2, "Bob"));
        mgr.addAccount(new Account(3, "Charlie"));

        // Account 1: three large debits within 60 seconds -> suspicious
        mgr.addTransaction(new Transaction(1, 1, TransactionType.DEBIT, 50.0, 1000));
        mgr.addTransaction(new Transaction(2, 1, TransactionType.DEBIT, 70.0, 1030));
        mgr.addTransaction(new Transaction(3, 1, TransactionType.DEBIT, 90.0, 1060)); // 1000..1060 inclusive => suspicious

        // Account 2: three large debits but spread out > 60 seconds -> NOT suspicious
        mgr.addTransaction(new Transaction(4, 2, TransactionType.DEBIT, 60.0, 2000));
        mgr.addTransaction(new Transaction(5, 2, TransactionType.DEBIT, 80.0, 2070));
        mgr.addTransaction(new Transaction(6, 2, TransactionType.DEBIT, 55.0, 2141)); // no 60-sec window contains all 3

        // Account 3: has credits and small debits; should not be suspicious
        mgr.addTransaction(new Transaction(7, 3, TransactionType.CREDIT, 1000.0, 3000));
        mgr.addTransaction(new Transaction(8, 3, TransactionType.DEBIT, 49.99, 3010));
        mgr.addTransaction(new Transaction(9, 3, TransactionType.DEBIT, 50.0, 3020));
        mgr.addTransaction(new Transaction(10, 3, TransactionType.DEBIT, 50.0, 3100)); // only 2 large debits within any window

//        List<Integer> suspicious = mgr.getSuspiciousAccounts();
//        Assert.assertEquals(Arrays.asList(1), suspicious);
//
//        // Second test case: input order is shuffled; should still detect
//        mgr = new AccountManager();
//        mgr.addAccount(new Account(10, "Daisy"));
//
//        mgr.addTransaction(new Transaction(100, 10, TransactionType.DEBIT, 50.0, 500));
//        mgr.addTransaction(new Transaction(101, 10, TransactionType.DEBIT, 50.0, 560));
//        mgr.addTransaction(new Transaction(102, 10, TransactionType.DEBIT, 50.0, 530)); // out of order
//        // 500, 530, 560 => within 60 inclusive => suspicious
//
//        suspicious = mgr.getSuspiciousAccounts();
//        Assert.assertEquals(Arrays.asList(10), suspicious);
    }
    
    public static void testOrderManager() {
        System.out.println("Running testOrderManager");
        OrderManager om = new OrderManager();

        om.addOrder(new Order(1, 10, 100, 25.0, 3.2, OrderStatus.PLACED));
        om.addOrder(new Order(2, 10, 101, 55.0, 1.4, OrderStatus.PREPARING));
        om.addOrder(new Order(3, 11, 102, 15.0, 6.0, OrderStatus.OUT_FOR_DELIVERY));
        om.addOrder(new Order(4, 11, 103, 40.0, 2.0, OrderStatus.DELIVERED));
        om.addOrder(new Order(5, 12, 104, 18.0, 4.5, OrderStatus.CANCELED));

        OrderStats stats = om.getOrderStatistics();
        Assert.assertEquals(5, stats.totalOrders);
        Assert.assertEquals(3, stats.activeOrders);
        Assert.assertEquals(2, stats.closedOrders);
      }
      
      private static void assertAlmost(double expected, double actual, double eps) {
        Assert.assertTrue(Math.abs(expected - actual) <= eps);
      }

      public static void testGetAverageDeliveryTimeByRestaurant() {
        System.out.println("Running testGetAverageDeliveryTimeByRestaurant");
        OrderManager om = new OrderManager();

        om.addOrder(new Order(1, 10, 100, 25.0, 3.2, OrderStatus.DELIVERED));
        om.addOrder(new Order(2, 10, 101, 55.0, 1.4, OrderStatus.DELIVERED));
        om.addOrder(new Order(3, 11, 102, 15.0, 6.0, OrderStatus.DELIVERED));

        om.addDelivery(1, new Delivery(101, 10, 40));
        om.addDelivery(2, new Delivery(102, 50, 80));
        om.addDelivery(2, new Delivery(103, 90, 150));
        om.addDelivery(3, new Delivery(104, 20, 50));

        // Ignore unknown order
        om.addDelivery(999, new Delivery(105, 0, 10));

        Map<Integer, Double> avg = om.getAverageDeliveryTimeByRestaurant();

        // restaurant 10: durations [30, 30, 60] => avg 40
        assertAlmost1(40.0, avg.get(10), 0.0001);
        // restaurant 11: durations [30] => avg 30
        assertAlmost1(30.0, avg.get(11), 0.0001);
      }  
      
      public static void testGetDeliveryFees() {
        System.out.println("Running testGetDeliveryFees");
        OrderManager om = new OrderManager();

        om.addOrder(new Order(1, 10, 100, 25.0, 1.0, OrderStatus.PLACED));  // rounded1 => base2
        om.addOrder(new Order(2, 10, 101, 35.0, 2.1, OrderStatus.PLACED));  // rounded3 => base4, 50% off => 2
        om.addOrder(new Order(3, 11, 102, 55.0, 10.0, OrderStatus.PLACED)); // free
        om.addOrder(new Order(4, 12, 103, 29.0, 1.2, OrderStatus.PLACED));  // rounded2 => base3

        Map<Integer, Double> fees = om.getDeliveryFees();

        assertAlmost1(2.0, fees.get(1), 0.0001);
        assertAlmost1(2.0, fees.get(2), 0.0001);
        assertAlmost1(0.0, fees.get(3), 0.0001);
        assertAlmost1(3.0, fees.get(4), 0.0001);
      }
}
