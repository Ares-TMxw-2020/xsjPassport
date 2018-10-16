package com.zjrb.passport.processor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zjrb.passport.listener.ZbCaptchaVerifyListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Function: CaptchaVerifyJsonProcessor 验证的json解析
 * <p>
 * Author: chen.h
 * Date: 2018/7/12
 */
public class VerifyJsonProcessor implements JsonProcessor {

    private ZbCaptchaVerifyListener listener;

    public VerifyJsonProcessor(@NonNull ZbCaptchaVerifyListener listener) {
        this.listener = listener;
    }

    @Override
    public void process(JSONObject jsonObject, @Nullable String passData) throws JSONException {
        boolean isValid = jsonObject.optBoolean("valid");
        listener.onSuccess(isValid, passData);
    }

}
