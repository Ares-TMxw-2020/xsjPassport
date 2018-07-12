package com.zjrb.passport.net;

import com.zjrb.passport.net.interfaces.IRequestHandler;

/**
 * Date: 2018/6/30 下午9:44
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 异步请求任务
 */
public class HttpAsyncTask implements Runnable {

    private HttpCall httpCall;

    private CallBack callBack;

    private IRequestHandler requestHandler;

    public HttpAsyncTask(HttpCall call, CallBack callBack, IRequestHandler requestHandler) {
        this.httpCall = call;
        this.callBack = callBack;
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
        requestHandler.handleRequest(httpCall, callBack);
    }
}
