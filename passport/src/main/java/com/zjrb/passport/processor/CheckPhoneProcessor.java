package com.zjrb.passport.processor;

import android.support.annotation.NonNull;

import com.zjrb.passport.listener.ZbCheckPhoneListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Function: CheckPhoneProcessor 验证手机是否绑定的json解析
 * <p>
 * Author: chen.h
 * Date: 2018/7/12
 */
public class CheckPhoneProcessor implements JsonProcessor {

    private ZbCheckPhoneListener listener;

    public CheckPhoneProcessor(@NonNull ZbCheckPhoneListener listener) {
        this.listener = listener;
    }

    @Override
    public void process(JSONObject jsonObject) throws JSONException {
        String phone = jsonObject.optString("phone_number");
        boolean isBind = jsonObject.optBoolean("phone_number_taken");
        listener.onSuccess(isBind);
    }


}
