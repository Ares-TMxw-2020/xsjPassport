package com.zjrb.passport.net;

import com.zjrb.passport.net.interfaces.IRequestHandler;

import java.util.concurrent.FutureTask;

/**
 * Date: 2018/6/28 下午11:35
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 调用方式封装
 */
public class HttpCall implements Call {

    final Request request;

    final ZbHttpClient.Config config;

    private volatile boolean canceled; // 标志是否取消请求

    IRequestHandler requestHandler = new RequestHandler();

    public HttpCall(ZbHttpClient.Config config, Request request) {
        this.config = config;
        this.request = request;
    }


    /**
     * 异步执行
     * @param callBack
     */
    @Override
    public void enqueue(CallBack callBack) {
        HttpAsyncTask task = new HttpAsyncTask(HttpCall.this, callBack, requestHandler);
        HttpThreadPool.getInstance().execute(new FutureTask<>(task, null));
    }

    /**
     * 取消请求,不建议使用
     */
    @Override
    public void cancel() {
        this.canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }

}
