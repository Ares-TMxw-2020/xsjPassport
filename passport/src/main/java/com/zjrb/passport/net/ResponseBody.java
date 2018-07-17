package com.zjrb.passport.net;

import com.zjrb.passport.constant.InnerConstant;
import com.zjrb.passport.util.Logger;

import java.io.UnsupportedEncodingException;

/**
 * Date: 2018/6/28 下午5:53
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 返回体封装,默认为字节流
 */
public class ResponseBody {

    byte[] bytes;

    public ResponseBody(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] bytes() {
        return this.bytes;
    }

    public String string() {
        try {
            return new String(bytes(), InnerConstant.DEF_CODE);
        } catch (UnsupportedEncodingException e) {
            Logger.e("字节流转为UTF-8格式的String异常");
            return "";
        }
    }

}
