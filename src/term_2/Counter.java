package term_2;
/*
 *
 *     @author Qmh
 *
 *   Yuan Li Bug Zhen Ai Sheng Ming
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 原子操作CAS
 *
 *
 *
 * */

public class Counter {
    private int i = 0;
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private void safeCount() {
        for (; ; ) {
            int i = atomicInteger.get();
            boolean suc = atomicInteger.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }
    }

    private void count() {
        i++;
    }

    public static void main(String args[]) {

        final Counter cas = new Counter();
        List<Thread> ts = new ArrayList<Thread>(600);
        long start = System.currentTimeMillis();

        for (int j = 0; j < 100; j++) {
            Thread t = new Thread(()->{
               for(int i=0;i<10000;i++){
                   cas.count();
                   cas.safeCount();
               }
            });
            ts.add(t);
        }


        for (Thread t :ts){
            t.start();
        }

        /*
         * 等待所有线程执行完成
         * */
        for (Thread t :ts){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(cas.i);
        System.out.println(cas.atomicInteger);
        System.out.println(System.currentTimeMillis()-start);

    }
}
