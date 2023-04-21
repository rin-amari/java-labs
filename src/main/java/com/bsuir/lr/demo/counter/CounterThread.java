package com.bsuir.lr.demo.counter;

public class CounterThread extends Thread {
    @Override
    public void start() {
        Counter.increment();
    }
}
