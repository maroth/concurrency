package Assignment3.Ex2;

import Util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Table {
    private int numberOfPhilosophers;
    private Philosopher[] philosophers;
    private Fork[] forks;

    public Table() {
        numberOfPhilosophers = 5;
        philosophers = new Philosopher[numberOfPhilosophers];
        forks = new Fork[numberOfPhilosophers];


        for (int i = 0; i < numberOfPhilosophers; i++) {
            forks[i] = new Fork(i);
        }
        for (int i = 0; i < numberOfPhilosophers; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % numberOfPhilosophers]);
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < numberOfPhilosophers; i++) {
            executorService.execute(philosophers[i]);
        }
        Util.waitForThreads(executorService);
    }
}
