package Assignment4.Ex2;

import java.util.HashSet;

public abstract class BaseSet<T> implements Set<T> {
    public Node<T> minNode;
    public Node<T> maxNode;

    public BaseSet() {
        maxNode = new MaxNode<>();
        minNode = new MinNode<>();
        minNode.next = maxNode;
        maxNode.previous = minNode;
        validate();
    }

    public boolean validate() {
        Node<T> cursor = minNode.next;
        if (cursor == maxNode) {
            //empty set
            return true;
        }
        T currentValue;
        HashSet<T> hashSet = new HashSet<>();
        while (cursor.next != maxNode) {
            if (cursor.next.previous != cursor) {
                // list not consistent
                return false;
            }
            currentValue = cursor.getObject();
            boolean added = hashSet.add(currentValue);
            if (!added) {
                // list contains duplicate elements
                return false;
            }
            cursor = cursor.next;
            if (cursor.compareTo(currentValue) < 0) {
                // list is not ordered
                return false;
            }
        }

        return true;
    }
}
