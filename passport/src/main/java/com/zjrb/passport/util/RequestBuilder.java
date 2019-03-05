package com.zjrb.passport.util;

import android.text.TextUtils;

import com.zjrb.passport.net.ApiManager;
import com.zjrb.passport.net.request.FormBody;
import com.zjrb.passport.net.request.Request;

import java.util.Map;
import java.util.TreeMap;

/**
 * Function: RequestBuilder
 * <p>
 * Author: chen.h
 * Date: 2018/7/17
 */
public class RequestBuilder {


    /**
     * 构建get方式请求
     *
     * @param builder ParamsBuilder
     * @return Request
     */
    public static Request buildGetRequest(ParamsBuilder builder) {
        Request request = null;
        try {
            FormBody body = new FormBody.Builder().map(builder.build()).build();
            request = new Request.Builder().get(body).api(builder.getApi()).token(builder.getToken()).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    /**
     * 构建post方式请求
     *
     * @param builder ParamsBuilder
     * @return Request
     */
    public static Request buildPostRequest(ParamsBuilder builder) {
        Request request = null;
        try {
            FormBody body = new FormBody.Builder().map(builder.build()).build();
            request = new Request.Builder().post(body).api(builder.getApi()).token(builder.getToken()).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }


    public static class ParamsBuilder {
        private TreeMap<String, String> map;
        private String token;
        private String api;

        public ParamsBuilder() {
            map = new TreeMap<>();
        }

        public ParamsBuilder api(String api) {
            this.api = api;
            return this;
        }

        /**
         * 某些请求请求头需要附加token,该方法用于进行token注入
         * @param token
         * @return
         */
        public ParamsBuilder injectToken(String token) {
            this.token = token;
            return this;
        }

        public String getToken() {
            return token;
        }

        public String getApi() {
            return api;
        }

        public ParamsBuilder add(String key, String value) {
            if (!TextUtils.isEmpty(key)) {
                map.put(key, value);
            }
            return this;
        }

        public String getUrl() {
            return ApiManager.joinUrl(api);
        }

        public Map<String, String> build() {
            return map;
        }

    }
}
