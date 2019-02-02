package term_8;

import java.util.concurrent.CountDownLatch;

/*
 *
 *     @author Qmh
 *     @Date 2019/2/2 18:39
 *
 */
public class JoinCountDownLatchTest {
    static CountDownLatch countDownLatch = new CountDownLatch(2);
    public static void main(String args[]) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(1);
                countDownLatch.countDown();
                System.out.println(2);
                countDownLatch.countDown();
            }
        }).start();
        countDownLatch.await();
        System.out.println("结束了");
    }
}
