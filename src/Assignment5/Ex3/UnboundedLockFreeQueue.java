package Assignment5.Ex3;

import java.util.concurrent.atomic.AtomicReference;

public class UnboundedLockFreeQueue<T> implements Ex3Queue<T> {

    class Node<T> {
        public T value;
        public AtomicReference<Node> next;

        public Node(T value) {
            this.value = value;
            next = new AtomicReference<>(null);
        }
    }

    private AtomicReference<Node> tail;
    private AtomicReference<Node> head;

    public UnboundedLockFreeQueue() {
        Node<T> sentinelNode = new Node<>(null);
        tail = new AtomicReference<>(sentinelNode);
        head = new AtomicReference<>(sentinelNode);
    }

    public void enq(T value) {
        Node newNode = new Node(value);
        while (true) {
            Node<T> last = tail.get();
            Node next = last.next.get(); //what happens if the queue is empty here?
            if (last == tail.get()) {
                if (next == null) {
                    if (last.next.compareAndSet(next, newNode)) {
                        tail.compareAndSet(last, newNode);
                        return;
                    }
                } else {
                    tail.compareAndSet(last, next);
                }
            }
        }
    }

    public T deq() throws Exception {
        while (true) {
            Node<T> first = head.get();
            Node<T> last = tail.get();
            Node<T> next = first.next.get();
            if (first == head.get()) {
                if (first == last) {
                    if (next == null) {
//                        System.out.println("queue empty");
                        throw new Exception();
                    }
                    tail.compareAndSet(last, next);
                } else {
                    T value = next.value;
                    if (head.compareAndSet(first, next))
                        return value;
                }
            }
        }
    }
}
