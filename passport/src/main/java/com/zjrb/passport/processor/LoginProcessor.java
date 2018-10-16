package com.zjrb.passport.processor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.zjrb.passport.Entity.LoginInfo;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.listener.ILoginResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Function: LoginJsonProcessor 登录的json解析
 * <p>
 * Author: chen.h
 * Date: 2018/7/12
 */
public class LoginProcessor implements JsonProcessor {

    private ILoginResult loginResult;

    public LoginProcessor(@NonNull ILoginResult loginResult) {
        this.loginResult = loginResult;
    }

    @Override
    public void process(JSONObject jsonObject, @Nullable String passData) throws JSONException {
        LoginInfo info = new LoginInfo();

        info.setPassportId(jsonObject.optInt("passport_id"));
        info.setPhoneNumber(jsonObject.optString("phone_number"));
        String token = jsonObject.optString("access_token");
        info.setToken(token);
        interceptToken(token);
        info.setCurrentLoginType(jsonObject.optInt("current_auth_type"));
        info.setNewUser(jsonObject.optBoolean("is_new"));

        loginResult.onSuccess(info, passData);
    }

    private void interceptToken(String token) {
        if (!TextUtils.isEmpty(token)) {
            ZbPassport.setToken(token);
        }
    }
}
