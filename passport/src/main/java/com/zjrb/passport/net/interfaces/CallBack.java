package com.zjrb.passport.net.interfaces;

import com.zjrb.passport.net.response.Response;

import java.io.IOException;

/**
 * Date: 2018/6/28 下午3:25
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 网络请求回调
 */
public interface CallBack {

    void onSuccess(Response response) throws IOException;

    void onFail(int errorCode, String msg);

}
