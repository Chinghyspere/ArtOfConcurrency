package term_4.DBConnectionPool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/*
 *
 *     @author Qmh
 *     @Date 2018/12/28 17:57
 *
 */
public class ConnectionPoolTest {
    static ConnectionPool pool = new ConnectionPool(10);
    //10个链接在连接池中

    //通过CountDownLatch计数器进行控制流程
    static CountDownLatch start = new CountDownLatch(1);
    static CountDownLatch end;

    public static void main(String args[]) throws InterruptedException {
        int threadCount = 10;
        int count = 20;
        end = new CountDownLatch(threadCount);

        //start(1) end(10)一般end为线程数
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();

        for(int i=0;i<threadCount;i++){
            Thread thread = new Thread(new ConnectionRunner(count,got,notGot),"ConnectionRunnerThread");
            thread.start();
        }
        //这里是防止当前线程继续向下运行，所以使用了start和run中的start.await对应，当所有线程都搞好了，都在await时start一开始，其他的都可以同时进行了
        start.countDown();
        //这里是为了防止当前线程直接打印结果，和run中的end.countDown对应，说明创建的所有线程数都结束了，才最好执行main的结果
        end.await();
        System.out.println("total invoke : "+(threadCount * count));
        System.out.println("got connection : "+ got);
        System.out.println("noGot connection : "+ notGot);
    }


    static class ConnectionRunner implements Runnable {
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;

        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (Exception ex) {
            }

            while (count > 0) {
                try {
                    Connection connection = pool.fetchConnection(1000);
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                    }
                } catch (Exception ex) {

                } finally {
                    count--;
                }
            }
            end.countDown();
        }
    }


}
