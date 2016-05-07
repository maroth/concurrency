package Assignment4.Ex2;

public interface Set<T extends Comparable> {
    boolean add(T x);
    boolean remove(T x);
    boolean contains(T x);
    boolean validate();
}
