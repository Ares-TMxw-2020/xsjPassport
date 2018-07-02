package com.zjrb.passport.net.interfaces;

import com.zjrb.passport.net.HttpCall;
import com.zjrb.passport.net.Response;

import java.io.IOException;

/**
 * Date: 2018/6/29 上午11:49
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 处理请求的接口
 */
public interface IRequestHandler {

    Response handleRequest(HttpCall call) throws IOException;

}
