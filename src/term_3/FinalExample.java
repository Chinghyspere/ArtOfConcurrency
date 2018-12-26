package term_3;
/*
 *
 *     @author Qmh
 *
 *   Yuan Li Bug Zhen Ai Sheng Ming
 */

public class FinalExample {


    /**
     * 对于final对象的引用赋值给另一个对象和final域的写入不重排序
     * 对于final对象的引用和final域的读不重排序
     */

    int i = 0;
    final int j;
    static FinalExample obj;

    public FinalExample() {
        i = 1;
        j = 2;
    }

    public static void writer() {
        obj = new FinalExample();
    }

    public static void reader() {
        FinalExample object = obj;
        int a = object.i;
        int b = object.j;
        System.out.println(Thread.currentThread().getName() + "------------> a =" + a + "   b =" + b);
    }


    public static void main(String args[]) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            Runnable r2 = () -> {
                reader();
            };
            Runnable r1 = () -> {
                writer();
            };
            Thread t1 = new Thread(r1);
            Thread t2 = new Thread(r2);
            t1.start();
            t2.start();
            /*t1.join();*/

        }
    }
}
