package Assignment4.Ex1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;

public class CASLock implements java.util.concurrent.locks.Lock {

    AtomicInteger atomicInteger = new AtomicInteger(UNLOCKED);

    static final Integer LOCKED = 1;
    static final Integer UNLOCKED = 0;

    @Override
    public void lock() {
        while(!this.atomicInteger.compareAndSet(UNLOCKED, LOCKED)) { }
    }

    @Override
    public void unlock() {
        this.atomicInteger.set(UNLOCKED);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
