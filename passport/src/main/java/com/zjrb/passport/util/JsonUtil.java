package com.zjrb.passport.util;

import com.zjrb.passport.domain.BindingListEntity;
import com.zjrb.passport.domain.LoginDataEntity;
import com.zjrb.passport.domain.ZbInfoEntity;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.domain.BaseData;
import com.zjrb.passport.net.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Function: JsonUtil
 * <p>
 * Author: chen.h
 * Date: 2018/7/5
 */
public class JsonUtil {

    public static BaseData jsonBaseData(Response response) {
        String jsonString = response.body().string();
        JSONObject jsonObject;
        BaseData data = null;
        try {
            jsonObject = new JSONObject(jsonString);
            data = new BaseData();
            data.code = jsonObject.optInt("code");
            data.message = jsonObject.optString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static LoginDataEntity jsonLoginData(Response response) {
        String jsonString = response.body().string();
        JSONObject jsonObject;
        LoginDataEntity loginDataEntity = null;
        try {
            jsonObject = new JSONObject(jsonString);
            loginDataEntity = new LoginDataEntity();
            loginDataEntity.code = jsonObject.optInt("code");
            loginDataEntity.message = jsonObject.optString("message");
            JSONObject innerObject = jsonObject.optJSONObject("data");
            if (innerObject != null) {
                ZbInfoEntity info = new ZbInfoEntity();
                info.setPassport_id(innerObject.optInt("passport_id"));
                info.setPhone_number(innerObject.optString("phone_number"));
                info.setAccess_token(innerObject.optString("access_token"));
                interceptToken(info.getAccess_token());
                info.setCurrent_auth_type(innerObject.optInt("current_auth_type"));
                info.setIs_new(innerObject.optBoolean("is_new"));

                JSONArray jsonArray = innerObject.getJSONArray("binding_list");
                if (jsonArray != null) {
                    List<BindingListEntity> list = new ArrayList<>();
                    for (int i = 0, length = jsonArray.length(); i < length; i++) {
                        JSONObject object = jsonArray.optJSONObject(i);
                        BindingListEntity bean = new BindingListEntity();
                        bean.setBinding_id(object.optInt("binding_id"));
                        bean.setAuth_type(object.optInt("auth_type"));
                        bean.setAuth_uid(object.optString("auth_uid"));
                        bean.setBinding_name(object.optString("binding_name"));
                        bean.setBinding_logo(object.optString("binding_logo"));
                        list.add(bean);
                    }
                    info.setBinding_list(list);
                }
                loginDataEntity.data = info;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return loginDataEntity;
    }

    private static void interceptToken(String token) {
        ZbPassport.setToken(token);
    }
}
