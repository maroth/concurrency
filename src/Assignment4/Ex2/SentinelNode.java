package Assignment4.Ex2;

public class SentinelNode<T> extends Node<T> {

    @Override
    public void setObject(T object) {
        throw new Error("cant set object on sentinel node");
    }
}
