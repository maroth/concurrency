package Assignment4.Ex2;

public class FineGrainedSet<T> extends BaseSet<T> {

    @Override
    public boolean add(T x) {
        return false;
    }

    @Override
    public boolean remove(T x) {
        return false;
    }

    @Override
    public boolean contains(T x) {
        return false;
    }
}
