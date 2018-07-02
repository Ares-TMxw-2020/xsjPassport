package com.zjrb.passport.net;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Date: 2018/6/30 下午9:05
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 线程池,单例模式
 */
public class HttpThreadPool {

    ThreadPoolExecutor threadPoolExecutor;

    /**
     * 核心线程数
     */
    public static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    /**
     * 最大存活时间
     */
    public static final int MAX_LIVE_TIME = 10;

    private static volatile HttpThreadPool threadPool; // 单例模式

    private BlockingQueue<Future<?>> queue = new LinkedBlockingQueue<>();

    public static HttpThreadPool getInstance() {
        if (threadPool == null) {
            synchronized (HttpThreadPool.class) {
                if (threadPool == null) {
                    threadPool = new HttpThreadPool();
                }
            }
        }
        return threadPool;
    }

    private HttpThreadPool() {
        threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, CORE_POOL_SIZE + 1, MAX_LIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), rejectedExecutionHandler);
        threadPoolExecutor.execute(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                FutureTask<?> task = null;
                try {
                    task = (FutureTask<?>) queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (task != null) {
                    threadPoolExecutor.execute(task);
                }
            }
        }
    };

    /**
     * 同步执行任务
     * @param callable 要执行的任务
     * @return 执行的结果
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public synchronized Response submit(Callable<Response> callable) throws InterruptedException, ExecutionException{
        if (callable == null) {
            throw new NullPointerException("请求任务为空,不能执行");
        }
        Future<Response> responseFuture = threadPoolExecutor.submit(callable);
        return responseFuture.get();
    }

    public void execute(FutureTask<?> task) {
        if (task == null) {
            throw new NullPointerException("请求任务为空,不能执行");
        }
        try {
            queue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                queue.put(new FutureTask<>(r, null));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

}
