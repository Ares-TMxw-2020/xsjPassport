package com.zjrb.passport.net.interfaces;

/**
 * Date: 2018/6/28 下午3:11
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 代表要执行的请求,参考okhttp的写法
 */
public interface Call {

    /**
     * 异步执行方式
     * @param callBack 回调接口
     */
    void enqueue(CallBack callBack);

    /**
     * 取消当前请求
     */
    void cancel();

}
