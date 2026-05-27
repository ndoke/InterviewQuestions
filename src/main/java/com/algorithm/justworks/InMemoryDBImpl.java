package com.algorithm.justworks;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryDBImpl implements InMemoryDB {
    // Helper class for Level 4 Undo functionality
    private static class RecordSnapshot {
        final Map<String, Integer> fields;
        final int modificationCount;

        RecordSnapshot(Map<String, Integer> fields, int modificationCount) {
            // Deep copy fields to preserve state at the moment of locking
            this.fields = fields == null ? null : new HashMap<>(fields);
            this.modificationCount = modificationCount;
        }
    }

    private final Map<String, Map<String, Integer>> database = new HashMap<>();
    private final Map<String, Integer> modificationCounts = new HashMap<>();
    private final Map<String, String> locks = new HashMap<>();
    private final Map<String, RecordSnapshot> snapshots = new HashMap<>();

    public InMemoryDBImpl() {}

    // --- Internal Helpers ---

    private boolean canModify(String key, String callerId) {
        String owner = locks.get(key);
        // If no lock exists, anyone can modify. If locked, callerId must match.
        return owner == null || owner.equals(callerId);
    }

    // --- Level 1 & 3 Operations ---

    @Override
    public Optional<Integer> setOrInc(String key, String field, int value) {
        return setOrIncByCaller(key, field, value, null);
    }

    @Override
    public Optional<Integer> setOrIncByCaller(String key, String field, int value, String callerId) {
        if (!canModify(key, callerId)) {
            return get(key, field);
        }

        Map<String, Integer> record = database.computeIfAbsent(key, k -> new HashMap<>());
        int newValue = record.getOrDefault(field, 0) + value;
        record.put(field, newValue);

        // Update modification count
        modificationCounts.put(key, modificationCounts.getOrDefault(key, 0) + 1);

        return Optional.of(newValue);
    }

    @Override
    public Optional<Integer> get(String key, String field) {
        Map<String, Integer> record = database.get(key);
        if (record == null || !record.containsKey(field)) {
            return Optional.empty();
        }
        return Optional.of(record.get(field));
    }

    @Override
    public boolean delete(String key, String field) {
        return deleteByCaller(key, field, null);
    }

    @Override
    public boolean deleteByCaller(String key, String field, String callerId) {
        if (!canModify(key, callerId)) {
            return false;
        }

        Map<String, Integer> record = database.get(key);
        if (record == null || !record.containsKey(field)) {
            return false;
        }

        record.remove(field);

        // Increment modification count for successful delete
        modificationCounts.put(key, modificationCounts.getOrDefault(key, 0) + 1);

        // If the record is now empty, it must be removed from stats immediately
        if (record.isEmpty()) {
            database.remove(key);
            modificationCounts.remove(key);
        }

        return true;
    }

    // --- Level 2 Statistics ---

    @Override
    public List<String> topNKeys(int n) {
        return modificationCounts.entrySet().stream()
                .sorted((e1, e2) -> {
                    int cmp = e2.getValue().compareTo(e1.getValue()); // Descending mods
                    if (cmp != 0) return cmp;
                    return e1.getKey().compareTo(e2.getKey()); // Lexicographical key
                })
                .limit(n)
                .map(e -> String.format("%s(%d)", e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    // --- Level 3 Locking ---

    @Override
    public Optional<String> lock(String callerId, String key) {
        if (!database.containsKey(key)) {
            return Optional.of("invalid_request");
        }

        String currentOwner = locks.get(key);
        if (currentOwner == null) {
            // Take snapshot for potential Undo (Level 4)
            snapshots.put(key, new RecordSnapshot(database.get(key), modificationCounts.getOrDefault(key, 0)));
            locks.put(key, callerId);
            return Optional.of("acquired");
        } else if (currentOwner.equals(callerId)) {
            return Optional.empty();
        } else {
            return Optional.of("already_locked");
        }
    }

    @Override
    public Optional<String> unlock(String key) {
        boolean exists = database.containsKey(key);
        if (!locks.containsKey(key)) {
            return exists ? Optional.empty() : Optional.of("invalid_request");
        }

        locks.remove(key);
        snapshots.remove(key);
        return Optional.of("released");
    }

    // --- Level 4 Advanced Ops ---

    @Override
    public boolean undo(String callerId, String key) {
        String owner = locks.get(key);
        if (owner == null || !owner.equals(callerId)) {
            return false;
        }

        RecordSnapshot snapshot = snapshots.get(key);

        // Revert Modification Count
        if (snapshot.modificationCount > 0) {
            modificationCounts.put(key, snapshot.modificationCount);
        } else {
            modificationCounts.remove(key);
        }

        // Revert Record Data
        if (snapshot.fields == null || snapshot.fields.isEmpty()) {
            database.remove(key);
        } else {
            database.put(key, new HashMap<>(snapshot.fields));
        }

        // Undo always releases the lock
        locks.remove(key);
        snapshots.remove(key);
        return true;
    }

    @Override
    public int logout(String callerId) {
        List<String> keysToUnlock = locks.entrySet().stream()
                .filter(entry -> entry.getValue().equals(callerId))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        for (String key : keysToUnlock) {
            unlock(key);
        }
        return keysToUnlock.size();
    }
}
