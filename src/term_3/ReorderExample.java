package term_3;
/*
 *
 *     @author Qmh
 *
 *   Yuan Li Bug Zhen Ai Sheng Ming
 */

import java.util.concurrent.locks.ReentrantLock;

public class ReorderExample {
    /**
     *
     * synchronized锁定对象，join用来维持顺序
     * 证明编译重排序出现的问题，由于a=1000 和flag=true 所以在reader之前就已经结束了两种结果
     */


    int a = 0;
    boolean flag = false;

    public  void write() {
        new ReentrantLock().lock();

        flag = true;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        a = 1000;

    }

  /*  public void write() {
        a = 1000;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = true;
    }
*/


    public  void reader() {
        if (flag) {
            int i = a * a;
            System.out.println("currenThread---->" + Thread.currentThread().getName() + ":     " + i);
        } else
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!失败!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    public static void main(String args[]) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            ReorderExample r = new ReorderExample();
            Runnable r1 = () -> {
                r.write();
            };
            Runnable r2 = () -> {
                r.reader();
            };
            Thread t1 = new Thread(r1);
            Thread t2 = new Thread(r2);
            t1.start();
            /*t1.join();*/
            t2.start();

        }

    }

}
