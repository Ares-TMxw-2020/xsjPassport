package com.zjrb.passport.net.interfaces;

import android.support.annotation.Nullable;

import com.zjrb.passport.net.request.HttpCall;

/**
 * Date: 2018/6/29 上午11:49
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 处理请求的接口
 */
public interface IRequestHandler {

    void handleRequest(HttpCall call, @Nullable CallBack callBack);

}
