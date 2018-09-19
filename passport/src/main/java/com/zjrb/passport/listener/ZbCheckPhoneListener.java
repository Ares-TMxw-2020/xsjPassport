package com.zjrb.passport.listener;

import android.support.annotation.Nullable;

import org.json.JSONObject;

/**
 * Function: ZbCheckListener
 * <p>
 * Author: chen.h
 * Date: 2018/6/28
 */
public interface ZbCheckPhoneListener extends IFailure {
    void onSuccess(boolean isBind, @Nullable JSONObject passData);
}
