package com.zjrb.passport.processor;

import android.support.annotation.NonNull;

import com.zjrb.passport.Entity.CheckPhoneInfo;
import com.zjrb.passport.listener.ZbCheckPhoneListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Date: 2019/4/8 9:29 AM
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 检测手机号是否注册过通行证
 */
public class CheckPhoneProcessor implements JsonProcessor {

    private ZbCheckPhoneListener listener;

    public CheckPhoneProcessor(@NonNull ZbCheckPhoneListener listener) {
        this.listener = listener;
    }

    @Override
    public void process(JSONObject jsonObject) throws JSONException {
        CheckPhoneInfo info = new CheckPhoneInfo();
        boolean exists = jsonObject.optBoolean("exist");
        info.setExist(exists);
        listener.onSuccess(info);
    }
}
