package Util;

import Assignment2.Ex1.Ex1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Util {

    public static void repeater(int iterations, Runnable action) {
        IntStream.rangeClosed(1, iterations).forEach((index) -> action.run());
    }

    public static int parseParam(String[] array, int index) {
        return Integer.parseInt(array[index]);
    }

    public static void waitForThreads(ExecutorService executorService) {
        try {
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            System.out.println("ERROR: INTERRUPTED WHILE WAITING FOR THREADS TO FINISH");
        }
    }

    public static void startMultipleThreads(ExecutorService executorService, Integer multiple, Runnable thread) {
        executorService.execute(() -> repeater(multiple, thread));
    }

    public static long measureTime(Runnable runnable) {
        Long begin = System.nanoTime();
        runnable.run();
        Long end = System.nanoTime();
        return end - begin;
    }

    public static long nanosToMillis(long nanos) {
        return nanos / 1000000;
    }

    public static int setSolarisAffinity() throws Exception {
        int processor;
        try {
            // retrieve process id
            String pid_name = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
            String [] pid_array = pid_name.split("@");
            int pid = Integer.parseInt( pid_array[0] );

            // random processor
            processor = new java.util.Random().nextInt( 32 );

            // Set process affinity to one processor (on Solaris)
            Process p = Runtime.getRuntime().exec("/usr/sbin/pbind -b " + processor + " " + pid);
            p.waitFor();
        }
        catch (Exception err) {
            System.out.println("Unable to bind to single processor (are you on Solaris?)");
            throw(err);
        }
        return processor;
    }

    public static void debug(String message) {
        if (Ex1.debug) {
            System.out.println(message);
        }
    }

    public static void print(String message) {
        if (!Ex1.table) {
            System.out.println(message);
        }
    }

    public static void pf(String message, Object value) {
        System.out.println(String.format(message + ": %d", value));
    }

}
