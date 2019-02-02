package term_4;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
 *
 *     @author Qmh
 *     @Date 2018/12/28 15:18
 *
 */
public class WaitNotify {

    /**
     * 等待通知消息队列，通过使用wait进入等待队列使用notifyall唤醒睡眠的对象
     * 过程是-------wait线程先得到lock对象然后进行操作，使用wait等待释放lock锁
     * 此时被notify对象得到，通过唤醒又唤醒wait线程
     */
    static boolean flag = true;
    static Object lock = new Object();

    public static void main(String args[]) throws InterruptedException {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(2);
        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
    }

    private static class Wait implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                while (flag) {
                    System.out.println(Thread.currentThread() + " flag is true. wait @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //满足条件时候，完成工作
                    System.out.println(Thread.currentThread() + " flag is false. running @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                }
            }
        }
    }

    private static class Notify implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock . notify @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                //唤醒别的线程此时并没有释放锁
                flag = false;
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //此时释放锁，二者又重新开始竞争lock了
           /* try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock again. sleep @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }
}
