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

    Ex1Volatile(int numberOfThreads, int counterLimit) {
        super(numberOfThreads, counterLimit);
        this.sharedCounterAccess = new int[numberOfThreads];
    }

    protected void lockedIncrement(int threadNumber) {
        while (this.sharedCounter < super.counterLimit) {
            this.petersonLock.lock(threadNumber);
            if (this.sharedCounter < super.counterLimit) {
                sharedCounter += 1;
                printStatus(threadNumber);
                this.sharedCounterAccess[threadNumber] += 1;
            }
            this.petersonLock.unlock(threadNumber);
        }
    }

    private void printStatus(int threadNumber) {
        float completed = ((float) this.sharedCounter) / super.counterLimit;
        Util.Util.log("" + completed + " (" + threadNumber + ")");
    }
}
