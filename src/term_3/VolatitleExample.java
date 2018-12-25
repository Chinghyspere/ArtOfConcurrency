package term_3;
/*
 *
 *     @author Qmh
 *
 *   Yuan Li Bug Zhen Ai Sheng Ming
 */

public class VolatitleExample {
    int a = 0;
    volatile boolean flag = false;

    public void write() {
        a = 1000;
        flag = true;

    }

    public void reader() {
        if (flag) {
            int i = a * a;
            System.out.println("currenThread---->" + Thread.currentThread().getName() + ":     " + i);
        } else
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!失败!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    public static void main(String args[]) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            VolatitleExample r = new VolatitleExample();
            Runnable r1 = () -> {
                r.write();
            };

            Runnable r2 = () -> {
                r.reader();
            };
            Thread t1 = new Thread(r1);
            Thread t2 = new Thread(r2);
            t1.start();
            t2.start();

        }

    }
}
