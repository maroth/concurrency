package Assignment3.Ex1;

public class Pot {
    private final int maxServings;

    private volatile int servings;

    public Pot(int maxServings) {
        this.maxServings = maxServings;
    }

    public boolean isEmpty() {
        return servings <= 0;
    }

    public void fill() {
        servings = maxServings;
    }

    public void eat() {
        servings -= 1;
        System.out.println(String.format("Pot contains %d", servings));
    }
}
