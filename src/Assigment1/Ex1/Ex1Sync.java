package Assigment1.Ex1;

public class Ex1Sync extends Ex1 {
    protected synchronized void changeCounter(Integer value) {
        counter += value;
    }
}
