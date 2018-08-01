package com.zjrb.passport;

import com.zjrb.passport.listener.IFailure;
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
import com.zjrb.passport.net.ZbHttpClient;
import com.zjrb.passport.net.interfaces.Call;
import com.zjrb.passport.net.interfaces.CallBack;
import com.zjrb.passport.net.response.Response;
import com.zjrb.passport.processor.CheckPhoneProcessor;
import com.zjrb.passport.processor.GetInfoProcessor;
import com.zjrb.passport.processor.LoginProcessor;
import com.zjrb.passport.processor.ResponseProcessor;
import com.zjrb.passport.processor.VerifyJsonProcessor;
import com.zjrb.passport.util.RequestBuilder.ParamsBuilder;

import java.io.IOException;

import static com.zjrb.passport.util.RequestBuilder.buildGetRequest;
import static com.zjrb.passport.util.RequestBuilder.buildPostRequest;


/**
 * Function: NetWork
 * <p>
 * Author: chen.h
 * Date: 2018/7/4
 */
public class NetWork {

    private final ZbHttpClient client;

    public NetWork() {
        client = new ZbHttpClient.Builder().build();
    }


    /**
     * 发送注册验证码
     */
    public Call sendRegisterCaptcha(String phoneNumber, final ZbCaptchaSendListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_SEND_REGISTER_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }

    /**
     * 发送登录验证码
     */
    public Call sendLoginCaptcha(String phoneNumber, final ZbCaptchaSendListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_SEND_LOGIN_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber);

        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }

    /**
     * 发送找回密码验证码
     */
    public Call sendRetrieveCaptcha(String phoneNumber, final ZbCaptchaSendListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_SEND_RESET_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }

    /**
     * 发送绑定手机验证码
     */
    public Call sendBindCaptcha(String phoneNumber, final ZbCaptchaSendListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_SEND_BINDING_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }


    /**
     * 验证注册验证码
     */
    public Call verifyRegisterCaptcha(String phoneNumber, String captcha, final ZbCaptchaVerifyListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_VALIDATE_REGISTER_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber)
                                                   .add("sms_token", captcha);
        return get(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }
        });
    }

    /**
     * 验证登录验证码
     */
    public Call verifyLoginCaptcha(String phoneNumber, String captcha, final ZbCaptchaVerifyListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_VALIDATE_LOGIN_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber)
                                                   .add("sms_token", captcha);
        return get(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }
        });
    }

    /**
     * 验证找回密码验证码
     */
    public Call verifyFindCaptcha(String phoneNumber, String captcha, final ZbCaptchaVerifyListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_VALIDATE_RESET_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber)
                                                   .add("sms_token", captcha);
        return get(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }
        });
    }

    /**
     * 验证绑定手机验证码
     */
    public Call verifyBindCaptcha(String phoneNumber, String captcha, final ZbCaptchaVerifyListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.SMS_VALIDATE_BINDING_TOKEN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber)
                                                   .add("sms_token", captcha);
        return get(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }
        });
    }


    /**
     * 手机号注册浙报通行证
     */
    public Call register(String phoneNumber, String password, String captcha, final ZbRegisterListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_REGISTER)
                                                   .inject()
                                                   .add("phone_number", phoneNumber)
                                                   .add("password", password)
                                                   .add("sms_token", captcha);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                LoginProcessor processor = new LoginProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }
        });
    }

    /**
     * 手机号密码登录
     */
    public Call login(String phoneNumber, String password, final ZbLoginListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_PASSWORD_LOGIN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber)
                                                   .add("password", password);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                LoginProcessor processor = new LoginProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }
        });
    }

    /**
     * 自定义账号密码登录
     */
    @Deprecated
    public Call loginCustom(String username, String password, final ZbLoginListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_PASSWORD_LOGIN)
                                                   .inject()
                                                   .add("username", username)
                                                   .add("password", password);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                LoginProcessor processor = new LoginProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }
        });
    }

    /**
     * 手机号与验证码登录
     */
    public Call loginCaptcha(String phoneNumber, String captcha, final ZbLoginListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_SMS_TOKEN_LOGIN)
                                                   .inject()
                                                   .add("phone_number", phoneNumber)
                                                   .add("sms_token", captcha);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                LoginProcessor processor = new LoginProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }
        });
    }

    /**
     * 第三方登录
     */
    public Call loginThird(int thirdType, String thirdUnionId, final ZbLoginListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.THIRD_PARTY_LOGIN)
                                                   .inject()
                                                   .add("auth_type", "" + thirdType)
                                                   .add("auth_uid", thirdUnionId);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                LoginProcessor processor = new LoginProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }
        });
    }

    /**
     * 获取通行证详情  Get请求
     */
    public Call getInfo(final ZbGetInfoListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_DETAIL).injectWithToken();
        return get(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                GetInfoProcessor processor = new GetInfoProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }
        });
    }

    /**
     * 第三方绑定
     */
    public Call bindThird(int thirdType, String thirdUnionId, final ZbBindThirdListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.THIRD_PARTY_BIND)
                                                   .injectWithToken()
                                                   .add("auth_type", "" + thirdType)
                                                   .add("auth_uid", thirdUnionId);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }

    /**
     * 第三方解绑
     */
    public Call unbindThird(int thirdType, final ZbUnBindThirdListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.THIRD_PARTY_UNBIND)
                                                   .injectWithToken()
                                                   .add("auth_type", "" + thirdType);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }

    /**
     * 绑定浙报通行证手机号
     */
    public Call bindPhone(String phoneNumber, String captcha, final ZbBindPhoneListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_BIND_PHONE_NUMBER)
                                                   .injectWithToken()
                                                   .add("new_phone_number", phoneNumber)
                                                   .add("sms_token", captcha);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }

    /**
     * 更改通行证密码
     */
    public Call changePassword(String oldPassWord, String newPassWord, final ZbChangePasswordListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_CHANGE_PASSWORD)
                                                   .injectWithToken()
                                                   .add("old_password", oldPassWord)
                                                   .add("new_password", newPassWord);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }

    /**
     * 在修改密码时，检查原密码是否正确的接口 get
     */
    public Call checkPassWord(String oldPassWord, final ZbCaptchaVerifyListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_CHECK_PASSWORD)
                                                   .injectWithToken()
                                                   .add("password", oldPassWord);
        return get(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }
        });
    }

    /**
     * 找回通行证密码
     */
    public Call findPassword(String phoneNumber, String captcha, String newPassword, final ZbFindPasswordListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_RESET_PASSWORD)
                                                   .inject()
                                                   .add("phone_number", phoneNumber)
                                                   .add("sms_token", captcha)
                                                   .add("new_password", newPassword);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }

    /**
     * 检测手机号是否已经绑定浙报通行证接口  Get请求
     */
    public Call checkBindState(String phoneNumber, final ZbCheckPhoneListener listener) {
        ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_CHECK_BINDING)
                                                   .inject()
                                                   .add("phone_number", phoneNumber);
        return get(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                CheckPhoneProcessor processor = new CheckPhoneProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }
        });
    }


    /**
     * 退出登录
     */
    public Call logout(final ZbLogoutListener listener) {
        final ParamsBuilder builder = new ParamsBuilder().url(ApiManager.EndPoint.PASSPORT_LOGOUT).injectWithToken();
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener, new ResponseProcessor.Interceptor() {
                    @Override
                    public void onIntercept() {
                        //退出清空token
                        ZbPassport.setToken("");
                    }
                });
            }
        });
    }


    private Call get(ParamsBuilder builder, final WrapListener wrapListener) {
        Call httpCall = client.newCall(buildGetRequest(builder));
        httpCall.enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                wrapListener.onSuccess(response);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                wrapListener.onFailure(errorCode, msg);
            }
        });
        return httpCall;
    }


    private Call post(ParamsBuilder builder, final WrapListener wrapListener) {
        Call httpCall = client.newCall(buildPostRequest(builder));
        httpCall.enqueue(new CallBack() {
            @Override
            public void onSuccess(Response response) throws IOException {
                wrapListener.onSuccess(response);
            }

            @Override
            public void onFail(int errorCode, String msg) {
                wrapListener.onFailure(errorCode, msg);
            }
        });
        return httpCall;
    }

    static abstract class WrapListener {

        final IFailure iFailure;

        WrapListener(IFailure iFailure) {this.iFailure = iFailure;}

        public abstract void onSuccess(Response response);

        public void onFailure(int errorCode, String errorMessage) {
            iFailure.onFailure(errorCode, errorMessage);
        }
    }

}

