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
 * The test class below includes 10 tests for Level 2.
 *
 * All have the same score.
 * You are not allowed to modify this file, but feel free to read the source code to better understand what is happening in every specific case.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Level2Tests {
    private InMemoryDB db;

    @BeforeEach
    void setUp() {
        db = new InMemoryDBImpl();
    }

    @Test
    @Order(1)
    void testLevel2Case01SimpleIncrementAndStats() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(20), db.setOrInc("employee", "age", 20));
            Assertions.assertEquals(Optional.of(234), db.setOrInc("employee", "id", 234));
            List<String> expected = new ArrayList<>(List.of("employee(2)"));
            Assertions.assertEquals(expected, db.topNKeys(2));
        });
    }

    @Test
    @Order(2)
    void testLevel2Case02SimpleOperationsAndStats() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertTrue(db.get("dept4", "floors").isEmpty());
            Assertions.assertEquals(Optional.of(10), db.setOrInc("dept4", "floors", 10));
            Assertions.assertEquals(Optional.of(14), db.setOrInc("dept4", "floors", 4));
            List<String> expected = new ArrayList<>(List.of("dept4(2)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            expected = new ArrayList<>(List.of("dept4(2)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            Assertions.assertEquals(Optional.of(15), db.setOrInc("dept4", "flowers", 15));
            Assertions.assertEquals(Optional.of(6), db.setOrInc("dept3", "floors", 6));
            Assertions.assertEquals(Optional.of(7), db.setOrInc("dept3", "floors", 1));
            Assertions.assertEquals(Optional.of(14), db.get("dept4", "floors"));
            expected = new ArrayList<>(List.of("dept4(3)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            Assertions.assertEquals(Optional.of(8), db.setOrInc("dept3", "floors", 1));
            Assertions.assertTrue(db.delete("dept4", "flowers"));
            expected = new ArrayList<>(List.of("dept4(4)", "dept3(3)"));
            Assertions.assertEquals(expected, db.topNKeys(2));
        });
    }

    @Test
    @Order(3)
    void testLevel2Case03KeyDeletedAndRecreatedAndStats() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(10), db.setOrInc("aaa", "b", 10));
            Assertions.assertEquals(Optional.of(20), db.setOrInc("a", "bbb", 20));
            Assertions.assertEquals(Optional.of(30), db.setOrInc("aaa", "bb", 30));
            Assertions.assertEquals(Optional.of(40), db.setOrInc("a", "bb", 40));
            Assertions.assertEquals(Optional.of(50), db.setOrInc("aaa", "bc", 50));
            List<String> expected = new ArrayList<>(List.of("aaa(3)", "a(2)"));
            Assertions.assertEquals(expected, db.topNKeys(2));
            Assertions.assertTrue(db.delete("aaa", "bc"));
            expected = new ArrayList<>(List.of("aaa(4)", "a(2)"));
            Assertions.assertEquals(expected, db.topNKeys(2));
            Assertions.assertTrue(db.delete("aaa", "bb"));
            expected = new ArrayList<>(List.of("aaa(5)", "a(2)"));
            Assertions.assertEquals(expected, db.topNKeys(2));
            Assertions.assertTrue(db.delete("aaa", "b"));
            expected = new ArrayList<>(List.of("a(2)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            Assertions.assertEquals(Optional.of(20), db.setOrInc("aaa", "bb", 20));
            expected = new ArrayList<>(List.of("a(2)", "aaa(1)"));
            Assertions.assertEquals(expected, db.topNKeys(2));
            expected = new ArrayList<>(List.of("a(2)", "aaa(1)"));
            Assertions.assertEquals(expected, db.topNKeys(2));
            Assertions.assertEquals(Optional.of(10), db.setOrInc("aaa", "b", 10));
            Assertions.assertTrue(db.delete("a", "bb"));
            expected = new ArrayList<>(List.of("a(3)", "aaa(2)"));
            Assertions.assertEquals(expected, db.topNKeys(2));
        });
    }

    @Test
    @Order(4)
    void testLevel2Case04MultipleFieldsWithinKeyAndStats() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(10), db.setOrInc("John", "experience", 10));
            Assertions.assertEquals(Optional.of(36), db.setOrInc("John", "age", 36));
            Assertions.assertEquals(Optional.of(2), db.setOrInc("age", "Jhon", 2));
            Assertions.assertTrue(db.get("Jhon", "experience").isEmpty());
            List<String> expected = new ArrayList<>(List.of("John(2)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            Assertions.assertEquals(Optional.of(160), db.setOrInc("James", "height", 160));
            Assertions.assertEquals(Optional.of(30), db.setOrInc("James", "age", 30));
            Assertions.assertEquals(Optional.of(31), db.setOrInc("James", "age", 1));
            expected = new ArrayList<>(List.of("James(3)", "John(2)"));
            Assertions.assertEquals(expected, db.topNKeys(2));
            Assertions.assertEquals(Optional.of(175), db.setOrInc("John", "height", 175));
            expected = new ArrayList<>(List.of("James(3)", "John(3)"));
            Assertions.assertEquals(expected, db.topNKeys(2));
            expected = new ArrayList<>(List.of("James(3)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            Assertions.assertEquals(Optional.of(5), db.setOrInc("James", "experience", 5));
            Assertions.assertEquals(Optional.of(12), db.setOrInc("James", "experience", 7));
            expected = new ArrayList<>(List.of("James(5)", "John(3)"));
            Assertions.assertEquals(expected, db.topNKeys(2));
        });
    }

    @Test
    @Order(5)
    void testLevel2Case05SimpleOperationsAndStatsTie() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(42), db.setOrInc("orange", "cost", 42));
            Assertions.assertEquals(Optional.of(42), db.get("orange", "cost"));
            List<String> expected = new ArrayList<>(List.of("orange(1)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            Assertions.assertEquals(Optional.of(20), db.setOrInc("banana", "cost", 20));
            Assertions.assertEquals(Optional.of(10), db.setOrInc("apple", "cost", 10));
            expected = new ArrayList<>(List.of("apple(1)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            expected = new ArrayList<>(List.of("apple(1)", "banana(1)", "orange(1)"));
            Assertions.assertEquals(expected, db.topNKeys(3));
            Assertions.assertEquals(Optional.of(15), db.setOrInc("apple", "cost", 5));
            Assertions.assertEquals(Optional.of(52), db.setOrInc("orange", "cost", 10));
            expected = new ArrayList<>(List.of("apple(2)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            expected = new ArrayList<>(List.of("apple(2)", "orange(2)", "banana(1)"));
            Assertions.assertEquals(expected, db.topNKeys(3));
        });
    }

    @Test
    @Order(6)
    void testLevel2Case06NExceedsAmountOfKeys() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(70), db.setOrInc("foo", "bar", 70));
            Assertions.assertEquals(Optional.of(60), db.setOrInc("foo", "two", 60));
            Assertions.assertEquals(Optional.of(100), db.setOrInc("foo", "bar", 30));
            Assertions.assertEquals(Optional.of(40), db.setOrInc("boo", "bar", 40));
            Assertions.assertEquals(Optional.of(40), db.setOrInc("boo", "two", 40));
            Assertions.assertEquals(Optional.of(100), db.get("foo", "bar"));
            List<String> expected = new ArrayList<>(List.of("foo(3)", "boo(2)"));
            Assertions.assertEquals(expected, db.topNKeys(2));
            expected = new ArrayList<>(List.of("foo(3)", "boo(2)"));
            Assertions.assertEquals(expected, db.topNKeys(3));
            expected = new ArrayList<>(List.of("foo(3)", "boo(2)"));
            Assertions.assertEquals(expected, db.topNKeys(100));
            Assertions.assertEquals(Optional.of(90), db.setOrInc("boo", "two", 50));
            Assertions.assertTrue(db.delete("foo", "bar"));
            Assertions.assertTrue(db.get("foo", "bar").isEmpty());
            expected = new ArrayList<>(List.of("foo(4)", "boo(3)"));
            Assertions.assertEquals(expected, db.topNKeys(30));
            Assertions.assertEquals(Optional.of(80), db.setOrInc("foo", "two", 20));
            Assertions.assertEquals(Optional.of(230), db.setOrInc("foo", "two", 150));
            Assertions.assertEquals(Optional.of(100), db.setOrInc("foot", "wo", 100));
            Assertions.assertEquals(Optional.of(70), db.setOrInc("foo", "bar", 70));
            expected = new ArrayList<>(List.of("foo(7)", "boo(3)", "foot(1)"));
            Assertions.assertEquals(expected, db.topNKeys(10));
            Assertions.assertEquals(Optional.of(230), db.get("foo", "two"));
        });
    }

    @Test
    @Order(7)
    void testLevel2Case07EmptyDatabase() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertTrue(db.topNKeys(1).isEmpty());
            Assertions.assertTrue(db.topNKeys(15).isEmpty());
            Assertions.assertFalse(db.delete("key1", "field1"));
            Assertions.assertTrue(db.topNKeys(1).isEmpty());
            Assertions.assertTrue(db.get("key1", "field1").isEmpty());
            Assertions.assertTrue(db.topNKeys(1).isEmpty());
            Assertions.assertEquals(Optional.of(5), db.setOrInc("key1", "field1", 5));
            List<String> expected = new ArrayList<>(List.of("key1(1)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            Assertions.assertTrue(db.delete("key1", "field1"));
            Assertions.assertTrue(db.topNKeys(1).isEmpty());
        });
    }

    @Test
    @Order(8)
    void testLevel2Case08MultipleOperationsAndEdgeCasesCombined() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertTrue(db.topNKeys(5).isEmpty());
            Assertions.assertFalse(db.delete("key", "key"));
            Assertions.assertFalse(db.delete("key", "key2"));
            Assertions.assertTrue(db.get("A", "B").isEmpty());
            Assertions.assertTrue(db.get("key", "key").isEmpty());
            Assertions.assertFalse(db.delete("key", "key"));
            Assertions.assertEquals(Optional.of(82), db.setOrInc("key", "key", 82));
            Assertions.assertEquals(Optional.of(54), db.setOrInc("foo", "bar", 54));
            List<String> expected = new ArrayList<>(List.of("foo(1)", "key(1)"));
            Assertions.assertEquals(expected, db.topNKeys(5));
            Assertions.assertEquals(Optional.of(50), db.setOrInc("k", "ey", 50));
            Assertions.assertEquals(Optional.of(14), db.setOrInc("foot", "ball", 14));
            expected = new ArrayList<>(List.of("foo(1)", "foot(1)", "k(1)", "key(1)"));
            Assertions.assertEquals(expected, db.topNKeys(10));
            expected = new ArrayList<>(List.of("foo(1)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            Assertions.assertFalse(db.delete("key", "bar"));
            Assertions.assertTrue(db.delete("key", "key"));
            Assertions.assertTrue(db.get("key", "key").isEmpty());
            Assertions.assertTrue(db.get("k", "eykey").isEmpty());
            Assertions.assertEquals(Optional.of(20), db.setOrInc("key", "key", 20));
            Assertions.assertEquals(Optional.of(30), db.setOrInc("key", "key2", 30));
            Assertions.assertEquals(Optional.of(20), db.get("key", "key"));
            Assertions.assertTrue(db.delete("key", "key"));
            expected = new ArrayList<>(List.of("key(3)", "foo(1)", "foot(1)", "k(1)"));
            Assertions.assertEquals(expected, db.topNKeys(10));
        });
    }

    @Test
    @Order(9)
    void testLevel2Case09MixedMultipleOperations1() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(30), db.setOrInc("a", "b", 30));
            Assertions.assertEquals(Optional.of(41), db.setOrInc("a", "c", 41));
            Assertions.assertTrue(db.get("c", "a").isEmpty());
            Assertions.assertEquals(Optional.of(45), db.setOrInc("a", "d", 45));
            Assertions.assertTrue(db.delete("a", "c"));
            Assertions.assertFalse(db.delete("a", "c"));
            Assertions.assertEquals(Optional.of(51), db.setOrInc("a", "b", 21));
            Assertions.assertEquals(Optional.of(96), db.setOrInc("a", "d", 51));
            List<String> expected = new ArrayList<>(List.of("a(6)"));
            Assertions.assertEquals(expected, db.topNKeys(15));
            Assertions.assertEquals(Optional.of(200), db.setOrInc("b", "a", 200));
            expected = new ArrayList<>(List.of("a(6)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            Assertions.assertTrue(db.delete("b", "a"));
            Assertions.assertEquals(Optional.of(107), db.setOrInc("a", "d", 11));
            expected = new ArrayList<>(List.of("a(7)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
        });
    }

    @Test
    @Order(10)
    void testLevel2Case10MixedMultipleOperations2() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertTrue(db.topNKeys(3).isEmpty());
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
            List<String> expected = new ArrayList<>(List.of("a(1)"));
            Assertions.assertEquals(expected, db.topNKeys(3));
            j = 7;
            for (int i = 0; i < 20; i++) {
                j += 10;
                Assertions.assertEquals(Optional.of(j), db.setOrInc("a", "A", 10));
            }
            Assertions.assertEquals(Optional.of(228), db.setOrInc("a", "A", 21));
            Assertions.assertTrue(db.get("a", "a").isEmpty());
            Assertions.assertEquals(Optional.of(228), db.get("a", "A"));
            expected = new ArrayList<>(List.of("a(22)"));
            Assertions.assertEquals(expected, db.topNKeys(5));
            Assertions.assertEquals(Optional.of(50), db.setOrInc("a", "a", 50));
            Assertions.assertEquals(Optional.of(120), db.setOrInc("b", "a", 120));
            Assertions.assertEquals(Optional.of(140), db.setOrInc("c", "c", 140));
            expected = new ArrayList<>(List.of("a(23)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            Assertions.assertEquals(Optional.of(10), db.setOrInc("b", "b", 10));
            expected = new ArrayList<>(List.of("a(23)", "b(2)", "c(1)"));
            Assertions.assertEquals(expected, db.topNKeys(10));
            Assertions.assertEquals(Optional.of(20), db.setOrInc("b", "b", 10));
            expected = new ArrayList<>(List.of("a(23)", "b(3)", "c(1)"));
            Assertions.assertEquals(expected, db.topNKeys(5));
        });
    }
}
