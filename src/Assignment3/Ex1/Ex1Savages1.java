package Assignment3.Ex1;

import Util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ex1Savages1 {

    private static int maxNumberOfPortions;

    private static int numberOfSavages;

    public static void main(String[] args) {
        numberOfSavages = 1000;
        maxNumberOfPortions = 3;

        Savage[] savages = new Savage[numberOfSavages];
        Pot pot = new Pot(maxNumberOfPortions);
        Cook cook = new Cook(pot);
        for (int i = 0; i < numberOfSavages; i++) {
            savages[i] = new Savage(pot, cook, false);
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(cook);
        for (int i = 0; i < numberOfSavages; i++) {
            executorService.execute(savages[i]);
        }

        Util.waitForThreads(executorService);
    }
}
