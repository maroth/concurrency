package Assignment3.Ex1;

import Util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ex1Savages1 {

    private static int maxNumberOfPortions;

    private static int numberOfSavages;

    public static void main(String[] args) {
        numberOfSavages = 100;
        maxNumberOfPortions = 3;

        Savage[] savages = new Savage[numberOfSavages];
        Pot pot = new Pot(maxNumberOfPortions);
        Cook cook = new Cook(pot);
        for (int i = 0; i < numberOfSavages; i++) {
            savages[i] = new Savage(i, pot, cook);
        }

        ExecutorService cookExecutor = Executors.newSingleThreadExecutor();
        cookExecutor.execute(cook);

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < numberOfSavages; i++) {
            executorService.execute(savages[i]);
        }

        Util.waitForThreads(executorService);
        cook.stop();
    }
}
