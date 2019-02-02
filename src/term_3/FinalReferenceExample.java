package term_3;
/*
 *
 *     @author Qmh
 *
 *   Yuan Li Bug Zhen Ai Sheng Ming
 */

public class FinalReferenceExample {
    final int[] intArrays;
    static FinalReferenceExample obj;

    public FinalReferenceExample() {
        intArrays = new int[1];
        intArrays[0] = 1;
    }

    public static void writerOne() {
        obj = new FinalReferenceExample();
    }

    public static void writerTwo() {
        obj.intArrays[0] = 2;
    }

    public static void reader() {
        if (obj != null) {
            int temp1 = obj.intArrays[0];
            System.out.println(temp1);
        }
    }

    public static void main(String args[]) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            Runnable r1 = () -> {
                writerOne();
            };
            Runnable r3 = () -> {
                writerTwo();
            };
            Runnable r2 = () -> {
                reader();


            };
            Thread t1 = new Thread(r1);
            Thread t2 = new Thread(r2);
            Thread t3 = new Thread(r3);
            t1.start();
            t2.start();
            t3.start();
        }
        /*t1.join();*/


    }

}
