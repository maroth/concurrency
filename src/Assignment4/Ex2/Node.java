package Assignment4.Ex2;

public class Node<T> {

    public T object;
    public Integer key;
    private Node next;
    private Node previous;

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

    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }

    public Node<T> getPrevious() {
        return previous;
    }

    public int compareTo(T value) {
        return this.key.compareTo(value.hashCode());
    }
}
