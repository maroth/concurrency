package Assignment5.Ex3;

public interface Ex3Queue<T> {
    void enq(T item);

    T deq() throws Exception;
}
