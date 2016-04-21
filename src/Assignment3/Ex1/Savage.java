package Assignment3.Ex1;

public class Savage implements Runnable{

    private final Pot pot;
    private final Cook cook;
    private final boolean forever;

    public Savage(Pot pot, Cook cook, boolean forever) {
        this.pot = pot;
        this.cook = cook;
        this.forever = forever;
    }

    @Override
    public void run() {
        do {
            if (pot.isEmpty()) {
                cook.cook();
            }
            pot.eat();
        } while (forever);
    }
}
