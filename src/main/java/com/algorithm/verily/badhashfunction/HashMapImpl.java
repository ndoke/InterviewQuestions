package com.algorithm.verily.badhashfunction;

import java.util.LinkedList;
import java.util.List;

class HashMapImpl {
    private int capacity = 1;
    List<KVPair>[] data;

    public HashMapImpl() {
        data = new LinkedList[capacity];
    }

    public void put(String key, Object val) {
        // calculate the hash value and store the value
        int index = hashFunc(key);
        if (data[index] == null) {
            data[index] = new LinkedList<>();
        }
        data[index].add(new KVPair(key, val));
    }

    public Object get(String key) {
        // calculate the hash value and retrive the value
        int index = hashFunc(key);
        List<KVPair> kvps = data[index];
        for (KVPair kvp : kvps) {
            if (kvp.key.equals(key)) {
                return kvp.val;
            }
        }

        return null;
    }

    public void remove(String key) {
        // calculate the hash value and retrive the value
        int index = hashFunc(key);
        for (KVPair kvPair : data[index]) {
            if (kvPair.key.equals(key)) {
                data[index].remove(kvPair);
                break;
            }
        }
    }

    private int hashFunc(String key) {
        int hash = key == null ? 0 : key.hashCode();
        return Math.abs(hash) % capacity; // 0 - 99
    }
}
