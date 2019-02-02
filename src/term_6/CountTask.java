package term_6;

/*
 *
 *     @author Qmh
 *     @Date 2019/1/29 10:17
 *
 */


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/*
 * 采用Fork/Join框架原理是大任务分成小任务，分别放到双向队列数组中
 * 一个线程对应一个队列
 * 完成后采用work-stealing算法增加效率
 * 最后集合结果
 *
 * 计算1+2+3+4的结果
 * */

// 使用时采用继承ForkJoinTask子类即可
public class CountTask extends RecursiveTask<Integer> {
    //分配任务，一个任务加两个数，所以阀值为2
    public static final int THRESHOLD = 2;
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            for (int i = start; i <= end; i++)
                sum += i;
        } else {
            int middle = (start + end) / 2;
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle + 1, end);
            //执行子任务
            leftTask.fork();
            rightTask.fork();
            //等待子任务执行完，得到结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();
            sum = leftResult + rightResult;
        }
        return sum;
    }

    public static void main(String args[]) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //生成任务计算1+2+3+4
        CountTask task = new CountTask(1, 46);
        Future<Integer> result = forkJoinPool.submit(task);
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
