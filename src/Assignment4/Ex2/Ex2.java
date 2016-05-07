package Assignment4.Ex2;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Ex2 {
    public static void main(String[] args) {
        // int numberOfThreads = Util.Util.parseParam(args, 1);
        int numberOfThreads = 2;
        int totalNumbers = 100000;
        BaseSet set = new NaiveSet<Integer>();
        ExecutorService executorService = Executors.newCachedThreadPool();

        int[] numbers = new int[totalNumbers];
        for (int i = 0; i < 100000; i++) {
            numbers[i] = ThreadLocalRandom.current().nextInt(0, 100);
        }

        int segmentSize = totalNumbers / numberOfThreads;
        int[][] segments = new int[numberOfThreads][segmentSize];
        for (int i = 0; i < numberOfThreads; i++) {
            segments[i] = Arrays.copyOfRange(numbers, i * segmentSize, (i + 1) * segmentSize);
        }

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
    }

    public static class Adder implements Runnable {
        private int[] segment;
        private Set set;

        public Adder(int[] segment, Set set) {
            this.segment = segment;
            this.set = set;
        }

        @Override
        public void run() {
            for (int j = 0; j < segment.length; j++) {
                set.add(segment[j]);
                System.out.println("added " + j);
            }
        }
    }

    public static class Remover implements Runnable {
        private int[] segment;
        private Set set;

        public Remover(int[] segment, Set set) {
            this.segment = segment;
            this.set = set;
        }

        @Override
        public void run() {
            for (int j = 0; j < segment.length; j++) {
                set.remove(segment[j]);
                System.out.println("removed " + j);
            }
        }
    }
}