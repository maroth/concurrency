package Ex1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by markusroth on 15/03/16.
 */
public class Benchmark {
    public static void main(String[] args) {
        int i = 1000000;
        String cpuInfo;
        cpuInfo = executeCommand("sysctl -n machdep.cpu.brand_string ");
        System.out.println(cpuInfo);
        System.out.println(runTest(1, 1, i));
        System.out.println(runTest(2, 2, i));
        System.out.println(runTest(4, 4, i));
        System.out.println(runTest(8, 8, i));
    }

    private static String runTest(int m, int n, int i) {
        long reference = runTest(m, n, i, new Ex1NoSync());
        float sync = normalizeTime(reference, runTest(m, n, i, new Ex1Sync()));
        float reentrantLock = normalizeTime(reference, runTest(m, n, i, new Ex1ReentrantLock()));
        return String.format(
                "m: %d, n: %d, i: %d --> NoSync: 1 / Sync: %,6f / ReentrantLock: %,6f",
                m, n, i, sync, reentrantLock);
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
