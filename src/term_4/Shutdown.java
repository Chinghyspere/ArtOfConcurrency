package term_4;

import org.omg.CORBA.TIMEOUT;

import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/*
 *
 *     @author Qmh
 *     @Date 2018/12/28 14:38
 *
 */
public class Shutdown {

    /**
     * 通过一个控制on进行操作
     * 或者通过中断来进行安全的终止线程
     */
    public static void main(String args[]) throws InterruptedException {
        Runner r = new Runner();
        Thread countThread = new Thread(r, "countThread");
        countThread.start();
        TimeUnit.SECONDS.sleep(1);
        countThread.interrupt();

        Runner r2 = new Runner();
        countThread = new Thread(r2, "countThread");
        countThread.start();
        TimeUnit.SECONDS.sleep(1);
        r2.cancel();

    }

    private static class Runner implements Runnable {
        private long i;
        private volatile boolean on = true;

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println("Count i  =" + i);

        }

        public void cancel() {
            on = false;
        }
    }
}
