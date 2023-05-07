package com.bsuir.lr.demo.counter;

public class Counter {
    private static int count = 0;
    public static synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
