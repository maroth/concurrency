package Assignment4.Ex2;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Ex2 {
    public static void main(String[] args) {
        // int numberOfThreads = Util.Util.parseParam(args, 1);

        int totalNumbers = 100000;
        System.out.println("Total random numbers: " + totalNumbers);

        runTestSet(2, totalNumbers);
        runTestSet(4, totalNumbers);
        runTestSet(8, totalNumbers);
    }

    private static void runTestSet(int numberOfThreads, int totalNumbers) {
        System.out.println("\nTHREADS: " + numberOfThreads);

        long coarse = runTest(numberOfThreads, totalNumbers, new CoarseSet<Integer>());
        System.out.println("Coarse locked set: " + coarse);

        long fine = runTest(numberOfThreads, totalNumbers, new FineGrainedSet<>());
        System.out.println("Fine grain locked set: " + fine);

        long optimistic = runTest(numberOfThreads, totalNumbers, new OptimisticFineGrainedSet<>());
        System.out.println("Optimistic fine grain locked set: " + optimistic);
    }

    private static long runTest(int numberOfThreads, int totalNumbers, BaseSet set) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        int[] numbers = new int[totalNumbers];
        for (int i = 0; i < totalNumbers; i++) {
            numbers[i] = ThreadLocalRandom.current().nextInt(0, 100);
        }

        int segmentSize = totalNumbers / numberOfThreads;
        int[][] segments = new int[numberOfThreads][segmentSize];
        for (int i = 0; i < numberOfThreads; i++) {
            segments[i] = Arrays.copyOfRange(numbers, i * segmentSize, (i + 1) * segmentSize);
        }

        long duration = Util.Util.measureTime(() -> {
            for (int i = 0; i < numberOfThreads; i++) {
                if (i < (numberOfThreads / 2)) {
                    Runnable adder = new Adder(segments[i], set);
                    executorService.execute(adder);
                } else {
                    Runnable remover = new Remover(segments[i], set);
                    executorService.execute(remover);
                }
            }
            Util.Util.waitForThreads(executorService);
        });

        return Util.Util.nanosToMillis(duration);
    }

    public static class Adder implements Runnable {
        private int[] segment;
        private BaseSet set;

        public Adder(int[] segment, BaseSet set) {
            this.segment = segment;
            this.set = set;
        }

        @Override
        public void run() {
            for (int j = 0; j < segment.length; j++) {
                try {
                    boolean added = set.add(segment[j]);
//                    System.out.println("added " + j + " (" + added + ")");
                } catch (Error e) {
                    System.out.println(e);
                    System.out.println(set.toString());
                    System.exit(1);
                }
            }
        }
    }

    public static class Remover implements Runnable {
        private int[] segment;
        private BaseSet set;

        public Remover(int[] segment, BaseSet set) {
            this.segment = segment;
            this.set = set;
        }

        @Override
        public void run() {
            for (int j = 0; j < segment.length; j++) {
                try {
                    boolean removed = set.remove(segment[j]);
//                    System.out.println("removed " + j + " (" + removed + ")");
                } catch (Error e) {
                    System.out.println(e);
                    System.out.println(set.toString());
                    System.exit(1);
                }
            }
        }
    }
}
