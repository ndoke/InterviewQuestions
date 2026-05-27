package com.algorithm.justworks;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.algorithm.justworks.InMemoryDB;
import com.algorithm.justworks.InMemoryDBImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * The test class below includes 10 tests for Level 4.
 *
 * All have the same score.
 * You are not allowed to modify this file, but feel free to read the source code to better understand what is happening in every specific case.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Level4Tests {
    private InMemoryDB db;

    @BeforeEach
    void setUp() {
        db = new InMemoryDBImpl();
    }

    @Test
    @Order(1)
    void testLevel4Case01SimpleUndo1() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(20), db.setOrInc("employee1", "age", 20));
            Assertions.assertEquals(Optional.of(21), db.setOrInc("employee2", "age", 21));
            Assertions.assertEquals(Optional.of(22), db.setOrInc("employee3", "age", 22));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("employer", "employee1"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("manager", "employee1"));
            Assertions.assertEquals(Optional.of(25), db.setOrIncByCaller("employee1", "age", 5, "employer"));
            Assertions.assertEquals(Optional.of(29), db.setOrIncByCaller("employee1", "age", 4, "employer"));
            Assertions.assertEquals(Optional.of(29), db.get("employee1", "age"));
            Assertions.assertTrue(db.undo("employer", "employee1"));
            Assertions.assertEquals(Optional.of(20), db.get("employee1", "age"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("manager", "employee1"));
            Assertions.assertEquals(Optional.of(40), db.setOrIncByCaller("employee1", "workLoad", 40, "manager"));
            Assertions.assertEquals(Optional.of(40), db.get("employee1", "workLoad"));
        });
    }

    @Test
    @Order(2)
    void testLevel4Case02SimpleUndo2() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(20), db.setOrInc("employee1", "age", 20));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("manager1", "employee1"));
            Assertions.assertEquals(Optional.of(21), db.setOrIncByCaller("employee1", "age", 1, "manager1"));
            Assertions.assertTrue(db.undo("manager1", "employee1"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("manager2", "employee1"));
            Assertions.assertEquals(Optional.of(25), db.setOrIncByCaller("employee1", "age", 5, "manager2"));
            Assertions.assertFalse(db.undo("manager1", "employee1"));
            Assertions.assertEquals(Optional.of("released"), db.unlock("employee1"));
            Assertions.assertEquals(Optional.of(35), db.setOrInc("employee1", "age", 10));
            Assertions.assertEquals(Optional.of(31), db.setOrInc("employee2", "age", 31));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("employer", "employee1"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("employer", "employee2"));
            Assertions.assertEquals(Optional.of(37), db.setOrIncByCaller("employee1", "age", 2, "employer"));
            Assertions.assertEquals(Optional.of(33), db.setOrIncByCaller("employee2", "age", 2, "employer"));
            List<String> expected = new ArrayList<>(List.of("employee1(4)", "employee2(2)"));
            Assertions.assertEquals(expected, db.topNKeys(2));
            Assertions.assertEquals(Optional.of(37), db.get("employee1", "age"));
            Assertions.assertEquals(Optional.of(33), db.get("employee2", "age"));
            Assertions.assertTrue(db.undo("employer", "employee1"));
            expected = new ArrayList<>(List.of("employee1(3)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            expected = new ArrayList<>(List.of("employee1(3)", "employee2(2)"));
            Assertions.assertEquals(expected, db.topNKeys(5));
            Assertions.assertEquals(Optional.of(35), db.get("employee1", "age"));
            Assertions.assertEquals(Optional.of(33), db.get("employee2", "age"));
        });
    }

    @Test
    @Order(3)
    void testLevel4Case03UndoForObjectWithMultipleFields() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(42), db.setOrInc("item1", "cost", 42));
            Assertions.assertEquals(Optional.of(1), db.setOrInc("item2", "cost1", 1));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("director", "item1"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("director", "item2"));
            Assertions.assertEquals(Optional.of(52), db.setOrIncByCaller("item1", "cost", 10, "director"));
            Assertions.assertEquals(Optional.of(21), db.setOrIncByCaller("item2", "cost1", 20, "director"));
            Assertions.assertEquals(Optional.of(30), db.setOrIncByCaller("item2", "cost2", 30, "director"));
            Assertions.assertEquals(Optional.of(40), db.setOrIncByCaller("item2", "cost3", 40, "director"));
            Assertions.assertTrue(db.setOrIncByCaller("item2", "cost4", 50, "direct").isEmpty());
            Assertions.assertTrue(db.setOrIncByCaller("item2", "cost4", 50, "director1").isEmpty());
            Assertions.assertEquals(Optional.of(52), db.get("item1", "cost"));
            Assertions.assertEquals(Optional.of(21), db.get("item2", "cost1"));
            Assertions.assertEquals(Optional.of(30), db.get("item2", "cost2"));
            Assertions.assertEquals(Optional.of(40), db.get("item2", "cost3"));
            Assertions.assertTrue(db.get("item2", "cost4").isEmpty());
            Assertions.assertTrue(db.get("item2", "cost4").isEmpty());
            List<String> expected = new ArrayList<>(List.of("item2(4)", "item1(2)"));
            Assertions.assertEquals(expected, db.topNKeys(5));
            Assertions.assertTrue(db.undo("director", "item2"));
            expected = new ArrayList<>(List.of("item1(2)", "item2(1)"));
            Assertions.assertEquals(expected, db.topNKeys(5));
            Assertions.assertEquals(Optional.of(52), db.get("item1", "cost"));
            Assertions.assertEquals(Optional.of(1), db.get("item2", "cost1"));
            Assertions.assertTrue(db.get("item2", "cost2").isEmpty());
            Assertions.assertTrue(db.get("item2", "cost3").isEmpty());
            expected = new ArrayList<>(List.of("item1(2)", "item2(1)"));
            Assertions.assertEquals(expected, db.topNKeys(5));
        });
    }

    @Test
    @Order(4)
    void testLevel4Case04UndoAndUnlock() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(175), db.setOrInc("worker2", "height", 175));
            Assertions.assertEquals(Optional.of(160), db.setOrInc("worker1", "height", 160));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("manager", "worker1"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("support", "worker1"));
            Assertions.assertEquals(Optional.of(10), db.setOrIncByCaller("worker1", "experience", 10, "manager"));
            Assertions.assertEquals(Optional.of("released"), db.unlock("worker1"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("support", "worker1"));
            Assertions.assertEquals(Optional.of(36), db.setOrIncByCaller("worker1", "age", 36, "support"));
            Assertions.assertTrue(db.lock("support", "worker1").isEmpty());
            Assertions.assertFalse(db.undo("manager", "worker1"));
            Assertions.assertEquals(Optional.of("released"), db.unlock("worker1"));
            Assertions.assertFalse(db.undo("support", "worker1"));
            Assertions.assertEquals(Optional.of(160), db.get("worker1", "height"));
            Assertions.assertEquals(Optional.of(10), db.get("worker1", "experience"));
            Assertions.assertEquals(Optional.of(36), db.get("worker1", "age"));
            Assertions.assertEquals(Optional.of(175), db.get("worker2", "height"));
        });
    }

    @Test
    @Order(5)
    void testLevel4Case05UndoAndDelete() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(27), db.setOrInc("key1", "field1", 27));
            Assertions.assertEquals(Optional.of(24), db.setOrInc("key1", "field2", 24));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("user", "key1"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("user", "key2"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("user2", "key2"));
            Assertions.assertEquals(Optional.of(75), db.setOrIncByCaller("key2", "field1", 75, "user"));
            Assertions.assertEquals(Optional.of(43), db.setOrInc("key2", "field2", 43));
            Assertions.assertTrue(db.deleteByCaller("key1", "field1", "user"));
            Assertions.assertTrue(db.deleteByCaller("key1", "field2", "user"));
            Assertions.assertTrue(db.undo("user", "key1"));
            Assertions.assertTrue(db.unlock("key2").isEmpty());
            Assertions.assertEquals(Optional.of(27), db.get("key1", "field1"));
            Assertions.assertEquals(Optional.of(24), db.get("key1", "field2"));
            Assertions.assertEquals(Optional.of(75), db.get("key2", "field1"));
            Assertions.assertEquals(Optional.of(43), db.get("key2", "field2"));
            Assertions.assertEquals(Optional.of(101), db.setOrIncByCaller("key2", "field1", 26, "user2"));
            Assertions.assertEquals(Optional.of(101), db.get("key2", "field1"));
        });
    }

    @Test
    @Order(6)
    void testLevel4Case06LogoutFromLock() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("user", "key"));
            Assertions.assertEquals(Optional.of(10), db.setOrIncByCaller("key", "field1", 10, "user"));
            Assertions.assertEquals(Optional.of(68), db.setOrIncByCaller("key", "field2", 68, "user"));
            Assertions.assertEquals(Optional.of(109), db.setOrInc("key", "field1", 99));
            Assertions.assertEquals(0, db.logout("user"));
            Assertions.assertEquals(Optional.of(111), db.setOrIncByCaller("key", "field1", 2, "user"));
            Assertions.assertEquals(Optional.of(100), db.setOrInc("key", "field2", 32));
            Assertions.assertEquals(Optional.of(111), db.get("key", "field1"));
            Assertions.assertEquals(Optional.of(100), db.get("key", "field2"));
        });
    }

    @Test
    @Order(7)
    void testLevel4Case07LogoutFromLockRequestsQueue() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("user1", "key"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("user2", "key"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("user3", "key"));
            Assertions.assertEquals(Optional.of(100), db.setOrInc("key", "field", 100));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("user1", "key"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("user2", "key"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("user3", "key"));
            Assertions.assertEquals(0, db.logout("user2"));
            Assertions.assertEquals(Optional.of("released"), db.unlock("key"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("user3", "key"));
            Assertions.assertEquals(Optional.of(800), db.setOrIncByCaller("key", "field", 700, "user3"));
            Assertions.assertEquals(Optional.of(800), db.setOrIncByCaller("key", "field", 6, "user1"));
            Assertions.assertEquals(Optional.of(800), db.setOrIncByCaller("key", "field", 1, "user2"));
            Assertions.assertEquals(Optional.of(804), db.setOrIncByCaller("key", "field", 4, "user3"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("user1", "key"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("user2", "key"));
            Assertions.assertTrue(db.lock("user3", "key").isEmpty());
            Assertions.assertEquals(0, db.logout("user2"));
            Assertions.assertEquals(Optional.of("released"), db.unlock("key"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("user1", "key"));
            Assertions.assertEquals(Optional.of(804), db.setOrInc("key", "field", 900));
            Assertions.assertEquals(Optional.of(1304), db.setOrIncByCaller("key", "field", 500, "user1"));
            Assertions.assertEquals(Optional.of(1304), db.setOrIncByCaller("key", "field", 600, "user3"));
            Assertions.assertEquals(Optional.of(1304), db.get("key", "field"));
            Assertions.assertEquals(Optional.of("released"), db.unlock("key"));
            Assertions.assertEquals(Optional.of(1305), db.setOrInc("key", "field", 1));
            Assertions.assertEquals(Optional.of(1307), db.setOrIncByCaller("key", "field", 2, "user1"));
            Assertions.assertEquals(Optional.of(1311), db.setOrIncByCaller("key", "field", 4, "user3"));
            Assertions.assertEquals(Optional.of(1311), db.get("key", "field"));
        });
    }

    @Test
    @Order(8)
    void testLevel4Case08LogoutFromLocksAndLockRequestsQueues() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("userToBeLoggedOut", "key1"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("user1", "key2"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("userToBeLoggedOut", "key2"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("user1", "key3"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("user2", "key3"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("userToBeLoggedOut", "key3"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("user3", "key3"));
            Assertions.assertEquals(0, db.logout("userToBeLoggedOut"));
            Assertions.assertEquals(Optional.of(39), db.setOrInc("key1", "field", 39));
            Assertions.assertEquals(Optional.of(39), db.get("key1", "field"));
            Assertions.assertEquals(Optional.of(16), db.setOrIncByCaller("key2", "field", 16, "user1"));
            Assertions.assertEquals(Optional.of(38), db.setOrIncByCaller("key2", "field", 22, "userToBeLoggedOut"));
            Assertions.assertEquals(0, db.logout("user2"));
            Assertions.assertEquals(Optional.of(122), db.setOrInc("key2", "field", 84));
            Assertions.assertEquals(Optional.of(122), db.get("key2", "field"));
            Assertions.assertEquals(Optional.of(54), db.setOrIncByCaller("key3", "field", 54, "user2"));
            Assertions.assertEquals(0, db.logout("user1"));
            Assertions.assertEquals(0, db.logout("user2"));
            Assertions.assertEquals(Optional.of(137), db.setOrIncByCaller("key3", "field", 83, "user2"));
            Assertions.assertEquals(Optional.of(158), db.setOrIncByCaller("key3", "field", 21, "user3"));
            Assertions.assertEquals(0, db.logout("user3"));
            Assertions.assertFalse(db.undo("user3", "key3"));
            Assertions.assertEquals(Optional.of(158), db.get("key3", "field"));
        });
    }

    @Test
    @Order(9)
    void testLevel4Case09AllCommandsMixed1() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(1), db.setOrInc("key1", "field1", 1));
            Assertions.assertEquals(Optional.of(3), db.setOrIncByCaller("key1", "field1", 2, "user1"));
            Assertions.assertEquals(Optional.of(7), db.setOrIncByCaller("key1", "field1", 4, "user2"));
            Assertions.assertEquals(Optional.of(7), db.get("key1", "field1"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("user1", "key1"));
            Assertions.assertTrue(db.lock("user1", "key1").isEmpty());
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("user2", "key1"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("user2", "key1"));
            Assertions.assertTrue(db.lock("user1", "key1").isEmpty());
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("user3", "key1"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("user3", "key1"));
            Assertions.assertTrue(db.lock("user1", "key1").isEmpty());
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("user4", "key1"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("user3", "key1"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("user4", "key1"));
            Assertions.assertEquals(Optional.of(1), db.setOrIncByCaller("key1", "field2", 1, "user1"));
            Assertions.assertEquals(Optional.of(1), db.setOrIncByCaller("key1", "field2", 2, "user2"));
            Assertions.assertEquals(Optional.of(1), db.get("key1", "field2"));
            Assertions.assertEquals(Optional.of(1), db.get("key1", "field2"));
            Assertions.assertEquals(Optional.of(1), db.setOrIncByCaller("key1", "field2", 4, "user2"));
            Assertions.assertEquals(Optional.of(1), db.setOrIncByCaller("key1", "field2", 8, "user3"));
            Assertions.assertEquals(Optional.of(1), db.get("key1", "field2"));
            Assertions.assertEquals(0, db.logout("user2"));
            Assertions.assertTrue(db.undo("user1", "key1"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("user3", "key1"));
            Assertions.assertFalse(db.deleteByCaller("key1", "field2", "user3"));
            Assertions.assertEquals(1, db.logout("user3"));
            Assertions.assertTrue(db.get("key1", "field2").isEmpty());
            List<String> expected = new ArrayList<>(List.of("key1(3)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            expected = new ArrayList<>(List.of("key1(3)"));
            Assertions.assertEquals(expected, db.topNKeys(10));
        });
    }

    @Test
    @Order(10)
    void testLevel4Case10AllCommandsMixed2() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("user2", "key3"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.unlock("key3"));
            Assertions.assertEquals(Optional.of(1), db.setOrInc("key3", "field3", 1));
            Assertions.assertEquals(Optional.of(3), db.setOrIncByCaller("key3", "field3", 2, "user1"));
            Assertions.assertEquals(Optional.of(7), db.setOrIncByCaller("key3", "field3", 4, "user2"));
            Assertions.assertEquals(Optional.of(15), db.setOrIncByCaller("key3", "field3", 8, "user3"));
            Assertions.assertEquals(Optional.of(15), db.get("key3", "field3"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.unlock("key1"));
            Assertions.assertEquals(Optional.of(13), db.setOrIncByCaller("key1", "field1", 13, "user1"));
            Assertions.assertFalse(db.undo("user1", "key1"));
            Assertions.assertEquals(Optional.of(13), db.get("key1", "field1"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("user1", "key2"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("user2", "key2"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("user3", "key2"));
            Assertions.assertEquals(Optional.of(1), db.setOrInc("key2", "field1", 1));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("user1", "key2"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("user2", "key2"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("user3", "key2"));
            Assertions.assertEquals(0, db.logout("user3"));
            Assertions.assertEquals(Optional.of(4), db.setOrIncByCaller("key2", "field1", 3, "user1"));
            Assertions.assertTrue(db.setOrIncByCaller("key2", "field2", 9, "user2").isEmpty());
            Assertions.assertTrue(db.setOrIncByCaller("key2", "field3", 27, "user3").isEmpty());
            Assertions.assertEquals(Optional.of(4), db.get("key2", "field1"));
            Assertions.assertTrue(db.undo("user1", "key2"));
            Assertions.assertEquals(Optional.of(1), db.get("key2", "field1"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("user2", "key2"));
            Assertions.assertEquals(Optional.of(9), db.setOrIncByCaller("key2", "field2", 9, "user2"));
            Assertions.assertEquals(Optional.of(9), db.get("key2", "field2"));
            Assertions.assertTrue(db.undo("user2", "key2"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("user3", "key2"));
            Assertions.assertEquals(Optional.of(11), db.setOrIncByCaller("key2", "field2", 11, "user3"));
            Assertions.assertEquals(Optional.of(11), db.get("key2", "field2"));
            List<String> expected = new ArrayList<>(List.of("key3(4)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            expected = new ArrayList<>(List.of("key3(4)", "key2(2)", "key1(1)"));
            Assertions.assertEquals(expected, db.topNKeys(10));
            Assertions.assertEquals(1, db.logout("user3"));
            Assertions.assertEquals(Optional.of(2), db.setOrInc("key2", "field1", 1));
            Assertions.assertEquals(Optional.of(4), db.setOrIncByCaller("key2", "field1", 2, "user1"));
            Assertions.assertEquals(Optional.of(8), db.setOrIncByCaller("key2", "field1", 4, "user2"));
            Assertions.assertEquals(Optional.of(16), db.setOrIncByCaller("key2", "field1", 8, "user3"));
            Assertions.assertEquals(Optional.of(16), db.get("key2", "field1"));
            Assertions.assertTrue(db.delete("key2", "field1"));
            Assertions.assertTrue(db.get("key2", "field1").isEmpty());
        });
    }
}
