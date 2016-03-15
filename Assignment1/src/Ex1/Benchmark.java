package Ex1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Benchmark {
    public static void main(String[] args) {
        System.out.println(executeCommand("sw_vers"));
        System.out.println(executeCommand("sysctl -n machdep.cpu.brand_string "));

        int i = 1000000;
        System.out.println(runTest(1, 1, i));
        System.out.println(runTest(2, 2, i));
        System.out.println(runTest(4, 4, i));
        System.out.println(runTest(8, 8, i));
    }

    private static String runTest(int m, int n, int i) {
        long noSyncTime = runTest(m, n, i, new Ex1NoSync());
        long syncTime = runTest(m, n, i, new Ex1Sync());
        long reentrantLockTime = runTest(m, n, i, new Ex1ReentrantLock());

        return String.format(
                "m: %d, n: %d, i: %d --> NoSync: %dms (1) / Sync: %dms (%,3f) / ReentrantLock: %dms (%,3f)",
                m, n, i,
                nanosToMillis(noSyncTime),
                nanosToMillis(syncTime), normalizeTime(noSyncTime, syncTime),
                nanosToMillis(reentrantLockTime), normalizeTime(noSyncTime, reentrantLockTime));
    }

    private static long nanosToMillis(long nanos) {
        return nanos / 1000000;
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
