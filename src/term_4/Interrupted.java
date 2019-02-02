package term_4;

/*
 *
 *     @author Qmh
 *     @Date 2018/12/28 14:15
 *
 */

import java.util.concurrent.TimeUnit;


/**
*
* 结果
 * SleepThread interrupted is false
 * BusyThread interrupted is true
 * 说明:一直运行的线程，中断后，中断标识位为true且不会异常
 * 而睡眠等非运行状态的线程会抛出一个中断异常且标识位清除所以为false
*
* */

public class Interrupted {

    public static void main(String args[]) throws InterruptedException {
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);
        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);
        sleepThread.start();
        busyThread.start();
        TimeUnit.SECONDS.sleep(5);
        sleepThread.interrupt();
        busyThread.interrupt();
        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());
        TimeUnit.SECONDS.sleep(5);
    }


    static class SleepRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    static class BusyRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
            }
        }
    }


}
