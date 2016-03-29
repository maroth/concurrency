package Assignment2.Ex1;

import Util.Util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors; import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Ex1 {

    public static boolean debug;
    public static boolean table;
    public static boolean singleProcessor;
    public static boolean isVolatile;

    protected int counterLimit;
    protected int numberOfThreads;
    PetersonLock petersonLock;

    protected abstract int getSharedCounter();
    protected abstract int[] getSharedCounterAccess();

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(
                    "Usage: Ex1 number_of_threads counter_limit " +
                    "args where args == (s)single processor, (d)ebug, (t)table, (v)olatile");
            System.exit(1);
        }
        int numberOfThreads = Util.parseParam(args, 0);
        int counterLimit = Util.parseParam(args, 1);
        if (args.length == 3) {
            Ex1.debug = args[2].contains("d");
            Ex1.table = args[2].contains("t");
            Ex1.singleProcessor = args[2].contains("s");
            Ex1.isVolatile = args[2].contains("v");
        }
        runTest(numberOfThreads, counterLimit);
    }

    public static void runTest(int numberOfThreads, int counterLimit) {
        Ex1 ex1;

        if (Ex1.singleProcessor) {
            try {
                Util.setSolarisAffinity();
            } catch (Exception ex) {
            }
        } else {
        }

        if (Ex1.isVolatile) {
            ex1 = new Ex1Volatile(numberOfThreads, counterLimit);
        } else {
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

        if (Ex1.table) {
            printTableOutput(duration, sharedCounterAccessList);
        } else {
            printRegularOutput(duration, sharedCounterAccessList);
        }
    }

    private void printRegularOutput(long duration, List sharedCounterAccessList) {
        Util.print("\nPETERSON LOCKED SHARED COUNTER TEST");
        if (Ex1.singleProcessor) {
            Util.print("single processor");
        } else {
            Util.print("multiple processors");
        }
        if (Ex1.isVolatile) {
            Util.print("volatile shared counter");
        } else {
            Util.print("non-volatile shared counter");
        }
        Util.print("number of threads: " + this.numberOfThreads);
        Util.print("duration in ms: " + Util.nanosToMillis(duration));
        Util.print("Final counter value: " + this.getSharedCounter());
        Util.print("Smallest access count: " + Collections.min(sharedCounterAccessList));
        Util.print("Biggest access count: " + Collections.max(sharedCounterAccessList));
    }

    private void printTableOutput(long duration, List sharedCounterAccessList) {
        String table = "";
        table += this.numberOfThreads;
        table += ",";
        table += this.getSharedCounter();
        table += ",";
        if (Ex1.isVolatile) table += "v";
        if (Ex1.singleProcessor) table += "s";
        table += ",";
        table += Util.nanosToMillis(duration);
        table += ",";
        table += Collections.min(sharedCounterAccessList);
        table += ",";
        table += Collections.max(sharedCounterAccessList);
        System.out.println(table);
    }

    protected abstract void lockedIncrement(int threadNumber);
}