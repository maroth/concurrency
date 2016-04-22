package Assignment3.Ex2;

import java.util.Random;

public class Philosopher implements Runnable {
    private final Fork leftFork;
    private final Fork rightFork;
    private final Random random;
    private final int id;

    public Philosopher(int id, Fork leftFork, Fork rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        random = new Random();
        print(String.format("Left Fork: %d, Right Fork: %d", leftFork.getId(), rightFork.getId()));
    }

    @Override
    public void run() {
        while(true) {
            if (leftFork.getId() < rightFork.getId()) {
                grabFork(leftFork);
                grabFork(rightFork);
            } else {
                grabFork(rightFork);
                grabFork(leftFork);
            }
            eat();
            releaseFork(leftFork);
            releaseFork(rightFork);
            sleep();
        }
    }

    private void grabFork(Fork fork) {
        print(String.format("grabbing fork %d", fork.getId()));
        fork.grab();
        print(String.format("grabbed fork %d", fork.getId()));
    }

    private void releaseFork(Fork fork) {
        print(String.format("releasing fork %d", fork.getId()));
        fork.release();
        print(String.format("released fork %d", fork.getId()));
    }

    private void sleep() {
        print("Sleeping");
        try {
            Thread.sleep(random.nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void eat() {
        print("Eating");
        try {
            Thread.sleep(random.nextInt(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void print(String message) {
        System.out.println(String.format("Philosopher %d: %s", id, message));
    }

}
