package com.zjrb.passport.listener;

import android.support.annotation.Nullable;

import org.json.JSONObject;

/**
 * Function: ZbCaptchaVerifyListener 验证码验证
 * <p>
 * Author: chen.h
 * Date: 2018/7/12
 */
public interface ZbCaptchaVerifyListener extends IFailure {
    void onSuccess(boolean isValid, @Nullable JSONObject passData);
}
