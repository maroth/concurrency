package Ex2;

import java.lang.reflect.Array;

public class CircularBuffer<T> {

    private T[] buffer;
    private int inPointer = 0;
    private int outPointer = 0;

    public CircularBuffer(Class<T> c, int size) {
        buffer = (T[]) Array.newInstance(c, size);
    }

    public void produce(T item) {
        buffer[inPointer++] = item;
    }

    public T consume(T item) {
        return buffer[outPointer++];
    }

    private incrementPointer(int pointer) {
        int result = (pointer + 1) % buffer.length;
    }
}
