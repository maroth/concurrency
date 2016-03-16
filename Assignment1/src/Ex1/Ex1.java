package Ex1;

import Util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import static Util.Util.repeater;

abstract class Ex1 {

    protected abstract void changeCounter(Integer value);

    volatile int counter = 0;

    private static class TestResult {
        long duration;
        int endCounter;
    }

    public static void main(String[] args) {
        int n = Util.parseParam(args, 0);
        int m = Util.parseParam(args, 1);
        int i = Util.parseParam(args, 2);

        System.out.println(executeCommand("sw_vers"));
        System.out.println(executeCommand("sysctl -n machdep.cpu.brand_string"));

        TestResult noSyncResult = runTest(m, n, i, new Ex1NoSync());
        TestResult syncResult = runTest(m, n, i, new Ex1Sync());
        TestResult reentrantLockResult = runTest(m, n, i, new Ex1ReentrantLock());

        System.out.println(String.format(
                "Test run with m: %d, n: %d, i: %d",
                m, n, i));

        System.out.println(String.format(
                "No Synchronization: %dms (1) with counter result %d",
                nanosToMillis(noSyncResult.duration), noSyncResult.endCounter));

        System.out.println(String.format(
                "Lock: %dms (%,3f) with counter result %d",
                nanosToMillis(syncResult.duration),
                normalizeTime(noSyncResult.duration, syncResult.duration),
                syncResult.endCounter));

        System.out.println(String.format(
                "Reentrant Lock: %dms (%,3f) with counter result %d",
                nanosToMillis(reentrantLockResult.duration),
                normalizeTime(noSyncResult.duration, reentrantLockResult.duration),
                reentrantLockResult.endCounter));
    }

    private final Runnable increment = () -> changeCounter(1);
    private final Runnable decrement = () -> changeCounter(-1);

    private static long nanosToMillis(long nanos) {
        return nanos / 1000000;
    }

    private static float normalizeTime(long reference, long value) {
        return (float) value / (float) reference;
    }

    private static TestResult runTest(int m, int n, int i, Ex1 ex1) {
        TestResult result = new TestResult();
        result.duration = measureTime(() -> result.endCounter = ex1.run(m, n, i));
        return result;
    }

    private int run(Integer m, Integer n, Integer i) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        repeater(n, () -> Util.startMultipleThreads(executorService, i, increment));
        repeater(m, () -> Util.startMultipleThreads(executorService, i, decrement));
        Util.waitForThreads(executorService);

        return counter;
    }

    private static long measureTime(Runnable runnable) {
        Long begin = System.nanoTime();
        runnable.run();
        Long end = System.nanoTime();
        return end - begin;
    }

    //from: http://www.mkyong.com/java/how-to-execute-shell-command-from-java/
    private static String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();
    }
}
