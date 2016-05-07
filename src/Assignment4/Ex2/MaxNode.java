package Assignment4.Ex2;

public class MaxNode<T> extends SentinelNode<T> {

    @Override
    public void setNext(Node<T> next) {
        throw new Error("cant add next node to max node");
    }

    @Override
    public int compareTo(T value) {
        return 1;
    }
}
