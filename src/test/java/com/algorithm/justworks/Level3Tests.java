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
 * The test class below includes 10 tests for Level 3.
 *
 * All have the same score.
 * You are not allowed to modify this file, but feel free to read the source code to better understand what is happening in every specific case.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Level3Tests {
    private InMemoryDB db;

    @BeforeEach
    void setUp() {
        db = new InMemoryDBImpl();
    }

    @Test
    @Order(1)
    void testLevel3Case01SimpleLock1() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(20), db.setOrInc("employee1", "age", 20));
            Assertions.assertEquals(Optional.of(21), db.setOrInc("employee2", "age", 21));
            Assertions.assertEquals(Optional.of(25), db.setOrInc("employee3", "age", 25));
            List<String> expected = new ArrayList<>(List.of("employee1(1)", "employee2(1)", "employee3(1)"));
            Assertions.assertEquals(expected, db.topNKeys(3));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("employer", "employee1"));
            Assertions.assertEquals(Optional.of(20), db.setOrInc("employee1", "age", 5));
            Assertions.assertEquals(Optional.of(26), db.setOrIncByCaller("employee2", "age", 5, "employer"));
            Assertions.assertEquals(Optional.of(32), db.setOrInc("employee3", "age", 7));
            expected = new ArrayList<>(List.of("employee2(2)", "employee3(2)", "employee1(1)"));
            Assertions.assertEquals(expected, db.topNKeys(3));
        });
    }

    @Test
    @Order(2)
    void testLevel3Case02SimpleLock2() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertTrue(db.topNKeys(10).isEmpty());
            Assertions.assertTrue(db.get("dept4", "floors").isEmpty());
            Assertions.assertEquals(Optional.of(10), db.setOrIncByCaller("dept4", "floors", 10, "builder"));
            Assertions.assertEquals(Optional.of(14), db.setOrIncByCaller("dept4", "floors", 4, "otherBuilder"));
            Assertions.assertEquals(Optional.of(6), db.setOrInc("dept3", "floors", 6));
            Assertions.assertEquals(Optional.of(10), db.setOrIncByCaller("dept2", "floors", 10, "builder"));
            Assertions.assertEquals(Optional.of(14), db.get("dept4", "floors"));
            List<String> expected = new ArrayList<>(List.of("dept4(2)", "dept2(1)", "dept3(1)"));
            Assertions.assertEquals(expected, db.topNKeys(10));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("builder", "dept3"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("builder", "dept4"));
            Assertions.assertEquals(Optional.of(24), db.setOrIncByCaller("dept4", "floors", 10, "builder"));
            Assertions.assertEquals(Optional.of(24), db.setOrIncByCaller("dept4", "floors", 20, "otherBuilder"));
            Assertions.assertEquals(Optional.of(6), db.setOrInc("dept3", "floors", 6));
            Assertions.assertEquals(Optional.of(17), db.setOrIncByCaller("dept2", "floors", 7, "builder"));
            Assertions.assertEquals(Optional.of(24), db.get("dept4", "floors"));
            Assertions.assertEquals(Optional.of(6), db.get("dept3", "floors"));
            Assertions.assertEquals(Optional.of(17), db.get("dept2", "floors"));
            expected = new ArrayList<>(List.of("dept4(3)", "dept2(2)", "dept3(1)"));
            Assertions.assertEquals(expected, db.topNKeys(10));
        });
    }

    @Test
    @Order(3)
    void testLevel3Case03LockAndUnlock() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(42), db.setOrInc("item1", "cost", 42));
            Assertions.assertEquals(Optional.of(42), db.get("item1", "cost"));
            List<String> expected = new ArrayList<>(List.of("item1(1)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("director", "item2"));
            Assertions.assertEquals(Optional.of(20), db.setOrInc("item2", "cost", 20));
            Assertions.assertEquals(Optional.of(60), db.setOrInc("item2", "cost", 40));
            Assertions.assertEquals(Optional.of(90), db.setOrIncByCaller("item2", "cost", 30, "direct"));
            Assertions.assertEquals(Optional.of(102), db.setOrIncByCaller("item2", "cost", 12, "director"));
            Assertions.assertEquals(Optional.of(139), db.setOrIncByCaller("item2", "cost", 37, "director"));
            Assertions.assertEquals(Optional.of(190), db.setOrIncByCaller("item2", "cost", 51, "other"));
            Assertions.assertEquals(Optional.of(52), db.setOrInc("item1", "cost", 10));
            Assertions.assertEquals(Optional.of(69), db.setOrIncByCaller("item1", "cost", 17, "director"));
            expected = new ArrayList<>(List.of("item2(6)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            Assertions.assertEquals(Optional.of(228), db.setOrIncByCaller("item2", "cost", 38, "other"));
            Assertions.assertTrue(db.unlock("item2").isEmpty());
            Assertions.assertEquals(Optional.of(270), db.setOrIncByCaller("item2", "cost", 42, "other"));
            Assertions.assertEquals(Optional.of(270), db.get("item2", "cost"));
            Assertions.assertTrue(db.delete("item1", "cost"));
            expected = new ArrayList<>(List.of("item2(8)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            Assertions.assertFalse(db.delete("item1", "cost"));
            Assertions.assertTrue(db.delete("item2", "cost"));
            Assertions.assertTrue(db.get("item2", "cost").isEmpty());
            Assertions.assertTrue(db.topNKeys(1).isEmpty());
        });
    }

    @Test
    @Order(4)
    void testLevel3Case04MultipleObjectsWithSameKey1() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(10), db.setOrInc("worker1", "experience", 10));
            Assertions.assertEquals(Optional.of(36), db.setOrInc("worker1", "age", 36));
            Assertions.assertEquals(Optional.of(10), db.get("worker1", "experience"));
            Assertions.assertEquals(Optional.of(175), db.setOrInc("worker2", "height", 175));
            Assertions.assertEquals(Optional.of(160), db.setOrInc("worker1", "height", 160));
            List<String> expected = new ArrayList<>(List.of("worker1(3)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("chief", "worker1"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("chief", "worker3"));
            Assertions.assertEquals(Optional.of(10), db.get("worker1", "experience"));
            expected = new ArrayList<>(List.of("worker1(3)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            Assertions.assertEquals(Optional.of(10), db.setOrInc("worker1", "experience", 2));
            Assertions.assertEquals(Optional.of(10), db.get("worker1", "experience"));
            Assertions.assertEquals(Optional.of(30), db.setOrInc("worker2", "age", 30));
            Assertions.assertEquals(Optional.of(30), db.get("worker2", "age"));
            Assertions.assertTrue(db.unlock("worker2").isEmpty());
            Assertions.assertEquals(Optional.of(177), db.setOrInc("worker2", "height", 2));
            Assertions.assertEquals(Optional.of(177), db.get("worker2", "height"));
            Assertions.assertEquals(Optional.of("released"), db.unlock("worker1"));
            Assertions.assertEquals(Optional.of(38), db.setOrInc("worker1", "age", 2));
            Assertions.assertEquals(Optional.of(38), db.get("worker1", "age"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("other", "worker1"));
            Assertions.assertEquals(Optional.of(38), db.setOrIncByCaller("worker1", "age", 2, "chief"));
            Assertions.assertEquals(Optional.of(38), db.get("worker1", "age"));
            Assertions.assertEquals(Optional.of(40), db.setOrIncByCaller("worker1", "age", 2, "other"));
            Assertions.assertEquals(Optional.of(40), db.get("worker1", "age"));
        });
    }

    @Test
    @Order(5)
    void testLevel3Case05MultipleObjectsWithSameKey2() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(10), db.setOrInc("a", "b", 10));
            Assertions.assertEquals(Optional.of(20), db.setOrInc("aaa", "bbb", 20));
            Assertions.assertEquals(Optional.of(30), db.setOrInc("a", "bb", 30));
            Assertions.assertEquals(Optional.of(40), db.setOrInc("aaa", "bb", 40));
            Assertions.assertEquals(Optional.of(50), db.setOrInc("a", "bc", 50));
            List<String> expected = new ArrayList<>(List.of("a(3)", "aaa(2)"));
            Assertions.assertEquals(expected, db.topNKeys(5));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("A", "a"));
            Assertions.assertFalse(db.delete("a", "bc"));
            Assertions.assertFalse(db.deleteByCaller("a", "bc", "a"));
            Assertions.assertFalse(db.deleteByCaller("a", "bc", "AA"));
            Assertions.assertTrue(db.deleteByCaller("a", "bc", "A"));
            expected = new ArrayList<>(List.of("a(4)", "aaa(2)"));
            Assertions.assertEquals(expected, db.topNKeys(3));
            Assertions.assertEquals(Optional.of(50), db.setOrIncByCaller("a", "bb", 20, "A"));
            expected = new ArrayList<>(List.of("a(5)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
            Assertions.assertEquals(Optional.of(20), db.setOrIncByCaller("a", "b", 10, "A"));
            Assertions.assertTrue(db.delete("aaa", "bb"));
            expected = new ArrayList<>(List.of("a(6)"));
            Assertions.assertEquals(expected, db.topNKeys(1));
        });
    }

    @Test
    @Order(6)
    void testLevel3Case06FullKeyDeletionAndRecreation() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(70), db.setOrInc("foo", "bar", 70));
            Assertions.assertEquals(Optional.of(60), db.setOrInc("foo", "two", 60));
            Assertions.assertEquals(Optional.of(100), db.setOrInc("foo", "bar", 30));
            Assertions.assertEquals(Optional.of(40), db.setOrInc("boo", "bar", 40));
            Assertions.assertEquals(Optional.of(40), db.setOrInc("boo", "two", 40));
            Assertions.assertEquals(Optional.of(15), db.setOrInc("a", "b", 15));
            Assertions.assertEquals(Optional.of(30), db.setOrInc("a", "c", 30));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("c1", "a"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("c2", "a"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("c1", "foo"));
            Assertions.assertTrue(db.deleteByCaller("a", "b", "c1"));
            Assertions.assertTrue(db.deleteByCaller("a", "c", "c1"));
            Assertions.assertEquals(Optional.of("released"), db.unlock("a"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.unlock("a"));
            Assertions.assertEquals(Optional.of(20), db.setOrInc("a", "b", 20));
            Assertions.assertEquals(Optional.of(10), db.setOrInc("a", "c", 10));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("c1", "a"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("c2", "a"));
            Assertions.assertTrue(db.deleteByCaller("a", "b", "c1"));
            Assertions.assertTrue(db.deleteByCaller("a", "c", "c1"));
            Assertions.assertEquals(Optional.of(50), db.setOrIncByCaller("a", "b", 50, "c1"));
            Assertions.assertEquals(Optional.of("released"), db.unlock("a"));
            Assertions.assertTrue(db.unlock("a").isEmpty());
            Assertions.assertTrue(db.lock("c1", "foo").isEmpty());
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("c2", "foo"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("c1", "boo"));
            Assertions.assertEquals(Optional.of(100), db.setOrInc("foo", "bar", 2));
            Assertions.assertEquals(Optional.of(40), db.setOrInc("boo", "bar", 3));
            Assertions.assertEquals(Optional.of(100), db.get("foo", "bar"));
            List<String> expected = new ArrayList<>(List.of("foo(3)", "boo(2)", "a(1)"));
            Assertions.assertEquals(expected, db.topNKeys(10));
            Assertions.assertEquals(Optional.of("released"), db.unlock("foo"));
            Assertions.assertEquals(Optional.of(110), db.setOrIncByCaller("foo", "two", 50, "c1"));
            Assertions.assertEquals(Optional.of(110), db.get("foo", "two"));
            Assertions.assertEquals(Optional.of(160), db.setOrIncByCaller("foo", "two", 50, "c2"));
            Assertions.assertEquals(Optional.of(160), db.get("foo", "two"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("c1", "foo"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("new", "foo"));
            Assertions.assertTrue(db.lock("c1", "foo").isEmpty());
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("new", "boo"));
            Assertions.assertFalse(db.delete("foo", "bar"));
            Assertions.assertTrue(db.deleteByCaller("foo", "bar", "c1"));
            Assertions.assertFalse(db.deleteByCaller("foo", "bar", "c1"));
            Assertions.assertTrue(db.get("foo", "bar").isEmpty());
            Assertions.assertEquals(Optional.of(47), db.setOrIncByCaller("boo", "bar", 7, "c1"));
            Assertions.assertEquals(Optional.of(47), db.get("boo", "bar"));
            Assertions.assertEquals(Optional.of("released"), db.unlock("foo"));
            Assertions.assertTrue(db.unlock("foo").isEmpty());
            Assertions.assertTrue(db.unlock("foo").isEmpty());
            Assertions.assertEquals(Optional.of(170), db.setOrIncByCaller("foo", "two", 10, "new"));
            Assertions.assertEquals(Optional.of(170), db.get("foo", "two"));
        });
    }

    @Test
    @Order(7)
    void testLevel3Case07MultipleTriesToAcquireTheSameKey() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of("invalid_request"), db.unlock("key1"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("someone", "key2"));
            Assertions.assertEquals(Optional.of(10), db.setOrInc("key1", "field1", 10));
            Assertions.assertEquals(Optional.of(20), db.setOrInc("key1", "field2", 20));
            Assertions.assertEquals(Optional.of(20), db.setOrInc("key2", "field1", 20));
            Assertions.assertEquals(Optional.of(50), db.setOrInc("key3", "field1", 50));
            Assertions.assertEquals(Optional.of(70), db.setOrInc("key3", "field2", 70));
            Assertions.assertEquals(Optional.of(10), db.setOrInc("key3", "field3", 10));
            List<String> expected = new ArrayList<>(List.of("key3(3)", "key1(2)", "key2(1)"));
            Assertions.assertEquals(expected, db.topNKeys(5));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("newone", "key1"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("someone", "key1"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("newtwo", "key1"));
            Assertions.assertEquals(Optional.of(20), db.setOrInc("key1", "field2", 20));
            Assertions.assertEquals(Optional.of(20), db.setOrIncByCaller("key1", "field2", 12, "newtwo"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("someone", "key1"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("someone", "key2"));
            Assertions.assertEquals(Optional.of(80), db.setOrIncByCaller("key1", "field2", 60, "newone"));
            Assertions.assertEquals(Optional.of(80), db.setOrIncByCaller("key1", "field2", 30, "someone"));
            Assertions.assertEquals(Optional.of(20), db.setOrInc("key2", "field1", 40));
            Assertions.assertEquals(Optional.of(150), db.setOrInc("key3", "field1", 100));
            Assertions.assertEquals(Optional.of("released"), db.unlock("key2"));
            Assertions.assertEquals(Optional.of(90), db.setOrInc("key2", "field1", 70));
            expected = new ArrayList<>(List.of("key3(4)", "key1(3)", "key2(2)"));
            Assertions.assertEquals(expected, db.topNKeys(5));
            expected = new ArrayList<>(List.of("key3(4)", "key1(3)"));
            Assertions.assertEquals(expected, db.topNKeys(2));
            Assertions.assertTrue(db.deleteByCaller("key1", "field1", "newone"));
            Assertions.assertFalse(db.deleteByCaller("key1", "field1", "newone"));
            Assertions.assertTrue(db.deleteByCaller("key1", "field2", "newone"));
            Assertions.assertEquals(Optional.of("released"), db.unlock("key1"));
            Assertions.assertEquals(Optional.of(40), db.setOrIncByCaller("key1", "field1", 40, "someone"));
            Assertions.assertTrue(db.unlock("key1").isEmpty());
            Assertions.assertEquals(Optional.of("acquired"), db.lock("newone", "key1"));
            Assertions.assertEquals(Optional.of(40), db.setOrInc("key1", "field1", 12));
            Assertions.assertTrue(db.lock("newone", "key1").isEmpty());
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("newtwo", "key1"));
            Assertions.assertEquals(Optional.of("released"), db.unlock("key1"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("newtwo", "key1"));
            Assertions.assertTrue(db.deleteByCaller("key1", "field1", "newtwo"));
            Assertions.assertTrue(db.delete("key3", "field2"));
            Assertions.assertTrue(db.delete("key3", "field1"));
            Assertions.assertFalse(db.delete("key4", "field1"));
            expected = new ArrayList<>(List.of("key3(6)", "key2(2)"));
            Assertions.assertEquals(expected, db.topNKeys(5));
            Assertions.assertTrue(db.delete("key3", "field3"));
            Assertions.assertTrue(db.get("key1", "field2").isEmpty());
            Assertions.assertTrue(db.get("key3", "field2").isEmpty());
        });
    }

    @Test
    @Order(8)
    void testLevel3Case08SameCallerForTwoKeys() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of("invalid_request"), db.unlock("key"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("b", "key2"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.unlock("key2"));
            Assertions.assertFalse(db.deleteByCaller("key", "key", "a"));
            Assertions.assertFalse(db.deleteByCaller("key2", "key2", "b"));
            Assertions.assertTrue(db.get("A", "B").isEmpty());
            Assertions.assertTrue(db.get("key2", "key2").isEmpty());
            Assertions.assertEquals(Optional.of(82), db.setOrIncByCaller("key", "key", 82, "b"));
            Assertions.assertEquals(Optional.of(54), db.setOrInc("foo", "bar", 54));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("a", "key"));
            Assertions.assertEquals(Optional.of(50), db.setOrInc("key2", "key2", 50));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("a", "key2"));
            Assertions.assertEquals(Optional.of(82), db.setOrInc("key", "key", 20));
            Assertions.assertEquals(Optional.of(82), db.get("key", "key"));
            Assertions.assertEquals(Optional.of("released"), db.unlock("key2"));
            Assertions.assertEquals(Optional.of(82), db.setOrInc("key", "key", 20));
            Assertions.assertEquals(Optional.of(82), db.get("key", "key"));
            Assertions.assertEquals(Optional.of(80), db.setOrInc("key2", "key2", 30));
            Assertions.assertEquals(Optional.of(80), db.get("key2", "key2"));
            Assertions.assertFalse(db.delete("key", "key"));
        });
    }

    @Test
    @Order(9)
    void testLevel3Case09MixedMultipleOperations1() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of(30), db.setOrIncByCaller("a", "b", 30, "b"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("a", "a"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("b", "a"));
            Assertions.assertTrue(db.setOrIncByCaller("a", "c", 41, "b").isEmpty());
            Assertions.assertTrue(db.setOrInc("a", "d", 45).isEmpty());
            Assertions.assertFalse(db.deleteByCaller("a", "b", "b"));
            Assertions.assertTrue(db.deleteByCaller("a", "b", "a"));
            Assertions.assertTrue(db.setOrInc("a", "b", 21).isEmpty());
            Assertions.assertTrue(db.setOrIncByCaller("a", "b", 51, "b").isEmpty());
            Assertions.assertEquals(Optional.of(27), db.setOrIncByCaller("a", "b", 27, "a"));
            Assertions.assertEquals(Optional.of(27), db.get("a", "b"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("c", "a"));
            Assertions.assertEquals(Optional.of("already_locked"), db.lock("d", "a"));
            Assertions.assertEquals(Optional.of(40), db.setOrIncByCaller("a", "c", 40, "a"));
            Assertions.assertEquals(Optional.of("released"), db.unlock("a"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("b", "a"));
            Assertions.assertEquals(Optional.of(90), db.setOrIncByCaller("a", "c", 50, "b"));
            Assertions.assertEquals(Optional.of(90), db.get("a", "c"));
            Assertions.assertEquals(Optional.of("released"), db.unlock("a"));
            Assertions.assertTrue(db.unlock("a").isEmpty());
            Assertions.assertEquals(Optional.of("acquired"), db.lock("c", "a"));
            Assertions.assertEquals(Optional.of(110), db.setOrIncByCaller("a", "c", 20, "c"));
            Assertions.assertEquals(Optional.of("released"), db.unlock("a"));
            Assertions.assertEquals(Optional.of("acquired"), db.lock("d", "a"));
            Assertions.assertEquals(Optional.of(140), db.setOrIncByCaller("a", "c", 30, "d"));
            Assertions.assertEquals(Optional.of(140), db.get("a", "c"));
        });
    }

    @Test
    @Order(10)
    void testLevel3Case10MixedMultipleOperations2() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("owner1", "a"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("owner2", "a"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("owner1", "a"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("owner1", "a"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.lock("owner3", "a"));
            Assertions.assertEquals(Optional.of(7), db.setOrIncByCaller("a", "a", 7, "owner1"));
            Assertions.assertEquals(Optional.of(70), db.setOrIncByCaller("a", "A", 70, "owner1"));
            int j = 7;
            for (int i = 0; i < 10; i++) {
                j += i;
                Assertions.assertEquals(Optional.of(j), db.setOrInc("a", "a", i));
            }
            for (int i = 0; i < 20; i++) {
                j += i;
                Assertions.assertEquals(Optional.of(j), db.setOrIncByCaller("a", "a", i, "owner1"));
            }
            for (int i = 0; i < 3; i++) {
                j += i;
                Assertions.assertEquals(Optional.of(j), db.setOrIncByCaller("a", "a", i, "owner3"));
            }
            for (int i = 0; i < 5; i++) {
                j += i;
                Assertions.assertEquals(Optional.of(j), db.setOrIncByCaller("a", "a", i, "owner1"));
            }
            Assertions.assertEquals(Optional.of(255), db.get("a", "a"));
            Assertions.assertTrue(db.deleteByCaller("a", "a", "owner2"));
            Assertions.assertFalse(db.deleteByCaller("a", "a", "owner3"));
            Assertions.assertFalse(db.deleteByCaller("a", "a", "owner1"));
            Assertions.assertFalse(db.deleteByCaller("a", "a", "owner1"));
            Assertions.assertFalse(db.deleteByCaller("a", "a", "owner2"));
            Assertions.assertTrue(db.delete("a", "A"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.unlock("a"));
            Assertions.assertEquals(Optional.of("invalid_request"), db.unlock("a"));
            Assertions.assertEquals(Optional.of(7), db.setOrInc("a", "A", 7));
            Assertions.assertEquals(Optional.of(17), db.setOrIncByCaller("a", "A", 10, "owner1"));
            j = 17;
            for (int i = 0; i < 20; i++) {
                j += 10;
                Assertions.assertEquals(Optional.of(j), db.setOrIncByCaller("a", "A", 10, "owner3"));
            }
            Assertions.assertTrue(db.unlock("a").isEmpty());
            Assertions.assertEquals(Optional.of(238), db.setOrInc("a", "A", 21));
            Assertions.assertEquals(Optional.of(238), db.get("a", "A"));
            Assertions.assertTrue(db.unlock("a").isEmpty());
            Assertions.assertTrue(db.deleteByCaller("a", "A", "owner2"));
        });
    }
}
