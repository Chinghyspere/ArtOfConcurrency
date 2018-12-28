package term_4.DBConnectionPool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/*
 *
 *     @author Qmh
 *     @Date 2018/12/28 17:48
 *
 */
public class ConnectionDriver {
    static class ConnectionHandler implements InvocationHandler{

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(method.getName().equals("commit")){
                TimeUnit.MILLISECONDS.sleep(100);
            }
            return null;
        }
    public static final Connection createConnection(){
           return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader()
                   ,new Class<?>[]{Connection.class},new ConnectionHandler());
    }

    }

    public static final Connection createConnection() {
        return null;
    }
}
