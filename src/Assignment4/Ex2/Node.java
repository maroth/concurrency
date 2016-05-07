package Assignment4.Ex2;

public class Node<T> {

    public T object;
    public Integer key;
    public Node next;
    public Node previous;

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

    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }

    public int compareTo(T value) {
        return this.key.compareTo(value.hashCode());
    }
}
