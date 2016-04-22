package Assignment3.Ex1;

import Util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ex1Savages2 {

    private static int maxNumberOfPortions;

    private static int numberOfSavages;

    public static void main(String[] args) {
        numberOfSavages = 3;
        maxNumberOfPortions = 2;

        FairSavage[] savages = new FairSavage[numberOfSavages];
        Pot pot = new Pot(maxNumberOfPortions);
        Cook cook = new Cook(pot);
        FairKeeper fairKeeper = new FairKeeper(numberOfSavages);
        for (int i = 0; i < numberOfSavages; i++) {
            savages[i] = new FairSavage(i, pot, cook, fairKeeper);
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(cook);
        for (int i = 0; i < numberOfSavages; i++) {
            executorService.execute(savages[i]);
        }

        Util.waitForThreads(executorService);
    }
}
