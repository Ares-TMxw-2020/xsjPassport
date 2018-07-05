package com.zjrb.passport.util;

import com.zjrb.passport.domain.BindingListBean;
import com.zjrb.passport.domain.ZbInfo;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.domain.BaseData;
import com.zjrb.passport.domain.LoginData;
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

    public static LoginData jsonLoginData(Response response) {
        String jsonString = response.body().string();
        JSONObject jsonObject;
        LoginData loginData = null;
        try {
            jsonObject = new JSONObject(jsonString);
            loginData = new LoginData();
            loginData.code = jsonObject.optInt("code");
            loginData.message = jsonObject.optString("message");
            JSONObject innerObject = jsonObject.optJSONObject("data");
            if (innerObject != null) {
                ZbInfo info = new ZbInfo();
                info.setPassport_id(innerObject.optInt("passport_id"));
                info.setPhone_number(innerObject.optString("phone_number"));
                info.setAccess_token(innerObject.optString("access_token"));
                interceptToken(info.getAccess_token());
                info.setCurrent_auth_type(innerObject.optInt("current_auth_type"));
                info.setIs_new(innerObject.optBoolean("is_new"));

                JSONArray jsonArray = innerObject.getJSONArray("binding_list");
                if (jsonArray != null) {
                    List<BindingListBean> list = new ArrayList<>();
                    for (int i = 0, length = jsonArray.length(); i < length; i++) {
                        JSONObject object = jsonArray.optJSONObject(i);
                        BindingListBean bean = new BindingListBean();
                        bean.setBinding_id(object.optInt("binding_id"));
                        bean.setAuth_type(object.optInt("auth_type"));
                        bean.setAuth_uid(object.optString("auth_uid"));
                        bean.setBinding_name(object.optString("binding_name"));
                        bean.setBinding_logo(object.optString("binding_logo"));
                        list.add(bean);
                    }
                    info.setBinding_list(list);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return loginData;
    }

    private static void interceptToken(String token) {
        ZbPassport.setToken(token);
    }
}
