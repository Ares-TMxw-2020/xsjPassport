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

    public void checkPhone(String phoneNumber, ZbCheckListener listener) {

    }

    public void sendRegisterCaptcha(String phoneNumber, final ZbCaptchaListener listener) {
        client.newCall(getRequest(ApiManager.EndPoint.SMS_SEND_REGISTER_TOKEN,
                                  new ParamsBuild().add("phone_number", phoneNumber)
                                                   .build(ApiManager.EndPoint.SMS_SEND_REGISTER_TOKEN)))
              .enqueue(new CallBack() {
                  @Override
                  public void onSuccess(Response response) throws IOException {
                      listener.onSuccess();
                  }

                  @Override
                  public void onFail(Request call, IOException e) {
                      listener.onFailure(0, "");
                  }
              });
    }

    public void sendLoginCaptcha(String phoneNumber, ZbCaptchaListener listener) {

    }

    public void sendRetrieveCaptcha(String phoneNumber, ZbCaptchaListener listener) {

    }

    public void sendBindCaptcha(String phoneNumber, ZbCaptchaListener listener) {

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


    public static Request getRequest(String api, Map<String, String> params) {
        Request request = null;
        try {
            FormBody body = new FormBody.Builder().map(params).build();
            request = new Request.Builder().post(body).url(ApiManager.joinUrl(api)).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    public static class ParamsBuild {
        private Map<String, String> map;

        public ParamsBuild() {
            map = new LinkedHashMap<>();
            ZbConfig zbConfig = ZbPassport.getZbConfig();
            map.put(K.APP_ID, "" + zbConfig.getAppId());
            map.put(K.APP_KEY, zbConfig.getAppKey());
            map.put(K.APP_SECRET, zbConfig.getAppSecret());
        }

        public ParamsBuild add(String key, String value) {
            if (!TextUtils.isEmpty(key)) {
                map.put(key, value);
            }
            return this;
        }

        public Map<String, String> build(String api) {
            map.put(K.SIGN, EncryptUtil.encrypt(api, map));
            return map;
        }
    }
}

