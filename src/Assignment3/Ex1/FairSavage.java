package Assignment3.Ex1;

public class FairSavage implements Runnable {
    private int id;
    private final Pot pot;
    private final Cook cook;
    private int round;
    private FairKeeper fairKeeper;
    private final boolean forever;

    public FairSavage(int id, Pot pot, Cook cook, FairKeeper fairKeeper, boolean forever) {
        this.id = id;
        this.pot = pot;
        this.cook = cook;
        this.fairKeeper = fairKeeper;
        this.forever = forever;
        this.round = 0;
    }

    @Override
    public void run() {
        do {
            synchronized (this) {
                while (pot.isEmpty()) {
                    print("pot is empty, notifying cook");
                    cook.cook();
                    while (pot.isEmpty()) {}
                }
                print(String.format("eating for round %d", round));
                pot.eat();
                fairKeeper.eat();
                while (fairKeeper.round == round) {
                }
                print(String.format("round %d finished ", round));
                round += 1;
            }
        } while (forever);
    }

    private void print(String message) {
        System.out.println(String.format("Savage %d: %s", id, message));
    }
}
