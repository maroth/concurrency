package Assignment4.Ex2;

public class Tests {
    public static void main(String[] args) throws Exception {
        BaseSet<Integer> testee = new NaiveSet<>();

        testee.add(1);
        System.out.println(testee.toString());

        boolean contains = testee.contains(1);
        if (!contains) throw new Exception("does not contain contained item");

        contains = testee.contains(0);
        if (contains) throw new Exception("contains item that is not contained");

        contains = testee.contains(76);
        if (contains) throw new Exception("contains item that is not contained");

        boolean removed = testee.remove(1);
        System.out.println(testee.toString());
        if (!removed) throw new Exception("did not remove existing item");

        removed = testee.remove(1);
        System.out.println(testee.toString());
        if (removed) throw new Exception("removed non-existing item");

        testee.add(1);

        removed = testee.remove(2);
        if (removed) throw new Exception("removed non-existing item");

        boolean added = testee.add(1);
        if (added) throw new Exception("added duplicate");

        added = testee.add(2);
        if (!added) throw new Exception("rejected valid value");

        added = testee.add(4);
        if (!added) throw new Exception("rejected valid value");

        added = testee.add(3);
        if (!added) throw new Exception("rejected valid value");

        testee.add(100);

        removed = testee.remove(90);
        if (removed) throw new Exception("removed non-existing item");

        removed = testee.remove(100);
        if (!removed) throw new Exception("did not remove existing item");

        if (!testee.validate()) throw new Exception("not valid");
    }
}
