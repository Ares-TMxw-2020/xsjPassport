package com.zjrb.passport;

import com.zjrb.passport.RequestBuilder.ParamsBuilder;
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
import com.zjrb.passport.net.interfaces.Call;
import com.zjrb.passport.net.interfaces.CallBack;
import com.zjrb.passport.net.Response;
import com.zjrb.passport.net.ZbHttpClient;
import com.zjrb.passport.processor.CheckJsonProcessor;
import com.zjrb.passport.processor.GetInfoProcessor;
import com.zjrb.passport.processor.LoginJsonProcessor;
import com.zjrb.passport.processor.ResponseProcessor;
import com.zjrb.passport.processor.VerifyJsonProcessor;

import java.io.IOException;

import static com.zjrb.passport.RequestBuilder.buildGetRequest;
import static com.zjrb.passport.RequestBuilder.buildPostRequest;

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


    public Call sendRegisterCaptcha(String phoneNumber, ZbCaptchaSendListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_SEND_REGISTER_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber);
        return requestPostWithNoData(listener, builder);
    }

    public Call sendLoginCaptcha(String phoneNumber, ZbCaptchaSendListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_SEND_LOGIN_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber);
        return requestPostWithNoData(listener, builder);
    }

    public Call sendRetrieveCaptcha(String phoneNumber, ZbCaptchaSendListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_SEND_RESET_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber);
        return requestPostWithNoData(listener, builder);
    }

    public Call sendBindCaptcha(String phoneNumber, ZbCaptchaSendListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_SEND_BINDING_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber);
        return requestPostWithNoData(listener, builder);
    }


    public Call verifyRegisterCaptcha(String phoneNumber, String captcha, final ZbCaptchaVerifyListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_VALIDATE_REGISTER_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber)
                                                   .add("sms_token", captcha);
        Call httpCall = client.newCall(buildGetRequest(builder));
        httpCall.enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
        return httpCall;
    }

    public Call verifyLoginCaptcha(String phoneNumber, String captcha, final ZbCaptchaVerifyListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_VALIDATE_LOGIN_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber)
                                                   .add("sms_token", captcha);
        Call httpCall = client.newCall(buildGetRequest(builder));
        httpCall.enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
        return httpCall;
    }

    public Call verifyFindCaptcha(String phoneNumber, String captcha, final ZbCaptchaVerifyListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_VALIDATE_RESET_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber)
                                                   .add("sms_token", captcha);
        Call httpCall = client.newCall(buildGetRequest(builder));
        httpCall.enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
        return httpCall;
    }

    public Call verifyBindCaptcha(String phoneNumber, String captcha, final ZbCaptchaVerifyListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_VALIDATE_BINDING_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber)
                                                   .add("sms_token", captcha);
        Call httpCall = client.newCall(buildGetRequest(builder));
        httpCall.enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
        return httpCall;
    }

    /**
     * post请求,返回结果无data,只有code和message的请求封装
     *
     * @param listener
     * @param builder
     */
    private Call requestPostWithNoData(final IResult listener, final ParamsBuilder builder) {
        Call httpCall = client.newCall(buildPostRequest(builder));
        httpCall.enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                ResponseProcessor.process(response, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
        return httpCall;
    }

    /**
     * 手机号注册浙报通行证
     *
     * @param phoneNumber
     * @param password
     * @param captcha
     * @param listener
     */
    public Call register(String phoneNumber, String password, String captcha, final ZbRegisterListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_REGISTER)
                                                   .inject()
                                                   .add("phone_number", phoneNumber)
                                                   .add("password", password)
                                                   .add("sms_token", captcha);
        Call httpCall = client.newCall(buildPostRequest(builder));
        httpCall.enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                LoginJsonProcessor processor = new LoginJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
        return httpCall;
    }

    /**
     * 手机号密码登录
     *
     * @param phoneNumber
     * @param password
     * @param listener
     */
    public Call login(String phoneNumber, String password, final ZbLoginListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_PASSWORD_LOGIN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber)
                                                   .add("password", password);
        Call httpCall = client.newCall(buildPostRequest(builder));
        httpCall.enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                LoginJsonProcessor processor = new LoginJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
        return httpCall;
    }

    /**
     * 手机号与验证码登录
     *
     * @param phoneNumber
     * @param captcha
     * @param listener
     */
    public Call loginCaptcha(String phoneNumber, String captcha, final ZbLoginListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_SMS_TOKEN_LOGIN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber)
                                                   .add("sms_token", captcha);
        Call httpCall = client.newCall(buildPostRequest(builder));
        httpCall.enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                LoginJsonProcessor processor = new LoginJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
        return httpCall;
    }

    public Call loginThird(int thirdType, String thirdUnionId, final ZbLoginListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.THIRD_PARTY_LOGIN)
                                                   .inject()
                                                   .add("auth_type", "" + thirdType)
                                                   .add("auth_uid", thirdUnionId);
        Call httpCall = client.newCall(buildPostRequest(builder));
        httpCall.enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                LoginJsonProcessor processor = new LoginJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
        return httpCall;
    }

    /**
     * 获取通行证详情  Get请求
     *
     * @param listener
     */
    public Call getInfo(final ZbGetInfoListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_DETAIL).injectWithToken();
        Call httpCall = client.newCall(buildGetRequest(builder));
        httpCall.enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                GetInfoProcessor processor = new GetInfoProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
        return httpCall;
    }

    /**
     * 绑定浙报通行证手机号
     *
     * @param phoneNumber
     * @param captcha
     * @param listener
     */
    public Call bindPhone(String phoneNumber, String captcha, final ZbBindPhoneListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_BIND_PHONE_NUMBER)
                                                   .injectWithToken()
                                                   .add("new_phone_number", phoneNumber)
                                                   .add("sms_token", captcha);
        return requestPostWithNoData(listener, builder);
    }

    /**
     * 更改通行证密码
     *
     * @param oldPassWord
     * @param newPassWord
     * @param listener
     */
    public Call changePassword(String oldPassWord, String newPassWord, final ZbChangePasswordListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_CHANGE_PASSWORD)
                                                   .injectWithToken()
                                                   .add("old_password", oldPassWord)
                                                   .add("new_password", newPassWord);
        return requestPostWithNoData(listener, builder);
    }

    /**
     * 在修改密码时，检查原密码是否正确的接口
     *
     * @param oldPassWord 原密码
     * @param listener
     */
    public Call checkPassWord(String oldPassWord, final ZbCaptchaVerifyListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_CHECK_PASSWORD)
                                                   .injectWithToken()
                                                   .add("password", oldPassWord);
        Call httpCall = client.newCall(buildGetRequest(builder));
        httpCall.enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) {
                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
        return httpCall;
    }

    /**
     * 找回通行证密码
     *
     * @param phoneNumber
     * @param captcha
     * @param newPassword
     * @param listener
     */
    public Call findPassword(String phoneNumber, String captcha, String newPassword, final ZbFindPasswordListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_RESET_PASSWORD)
                                                   .inject()
                                                   .add("phone_number", phoneNumber)
                                                   .add("sms_token", captcha)
                                                   .add("new_password", newPassword);
        return requestPostWithNoData(listener, builder);
    }

    /**
     * 检测手机号是否已经绑定浙报通行证接口  Get请求
     *
     * @param phoneNumber
     * @param listener
     */
    public Call checkBindState(String phoneNumber, final ZbCheckPhoneListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_CHECK_BINDING)
                                                   .inject()
                                                   .add("phone_number", phoneNumber);
        Call httpCall = client.newCall(buildGetRequest(builder));
        httpCall.enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {

                CheckJsonProcessor processor = new CheckJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                listener.onFailure(errorCode, msg);
            }
        });
        return httpCall;
    }


    public Call bindThird(int thirdType, String thirdUnionId, final ZbBindThirdListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.THIRD_PARTY_BIND)
                                                   .injectWithToken()
                                                   .add("auth_type", "" + thirdType)
                                                   .add("auth_uid", thirdUnionId);
        return requestPostWithNoData(listener, builder);
    }

    public Call unbindThird(int thirdType, final ZbUnBindThirdListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.THIRD_PARTY_UNBIND)
                                                   .injectWithToken()
                                                   .add("auth_type", "" + thirdType);
        return requestPostWithNoData(listener, builder);
    }


    /**
     * 退出登录
     *
     * @param listener
     */
    public Call logout(final ZbLogoutListener listener) {
        final ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_LOGOUT).injectWithToken();
        return requestPostWithNoData(listener, builder);
    }

}

