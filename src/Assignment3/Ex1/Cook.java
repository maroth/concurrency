package Assignment3.Ex1;

public class Cook implements Runnable {
    private final Pot pot;

    private boolean cookingRequired;

    public Cook(Pot pot) {
        this.pot = pot;
    }

    public void cook() {
        cookingRequired = true;
    }

    @Override
    public void run() {
        System.out.println("Starting Cook!");
        while (true) {
            while (!cookingRequired) { }
            synchronized (this) {
                System.out.println("Do I need to cook?");
                if (pot.isEmpty()) {
                    System.out.println("Cooking!");
                    if (pot.isEmpty()) {
                        pot.fill();
                        cookingRequired = false;
                    }
                }
            }
        }
    }
}
