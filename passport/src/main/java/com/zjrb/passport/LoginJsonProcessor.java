package com.zjrb.passport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Function: LoginJsonProcessor
 * <p>
 * Author: chen.h
 * Date: 2018/7/12
 */
public class LoginJsonProcessor implements JsonProcessor {

    @Override
    public void process(JSONObject jsonObject) throws JSONException {
        LoginInfo info = new LoginInfo();

        info.setPassportId(jsonObject.optInt("passport_id"));
        info.setPhoneNumber(jsonObject.optString("phone_number"));
        info.setToken(jsonObject.optString("access_token"));
        info.setCurrentLoginType(jsonObject.optInt("current_auth_type"));
        info.setNewUser(jsonObject.optBoolean("is_new"));

        JSONArray jsonArray = jsonObject.getJSONArray("binding_list");
        if (jsonArray != null) {
            List<LoginInfo.ThirdInfo> list = new ArrayList<>();
            for (int i = 0, length = jsonArray.length(); i < length; i++) {
                JSONObject object = jsonArray.optJSONObject(i);
                LoginInfo.ThirdInfo thirdInfo = new LoginInfo.ThirdInfo();
                thirdInfo.setBindId(object.optInt("binding_id"));
                thirdInfo.setLoginType(object.optInt("auth_type"));
                thirdInfo.setUid(object.optString("auth_uid"));
                thirdInfo.setChannel(object.optString("binding_name"));
                thirdInfo.setLogoUrl(object.optString("binding_logo"));
                list.add(thirdInfo);
            }
            info.setBindList(list);
        }
    }
}
