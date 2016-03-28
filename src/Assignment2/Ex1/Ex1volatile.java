package Assignment2.Ex1;

public class Ex1Volatile extends Ex1 {
    public volatile int sharedCounter;
    public volatile int[] sharedCounterAccess;

    @Override
    protected int getSharedCounter() {
        return this.sharedCounter;
    }

    @Override
    protected int[] getSharedCounterAccess() {
        return this.sharedCounterAccess;
    }

    @Override
    protected void setSharedCounter(int value) {
        this.sharedCounter = value;
    }

    @Override
    protected void setSharedCounterAccess(int value, int index) {
        this.sharedCounterAccess[index] = value;
    }

    Ex1Volatile(int numberOfThreads, int counterLimit) {
        super(numberOfThreads, counterLimit);
        this.sharedCounterAccess = new int[numberOfThreads];
    }
}
