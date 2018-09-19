package com.zjrb.passport.listener;

import android.support.annotation.Nullable;

import org.json.JSONObject;

/**
 * Function: IResult
 * <p>
 * Author: chen.h
 * Date: 2018/7/12
 */
public interface IResult extends IFailure {
    void onSuccess(@Nullable JSONObject passData);
}
