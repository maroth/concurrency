package Assignment4.Ex2;

public class NaiveSet<T extends Comparable> extends BaseSet<T> {

    @Override
    public boolean add(T toAdd) {
        if (!validate()) throw new Error("invalid");
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

        Node<T> previous = cursor.getPrevious();
        cursor.setPrevious(newNode);
        previous.setNext(newNode);

        if (!validate()) throw new Error("invalid");
        return true;
    }

    @Override
    public boolean remove(T toRemove) {
        if (!validate()) throw new Error("invalid");
        Node<T> cursor = minNode;

        while (cursor.compareTo(toRemove) < 0) {
            cursor = cursor.getNext();
        }

        if (cursor.compareTo(toRemove) != 0) {
            // set does not contain element
            return false;
        }

        Node<T> previous = cursor.getPrevious();
        Node<T> next = cursor.getNext();

        previous.setNext(next);
        next.setPrevious(previous);

        if (!validate()) throw new Error("invalid");
        return true;
    }

    @Override
    public boolean contains(T toCheck) {
        if (!validate()) throw new Error("invalid");
        Node<T> cursor = minNode;

        while (cursor.compareTo(toCheck) < 0) {
            cursor = cursor.getNext();
        }

        if (cursor.compareTo(toCheck) == 0) {
            return true;
        }

        return false;
    }
}
