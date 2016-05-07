package Assignment4.Ex2;

public class CoarseSet<T> extends BaseSet<T> {

    NaiveSet<T> naiveSet = new NaiveSet<>();

    @Override
    public boolean add(T toAdd) {
        synchronized(minNode) {
            return naiveSet.add(toAdd);
        }
    }

    @Override
    public boolean remove(T toRemove) {
        synchronized (minNode) {
            return naiveSet.remove(toRemove);
        }
    }

    @Override
    public boolean contains(T toCheck) {
        synchronized (minNode) {
            return naiveSet.contains(toCheck);
        }
    }
}
