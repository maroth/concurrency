package Assignment4.Ex2;

import java.util.HashSet;

public abstract class BaseSet<T> implements Set<T> {
    public Node<T> minNode;
    public Node<T> maxNode;

    public BaseSet() {
        maxNode = new MaxNode<>();
        minNode = new MinNode<>();
        minNode.setNext(maxNode);
        validate();
    }

    @Override
    public String toString() {
        String result = "";
        Node<T> cursor = minNode;
        while (cursor.getNext() != maxNode) {
            cursor = cursor.getNext();
            result += cursor.object.toString();
            result += " ";
        }
        return result;
    }

    public boolean validate() {
        Node<T> cursor = minNode.getNext();
        if (cursor == maxNode) {
            return true;
        }
        T currentValue;
        HashSet<T> hashSet = new HashSet<>();
        while (cursor != maxNode) {
            currentValue = cursor.getObject();
            boolean added = hashSet.add(currentValue);
            if (!added) {
                // list contains duplicate elements
                throw new Error ("list contains duplicate items");
            }
            cursor = cursor.getNext();
            if (cursor.isSmallerThan(currentValue)) {
                // list is not ordered
                throw new Error ("list not in order");
            }
        }

        return true;
    }
}
