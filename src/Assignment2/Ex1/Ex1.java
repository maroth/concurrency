package Assignment2.Ex1;

import Util.Util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors; import java.util.stream.Collectors;
import java.util.stream.IntStream;

abstract class Ex1 {

    protected int counterLimit;
    protected int numberOfThreads;
    PetersonLock petersonLock;

    protected abstract int getSharedCounter();
    protected abstract int[] getSharedCounterAccess();

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println(
                    "Usage: Ex1 number_of_threads counter_limit " +
                    "[single_processor|multiple_processors] [volatile|nonvolatile]");
            System.exit(1);
        }
        System.out.println("\nPETERSON LOCKED SHARED COUNTER TEST");
        int numberOfThreads = Util.parseParam(args, 0);
        int counterLimit = Util.parseParam(args, 1);
        boolean singleProcessor = args[2].equals("single_processor");
        boolean isVolatile = args[3].equals("volatile");
        runTest(numberOfThreads, counterLimit, singleProcessor, isVolatile);
    }

    public static void runTest(int numberOfThreads, int counterLimit, boolean singleProcessor, boolean isVolatile) {
        Ex1 ex1;

        if (singleProcessor) {
            try {
                Util.setSolarisAffinity();
                System.out.println("Using single processor");
            } catch (Exception ex) {
            }
        } else {
            System.out.println("Using multiple processors");
        }

        if (isVolatile) {
            System.out.println("volatile shared counter");
            ex1 = new Ex1Volatile(numberOfThreads, counterLimit);
        } else {
            System.out.println("non-volatile shared counter");
            ex1 = new Ex1NonVolatile(numberOfThreads, counterLimit);
        }

        ex1.run();
    }

    Ex1(int numberOfThreads, int counterLimit) {
        this.numberOfThreads = numberOfThreads;
        this.counterLimit = counterLimit;
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

        List sharedCounterAccessList =
                Arrays.stream(this.getSharedCounterAccess())
                .boxed()
                .collect(Collectors.toList());
        System.out.println("number of threads: " + this.numberOfThreads);
        System.out.println("duration in ms: " + Util.nanosToMillis(duration));
        System.out.println("Final counter value: " + this.getSharedCounter());
        System.out.println("Smallest access count: " + Collections.min(sharedCounterAccessList));
        System.out.println("Biggest access count: " + Collections.max(sharedCounterAccessList));
    }

    protected abstract void lockedIncrement(int threadNumber);
}