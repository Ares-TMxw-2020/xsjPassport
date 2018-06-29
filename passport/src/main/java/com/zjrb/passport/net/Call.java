package com.zjrb.passport.net;

import java.io.IOException;


/**
 * Date: 2018/6/28 下午3:11
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 代表要执行的请求,提供同步及异步两种调用方式,参考okhttp的写法
 */
public interface Call {

    /**
     * 同步方式
     * @return
     * @throws IOException
     */
    Response execute() throws IOException;

    /**
     * 异步执行方式
     * @param callBack
     */
    void enqueue(CallBack callBack);

}
