package Assignment4.Ex2;

import java.util.HashSet;

public abstract class BaseSet<T> implements Set<T> {
    public Node<T> minNode;
    public Node<T> maxNode;

    public BaseSet() {
        maxNode = new MaxNode<>();
        minNode = new MinNode<>();
        minNode.setNext(maxNode);
        maxNode.setPrevious(minNode);
        validate();
    }

    public boolean validate() {
        Node<T> cursor = minNode.getNext();
        if (cursor == maxNode) {
            //empty set
            return true;
        }
        T currentValue;
        HashSet<T> hashSet = new HashSet<>();
        while (cursor.getNext() != maxNode) {
            if (cursor.getNext().getPrevious() != cursor) {
                // list not consistent
                return false;
            }
            currentValue = cursor.getObject();
            boolean added = hashSet.add(currentValue);
            if (!added) {
                // list contains duplicate elements
                return false;
            }
            cursor = cursor.getNext();
            if (cursor.compareTo(currentValue) < 0) {
                // list is not ordered
                return false;
            }
        }

        return true;
    }
}
