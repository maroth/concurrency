package Assignment4.Ex2;

public class FineGrainedSet<T> extends BaseSet<T> {

    @Override
    public boolean add(T toAdd) {
//        if (!validate()) throw new Error("invalid");

        Node<T> prev = minNode;
        Node<T> cursor = minNode.getNext();

        prev.lock();
        cursor.lock();

        while (cursor.isSmallerThan(toAdd)) {
            prev.unlock();
            prev = cursor;
            cursor = cursor.getNext();
            cursor.lock();
        }

        if (cursor.isEqualTo(toAdd)) {
            //element already in set
            prev.unlock();
            cursor.unlock();
            return false;
        }

        Node<T> newNode = new Node<>();
        newNode.setObject(toAdd);

        newNode.setNext(cursor);
        prev.setNext(newNode);

        prev.unlock();
        cursor.unlock();

//        if (!validate()) throw new Error("invalid");
        return true;
    }

    @Override
    public boolean remove(T toRemove) {
//        if (!validate()) throw new Error("invalid");
        Node<T> prev = minNode;
        Node<T> cursor = minNode.getNext();

        prev.lock();
        cursor.lock();

        while (cursor.isSmallerThan(toRemove)) {
            prev.unlock();
            prev = cursor;
            cursor = cursor.getNext();
            cursor.lock();
        }

        if (!cursor.isEqualTo(toRemove)) {
            // set does not contain element
            cursor.unlock();
            prev.unlock();
            return false;
        }

        prev.setNext(cursor.getNext());

        prev.unlock();
        cursor.unlock();

//        if (!validate()) throw new Error("invalid");
        return true;
    }

    @Override
    public boolean contains(T toCheck) {
//        if (!validate()) throw new Error("invalid");

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
