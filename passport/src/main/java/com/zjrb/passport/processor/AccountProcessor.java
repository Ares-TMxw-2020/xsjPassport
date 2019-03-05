package com.zjrb.passport.processor;

import android.support.annotation.NonNull;

import com.zjrb.passport.Entity.AccountInfo;
import com.zjrb.passport.listener.ZbGetAccountInfoListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2019/2/27 10:41 AM
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 账号详情数据解析
 */
public class AccountProcessor implements JsonProcessor {

    private ZbGetAccountInfoListener listener;

    public AccountProcessor(@NonNull ZbGetAccountInfoListener listener) {
        this.listener = listener;
    }

    @Override
    public void process(JSONObject jsonObject) throws JSONException {
        JSONObject object = jsonObject.optJSONObject("user"); // data里包含一层user信息
        AccountInfo info = new AccountInfo();
        if (object != null) {
            info.setGroup_id(object.optInt("group_id"));
            info.setCurrent_client_id(object.optInt("current_client_id"));
            info.setAccount_id(object.optInt("account_id"));
            info.setPhone_number(object.optString("phone_number"));
            info.setPassword_reset_required(object.optBoolean("password_reset_required"));
            JSONArray third_parties = object.optJSONArray("third_parties");
            if (third_parties != null) {
                List<AccountInfo.ThirdPartiesBean> list = new ArrayList();
                for (int i = 0, length = third_parties.length(); i < length; i++) {
                    JSONObject thirdObject = third_parties.optJSONObject(i);
                    AccountInfo.ThirdPartiesBean bean = new AccountInfo.ThirdPartiesBean();
                    if (thirdObject != null) {
//                        bean.setAssociation_id(thirdObject.optInt("association_id"));
                        bean.setName(thirdObject.optString("name"));
//                        bean.setLogo(thirdObject.optString("logo"));
                        bean.setAuth_type(thirdObject.optInt("auth_type"));
                    }
                    list.add(bean);
                }
                info.setThird_parties(list);
            }
        }
        listener.onSuccess(info);
    }

}
