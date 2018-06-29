package com.zjrb.passport.net;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Date: 2018/6/28 下午3:52
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 请求体
 */
public abstract class RequestBody {

    /**
     * 类型
     * @return
     */
    abstract String contentType();

    /**
     * 写出内容,主要用于post请求写出表单内容
     * @param outputStream
     * @throws IOException
     */
    abstract void writeTo(OutputStream outputStream) throws IOException;

}
