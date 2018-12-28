package term_4.DBConnectionPool;

import com.sun.istack.internal.NotNull;

import java.sql.Connection;
import java.util.LinkedList;

/*
 *
 *     @author Qmh
 *     @Date 2018/12/28 17:46
 *
 */
public class ConnectionPool {
    private LinkedList<Connection> pool = new LinkedList<>();
    public ConnectionPool(int initialSize){
        for(int i=0;i<initialSize;i++){
            pool.addLast(ConnectionDriver.createConnection());
        }
    }

    public void releaseConnection(@NotNull Connection connection){
        synchronized (pool){
            pool.addLast(connection);
            pool.notifyAll();
        }
    }

    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool){
            if(mills <= 0){
                while(pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else {
                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                while(pool.isEmpty() && remaining>0){
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if(!pool.isEmpty()){
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
}
