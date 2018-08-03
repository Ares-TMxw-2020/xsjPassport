package com.zjrb.passport.util;

import android.text.TextUtils;

import com.zjrb.passport.ZbConfig;
import com.zjrb.passport.ZbPassport;
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
            request = new Request.Builder().get(body).url(builder.getUrl()).build();
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
            request = new Request.Builder().post(body).url(builder.getUrl()).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }


    public static class ParamsBuilder {
        private TreeMap<String, String> map;
        private String api;

        public ParamsBuilder() {
            map = new TreeMap<>();
        }

        public ParamsBuilder inject() {
            ZbConfig zbConfig = ZbPassport.getZbConfig();
            map.put(InnerConstant.APP_ID, "" + zbConfig.getAppId());
            map.put(InnerConstant.APP_KEY, zbConfig.getAppKey());
            map.put(InnerConstant.APP_SECRET, zbConfig.getAppSecret());
            return this;
        }

        public ParamsBuilder injectWithToken() {
            ZbConfig zbConfig = ZbPassport.getZbConfig();
            if (!TextUtils.isEmpty(zbConfig.getToken())) {
                map.put(InnerConstant.TOKEN, zbConfig.getToken());
            }
            map.put(InnerConstant.APP_ID, "" + zbConfig.getAppId());
            map.put(InnerConstant.APP_KEY, zbConfig.getAppKey());
            map.put(InnerConstant.APP_SECRET, zbConfig.getAppSecret());
            return this;
        }

        public ParamsBuilder url(String api) {
            this.api = api;
            return this;
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
            map.put(InnerConstant.SIGN, EncryptUtil.encrypt(api, map));
            return map;
        }

    }
}
