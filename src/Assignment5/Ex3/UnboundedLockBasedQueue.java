package Assignment5.Ex3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnboundedLockBasedQueue<T> implements Ex3Queue<T> {

    private Lock enqLock = new ReentrantLock();
    private Lock deqLock = new ReentrantLock();

    private Node tail;
    private Node head;

    class Node {
        public T value;
        public Node next;
        public Node(T value) {
            this.value = value;
            next = null;
        }

    }
    public void enq (T value) {
        enqLock.lock();
        try {
            Node newNode = new Node(value);
            tail.next = newNode;
            tail = newNode;
        } finally {
            enqLock.unlock();
        }
    }

    public UnboundedLockBasedQueue() {
        tail = new Node(null);
        head = tail;
    }

    public T deq() throws Exception {
        T result;
        deqLock.lock();
        try {
            if (head.next == null) {
//                System.out.println("queue empty");
                throw new Exception();
            }
            result = head.next.value;
            head = head.next;
        } finally {
            deqLock.unlock();
        }
        return result;
    }
}
