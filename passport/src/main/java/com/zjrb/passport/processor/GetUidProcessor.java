package com.zjrb.passport.processor;

import android.support.annotation.NonNull;

import com.zjrb.passport.Entity.UidInfo;
import com.zjrb.passport.listener.ZbGetUidListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Date: 2019/4/8 9:29 AM
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 获取钉钉UID
 */
public class GetUidProcessor implements JsonProcessor {

    private ZbGetUidListener listener;

    public GetUidProcessor(@NonNull ZbGetUidListener listener) {
        this.listener = listener;
    }

    @Override
    public void process(JSONObject jsonObject) throws JSONException {
        UidInfo info = new UidInfo();
        String uid = jsonObject.optString("uid");
        info.setUid(uid);
        listener.onSuccess(info);
    }
}
