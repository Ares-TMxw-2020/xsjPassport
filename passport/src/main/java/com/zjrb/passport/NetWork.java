package com.zjrb.passport;

import android.text.TextUtils;

import com.zjrb.passport.constant.InnerConstant;
import com.zjrb.passport.listener.IResult;
import com.zjrb.passport.listener.ZbBindPhoneListener;
import com.zjrb.passport.listener.ZbBindThirdListener;
import com.zjrb.passport.listener.ZbCaptchaSendListener;
import com.zjrb.passport.listener.ZbCaptchaVerifyListener;
import com.zjrb.passport.listener.ZbChangePasswordListener;
import com.zjrb.passport.listener.ZbCheckPhoneListener;
import com.zjrb.passport.listener.ZbFindPasswordListener;
import com.zjrb.passport.listener.ZbGetInfoListener;
import com.zjrb.passport.listener.ZbLoginListener;
import com.zjrb.passport.listener.ZbLogoutListener;
import com.zjrb.passport.listener.ZbRegisterListener;
import com.zjrb.passport.listener.ZbUnBindThirdListener;
import com.zjrb.passport.net.ApiManager;
import com.zjrb.passport.net.CallBack;
import com.zjrb.passport.net.FormBody;
import com.zjrb.passport.net.Request;
import com.zjrb.passport.net.Response;
import com.zjrb.passport.net.ZbHttpClient;
import com.zjrb.passport.net.util.EncryptUtil;
import com.zjrb.passport.processor.CheckJsonProcessor;
import com.zjrb.passport.processor.LoginJsonProcessor;
import com.zjrb.passport.processor.ResponseProcessor;
import com.zjrb.passport.processor.VerifyJsonProcessor;
import com.zjrb.passport.util.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Function: NetWork
 * <p>
 * Author: chen.h
 * Date: 2018/7/4
 */
public class NetWork {
    ZbHttpClient client;

    public NetWork() {
        client = new ZbHttpClient.Builder().build();
    }


    public void sendRegisterCaptcha(String phoneNumber, ZbCaptchaSendListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_SEND_REGISTER_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber);
        requestPostWithNoData(listener, builder);
    }

    public void sendLoginCaptcha(String phoneNumber, ZbCaptchaSendListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_SEND_LOGIN_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber);
        requestPostWithNoData(listener, builder);
    }

    public void sendRetrieveCaptcha(String phoneNumber, ZbCaptchaSendListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_SEND_RESET_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber);
        requestPostWithNoData(listener, builder);
    }

    public void sendBindCaptcha(String phoneNumber, ZbCaptchaSendListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_SEND_BINDING_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber);
        requestPostWithNoData(listener, builder);
    }


    public void verifyRegisterCaptcha(String phoneNumber, String captcha, final ZbCaptchaVerifyListener listener) {
        final ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_VALIDATE_REGISTER_TOKEN)
                                                         .inject()
                                                         .add("phone_number", phoneNumber)
                                                         .add("sms_token", captcha);
        client.newCall(buildPostRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
    }

    public void verifyLoginCaptcha(String phoneNumber, String captcha, final ZbCaptchaVerifyListener listener) {
        final ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_VALIDATE_LOGIN_TOKEN)
                                                         .inject()
                                                         .add("phone_number", phoneNumber)
                                                         .add("sms_token", captcha);
        client.newCall(buildPostRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
    }

    public void verifyFindCaptcha(String phoneNumber, String captcha, final ZbCaptchaVerifyListener listener) {
        final ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_VALIDATE_RESET_TOKEN)
                                                         .inject()
                                                         .add("phone_number", phoneNumber)
                                                         .add("sms_token", captcha);
        client.newCall(buildPostRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
    }

    public void verifyBindCaptcha(String phoneNumber, String captcha, final ZbCaptchaVerifyListener listener) {
        final ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_VALIDATE_BINDING_TOKEN)
                                                         .inject()
                                                         .add("phone_number", phoneNumber)
                                                         .add("sms_token", captcha);
        client.newCall(buildPostRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
    }

    /**
     * post请求,返回结果无data,只有code和message的请求封装
     *
     * @param listener
     * @param builder
     */
    private void requestPostWithNoData(final IResult listener, final ParamsBuilder builder) {
        client.newCall(buildPostRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                ResponseProcessor.process(response, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
    }

    /**
     * 手机号注册浙报通行证
     *
     * @param phoneNumber
     * @param password
     * @param captcha
     * @param listener
     */
    public void register(String phoneNumber, String password, String captcha, final ZbRegisterListener listener) {
        final ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_REGISTER)
                                                         .inject()
                                                         .add("phone_number", phoneNumber)
                                                         .add("password", password)
                                                         .add("sms_token", captcha);
        client.newCall(buildPostRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                LoginJsonProcessor processor = new LoginJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
    }

    /**
     * 手机号密码登录
     *
     * @param phoneNumber
     * @param password
     * @param listener
     */
    public void login(String phoneNumber, String password, final ZbLoginListener listener) {
        final ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_PASSWORD_LOGIN)
                                                         .inject()
                                                         .add("phone_number", phoneNumber)
                                                         .add("password", password);
        client.newCall(buildPostRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                LoginJsonProcessor processor = new LoginJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
    }

    /**
     * 手机号与验证码登录
     *
     * @param phoneNumber
     * @param captcha
     * @param listener
     */
    public void loginCaptcha(String phoneNumber, String captcha, final ZbLoginListener listener) {
        final ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_SMS_TOKEN_LOGIN)
                                                         .inject()
                                                         .add("phone_number", phoneNumber)
                                                         .add("sms_token", captcha);
        client.newCall(buildPostRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                LoginJsonProcessor processor = new LoginJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
    }

    public void loginThird(int thirdType, String thirdUnionId, final ZbLoginListener listener) {
        final ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.THIRD_PARTY_LOGIN)
                                                         .inject()
                                                         .add("auth_type", "" + thirdType)
                                                         .add("auth_uid", thirdUnionId);
        client.newCall(buildPostRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                LoginJsonProcessor processor = new LoginJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
    }

    /**
     * 获取通行证详情  Get请求
     *
     * @param listener
     */
    public void getInfo(final ZbGetInfoListener listener) {
        final ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_DETAIL).injectWithToken();
        client.newCall(buildGetRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                LoginJsonProcessor processor = new LoginJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
    }

    /**
     * 绑定浙报通行证手机号
     *
     * @param phoneNumber
     * @param captcha
     * @param listener
     */
    public void bindPhone(String phoneNumber, String captcha, final ZbBindPhoneListener listener) {
        final ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_BIND_PHONE_NUMBER)
                                                         .injectWithToken()
                                                         .add("new_phone_number", phoneNumber)
                                                         .add("sms_token", captcha);
        requestPostWithNoData(listener, builder);
    }

    /**
     * 更改通行证密码
     *
     * @param oldPassWord
     * @param newPassWord
     * @param listener
     */
    public void changePassword(String oldPassWord, String newPassWord, final ZbChangePasswordListener listener) {
        final ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_CHANGE_PASSWORD)
                                                         .injectWithToken()
                                                         .add("old_password", oldPassWord)
                                                         .add("new_password", newPassWord);
        requestPostWithNoData(listener, builder);
    }

    /**
     * 在修改密码时，检查原密码是否正确的接口
     *
     * @param oldPassWord 原密码
     * @param listener
     */
    public void checkPassWord(String oldPassWord, final ZbCaptchaVerifyListener listener) {
        final ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_CHECK_PASSWORD).injectWithToken()
                .add("password",
                        oldPassWord);
        client.newCall(buildGetRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) {
                Logger.d(builder, response);
                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
    }

    /**
     * 找回通行证密码
     *
     * @param phoneNumber
     * @param captcha
     * @param newPassword
     * @param listener
     */
    public void findPassword(String phoneNumber, String captcha, String newPassword, final ZbFindPasswordListener listener) {
        final ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_RESET_PASSWORD)
                                                         .inject()
                                                         .add("phone_number", phoneNumber)
                                                         .add("sms_token", captcha)
                                                         .add("new_password", newPassword);
        requestPostWithNoData(listener, builder);
    }

    /**
     * 检测手机号是否已经绑定浙报通行证接口  Get请求
     *
     * @param phoneNumber
     * @param listener
     */
    public void checkBindState(String phoneNumber, final ZbCheckPhoneListener listener) {
        final ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_CHECK_BINDING)
                                                         .inject()
                                                         .add("phone_number", phoneNumber);
        client.newCall(buildGetRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                CheckJsonProcessor processor = new CheckJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
    }

    /**
     * 退出登录
     *
     * @param listener
     */
    public void logout(final ZbLogoutListener listener) {
        final ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_LOGOUT).injectWithToken();
        requestPostWithNoData(listener, builder);
    }


    public void bindThird(int thirdType, String thirdUnionId, final ZbBindThirdListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.THIRD_PARTY_BIND)
                                                   .injectWithToken()
                                                   .add("auth_type", "" + thirdType)
                                                   .add("auth_uid", thirdUnionId);
        requestPostWithNoData(listener, builder);
    }

    public void unbindThird(int thirdType, final ZbUnBindThirdListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.THIRD_PARTY_UNBIND)
                                                   .injectWithToken()
                                                   .add("auth_type", "" + thirdType);
        requestPostWithNoData(listener, builder);
    }

    /**
     * 构建get方式请求
     *
     * @param builder
     * @return
     */
    public static Request buildGetRequest(ParamsBuilder builder) {
        Request request = null;
        try {
            FormBody body = new FormBody.Builder().map(builder.build()).build();
            request = new Request.Builder().get(body).url(builder.getUrl()).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    /**
     * 构建post方式请求
     *
     * @param builder
     * @return
     */
    public static Request buildPostRequest(ParamsBuilder builder) {
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
        private TreeMap<String, String> map;
        private String api;

        public ParamsBuilder() {
            map = new TreeMap<>();
        }

        public ParamsBuilder inject() {
            ZbConfig zbConfig = ZbPassport.getZbConfig();
            map.put(InnerConstant.APP_ID, "" + zbConfig.getAppId());
            map.put(InnerConstant.APP_KEY, zbConfig.getAppKey());
            map.put(InnerConstant.APP_SECRET, zbConfig.getAppSecret());
            return this;
        }

        public ParamsBuilder injectWithToken() {
            ZbConfig zbConfig = ZbPassport.getZbConfig();
            if (!TextUtils.isEmpty(zbConfig.getToken())) {
                map.put(InnerConstant.TOKEN, zbConfig.getToken());
            }
            map.put(InnerConstant.APP_ID, "" + zbConfig.getAppId());
            map.put(InnerConstant.APP_KEY, zbConfig.getAppKey());
            map.put(InnerConstant.APP_SECRET, zbConfig.getAppSecret());
            return this;
        }

        public ParamsBuilder url(String api) {
            this.api = api;
            return this;
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
            map.put(InnerConstant.SIGN, EncryptUtil.encrypt(api, map));
            return map;
        }

        public String paramString() {
            StringBuilder sb = new StringBuilder();
            for (String k : map.keySet()) {
                sb.append(k).append("=").append(map.get(k)).append("&");
            }
            sb.setLength(sb.length() - 1);
            return sb.toString();
        }
    }
}

