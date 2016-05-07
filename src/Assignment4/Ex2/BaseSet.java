package Assignment4.Ex2;

import java.util.HashSet;

public abstract class BaseSet<T extends Comparable> implements Set<T> {
    public Node<T> minNode;
    public Node<T> maxNode;

    public BaseSet() {
        maxNode = new Node<T>(null, null);
        maxNode.isMaxNode = true;
        minNode = new Node<T>(null, maxNode);
        minNode.isMinNode = true;
        validate();
    }

    public boolean validate() {
        Node cursor = minNode.next;
        if (cursor == maxNode) {
            //empty set
            return true;
        }
        Comparable currentValue;
        HashSet<Comparable> hashSet = new HashSet<>();
        while (cursor.next != maxNode) {
            currentValue = cursor.object;
            boolean added = hashSet.add(currentValue);
            if (!added) {
                // list contains duplicate elements
                return false;
            }
            cursor = cursor.next;
            if (cursor.object.compareTo(currentValue) < 0) {
                // list is not ordered
                return false;
            }
        }
        return true;
    }
}
