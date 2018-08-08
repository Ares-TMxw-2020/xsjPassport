package com.zjrb.passport.util;

import android.text.TextUtils;

import com.zjrb.passport.net.request.Request;
import com.zjrb.passport.net.request.RequestBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

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

    /**
     * get请求url封装
     * @param urlPath
     * @param params
     * @return
     */
    public static String buildGetUrl(String urlPath, Map<String, String> params) {
        if (TextUtils.isEmpty(urlPath) || params == null || params.size() == 0) {
            return urlPath;
        }
        if (!urlPath.endsWith("?")) {
            urlPath += "?";
        }
        String paramsStr = buildGetParams(params);
        StringBuilder sbUrl = new StringBuilder(urlPath);
        sbUrl.append(paramsStr);
        return sbUrl.toString();
    }

    public static String buildGetParams(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            if (TextUtils.isEmpty(key)) {
                continue;
            }
            try {
                if (TextUtils.equals(key, InnerConstant.SIGN)) { // sign为sha256加密后的字串,不包含特殊字符,这里进不进行encode都行,这里跟ios逻辑保持一致,不再encode
                    sb = sb.append(key + "=" + (TextUtils.isEmpty(params.get(key)) ? "" : params.get(key)) + "&");
                } else {
                    sb = sb.append(key + "=" + (TextUtils.isEmpty(params.get(key)) ? "" : URLEncoder.encode(params.get(key), "UTF-8")) + "&");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        String paramsStr = sb.substring(0, sb.length() - 1);
        return paramsStr;
    }


    public static String getValueEncoded(String value) {
        if (value == null) return "null";
        String newValue = value.replace("\n", "");
        for (int i = 0, length = newValue.length(); i < length; i++) {
            char c = newValue.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                try {
                    return URLEncoder.encode(newValue, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return newValue;
    }

}
