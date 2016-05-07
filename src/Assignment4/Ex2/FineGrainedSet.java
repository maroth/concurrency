package Assignment4.Ex2;

public class FineGrainedSet<T> extends BaseSet<T> {

    @Override
    public boolean add(T toAdd) {
        if (!validate()) throw new Error("invalid");
        Node<T> cursor = minNode;
        minNode.lock();

        while (cursor.compareTo(toAdd) < 0) {
            if (cursor != minNode){
                cursor.getPrevious().unlock();
            }
            cursor = cursor.getNext();
            cursor.lock();
        }

        if (cursor.compareTo(toAdd) == 0) {
            //element already in set
            cursor.unlock();
            cursor.getPrevious().unlock();
            return false;
        }

        Node<T> newNode = new Node<>();
        newNode.setObject(toAdd);
        newNode.setNext(cursor);
        newNode.setPrevious(cursor.getPrevious());

        Node<T> previous = cursor.getPrevious();
        cursor.setPrevious(newNode);
        previous.setNext(newNode);

        previous.unlock();
        cursor.unlock();

        if (!validate()) throw new Error("invalid");
        return true;
    }

    @Override
    public boolean remove(T toRemove) {
        if (!validate()) throw new Error("invalid");
        Node<T> cursor = minNode;
        minNode.lock();

        while (cursor.compareTo(toRemove) < 0) {
            if (cursor != minNode) {
                cursor.getPrevious().unlock();
            }
            cursor = cursor.getNext();
            cursor.lock();
        }

        if (cursor.compareTo(toRemove) != 0) {
            // set does not contain element
            cursor.unlock();
            cursor.getPrevious().unlock();
            return false;
        }

        Node<T> previous = cursor.getPrevious();
        Node<T> next = cursor.getNext();

        previous.setNext(next);
        next.setPrevious(previous);

        if (!validate()) throw new Error("invalid");

        cursor.unlock();
        cursor.getPrevious().unlock();
        return true;
    }

    @Override
    public boolean contains(T toCheck) {
        return false;
    }
}
