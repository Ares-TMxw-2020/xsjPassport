package com.zjrb.passport.net;

import java.io.IOException;

/**
 * Date: 2018/6/28 下午11:35
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 调用方式封装
 */
public class HttpCall implements Call {

    final Request request;

    final ZbHttpClient.Config config;

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
        return null;
    }

    /**
     * 异步执行
     * @param callBack
     */
    @Override
    public void enqueue(CallBack callBack) {

    }
}
