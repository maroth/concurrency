package Ex1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

/**
 * Created by markusroth on 15/03/16.
 */
public abstract class Ex1 {

    protected abstract void changeCounter(Integer value);

    volatile protected static int counter = 0;

    public int run(Integer m, Integer n, Integer i) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        repeater(n, () -> startThread(executorService, i, increment));
        repeater(m, () -> startThread(executorService, i, decrement));
        waitForThreads(executorService);

        return counter;
    }

    private static void repeater(int iterations, Runnable action) {
        IntStream.rangeClosed(1, iterations).forEach((index) -> action.run());
    }

    Runnable increment = () -> changeCounter(1);
    Runnable decrement = () -> changeCounter(-1);

    private static void startThread(ExecutorService executorService, Integer iterations, Runnable action) {
        executorService.execute(() -> repeater(iterations, action));
    }

    private static void waitForThreads(ExecutorService executorService) {
        try {
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            System.out.println("ERROR: INTERRUPTED WHILE WAITING FOR THREADS TO FINISH");
        }
    }
}
