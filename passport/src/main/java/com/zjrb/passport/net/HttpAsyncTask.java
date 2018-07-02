package com.zjrb.passport.net;

import com.zjrb.passport.net.interfaces.IRequestHandler;
import com.zjrb.passport.net.interfaces.IResponseHandler;

import java.io.IOException;

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
    private IResponseHandler responseHandler = IResponseHandler.RESPONSE_HANDLER;

    public HttpAsyncTask(HttpCall call, CallBack callBack, IRequestHandler requestHandler) {
        this.httpCall = call;
        this.callBack = callBack;
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
        try {
            Response response = requestHandler.handleRequest(httpCall);
            responseHandler.handleSuccess(callBack, response);
        } catch (IOException e) {
            responseHandler.handleFail(callBack, httpCall.request, e);
        }
    }
}
