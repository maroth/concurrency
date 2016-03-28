package Assignment2.Ex1;

import java.util.concurrent.atomic.AtomicInteger;

import static Util.Util.log;

public class PetersonLock {
    private int numberOfThreads;
    private AtomicInteger[] level;
    private AtomicInteger[] victim;

    public PetersonLock(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        this.level = new AtomicInteger[numberOfThreads];
        this.victim = new AtomicInteger[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            this.level[i] = new AtomicInteger(-1);
            this.victim[i] = new AtomicInteger(-1);
        }
    }

    private void printStatus() {
        for (int i = 0; i < numberOfThreads; i++) {
            System.out.println("Level[" + i + "]: " + level[i]);
            System.out.println("Victim[" + i + "]: " + victim[i]);
        }
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }

    public void lock(int threadNumber) {
        for (int currentLevel = 1; currentLevel < this.numberOfThreads; currentLevel++) {
            log("Thread " + threadNumber + " entering level " + currentLevel);
            this.level[threadNumber].set(currentLevel);
            this.victim[currentLevel].set(threadNumber);
            while(threadWithHigherOrEqualLevelExists(threadNumber, currentLevel)
                    && threadIsVictimOnLevel(threadNumber, currentLevel)) {
            }
        }
        log(" Thread " + threadNumber + " got lock");
    }

    private boolean threadIsVictimOnLevel(int threadNumber, int level) {
        return this.victim[level].get() == threadNumber;
    }

    private boolean threadWithHigherOrEqualLevelExists(int threadNumber, int currentLevel) {
        for (int index = 0; index < this.level.length; index++) {
            if (this.level[index].get() >= currentLevel && index != threadNumber) {
                return true;
            }
        }
        return false;
    }

    public void unlock(int threadNumber) {
        log(" Thread " + threadNumber + " released lock");
        this.level[threadNumber] = new AtomicInteger(0);
    }
}
