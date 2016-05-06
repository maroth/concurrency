package Assignment4.Ex1;

import Util.Util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestLock {
    public static final int numberOfThreads = 4;
    public static int[] threadsCounter = new int[numberOfThreads];
    public static int counter = 0;

    public static Lock casLock = new CASLock();
    public static Lock ccasLock = new CCASLock();
    public static PetersonLock petersonLock = new PetersonLock(numberOfThreads);

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            testLock(casLock);
            reset();
            testLock(ccasLock);
            reset();
            testPetersonLock();
            reset();
        }
    }

    public static void reset() {
        counter = 0;
        threadsCounter = new int[numberOfThreads];
    }

    public static void testLock(Lock lock) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        long duration;
        duration = Util.measureTime(() -> {
            IntStream.range(0, numberOfThreads).forEach(
                    index -> executorService.execute(
                            () -> lockedIncrement(index, lock)));
            Util.waitForThreads(executorService);
        });

//        Util.print("duration (ms): " + Util.nanosToMillis(duration));
//        Util.print("Final counter value: " + counter);
//        Util.print("number of threads: " + numberOfThreads);

        List sharedCounterAccessList = Arrays.stream(threadsCounter).boxed().collect(Collectors.toList());
//        Util.print("Smallest access count: " + Collections.min(sharedCounterAccessList));
//        Util.print("Biggest access count: " + Collections.max(sharedCounterAccessList));

        String table = "";
        table += lock.getClass().getName();
        table += ",";
        table += numberOfThreads;
        table += ",";
        table += counter;
        table += ",";
        table += Util.nanosToMillis(duration);
        table += ",";
        table += Collections.min(sharedCounterAccessList);
        table += ",";
        table += Collections.max(sharedCounterAccessList);
        System.out.println(table);
    }

    public static void testPetersonLock() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        long duration;
        duration = Util.measureTime(() -> {
            IntStream.range(0, numberOfThreads).forEach(
                    index -> executorService.execute(
                            () -> lockedIncrementPeterson(index)));
            Util.waitForThreads(executorService);
        });

//        Util.print("duration (ms): " + Util.nanosToMillis(duration));
//        Util.print("Final counter value: " + counter);
//        Util.print("number of threads: " + numberOfThreads);
//
        List sharedCounterAccessList = Arrays.stream(threadsCounter).boxed().collect(Collectors.toList());
//        Util.print("Smallest access count: " + Collections.min(sharedCounterAccessList));
//        Util.print("Biggest access count: " + Collections.max(sharedCounterAccessList));

        String table = "";
        table += "peterson";
        table += ",";
        table += numberOfThreads;
        table += ",";
        table += counter;
        table += ",";
        table += Util.nanosToMillis(duration);
        table += ",";
        table += Collections.min(sharedCounterAccessList);
        table += ",";
        table += Collections.max(sharedCounterAccessList);
        System.out.println(table);
    }


    public static boolean counterDone()  {
        return counter < 3000000;
    }

    public static void lockedIncrement(int threadId, Lock lock) {
        while (counterDone()) {
            lock.lock();
            if (counterDone()) {
                threadsCounter[threadId] += 1;
                counter += 1;
            }
            lock.unlock();
        }
    }

    public static void lockedIncrementPeterson(int threadId) {
        while (counterDone()) {
            petersonLock.lock(threadId);
            if (counterDone()) {
                threadsCounter[threadId] += 1;
                counter += 1;
            }
            petersonLock.unlock(threadId);
        }
    }
}
