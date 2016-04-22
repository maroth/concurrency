package Assignment3.Ex1;

public class Savage implements Runnable {

    private int id;
    private final Pot pot;
    private final Cook cook;

    public Savage(int id, Pot pot, Cook cook) {
        this.id = id;
        this.pot = pot;
        this.cook = cook;
    }

    @Override
    public void run() {
        synchronized(pot) {
            if (pot.isEmpty()) {
                print("pot is empty, notifying cook");
                cook.cook();
                while (pot.isEmpty()) {}
            }
            print(String.format("eating", null));
            pot.eat();
        }
    }

    private void print(String message) {
        System.out.println(String.format("Savage %d: %s", id, message));
    }
}
