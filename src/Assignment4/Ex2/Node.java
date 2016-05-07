package Assignment4.Ex2;

import java.util.concurrent.locks.ReentrantLock;

public class Node<T> {

    public T object;
    private Integer key;
    private Node next;
    private ReentrantLock lock = new ReentrantLock();

    public void setObject(T object) {
        this.object = object;
        key = object.hashCode();
    }

    public T getObject() {
        return object;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getNext() {
        return next;
    }

    public boolean isEqualTo(T value) {
        return this.object.equals(value);
    }

    public boolean isSmallerThan(T value) {
        return this.getKey().compareTo(value.hashCode()) < 0;
    }

    public boolean isSmallerOrEqual(Node<T> node) {
        return this.getKey().compareTo(node.getKey()) <= 0;
    }

    public Integer getKey() {
        return this.key;
    }

    public void lock() {
        this.lock.lock();
    }

    public void unlock() {
        this.lock.unlock();
    }
}
