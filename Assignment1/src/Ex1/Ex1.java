package Ex1;

import Util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Util.Util.repeater;

abstract class Ex1 {

    protected abstract void changeCounter(Integer value);

    volatile static int counter = 0;

    public int run(Integer m, Integer n, Integer i) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        repeater(n, () -> Util.startThread(executorService, i, increment));
        repeater(m, () -> Util.startThread(executorService, i, decrement));
        Util.waitForThreads(executorService);

        return counter;
    }

    private final Runnable increment = () -> changeCounter(1);
    private final Runnable decrement = () -> changeCounter(-1);

}
