package Ex2;

import Util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ex2 {
        public static void main(String[] args) {
                int t = 100;
                int n = 6;
                CircularBuffer<String> buffer = new CircularBuffer<String>(String.class, n);

                Runnable producer = () -> { while (true) buffer.produce("ex2"); };
                Runnable consumer = () -> { while (true) buffer.consume(); };

                ExecutorService executorService = Executors.newCachedThreadPool();
                //start t threads to do producer

                Util.repeater(t, () -> executorService.execute(producer));
                Util.repeater(t, () -> executorService.execute(consumer));

                Util.waitForThreads(executorService);

        }

}
