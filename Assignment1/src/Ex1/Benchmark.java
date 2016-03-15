package Ex1;

/**
 * Created by markusroth on 15/03/16.
 */
public class Benchmark {
    public static void main(String[] args) {
        int i = 100000000;
        System.out.println(runTest(1, 1, i));
        System.out.println(runTest(2, 2, i));
        System.out.println(runTest(4, 4, i));
        System.out.println(runTest(8, 8, i));
    }

    private static String runTest(int m, int n, int i) {
        long reference = runTest(m, n, i, new Ex1NoSync());
        float sync = normalizeTime(reference, runTest(m, n, i, new Ex1Sync()));
        float reentrantLock = normalizeTime(reference, runTest(m, n, i, new Ex1ReentrantLock()));
        String result = String.format(
                "m: %d, n: %d, i: %d --> NoSync: 1 / Sync: %,6f / ReentrantLock: %,6f",
                m, n, i, sync, reentrantLock);
        return result;
    }

    private static float normalizeTime(long reference, long value) {
        return (float) value / (float) reference;
    }

    private static long runTest(int m, int n, int i, Ex1 ex1) {
        return measureTime(() -> ex1.run(m, n, i));
    }

    private static long measureTime(Runnable runnable) {
        Long begin = System.nanoTime();
        runnable.run();
        Long end = System.nanoTime();
        Long duration = end - begin;
        return duration;
    }
}
