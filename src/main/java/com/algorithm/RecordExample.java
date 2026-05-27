package com.algorithm;

public record RecordExample(int n) {
//    public RecordExample(int n) {
//        this.n = n << 2;
//    }

    public RecordExample {
        n = n << 2;
    }
}
