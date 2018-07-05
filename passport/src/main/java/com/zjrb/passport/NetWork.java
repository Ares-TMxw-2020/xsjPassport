package com.zjrb.passport;

import android.text.TextUtils;

import com.zjrb.passport.constant.K;
import com.zjrb.passport.listener.ZbBindListener;
import com.zjrb.passport.listener.ZbCaptchaListener;
import com.zjrb.passport.listener.ZbCheckListener;
import com.zjrb.passport.listener.ZbGetInfoListener;
import com.zjrb.passport.listener.ZbLoginListener;
import com.zjrb.passport.listener.ZbRegisterListener;
import com.zjrb.passport.listener.ZbUnbindListener;
import com.zjrb.passport.net.ApiManager;
import com.zjrb.passport.net.CallBack;
import com.zjrb.passport.net.FormBody;
import com.zjrb.passport.net.Request;
import com.zjrb.passport.net.Response;
import com.zjrb.passport.net.ZbHttpClient;
import com.zjrb.passport.net.util.EncryptUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Function: NetWork
 * <p>
 * Author: chen.h
 * Date: 2018/7/4
 */
public class NetWork {
    ZbHttpClient client;

    public NetWork() {
        client = new ZbHttpClient.Builder().connTimeOut(10 * 1000)
                                           .readTimeOut(10 * 1000)
                                           .writeTimeOut(10 * 1000)
                                           .build();
    }

    public void checkPhone(String phoneNumber, ZbCheckListener listener) {

    }

    private BaseData json(String jsonString) {
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

    public void sendRegisterCaptcha(String phoneNumber, final ZbCaptchaListener listener) {
        ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.SMS_SEND_REGISTER_TOKEN).add("phone_number",
                                                                                                   phoneNumber);
        client.newCall(getRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                BaseData data = json(response.body().string());
                if (data.code == StatusCode.SUCCESS) {
                    listener.onSuccess();
                } else {
                    listener.onFailure(data.code, data.message);
                }
            }

            @Override
            public void onFail(Request call, IOException e) {
                listener.onFailure(-1, StatusCode.getMessage(-1));
            }
        });
    }

    public void sendLoginCaptcha(String phoneNumber, final ZbCaptchaListener listener) {
        ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.SMS_SEND_LOGIN_TOKEN).add("phone_number",
                                                                                                phoneNumber);
        client.newCall(getRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                BaseData data = json(response.body().string());
                if (data.code == StatusCode.SUCCESS) {
                    listener.onSuccess();
                } else {
                    listener.onFailure(data.code, data.message);
                }
            }

            @Override
            public void onFail(Request call, IOException e) {
                listener.onFailure(-1, StatusCode.getMessage(-1));
            }
        });
    }

    public void sendRetrieveCaptcha(String phoneNumber, final ZbCaptchaListener listener) {
        ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.SMS_SEND_RESET_TOKEN).add("phone_number",
                                                                                                phoneNumber);
        client.newCall(getRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                BaseData data = json(response.body().string());
                if (data.code == StatusCode.SUCCESS) {
                    listener.onSuccess();
                } else {
                    listener.onFailure(data.code, data.message);
                }
            }

            @Override
            public void onFail(Request call, IOException e) {
                listener.onFailure(-1, StatusCode.getMessage(-1));
            }
        });
    }

    public void sendBindCaptcha(String phoneNumber, final ZbCaptchaListener listener) {
        ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.SMS_SEND_BINDING_TOKEN).add("phone_number",
                                                                                                  phoneNumber);
        client.newCall(getRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                BaseData data = json(response.body().string());
                if (data.code == StatusCode.SUCCESS) {
                    listener.onSuccess();
                } else {
                    listener.onFailure(data.code, data.message);
                }
            }

            @Override
            public void onFail(Request call, IOException e) {
                listener.onFailure(-1, StatusCode.getMessage(-1));
            }
        });
    }

    public void register(String phoneNumber, String password, String captcha, ZbRegisterListener listener) {

    }

    public void login(String phoneNumber, String password, ZbLoginListener listener) {

    }

    public void loginCaptcha(String phoneNumber, String captcha, ZbLoginListener listener) {

    }

    public void loginThird(String thirdUniqueId, ZbLoginListener listener) {

    }

    public void getInfo(String token, ZbGetInfoListener listener) {

    }

    public void retrievePassword() {
        //TODO 逻辑待商榷
    }

    public void resetPassword() {
        //TODO 逻辑待商榷
    }

    public void bindPhone(String phoneNumber, String captcha, ZbBindListener listener) {}


    public void unbindThird(String thirdUniqueId, ZbUnbindListener listener) {}


    public static Request getRequest(ParamsBuilder builder) {
        Request request = null;
        try {
            FormBody body = new FormBody.Builder().map(builder.build()).build();
            request = new Request.Builder().post(body).url(builder.getUrl()).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    public static class ParamsBuilder {
        private Map<String, String> map;
        private String api;

        public ParamsBuilder(String api) {
            map = new LinkedHashMap<>();
            this.api = api;
            ZbConfig zbConfig = ZbPassport.getZbConfig();
            map.put(K.APP_ID, "" + zbConfig.getAppId());
            map.put(K.APP_KEY, zbConfig.getAppKey());
            map.put(K.APP_SECRET, zbConfig.getAppSecret());
        }

        public ParamsBuilder add(String key, String value) {
            if (!TextUtils.isEmpty(key)) {
                map.put(key, value);
            }
            return this;
        }

        public String getUrl() {
            return ApiManager.joinUrl(api);
        }

        public Map<String, String> build() {
            map.put(K.SIGN, EncryptUtil.encrypt(api, map));
            return map;
        }
    }
}

