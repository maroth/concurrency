package Assignment4.Ex2;

public class NaiveSet<T> extends BaseSet<T> {

    @Override
    public boolean add(T toAdd) {
        if (!validate()) throw new Error("invalid");

        Node<T> prev = null;
        Node<T> cursor = minNode;
        while (cursor.isSmallerThan(toAdd)) {
            prev = cursor;
            cursor = cursor.getNext();
        }

        if (cursor.isEqualTo(toAdd)) {
            // toAdd not in set
            return false;
        }

        Node<T> newNode = new Node<>();
        newNode.setObject(toAdd);
        newNode.setNext(cursor);
        prev.setNext(newNode);

        if (!validate()) throw new Error("invalid");
        return true;
    }

    @Override
    public boolean remove(T toRemove) {
        if (!validate()) throw new Error("invalid");

        Node<T> prev = null;
        Node<T> cursor = minNode;

        while (cursor.isSmallerThan(toRemove)) {
            prev = cursor;
            cursor = cursor.getNext();
        }

        if (!cursor.isEqualTo(toRemove)) {
            // set does not contain element
            return false;
        }

        prev.setNext(cursor.getNext());

        if (!validate()) throw new Error("invalid");
        return true;
    }

    @Override
    public boolean contains(T toCheck) {
        if (!validate()) throw new Error("invalid");

        Node<T> cursor = minNode;
        while (cursor.isSmallerThan(toCheck)) {
            cursor = cursor.getNext();
        }

        if (cursor.isEqualTo(toCheck)) {
            return true;
        }

        return false;
    }
}
