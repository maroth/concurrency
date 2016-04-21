package Assignment3.Ex1;

public class FairKeeper {
    public volatile int round;
    public int eatenThisRound;
    public int numberOfSavages;

    public FairKeeper(int numberOfSavages) {
        this.numberOfSavages = numberOfSavages;
    }

    public void eat() {
        synchronized (FairKeeper.class) {
            eatenThisRound += 1;
            if (eatenThisRound == numberOfSavages) {
                round += 1;
                eatenThisRound = 0;
            }
        }
    }
}
