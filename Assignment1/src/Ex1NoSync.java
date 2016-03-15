import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Ex1NoSync {

    volatile static int counter = 0;

    public static void main(String[] args) {
        Function<Integer, Integer> parseParam = (index) -> Integer.parseInt(args[index]);
        Integer m = parseParam.apply(0);
        Integer n = parseParam.apply(1);
        Integer i = parseParam.apply(2);

        BiConsumer<Integer, Runnable> repeater =
                (iterations, action) -> IntStream.rangeClosed(1, iterations).forEach((index) -> action.run());

        Runnable increment = () -> counter += 1;
        Runnable decrement = () -> counter -= 1;

        ExecutorService executorService = Executors.newCachedThreadPool();

        BiConsumer<Integer, Runnable> startThread =
                (iterations, action) -> executorService.execute(() -> repeater.accept(iterations, action));

        repeater.accept(n, () -> startThread.accept(i, increment));
        repeater.accept(m, () -> startThread.accept(i, decrement));

        try {
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {

        }

        System.out.println(counter);
    }
}
