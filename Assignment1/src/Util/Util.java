package Util;

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
}
