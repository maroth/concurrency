package Assignment5.Ex3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Ex3 {

    public static AtomicInteger mySyncPoint = new AtomicInteger(0);

    public static void fillQueue(Ex3Queue<Object> queue, int count, int numberOfThreads) {
        mySyncPoint.addAndGet(1);
        while (mySyncPoint.get() < numberOfThreads) {
        }
        for (int i = 0; i < count; i++) {
            queue.enq(new Object());
        }
    }

    public static void emptyQueue(Ex3Queue<Object> queue, int count, int numberOfThreads) {
        mySyncPoint.addAndGet(1);
        while (mySyncPoint.get() < numberOfThreads) {
        }
        for (int i = 0; i < count; i++) {
            try {
                queue.deq();
            } catch (Exception e) {
                //queue is empty
            }
        }
    }

    public static void main(String[] args) {
        int counter = Util.Util.parseParam(args, 0);
        int iterations = Util.Util.parseParam(args, 1);
        System.out.println("Threads, lockBased, lockFree");
        for (int i = 0; i < iterations; i++) {
            runTestSuite(2, counter);
        }
        for (int i = 0; i < iterations; i++) {
            runTestSuite(4, counter);
        }
        for (int i = 0; i < iterations; i++) {
            runTestSuite(8, counter);
        }
    }

    private static void runTestSuite(int numberOfThreads, int counter) {
//        System.out.println("\nNumber of threads: " + numberOfThreads);

        long lockBasedDuration = runTest(new UnboundedLockBasedQueue<>(), counter, numberOfThreads);
//        System.out.println("unbounded lock based queue (ms): " + lockBasedDuration);
        long lockFreeDuration = runTest(new UnboundedLockFreeQueue<>(), counter, numberOfThreads);
//        System.out.println("unbounded lock free queue (ms): " + lockFreeDuration);

        System.out.println(String.format("%d, %d, %d", numberOfThreads, lockBasedDuration, lockFreeDuration));
    }

    private static long runTest(Ex3Queue<Object> queue, int totalNumbers, int numberOfThreads) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        int segmentSize = totalNumbers / numberOfThreads;

        long duration = Util.Util.measureTime(() -> {
            for (int i = 0; i < numberOfThreads; i++) {
                if (i < (numberOfThreads / 2)) {
                    Runnable enqueuer = () -> fillQueue(queue, segmentSize, numberOfThreads);
                    executorService.execute(enqueuer);
                } else {
                    Runnable dequeuer = () -> emptyQueue(queue, segmentSize, numberOfThreads);
                    executorService.execute(dequeuer);
                }
            }
            Util.Util.waitForThreads(executorService);
        });

        return Util.Util.nanosToMillis(duration);
    }
}
