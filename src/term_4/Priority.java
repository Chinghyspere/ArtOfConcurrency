package term_4;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
 *
 *     @author Qmh
 *
 *   Yuan Li Bug Zhen Ai Sheng Ming
 */

public class Priority {
    private static volatile boolean notStart = true;
    private static volatile boolean notEnd = true;

    /**
     *
     * 程序的正确性与线程优先级无关，意思是这个输出的结果
     * 表示线程优先级为1或者为10，jobcount次数差不多
     *
     * */



    public static void main(String args[]) throws InterruptedException {
        List<Job> jobs = new ArrayList<>();
        for(int i=0;i<10;i++){
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);
            Thread thread = new Thread(job,"Thread"+i);
            thread.setPriority(priority);
            thread.start();
        }
      notStart = false;
        TimeUnit.SECONDS.sleep(5);
        notEnd = false;

        for(Job job : jobs){
            System.out.println("Job Priority : "+job.priority+", Count : "+job.jobCount);
        }

    }

    static class  Job implements  Runnable {
        private int priority;
        private long jobCount;
        public Job(int priority){
            this.priority=priority;
        }

        public void run(){
            while (notStart){
                Thread.yield();
            }
            while(notEnd){
                Thread.yield();
                jobCount++;
            }
        }
    }


}
