package term_4;

/*
 *
 *     @author Qmh
 *     @Date 2018/12/28 15:04
 *
 */
public class Synchronized {
    /**
     * 使用javap 编译看指令
     */
    public static void main(String args[]) {
        synchronized (Synchronized.class) {
        }
        m();
    }

    private static synchronized void m() {
        System.out.println("m方法");
    }


}
