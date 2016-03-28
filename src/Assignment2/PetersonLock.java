package Assignment2;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class PetersonLock {
    private int numberOfThreads;
    private AtomicInteger[] level;
    private AtomicInteger[] victim;

    public PetersonLock(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        this.level = new AtomicInteger[numberOfThreads];
        this.victim = new AtomicInteger[numberOfThreads];

        Arrays.fill(this.level, new AtomicInteger(-1));
        Arrays.fill(this.victim, new AtomicInteger(-1));
    }

    public void lock(int threadNumber) {
        for (int currentLevel = 1; currentLevel < threadNumber; currentLevel++) {
            this.level[threadNumber].set(currentLevel);
            this.victim[currentLevel].set(threadNumber);
            while(threadWithHigherLevelExists(threadNumber, currentLevel)
                    && threadIsVictimOnLevel(threadNumber, currentLevel)) {}
        }

    }

    private boolean threadIsVictimOnLevel(int threadNumber, int level) {
        return this.victim[level].equals(threadNumber);
    }

    private boolean threadWithHigherLevelExists(int threadNumber, int currentLevel) {
        for (int index = 0; index < threadNumber; index++) {
            if (this.level[index].get() > currentLevel && index != threadNumber) {
                return true;
            }
        }
        return false;
    }

    public void unlock(int threadNumber) {
        this.level[threadNumber] = new AtomicInteger(0);
    }
}
