package com.zjrb.passport.net.util;

import com.zjrb.passport.net.Request;
import com.zjrb.passport.net.RequestBody;

/**
 * Date: 2018/6/28 下午4:19
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 网络帮助类
 */
public class Util {

    public static void checkMethod(Request.HttpMethod method, RequestBody body) {
        if (method == null) {
            throw new NullPointerException("请求方式不能为空");
        }
        if (body != null && Request.HttpMethod.checkNoBody(method)) {
            throw new IllegalStateException(method + "请求方式不能有请求体");
        }
        if (body == null && Request.HttpMethod.checkNeedBody(method)) {
            throw new IllegalStateException(method + "请求方式必须有请求体");
        }
    }


}
