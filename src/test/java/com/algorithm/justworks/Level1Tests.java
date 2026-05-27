package com.algorithm.justworks;

import java.time.Duration;
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
 * The test class below includes 10 tests for Level 1.
 *
 * All have the same score.
 * You are not allowed to modify this file, but feel free to read the source code to better understand what is happening in every specific case.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Level1Tests {
    private InMemoryDB db;

    @BeforeEach
    void setUp() {
        db = new InMemoryDBImpl();
    }

    @Test
    @Order(1)
    void testLevel1Case01SimpleIncrementAndGet1() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(20), db.setOrInc("employee1", "age", 20));
            Assertions.assertEquals(Optional.of(21), db.setOrInc("employee2", "age", 21));
            Assertions.assertEquals(Optional.of(20), db.get("employee1", "age"));
        });
    }

    @Test
    @Order(2)
    void testLevel1Case02SimpleIncrementAndGet2() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertTrue(db.get("dept4", "floors").isEmpty());
            Assertions.assertTrue(db.get("dept4", "floors").isEmpty());
            Assertions.assertEquals(Optional.of(22), db.setOrInc("dept4", "floors", 22));
            Assertions.assertEquals(Optional.of(22), db.get("dept4", "floors"));
            Assertions.assertEquals(Optional.of(22), db.get("dept4", "floors"));
            Assertions.assertEquals(Optional.of(26), db.setOrInc("dept4", "floors", 4));
            Assertions.assertEquals(Optional.of(26), db.get("dept4", "floors"));
            Assertions.assertEquals(Optional.of(36), db.setOrInc("dept4", "floors", 10));
            Assertions.assertEquals(Optional.of(36), db.get("dept4", "floors"));
        });
    }

    @Test
    @Order(3)
    void testLevel1Case03SimpleIncrementGetAndDelete() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(30), db.setOrInc("item1", "cost", 30));
            Assertions.assertEquals(Optional.of(30), db.get("item1", "cost"));
            Assertions.assertTrue(db.delete("item1", "cost"));
            Assertions.assertFalse(db.delete("item1", "cost"));
            Assertions.assertTrue(db.get("item1", "cost").isEmpty());
            Assertions.assertEquals(Optional.of(50), db.setOrInc("item2", "cost", 50));
            Assertions.assertEquals(Optional.of(60), db.setOrInc("item3", "cost", 60));
            Assertions.assertEquals(Optional.of(64), db.setOrInc("item3", "cost", 4));
            Assertions.assertEquals(Optional.of(50), db.get("item2", "cost"));
            Assertions.assertTrue(db.delete("item2", "cost"));
            Assertions.assertEquals(Optional.of(64), db.get("item3", "cost"));
        });
    }

    @Test
    @Order(4)
    void testLevel1Case04MultipleObjectsWithSameKey() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(4), db.setOrInc("worker1", "experience", 4));
            Assertions.assertEquals(Optional.of(25), db.setOrInc("worker1", "age", 25));
            Assertions.assertEquals(Optional.of(2), db.setOrInc("age", "worker1", 2));
            Assertions.assertEquals(Optional.of(25), db.get("worker1", "age"));
            Assertions.assertEquals(Optional.of(4), db.get("worker1", "experience"));
            Assertions.assertTrue(db.get("worker1", "height").isEmpty());
            Assertions.assertEquals(Optional.of(182), db.setOrInc("worker1", "height", 182));
            Assertions.assertEquals(Optional.of(29), db.setOrInc("worker1", "age", 4));
            Assertions.assertEquals(Optional.of(8), db.setOrInc("worker1", "experience", 4));
            Assertions.assertEquals(Optional.of(8), db.get("worker1", "experience"));
            Assertions.assertEquals(Optional.of(29), db.get("worker1", "age"));
            Assertions.assertEquals(Optional.of(182), db.get("worker1", "height"));
            Assertions.assertEquals(Optional.of(2), db.get("age", "worker1"));
        });
    }

    @Test
    @Order(5)
    void testLevel1Case05MultipleUnusedDeletes() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(1), db.setOrInc("A", "BC", 1));
            Assertions.assertEquals(Optional.of(2), db.setOrInc("AB", "C", 2));
            Assertions.assertFalse(db.delete("BC", "A"));
            Assertions.assertTrue(db.get("BC", "A").isEmpty());
            Assertions.assertEquals(Optional.of(1), db.get("A", "BC"));
            Assertions.assertEquals(Optional.of(2), db.get("AB", "C"));
            Assertions.assertTrue(db.delete("A", "BC"));
            Assertions.assertFalse(db.delete("A", "BC"));
            Assertions.assertFalse(db.delete("B", "AC"));
            Assertions.assertFalse(db.delete("A", "BC"));
            Assertions.assertTrue(db.get("A", "BC").isEmpty());
            Assertions.assertEquals(Optional.of(2), db.get("AB", "C"));
        });
    }

    @Test
    @Order(6)
    void testLevel1Case06Resets() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(70), db.setOrInc("foo", "bar", 70));
            Assertions.assertEquals(Optional.of(60), db.setOrInc("foo", "two", 60));
            Assertions.assertEquals(Optional.of(100), db.setOrInc("foo", "bar", 30));
            Assertions.assertEquals(Optional.of(100), db.get("foo", "bar"));
            Assertions.assertEquals(Optional.of(60), db.get("foo", "two"));
            Assertions.assertEquals(Optional.of(110), db.setOrInc("foo", "two", 50));
            Assertions.assertTrue(db.delete("foo", "bar"));
            Assertions.assertTrue(db.get("foo", "bar").isEmpty());
            Assertions.assertEquals(Optional.of(130), db.setOrInc("foo", "two", 20));
            Assertions.assertEquals(Optional.of(280), db.setOrInc("foo", "two", 150));
            Assertions.assertEquals(Optional.of(30), db.setOrInc("foot", "wo", 30));
            Assertions.assertEquals(Optional.of(70), db.setOrInc("foo", "bar", 70));
            Assertions.assertEquals(Optional.of(70), db.get("foo", "bar"));
            Assertions.assertEquals(Optional.of(280), db.get("foo", "two"));
        });
    }

    @Test
    @Order(7)
    void testLevel1Case07ResetsAndDeletesWithSameKeys() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(5), db.setOrInc("key1", "field1", 5));
            Assertions.assertEquals(Optional.of(3), db.setOrInc("key1", "field2", 3));
            Assertions.assertEquals(Optional.of(1), db.setOrInc("key2", "field1", 1));
            Assertions.assertEquals(Optional.of(12), db.setOrInc("key3", "field1", 12));
            Assertions.assertEquals(Optional.of(72), db.setOrInc("key3", "field2", 72));
            Assertions.assertEquals(Optional.of(37), db.setOrInc("key3", "field3", 37));
            Assertions.assertEquals(Optional.of(13), db.setOrInc("key1", "field2", 10));
            Assertions.assertEquals(Optional.of(31), db.setOrInc("key2", "field1", 30));
            Assertions.assertEquals(Optional.of(28), db.setOrInc("key3", "field1", 16));
            Assertions.assertTrue(db.delete("key1", "field1"));
            Assertions.assertTrue(db.delete("key3", "field2"));
            Assertions.assertTrue(db.delete("key2", "field1"));
            Assertions.assertFalse(db.delete("key4", "field1"));
            Assertions.assertEquals(Optional.of(33), db.setOrInc("key1", "field2", 20));
            Assertions.assertEquals(Optional.of(50), db.setOrInc("key2", "field1", 50));
            Assertions.assertEquals(Optional.of(77), db.setOrInc("key3", "field3", 40));
            Assertions.assertTrue(db.get("key1", "field1").isEmpty());
            Assertions.assertEquals(Optional.of(33), db.get("key1", "field2"));
            Assertions.assertEquals(Optional.of(50), db.get("key2", "field1"));
            Assertions.assertTrue(db.get("key2", "field2").isEmpty());
            Assertions.assertEquals(Optional.of(28), db.get("key3", "field1"));
            Assertions.assertTrue(db.get("key3", "field2").isEmpty());
            Assertions.assertEquals(Optional.of(77), db.get("key3", "field3"));
        });
    }

    @Test
    @Order(8)
    void testLevel1Case08RandomOrderedOperations() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertFalse(db.delete("key", "key"));
            Assertions.assertFalse(db.delete("key", "key2"));
            Assertions.assertTrue(db.get("A", "B").isEmpty());
            Assertions.assertTrue(db.get("key", "key").isEmpty());
            Assertions.assertFalse(db.delete("key", "key"));
            Assertions.assertEquals(Optional.of(12), db.setOrInc("key", "key", 12));
            Assertions.assertEquals(Optional.of(906), db.setOrInc("foo", "bar", 906));
            Assertions.assertFalse(db.delete("key", "bar"));
            Assertions.assertFalse(db.delete("key", "key2"));
            Assertions.assertEquals(Optional.of(12), db.get("key", "key"));
            Assertions.assertTrue(db.get("k", "eykey").isEmpty());
            Assertions.assertEquals(Optional.of(72), db.setOrInc("key", "key", 60));
            Assertions.assertEquals(Optional.of(72), db.get("key", "key"));
        });
    }

    @Test
    @Order(9)
    void testLevel1Case09MixedMultipleOperations1() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(71), db.setOrInc("a", "b", 71));
            Assertions.assertEquals(Optional.of(19), db.setOrInc("a", "c", 19));
            Assertions.assertTrue(db.get("c", "a").isEmpty());
            Assertions.assertEquals(Optional.of(46), db.setOrInc("a", "d", 46));
            Assertions.assertTrue(db.delete("a", "c"));
            Assertions.assertFalse(db.delete("a", "c"));
            Assertions.assertEquals(Optional.of(142), db.setOrInc("a", "b", 71));
            Assertions.assertEquals(Optional.of(97), db.setOrInc("a", "d", 51));
            Assertions.assertTrue(db.delete("a", "b"));
            Assertions.assertEquals(Optional.of(108), db.setOrInc("a", "d", 11));
            Assertions.assertTrue(db.get("a", "c").isEmpty());
            Assertions.assertTrue(db.get("a", "b").isEmpty());
            Assertions.assertEquals(Optional.of(108), db.get("a", "d"));
        });
    }

    @Test
    @Order(10)
    void testLevel1Case10MixedMultipleOperations2() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(7), db.setOrInc("a", "a", 7));
            Assertions.assertEquals(Optional.of(70), db.setOrInc("a", "A", 70));
            int j = 7;
            for (int i = 0; i < 10; i++) {
                j += i;
                Assertions.assertEquals(Optional.of(j), db.setOrInc("a", "a", i));
            }
            Assertions.assertEquals(Optional.of(52), db.get("a", "a"));
            Assertions.assertTrue(db.delete("a", "a"));
            Assertions.assertTrue(db.delete("a", "A"));
            Assertions.assertEquals(Optional.of(7), db.setOrInc("a", "A", 7));
            j = 7;
            for (int i = 0; i < 20; i++) {
                j += 10;
                Assertions.assertEquals(Optional.of(j), db.setOrInc("a", "A", 10));
            }
            Assertions.assertEquals(Optional.of(228), db.setOrInc("a", "A", 21));
            Assertions.assertTrue(db.get("a", "a").isEmpty());
            Assertions.assertEquals(Optional.of(228), db.get("a", "A"));
        });
    }
}
