package Assignment1.Ex2;

import Util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ex2 {
    public static void main(String[] args) {
        int t = Util.parseParam(args, 0);
        int n = Util.parseParam(args, 1);

        class BufferContents {}

        CircularBuffer<BufferContents> buffer = new CircularBuffer<BufferContents>(BufferContents.class, n);

        Runnable producer = () -> { while (true) buffer.produce(new BufferContents()); };
        Runnable consumer = () -> { while (true) buffer.consume(); };

        ExecutorService executorService = Executors.newCachedThreadPool();
        Util.repeater(t, () -> executorService.execute(producer));
        Util.repeater(t, () -> executorService.execute(consumer));
        Util.waitForThreads(executorService);
    }
}
