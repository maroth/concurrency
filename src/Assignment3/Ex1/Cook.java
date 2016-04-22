package Assignment3.Ex1;

public class Cook implements Runnable {
    private final Pot pot;

    private volatile boolean cookingRequired;

    private volatile boolean abort;

    public Cook(Pot pot) {
        this.pot = pot;
    }

    public void cook() {
        cookingRequired = true;
    }

    public void stop() {
        abort = true;
    }

    @Override
    public void run() {
        System.out.println("Starting Cook!");
        while (!abort) {

            while (!cookingRequired && !abort) {
            }
            System.out.println("Do I need to cook?");

            //the pot.isEmpty() should aways be true since only one savage can be in the synchronized block,
            //this is just here so you don't think the constraint not implemented
            if (pot.isEmpty()) {
                System.out.println("Cooking!");
                pot.fill();
                cookingRequired = false;
            }
        }
        System.out.println("Cook stopping!");
    }
}
