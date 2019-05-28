package com.zjrb.passport.net;

import com.zjrb.passport.util.Logger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.FutureTask;
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
    public static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() + 1;

    /**
     * 最大存活时间
     */
    public static final int MAX_LIVE_TIME = 10;

    private static volatile HttpThreadPool threadPool; // 单例模式

//    private BlockingQueue<Future<?>> queue = new LinkedBlockingQueue<>();

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
        threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, 2 * CORE_POOL_SIZE, MAX_LIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(128));
//        threadPoolExecutor.execute(runnable);
    }

//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            while (true) {
//                FutureTask<?> task = null;
//                try {
//                    task = (FutureTask<?>) queue.take();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                if (task != null) {
//                    threadPoolExecutor.execute(task);
//                }
//            }
//        }
//    };


    public void execute(FutureTask<?> task) {
        if (task == null) {
            Logger.e("请求任务为空,不能执行");
        }
        try {
//            queue.put(task);
            threadPoolExecutor.execute(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}
