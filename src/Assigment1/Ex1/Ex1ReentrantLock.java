package Assigment1.Ex1;

import java.util.concurrent.locks.ReentrantLock;

public class Ex1ReentrantLock extends Ex1 {

    private static final ReentrantLock lock = new ReentrantLock();

    protected void changeCounter(Integer value) {
        lock.lock();
        counter += value;
        lock.unlock();
    }
}
