package com.algorithm.justworks;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * `InMemoryDB` interface.
 */
public interface InMemoryDB {
    /**
     * Should insert a `field`-`value` pair to the record
     * associated with `key`.
     * All `value` should be integers.
     * If the `field` in the record already exists, increase the
     * current value by the specified `value`.
     * If the record or `field` does not exist, a new one should be
     * created with the value set to the `value`.
     * This operation should return the inserted or updated value
     * (in this level, `Optional.empty()` is never returned).
     */
    default Optional<Integer> setOrInc(String key, String field, int value) {
        // default implementation
        return Optional.empty();
    }

    /**
     * Should return the value within `field` of the record
     * associated with `key`.
     * If the record or the `field` does not exist, should return
     * `Optional.empty()`.
     */
    default Optional<Integer> get(String key, String field) {
        // default implementation
        return Optional.empty();
    }

    /**
     * Should remove `field` from the record associated with `key`.
     * Returns `true` if the `field` was deleted, and `false`
     * otherwise.
     * If all fields in a record have been deleted, the record
     * should be deleted.
     */
    default boolean delete(String key, String field) {
        // default implementation
        return false;
    }

    /**
     * Should return the keys of the top `n` records when all
     * records are sorted in descending order by the number of
     * successful modifications to them, such as adding, deleting,
     * or changing a field within the record.
     * Increasing a value by `0` is considered a successful
     * modification.
     * In case of a tie, keys must be sorted in
     * [lexicographical](keyword://lexicographical-order-for-
     * strings) order of their names.
     * The result should be a string in the following format: `["<k
     * ey1>(<numberOfModifications1>)", "<key2>(<numberOfModificati
     * ons2>)", ..., "<keyN>(<numberOfModificationsN>)"]`.
     * If less than `n` records exist in the system, return all
     * keys in the described format.
     * If there are no records at all, return an empty list.
     * If a record is deleted from the database (i.e., if all of
     * its fields are deleted), the counter for the number of
     * successful modifications to the record must also be deleted.
     */
    default List<String> topNKeys(int n) {
        // default implementation
        return Collections.emptyList();
    }

    /**
     * If a record with the given `key` does not exist, or is not
     * locked, or the user associated with `callerId` is the one
     * who has locked it, perform the `setOrInc` operation
     * described in Level 1.
     * Otherwise, ignore the operation and return the existing
     * `value` if the `field` exists; otherwise, return
     * `Optional.empty()`.
     */
    default Optional<Integer> setOrIncByCaller(String key, String field, int value, String callerId) {
        // default implementation
        return Optional.empty();
    }

    /**
     * If the record with the given `key` exists, and it is either
     * not locked or the user associated with `callerId` is the one
     * who has locked it, perform the `delete` operation described
     * in Level 1.
     * Otherwise, ignore the operation and return `false`.
     */
    default boolean deleteByCaller(String key, String field, String callerId) {
        // default implementation
        return false;
    }

    /**
     * Should request to lock the record associated with `key` to
     * the user associated with `callerId`.
     * After locking a record, the `setOrInc` and `delete`
     * operations cannot be performed on that record, so they
     * should be ignored if called.
     * The operation returns one of the following to signal lock
     * status:
     *   * `"acquired"` - if the `key` is valid and the record is
     *   successfully locked to the user;
     *   * `"already_locked"` - if the record is already locked by
     *   another user;
     *   * `Optional.empty()` - if the record is already locked by
     *   the same user;
     *   * `"invalid_request"` - if the `key` doesn't exist in the
     *   database.
     */
    default Optional<String> lock(String callerId, String key) {
        // default implementation
        return Optional.empty();
    }

    /**
     * Should release the current lock on the record associated
     * with `key`.
     * The only way to release the lock from any record is to call
     * the `unlock` operation explicitly.
     * It does nothing if the record is not currently locked by any
     * users.
     * If the record is not present in the database when unlocked,
     * meaning it was entirely deleted and then unlocked, the lock
     * should be released.
     * **Note** that if the key was entirely deleted and re-created
     * with the same `key` during one lock, it is considered the
     * same record.
     * Returns one of the following to signal lock status:
     *   * `"released"` - if the lock was released during the
     *   operation, including the case when the record was locked,
     *   entirely deleted and then unlocked;
     *   * `Optional.empty()` - if the `key` exists, but was not
     *   locked;
     *   * `"invalid_request"` - if the `key` doesn't exist in the
     *   database.
     */
    default Optional<String> unlock(String key) {
        // default implementation
        return Optional.empty();
    }

    /**
     * Should roll back the record associated with `key` to the
     * state before `callerId` locked the record - all operations
     * on the record during the lock should be rolled back and
     * undone, and the modification counter should be reverted.
     * It also releases the lock from `callerId`.
     * It does nothing if the record is not currently locked by
     * `callerId`.
     * Returns `true` if the rollback was successful or `false`
     * otherwise.
     */
    default boolean undo(String callerId, String key) {
        // default implementation
        return false;
    }

    /**
     * Should release all records locked by user `callerId`.
     * Returns the number of released records.
     */
    default int logout(String callerId) {
        // default implementation
        return 0;
    }
}
