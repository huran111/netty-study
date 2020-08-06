package 同步阻塞IO.demo02;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-03 13:51
 */
public class TimeServerHandlerExecutePool {
    private ExecutorService executorService;

    public TimeServerHandlerExecutePool(int maxPollSize, int queueSize) {
        executorService = new ThreadPoolExecutor(1, maxPollSize, 120L, TimeUnit.SECONDS
                , new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task) {
        executorService.execute(task);
    }
}