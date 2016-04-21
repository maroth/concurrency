package Assignment3.Ex2;

import java.util.concurrent.locks.ReentrantLock;

public class Fork {
    ReentrantLock lock;
    private int id;

    public Fork(int id) {
        this.lock = new ReentrantLock();
        this.id = id;
    }

    public void grab() {
        lock.lock();
    }

    public void release() {
        lock.unlock();
    }

    public int getId() {
        return id;
    }
}
