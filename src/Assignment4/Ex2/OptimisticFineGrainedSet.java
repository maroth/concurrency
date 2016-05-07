package Assignment4.Ex2;

public class OptimisticFineGrainedSet<T> extends BaseSet<T> {

    @Override
    public boolean add(T toAdd) {
        if (!validate()) throw new Error("invalid");

        Node<T> prev;
        Node<T> cursor;

        while (true) {
            prev = minNode;
            cursor = minNode.getNext();

            while (cursor.isSmallerThan(toAdd)) {
                prev = cursor;
                cursor = cursor.getNext();
            }

            try {
                prev.lock();
                cursor.lock();

                if (validateOptimistic(prev, cursor)) {
                    if (cursor.isEqualTo(toAdd)) {
                        //element already in set
                        return false;
                    } else {
                        Node<T> newNode = new Node<>();
                        newNode.setObject(toAdd);
                        newNode.setNext(cursor);
                        prev.setNext(newNode);
                        return true;
                    }
                }
            } finally {
                prev.unlock();
                cursor.unlock();
//                if (!validate()) throw new Error("invalid");
            }
        }
    }

    @Override
    public boolean remove(T toRemove) {
//      if (!validateOptimistic()) throw new Error("invalid");

        Node<T> prev;
        Node<T> cursor;

        while (true) {
            prev = minNode;
            cursor = minNode.getNext();
            while (cursor.isSmallerThan(toRemove)) {
                if (cursor.isEqualTo(toRemove)) {
                    break;
                }
                prev = cursor;
                cursor = cursor.getNext();
            }

            try {
                prev.lock();
                cursor.lock();
                if (validateOptimistic(prev, cursor)) {
                    if (cursor.isEqualTo(toRemove)) {
                        prev.setNext(cursor.getNext());
                        return true;
                    } else {
                        //object not found
                        return false;
                    }
                }
            } finally {
                prev.unlock();
                cursor.unlock();
            }
        }
//        if (!validateOptimistic()) throw new Error("invalid");
    }


    @Override
    public boolean contains(T toCheck) {
//        if (!validateOptimistic()) throw new Error("invalid");

        while (true) {
            Node<T> prev = minNode;
            Node<T> cursor = prev.getNext();

            while (cursor.isSmallerThan(toCheck)) {
                prev = cursor;
                cursor = cursor.getNext();
            }

            try {
                prev.lock();
                cursor.lock();

                if (validateOptimistic(prev, cursor)) {
                    return (cursor.isEqualTo(toCheck));
                }

            } finally {
                prev.unlock();
                cursor.unlock();
            }
        }
    }

    private boolean validateOptimistic(Node<T> prev, Node<T> cursor) {
        Node<T> validation_cursor = minNode;
        while (validation_cursor.isSmallerOrEqual(cursor) && validation_cursor != maxNode) {
            if (validation_cursor == prev) {
                return prev.getNext() == cursor;
            }
            validation_cursor = validation_cursor.getNext();
        }

        //prev not reachable
        return false;
    }
}
