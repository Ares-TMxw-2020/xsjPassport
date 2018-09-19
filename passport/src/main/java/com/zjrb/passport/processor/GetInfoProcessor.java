package com.zjrb.passport.processor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zjrb.passport.Entity.LoginInfo;
import com.zjrb.passport.Entity.ThirdInfo;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.listener.ILoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Function: GetInfoProcessor 获取详情的json解析
 * <p>
 * Author: chen.h
 * Date: 2018/7/17
 */
public class GetInfoProcessor implements JsonProcessor {

    private ILoginResult loginResult;

    public GetInfoProcessor(@NonNull ILoginResult loginResult) {
        this.loginResult = loginResult;
    }

    @Override
    public void process(JSONObject jsonObject, @Nullable JSONObject passData) throws JSONException {
        LoginInfo info = new LoginInfo();

        info.setPassportId(jsonObject.optInt("passport_id"));
        info.setPhoneNumber(jsonObject.optString("phone_number"));
        info.setToken(ZbPassport.getZbConfig().getToken());
        info.setCurrentLoginType(jsonObject.optInt("current_auth_type"));
        info.setNewUser(jsonObject.optBoolean("is_new"));

        JSONArray jsonArray = jsonObject.optJSONArray("binding_list");
        if (jsonArray != null) {
            List<ThirdInfo> list = new ArrayList<>();
            for (int i = 0, length = jsonArray.length(); i < length; i++) {
                JSONObject object = jsonArray.optJSONObject(i);
                ThirdInfo thirdInfo = new ThirdInfo();
                thirdInfo.setBindId(object.optInt("binding_id"));
                thirdInfo.setLoginType(object.optInt("auth_type"));
                thirdInfo.setUid(object.optString("auth_uid"));
                thirdInfo.setChannelName(object.optString("binding_name"));
                thirdInfo.setLogoUrl(object.optString("binding_logo"));
                list.add(thirdInfo);
            }
            info.setBindList(list);
        }

        loginResult.onSuccess(info, passData);
    }
}
