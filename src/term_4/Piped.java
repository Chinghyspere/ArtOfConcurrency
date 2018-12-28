package term_4;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/*
 *
 *     @author Qmh
 *     @Date 2018/12/28 16:09
 *
 */



/**
 *
 * 通过Piped相互连接把读和写连接起来，然后一边写就可以一边读这就是管道
 *
 * */
public class Piped {
    public static void main(String args[]) throws IOException {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        out.connect(in);
        Thread printThread = new Thread(new Print(in), "PrintPipedThread");
        printThread.start();
        int receive = 0;
        while ((receive = System.in.read()) != -1) {
            out.write(receive);
        }

    }


    static class Print implements Runnable {
        private PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            int receive = 0;
            try {
                while ((receive = in.read()) != -1) {
                    System.out.print((char) receive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
