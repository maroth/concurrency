package Assignment4.Ex2;

public class NaiveSet<T extends Comparable> extends BaseSet<T> {

    @Override
    public boolean add(T toAdd) {
        Node<T> cursor = minNode;
        while (cursor.compareTo(toAdd) < 0) {
            cursor = cursor.getNext();
        }

        if (cursor.compareTo(toAdd) == 0) {
            return false;
        }

        Node<T> newNode = new Node<>();
        newNode.setObject(toAdd);
        newNode.setNext(cursor);
        newNode.setPrevious(cursor.getPrevious());
        cursor.setPrevious(newNode);
        cursor.getPrevious().setNext(newNode);
        if (!validate()) throw new Error("invalid");
        return true;
    }

    @Override
    public boolean remove(T toRemove) {
        Node<T> cursor = minNode;

        while (cursor.compareTo(toRemove) < 0) {
            cursor = cursor.getNext();
        }

        if (cursor.compareTo(toRemove) != 0 || cursor == maxNode) {
            // set does not contain element
            return false;
        }

        cursor.getPrevious().setNext(cursor.getNext());
        cursor.getNext().setPrevious(cursor.getPrevious());

        if (!validate()) throw new Error("invalid");
        return true;
    }

    @Override
    public boolean contains(T toCheck) {
        Node<T> cursor = minNode;

        while (cursor.compareTo(toCheck) < 0) {
            cursor = cursor.getNext();
        }

        if (cursor.compareTo(toCheck) != 0 || cursor == maxNode) {
            // set does not contain element
            return false;
        }

        return true;
    }
}
