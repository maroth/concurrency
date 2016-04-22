package Assignment3.Ex1;

public class FairSavage implements Runnable {
    private int id;
    private final Pot pot;
    private final Cook cook;
    private int round;
    private FairKeeper fairKeeper;

    public FairSavage(int id, Pot pot, Cook cook, FairKeeper fairKeeper) {
        this.id = id;
        this.pot = pot;
        this.cook = cook;
        this.fairKeeper = fairKeeper;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (pot) {
                if (pot.isEmpty()) {
                    print("pot is empty, notifying cook");
                    cook.cook();
                    while (pot.isEmpty()) {
                    }
                }
                print(String.format("eating for round %d", round));
                pot.eat();
                fairKeeper.eat();
            }

            while (fairKeeper.round == round) {
            }

            print(String.format("round %d finished ", round));
            round += 1;
        }
    }

    private void print(String message) {
        System.out.println(String.format("Savage %d: %s", id, message));
    }
}
