package com.zjrb.passport.processor;

import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Function: JsonProcessor
 * <p>
 * Author: chen.h
 * Date: 2018/7/12
 */
public interface JsonProcessor {
    void process(JSONObject jsonObject, @Nullable  JSONObject passData) throws JSONException;
}
