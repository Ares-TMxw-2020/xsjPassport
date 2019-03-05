package com.zjrb.passport.processor;

import android.support.annotation.NonNull;

import com.zjrb.passport.Entity.ClientInfo;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.listener.ZbInitListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2019/2/26 6:49 PM
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 初始化接口解析处理
 */
public class InitProcessor implements JsonProcessor {

    private ZbInitListener zbInitListener;

    public InitProcessor(@NonNull ZbInitListener zbInitListener) {
        this.zbInitListener = zbInitListener;
    }

    @Override
    public void process(JSONObject jsonObject) throws JSONException {
        JSONObject client = jsonObject.optJSONObject("client"); // data里面还有一层client
        ClientInfo info = new ClientInfo();
        if (client != null) {
            info.setClient_id(client.optInt("client_id"));
            info.setApp_name(client.optString("app_name"));
            info.setApp_logo(client.optString("app_logo"));
            info.setAccount_merge(client.optBoolean("account_merge"));
            info.setSignature_key(client.optString("signature_key"));
            ZbPassport.getZbConfig().setSignatureKey(client.optString("signature_key")); // 保存init接口下发的signatureKey到本地
            JSONArray supported_third_party = client.optJSONArray("supported_third_party");
            if (supported_third_party != null) {
                List<String> list = new ArrayList<>();
                for (int i = 0, length = supported_third_party.length(); i < length; i++) {
                    String thirdParty = supported_third_party.optString(i);
                    list.add(thirdParty);
                }
                info.setSupported_third_party(list);
            }
        }
        zbInitListener.onSuccess(info);
    }
}
