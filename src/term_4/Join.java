package term_4;

/*
 *
 *     @author Qmh
 *     @Date 2018/12/28 16:34
 *
 */

import java.util.concurrent.TimeUnit;

/**
 * 10个线程进行，每个编号，都要等前一个完成，才能进行，0号需要等main结束
 */
public class Join {

    /**
     * public static void main(String args[]) throws InterruptedException {
     * Thread[] threads = new Thread[10];
     * init(threads);
     * Thread t = Thread.currentThread();
     * for(int i=0;i<10;i++){
     * //此处是要等待t.join才能运行，而t是当前线程
     * t.join();
     * threads[i].setName("线程"+i);
     * threads[i].start();
     * t = threads[i];
     * }
     * }
     * <p>
     * private static void init(Thread[] threads) {
     * for(Thread t : threads){
     * t = new Thread(new Runnable() {
     *
     * @Override public void run() {
     * System.out.println("现在是："+Thread.currentThread().getName());
     * }
     * });
     * }
     * }
     */

    public static void main(String args[]) throws InterruptedException {
        Thread pervious = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runner(pervious), "线程:" + i);
            thread.start();
            pervious = thread;
        }
        TimeUnit.SECONDS.sleep(3);
        System.out.println(Thread.currentThread().getName() + "运行中！！！");

    }


    static class Runner implements Runnable {
        private Thread pervious;

        public Runner(Thread pervious) {
            this.pervious = pervious;
        }

        @Override
        public void run() {
            try {
                pervious.join();
                System.out.println(Thread.currentThread().getName() + "  terminate");
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + "  terminate");
            }
        }
    }


}
