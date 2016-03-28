package Assignment2.Ex1;

public class Ex1NonVolatile extends Ex1 {
    private int sharedCounter;
    private int[] sharedCounterAccess;

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

    Ex1NonVolatile(int numberOfThreads, int counterLimit) {
        super(numberOfThreads, counterLimit);
        this.sharedCounterAccess = new int[numberOfThreads];
    }
}
