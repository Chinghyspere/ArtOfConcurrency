package term_4;
/*
 *
 *     @author Qmh
 *     @Date 2018/12/28 11:14
 *
 */

import java.util.concurrent.TimeUnit;

public class Daemon {
    /**
     *
     *  指令重排序且无正面线程导致jvm直接关闭所以此时只会打出开始了结束了等
     * 加上join的可指定按规定线程进行
     *
     * */
    public  static void main(String args[]) throws InterruptedException {

        System.out.println("开始了");
        Thread thread =  new Thread(()->{
            try{
                System.out.println("我是后台线程");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("睡醒了！！！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("我结束了，不过我会打印出来");
            }
        });
        thread.setDaemon(true);
        thread.start();
        /* thread.join();*/
        System.out.println("结束了");





    }

}
