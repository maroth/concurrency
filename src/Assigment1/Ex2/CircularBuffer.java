package Assigment1.Ex2;

import java.lang.reflect.Array;
import java.util.concurrent.Semaphore;

public class CircularBuffer<T> {

    private final Semaphore semaphore;
    private T[] buffer;
    private int inPointer = 0;
    private int outPointer = 0;

    public CircularBuffer(Class<T> c, int size) {
        buffer = (T[]) Array.newInstance(c, size);
        semaphore = new Semaphore(size);
    }

    public void produce(T item) {
        int newInPointer = incrementPointer(inPointer);
        semaphore.release();
        buffer[inPointer] = item;
        inPointer = newInPointer;
    }

    public T consume() {
        T result = null;
        try {
            semaphore.acquire();
            int newOutPointer = incrementPointer(outPointer);
            result = buffer[outPointer];
            outPointer = newOutPointer;
        } catch (InterruptedException ex) {

        }
        return result;
    }

    private int incrementPointer(int pointer) {
        return (pointer + 1) % buffer.length;
    }
}
