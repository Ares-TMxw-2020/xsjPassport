package com.zjrb.passport.processor;

import android.support.annotation.NonNull;

import com.zjrb.passport.constant.ErrorCode;
import com.zjrb.passport.listener.IFailure;
import com.zjrb.passport.listener.IResult;
import com.zjrb.passport.net.response.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Function: ResponseProcessor
 * <p>
 * Author: chen.h
 * Date: 2018/7/12
 */
public class ResponseProcessor {

    public static final int SUCCESS = 0;

    /**
     * JSON 解析,返回结果只有code和message及data_bypass
     */
    public static void process(Response response, IResult iResult) {
        String jsonString = response.body().string();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            int code = jsonObject.optInt("code");
            JSONObject passObject = jsonObject.optJSONObject("data_bypass");
            if (code == SUCCESS) {
                iResult.onSuccess(passObject);
            } else {
                String message = jsonObject.optString("message");
                iResult.onFailure(code, message);
            }
        } catch (JSONException e) {
            iResult.onFailure(ErrorCode.ERROR_JSON, e.getMessage());
        }
    }

    /**
     * JSON 解析,返回结果只有code和message及data_bypass，支持成功拦截
     */
    public static void process(Response response, IResult iResult, Interceptor interceptor) {
        String jsonString = response.body().string();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            int code = jsonObject.optInt("code");
            JSONObject passObject = jsonObject.optJSONObject("data_bypass");
            if (code == SUCCESS) {
                interceptor.onIntercept();
                iResult.onSuccess(passObject);
            } else {
                String message = jsonObject.optString("message");
                iResult.onFailure(code, message);
            }
        } catch (JSONException e) {
            iResult.onFailure(ErrorCode.ERROR_JSON, e.getMessage());
        }
    }


    /**
     * JSON 解析
     *
     * @param processor data的json解析器
     */
    public static void process(Response response, @NonNull JsonProcessor processor, IFailure iFailure) {
        String jsonString = response.body().string();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            int code = jsonObject.optInt("code");
            if (code == SUCCESS) {
                JSONObject innerObject = jsonObject.optJSONObject("data");
                JSONObject passObject = jsonObject.optJSONObject("data_bypass");
                if (innerObject != null) {
                    processor.process(innerObject, passObject);
                } else {
                    //接口定义了data返回，当没有data返回就认为接口返回错误
                    iFailure.onFailure(ErrorCode.ERROR_INTERFACE_DATA, "错误的接口返回");
                }
            } else {
                String message = jsonObject.optString("message");
                iFailure.onFailure(code, message);
            }
        } catch (JSONException e) {
            iFailure.onFailure(ErrorCode.ERROR_JSON, e.getMessage());
        }
    }

    /**
     * JSON 解析，支持成功拦截
     *
     * @param processor data的json解析器
     */
    public static void process(Response response, @NonNull JsonProcessor processor, IFailure iFailure, Interceptor interceptor) {
        String jsonString = response.body().string();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            int code = jsonObject.optInt("code");
            if (code == SUCCESS) {
                JSONObject innerObject = jsonObject.optJSONObject("data");
                JSONObject passObject = jsonObject.optJSONObject("data_bypass");
                if (innerObject != null) {
                    interceptor.onIntercept();
                    processor.process(innerObject, passObject);
                } else {
                    //接口定义了data返回，当没有data返回就认为接口返回错误
                    iFailure.onFailure(ErrorCode.ERROR_INTERFACE_DATA, "错误的接口返回");
                }
            } else {
                String message = jsonObject.optString("message");
                iFailure.onFailure(code, message);
            }
        } catch (JSONException e) {
            iFailure.onFailure(ErrorCode.ERROR_JSON, e.getMessage());
        }
    }

    /**
     * 成功拦截接口
     */
    public interface Interceptor {

        void onIntercept();

    }
}
