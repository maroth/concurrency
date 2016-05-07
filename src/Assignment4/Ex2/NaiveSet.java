package Assignment4.Ex2;

public class NaiveSet<T extends Comparable> extends BaseSet<T> {

    @Override
    public boolean add(T toAdd) {
        Node<T> cursor = minNode;
        while (cursor.compareTo(toAdd) < 0) {
            cursor = cursor.next;
        }

        if (cursor.compareTo(toAdd) == 0) {
            return false;
        }

        Node<T> newNode = new Node<>();
        newNode.setObject(toAdd);
        newNode.setNext(cursor);
        newNode.setPrevious(cursor.previous);
        cursor.previous.next = newNode;
        return true;
    }

    @Override
    public boolean remove(T toRemove) {
        Node<T> cursor = minNode;

        while (cursor.compareTo(toRemove) < 0) {
            cursor = cursor.next;
        }

        if (cursor.compareTo(toRemove) != 0 || cursor == maxNode) {
            // set does not contain element
            return false;
        }

        cursor.previous.next = cursor.next;
        cursor.next.previous = cursor.previous;

        return true;
    }

    @Override
    public boolean contains(T toCheck) {
        Node<T> cursor = minNode;

        while (cursor.compareTo(toCheck) < 0) {
            cursor = cursor.next;
        }

        if (cursor.compareTo(toCheck) != 0 || cursor == maxNode) {
            // set does not contain element
            return false;
        }

        return true;
    }
}
