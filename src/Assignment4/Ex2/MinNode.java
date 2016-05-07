package Assignment4.Ex2;

public class MinNode<T> extends SentinelNode<T> {

    @Override
    public boolean isSmallerThan(T value) {
        return true;
    }

    @Override
    public boolean isEqualTo(T value) {
        return value instanceof MinNode;
    }

    @Override
    public boolean isSmallerOrEqual(Node<T> node) {
        return true;
    }

    @Override
    public Integer getKey() {
        return Integer.MIN_VALUE;
    }
}
