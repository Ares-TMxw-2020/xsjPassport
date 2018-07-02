package com.zjrb.passport.net;

import com.zjrb.passport.net.interfaces.IRequestHandler;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
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

    IRequestHandler requestHandler = new RequestHandler();

    public HttpCall(ZbHttpClient.Config config, Request request) {
        this.config = config;
        this.request = request;
    }

    /**
     * 同步执行
     * @return
     * @throws IOException
     */
    @Override
    public Response execute() throws IOException {
        SyncTask task = new SyncTask();
        Response response;
        try {
            response = HttpThreadPool.getInstance().submit(task);
            return response;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new Response.Builder()
                .code(400)
                .message("同步请求异常中断")
                .body(new ResponseBody(null))
                .build();
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

    public class SyncTask implements Callable<Response> {

        @Override
        public Response call() throws Exception {
            return requestHandler.handleRequest(HttpCall.this);
        }
    }
}
