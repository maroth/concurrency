package Assignment4.Ex2;

public class MinNode<T> extends SentinelNode<T> {

    @Override
    public Node<T> getPrevious() {
        throw new Error("cant get previous node of min node");
    }

    @Override
    public void setPrevious(Node<T> next) {
        throw new Error("cant set previous node of min node");
    }

    @Override
    public int compareTo(T value) {
        return -1;
    }
}
