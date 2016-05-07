package Assignment4.Ex2;

public class Node<T extends Comparable> {

    public T object;
    public int key;
    public Node next;
    public boolean isMaxNode = false;
    public boolean isMinNode = false;

    public Node(T value, Node next) {
        this.object = value;
        if (value != null) {
            this.key = value.hashCode();
        }
        this.next = next;
    }

    public int compareTo(T value) {
        if (isMaxNode) return 1;
        if (isMinNode) return -1;
        return this.object.compareTo(value);
    }

    public int compareTo(Node<T> other) {
        if (isMaxNode || other.isMinNode) return 1;
        if (isMinNode || other.isMaxNode) return -1;
        return this.object.compareTo(other.object);
    }
}
