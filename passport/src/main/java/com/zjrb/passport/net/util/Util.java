package com.zjrb.passport.net.util;

import android.text.TextUtils;

import com.zjrb.passport.net.Request;
import com.zjrb.passport.net.RequestBody;

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
            if (params.get(key) == null) {
                continue;
            }
            sb = sb.append(key + "=" + URLEncoder.encode(params.get(key).toString()) + "&");
        }

        String paramsStr = sb.substring(0, sb.length() - 1).toString();
        return paramsStr;
    }

}
