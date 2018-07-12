package com.zjrb.passport;

import android.support.annotation.NonNull;

import com.zjrb.passport.listener.IFailure;
import com.zjrb.passport.listener.IResult;
import com.zjrb.passport.net.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Function: ResponseProcessor
 * <p>
 * Author: chen.h
 * Date: 2018/7/12
 */
public class ResponseProcessor {

    public static void process(Response response, IResult iResult) {
        String jsonString = response.body().string();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            int code = jsonObject.optInt("code");
            if (code == StatusCode.SUCCESS) {
                iResult.onSuccess();
            } else {
                String message = jsonObject.optString("message");
                iResult.onFailure(code, message);
            }
        } catch (JSONException e) {
            iResult.onFailure(StatusCode.ERROR_JSON, e.getMessage());
        }
    }


    public static void process(Response response, @NonNull JsonProcessor processor, IFailure iFailure) {
        String jsonString = response.body().string();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            int code = jsonObject.optInt("code");
            if (code == StatusCode.SUCCESS) {
                JSONObject innerObject = jsonObject.optJSONObject("data");
                if (innerObject != null) {
                    processor.process(innerObject);
                } else {
                    iFailure.onFailure(StatusCode.ERROR_JSON, "错误的接口返回");
                }
            } else {
                String message = jsonObject.optString("message");
                iFailure.onFailure(code, message);
            }
        } catch (JSONException e) {
            iFailure.onFailure(StatusCode.ERROR_JSON, e.getMessage());
        }
    }
}
