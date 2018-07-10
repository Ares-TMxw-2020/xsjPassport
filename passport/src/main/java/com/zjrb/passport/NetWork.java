package com.zjrb.passport;

import android.text.TextUtils;

import com.zjrb.passport.constant.K;
import com.zjrb.passport.domain.BaseData;
import com.zjrb.passport.domain.CheckBindingEntity;
import com.zjrb.passport.domain.LoginDataEntity;
import com.zjrb.passport.listener.ZbBindListener;
import com.zjrb.passport.listener.ZbChangePasswordListener;
import com.zjrb.passport.listener.ZbCheckListener;
import com.zjrb.passport.listener.ZbGetInfoListener;
import com.zjrb.passport.listener.ZbListener;
import com.zjrb.passport.listener.ZbLoginListener;
import com.zjrb.passport.listener.ZbLogoutListener;
import com.zjrb.passport.listener.ZbRegisterListener;
import com.zjrb.passport.listener.ZbResetPasswordListener;
import com.zjrb.passport.net.ApiManager;
import com.zjrb.passport.net.CallBack;
import com.zjrb.passport.net.FormBody;
import com.zjrb.passport.net.Request;
import com.zjrb.passport.net.Response;
import com.zjrb.passport.net.ZbHttpClient;
import com.zjrb.passport.net.util.EncryptUtil;
import com.zjrb.passport.util.JsonUtil;
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
        client = new ZbHttpClient.Builder().connTimeOut(10 * 1000)
                                           .readTimeOut(10 * 1000)
                                           .writeTimeOut(10 * 1000)
                                           .build();
    }


    public void sendRegisterCaptcha(String phoneNumber, final ZbListener listener) {
        ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.SMS_SEND_REGISTER_TOKEN).inject()
                                                                                              .add("phone_number",
                                                                                                   phoneNumber);
        requestWithNoData(listener, builder);
    }

    public void sendLoginCaptcha(String phoneNumber, final ZbListener listener) {
        ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.SMS_SEND_LOGIN_TOKEN).inject()
                                                                                           .add("phone_number",
                                                                                                phoneNumber);
        requestWithNoData(listener, builder);
    }

    public void sendRetrieveCaptcha(String phoneNumber, final ZbListener listener) {
        ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.SMS_SEND_RESET_TOKEN).inject()
                                                                                           .add("phone_number",
                                                                                                phoneNumber);
        requestWithNoData(listener, builder);
    }

    public void sendBindCaptcha(String phoneNumber, final ZbListener listener) {
        ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.SMS_SEND_BINDING_TOKEN).inject()
                                                                                             .add("phone_number",
                                                                                                  phoneNumber);
        requestWithNoData(listener, builder);
    }

    /**
     * 返回结果无data,只有code和message的请求封装
     *
     * @param listener
     * @param builder
     */
    private void requestWithNoData(final ZbListener listener, final ParamsBuilder builder) {
        client.newCall(buildPostRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                BaseData data = JsonUtil.jsonBaseData(response);
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

    /**
     * 手机号注册浙报通行证
     *
     * @param phoneNumber
     * @param password
     * @param captcha
     * @param listener
     */
    public void register(String phoneNumber, String password, String captcha, final ZbRegisterListener listener) {
        final ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.PASSPORT_REGISTER).inject()
                                                                                              .add("phone_number",
                                                                                                   phoneNumber)
                                                                                              .add("password", password)
                                                                                              .add("sms_token",
                                                                                                   captcha);
        client.newCall(buildPostRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                LoginDataEntity data = JsonUtil.jsonLoginData(response);
                if (data.code == StatusCode.SUCCESS) {
                    listener.onSuccess(data.data);
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

    /**
     * 手机号密码登录
     *
     * @param phoneNumber
     * @param password
     * @param listener
     */
    public void login(String phoneNumber, String password, final ZbLoginListener listener) {
        final ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.PASSPORT_PASSWORD_LOGIN).inject()
                                                                                                    .add("phone_number",
                                                                                                         phoneNumber)
                                                                                                    .add("password",
                                                                                                         password);
        client.newCall(buildPostRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                LoginDataEntity data = JsonUtil.jsonLoginData(response);
                if (data.code == StatusCode.SUCCESS) {
                    listener.onSuccess(data.data);
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

    /**
     * 手机号与验证码登录
     *
     * @param phoneNumber
     * @param captcha
     * @param listener
     */
    public void loginCaptcha(String phoneNumber, String captcha, final ZbLoginListener listener) {
        final ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.PASSPORT_SMS_TOKEN_LOGIN).inject()
                                                                                                     .add("phone_number",
                                                                                                          phoneNumber)
                                                                                                     .add("sms_token",
                                                                                                          captcha);
        client.newCall(buildPostRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                LoginDataEntity data = JsonUtil.jsonLoginData(response);
                if (data.code == StatusCode.SUCCESS) {
                    listener.onSuccess(data.data);
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

    public void loginThird(int thirdType, String thirdUnionId, final ZbLoginListener listener) {
        final ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.THIRD_PARTY_LOGIN).inject()
                                                                                              .add("auth_type",
                                                                                                   "" + thirdType)
                                                                                              .add("auth_uid",
                                                                                                   thirdUnionId);
        client.newCall(buildPostRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                LoginDataEntity data = JsonUtil.jsonLoginData(response);
                if (data.code == StatusCode.SUCCESS) {
                    listener.onSuccess(data.data);
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

    /**
     * 获取通行证详情  Get请求
     *
     * @param listener
     */
    public void getInfo(final ZbGetInfoListener listener) {
        final ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.PASSPORT_DETAIL).injectWithToken();
        client.newCall(buildGetRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                LoginDataEntity data = JsonUtil.jsonLoginData(response);
                if (data.code == StatusCode.SUCCESS) {
                    listener.onSuccess(data.data);
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

    /**
     * 绑定浙报通行证手机号
     *
     * @param phoneNumber
     * @param captcha
     * @param listener
     */
    public void bindPhone(String phoneNumber, String captcha, final ZbBindListener listener) {
        final ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.PASSPORT_BIND_PHONE_NUMBER).injectWithToken()
                                                                                                       .add("new_phone_number",
                                                                                                            phoneNumber)
                                                                                                       .add("sms_token",
                                                                                                            captcha);
        client.newCall(buildPostRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                BaseData data = JsonUtil.jsonBaseData(response);
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

    /**
     * 更改通行证密码
     *
     * @param oldPassWord
     * @param newPassWord
     * @param listener
     */
    public void changePassword(String oldPassWord, String newPassWord, final ZbChangePasswordListener listener) {
        final ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.PASSPORT_CHANGE_PASSWORD).injectWithToken()
                                                                                                     .add("old_password",
                                                                                                          oldPassWord)
                                                                                                     .add("new_password",
                                                                                                          newPassWord);
        client.newCall(buildPostRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                BaseData data = JsonUtil.jsonBaseData(response);
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

    /**
     * 找回通行证密码
     *
     * @param phoneNumber
     * @param captcha
     * @param newPassword
     * @param listener
     */
    public void findPassword(String phoneNumber, String captcha, String newPassword, final ZbResetPasswordListener listener) {
        final ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.PASSPORT_RESET_PASSWORD).inject()
                                                                                                    .add("phone_number",
                                                                                                         phoneNumber)
                                                                                                    .add("sms_token",
                                                                                                         captcha)
                                                                                                    .add("new_password",
                                                                                                         newPassword);
        client.newCall(buildPostRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                BaseData data = JsonUtil.jsonBaseData(response);
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

    /**
     * 检测手机号是否已经绑定浙报通行证接口  Get请求
     *
     * @param phoneNumber
     * @param listener
     */
    public void checkBindState(String phoneNumber, final ZbCheckListener listener) {
        final ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.PASSPORT_CHECK_BINDING).inject()
                                                                                                   .add("phone_number",
                                                                                                        phoneNumber);
        client.newCall(buildGetRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                CheckBindingEntity data = JsonUtil.parseCheckBinding(response);
                if (data != null) {
                    if (data.code == StatusCode.SUCCESS) {
                        listener.onSuccess(data.data);
                    } else {
                        listener.onFailure(data.code, data.message);
                    }
                }
            }

            @Override
            public void onFail(Request call, IOException e) {
                listener.onFailure(-1, StatusCode.getMessage(-1));
            }
        });
    }

    /**
     * 退出登录
     *
     * @param listener
     */
    public void logout(final ZbLogoutListener listener) {
        final ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.PASSPORT_LOGOUT).injectWithToken();
        client.newCall(buildPostRequest(builder)).enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                Logger.d(builder, response);
                BaseData data = JsonUtil.jsonBaseData(response);
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


    public void bindThird(int thirdType, String thirdUnionId, final ZbListener listener) {
        ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.THIRD_PARTY_BIND).injectWithToken()
                                                                                       .add("auth_type", "" + thirdType)
                                                                                       .add("auth_uid", thirdUnionId);
        requestWithNoData(listener, builder);
    }

    public void unbindThird(int thirdType, final ZbListener listener) {
        ParamsBuilder builder = new ParamsBuilder(ApiManager.EndPoint.THIRD_PARTY_UNBIND).injectWithToken()
                                                                                         .add("auth_type",
                                                                                              "" + thirdType);
        requestWithNoData(listener, builder);
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

        public ParamsBuilder(String api) {
            map = new TreeMap<>();
            this.api = api;

        }

        public ParamsBuilder inject() {
            ZbConfig zbConfig = ZbPassport.getZbConfig();
            map.put(K.APP_ID, "" + zbConfig.getAppId());
            map.put(K.APP_KEY, zbConfig.getAppKey());
            map.put(K.APP_SECRET, zbConfig.getAppSecret());
            return this;
        }

        public ParamsBuilder injectWithToken() {
            ZbConfig zbConfig = ZbPassport.getZbConfig();
            if (!TextUtils.isEmpty(zbConfig.getToken())) {
                map.put(K.TOKEN, zbConfig.getToken());
            }
            map.put(K.APP_ID, "" + zbConfig.getAppId());
            map.put(K.APP_KEY, zbConfig.getAppKey());
            map.put(K.APP_SECRET, zbConfig.getAppSecret());
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
            map.put(K.SIGN, EncryptUtil.encrypt(api, map));
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

