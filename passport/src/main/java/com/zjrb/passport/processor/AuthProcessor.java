package com.zjrb.passport.processor;

import android.support.annotation.NonNull;

import com.zjrb.passport.Entity.AuthInfo;
import com.zjrb.passport.listener.ZbAuthListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Date: 2019/2/27 10:12 AM
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 认证接口json解析处理
 */
public class AuthProcessor implements JsonProcessor {

    private ZbAuthListener listener;

    public AuthProcessor(@NonNull ZbAuthListener listener) {
        this.listener = listener;
    }

    @Override
    public void process(JSONObject jsonObject) throws JSONException {
        JSONObject authorizationCode = jsonObject.optJSONObject("authorization_code"); // data里面还有一层authorization_code
        AuthInfo info = new AuthInfo();
        if (authorizationCode != null) {
            info.setGrant_type(authorizationCode.optString("grant_type"));
            info.setCode(authorizationCode.optString("code"));
            info.setRedirect_uri(authorizationCode.optString("redirect_uri"));
            info.setClient_id(authorizationCode.optInt("client_id"));
        }
        listener.onSuccess(info);
    }
}
