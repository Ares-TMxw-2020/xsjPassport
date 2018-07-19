package com.zjrb.passport.processor;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.zjrb.passport.Entity.LoginInfo;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.listener.ILoginResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Function: LoginJsonProcessor
 * <p>
 * Author: chen.h
 * Date: 2018/7/12
 */
public class LoginJsonProcessor implements JsonProcessor {

    private ILoginResult loginResult;

    public LoginJsonProcessor(@NonNull ILoginResult loginResult) {
        this.loginResult = loginResult;
    }

    @Override
    public void process(JSONObject jsonObject) throws JSONException {
        LoginInfo info = new LoginInfo();

        info.setPassportId(jsonObject.optInt("passport_id"));
        info.setPhoneNumber(jsonObject.optString("phone_number"));
        String token = jsonObject.optString("access_token");
        info.setToken(token);
        interceptToken(token);
        info.setCurrentLoginType(jsonObject.optInt("current_auth_type"));
        info.setNewUser(jsonObject.optBoolean("is_new"));

        loginResult.onSuccess(info);
    }

    private void interceptToken(String token) {
        if (!TextUtils.isEmpty(token)) {
            ZbPassport.setToken(token);
        }
    }
}
