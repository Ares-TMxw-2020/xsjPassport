package com.zjrb.passport.listener;

import android.support.annotation.Nullable;

/**
 * Function: ZbCaptchaVerifyListener 验证码验证
 * <p>
 * Author: chen.h
 * Date: 2018/7/12
 */
public interface ZbCaptchaVerifyListener extends IFailure {
    void onSuccess(boolean isValid, @Nullable String passData);
}
