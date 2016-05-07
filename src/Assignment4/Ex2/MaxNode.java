package Assignment4.Ex2;

public class MaxNode<T> extends SentinelNode<T> {

    @Override
    public void setNext(Node<T> next) {
        throw new Error("cant add next node to max node");
    }

    @Override
    public Node<T> getNext() {
        throw new Error("cant get next node of max node");
    }

    @Override
    public boolean isSmallerThan(T value) {
        return false;
    }

    @Override
    public boolean isEqualTo(T value) {
        return value instanceof MaxNode;
    }

    @Override
    public Integer getKey() {
        return Integer.MAX_VALUE;
    }
}
