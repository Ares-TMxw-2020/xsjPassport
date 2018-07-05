package com.zjrb.passport.util;

import com.zjrb.passport.ZbInfo;
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
            data.code = jsonObject.getInt("code");
            data.message = jsonObject.getString("message");
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
            loginData.code = jsonObject.getInt("code");
            loginData.message = jsonObject.getString("message");
            JSONObject innerObject = jsonObject.getJSONObject("data");
            if (innerObject != null) {
                ZbInfo info = new ZbInfo();
                info.setPassport_id(innerObject.getInt("passport_id"));
                info.setPhone_number(innerObject.getString("phone_number"));
                info.setAccess_token(innerObject.getString("access_token"));
                info.setCurrent_auth_type(innerObject.getInt("current_auth_type"));
                info.setIs_new(innerObject.getBoolean("is_new"));

                JSONArray jsonArray = innerObject.getJSONArray("binding_list");
                if (jsonArray != null) {
                    List<ZbInfo.BindingListBean> list = new ArrayList<>();
                    for (int i = 0, length = jsonArray.length(); i < length; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        ZbInfo.BindingListBean bean = new ZbInfo.BindingListBean();
                        bean.setBinding_id(object.getInt("binding_id"));
                        bean.setAuth_type(object.getInt("auth_type"));
                        bean.setAuth_uid(object.getString("auth_uid"));
                        bean.setBinding_name(object.getString("binding_name"));
                        bean.setBinding_logo(object.getString("binding_logo"));
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
}
