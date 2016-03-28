package Assignment2.Ex1;

public class Ex1NonVolatile extends Ex1 {

    private int sharedCounter;
    private int[] sharedCounterAccess;

    Ex1NonVolatile(int numberOfThreads, int counterLimit) {
        super(numberOfThreads, counterLimit);
        this.sharedCounterAccess = new int[numberOfThreads];
    }

    @Override
    protected int getSharedCounter() {
        return this.sharedCounter;
    }

    @Override
    protected int[] getSharedCounterAccess() {
        return this.sharedCounterAccess;
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
        if (this.counterLimit > 10000) {
            if (this.sharedCounter % (super.counterLimit / 1000) == 0) {
                float completed = ((float) this.sharedCounter) / super.counterLimit;
                System.out.println("" + completed + " (" + threadNumber + ")");
            }
        }
    }
}
