package Assignment2;

import Util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

class Ex1 {

    private int counterLimit;
    private int numberOfThreads;
    private PetersonLock petersonLock;
    private volatile int sharedCounter;
    private volatile int[] sharedCounterAccess;

    public static void main(String[] args) {
        int numberOfThreads = Util.parseParam(args, 0);
        int counterLimit = Util.parseParam(args, 1);
        Ex1 ex1 = new Ex1(numberOfThreads, counterLimit);
        ex1.run();
    }

    private Ex1(int numberOfThreads, int counterLimit) {
        this.numberOfThreads = numberOfThreads;
        this.counterLimit = counterLimit;
        this.sharedCounterAccess = new int[numberOfThreads];
        this.petersonLock = new PetersonLock(numberOfThreads);
    }

    private void run() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        long duration = Util.measureTime(() -> {
                    IntStream.range(0, this.numberOfThreads).forEach(
                            index -> executorService.execute(
                                    () -> lockedIncrement(index)));
                    Util.waitForThreads(executorService);
                });

        System.out.println("number of threads: " + this.numberOfThreads);
        System.out.println("duation in ms: " + Util.nanosToMillis(duration));
        System.out.println("Final counter value: " + this.sharedCounter);
        IntStream.range(0, this.numberOfThreads).forEach(
                index -> System.out.println(
                        "Access number " + index + ": " + this.sharedCounterAccess[index]));
    }

    private void lockedIncrement(int threadNumber) {
        while (!counterLimitReached()) {
            this.petersonLock.lock(threadNumber);
            if (!counterLimitReached()) {
                this.sharedCounter += 1;
                this.sharedCounterAccess[threadNumber] += 1;
            }
            this.petersonLock.unlock(threadNumber);
        }
    }

    private boolean counterLimitReached() {
        return this.sharedCounter >= counterLimit;
    }
}