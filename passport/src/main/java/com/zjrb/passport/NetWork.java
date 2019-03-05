package com.zjrb.passport;

import android.text.TextUtils;

import com.zjrb.passport.listener.IFailure;
import com.zjrb.passport.listener.ZbAuthListener;
import com.zjrb.passport.listener.ZbGetAccountInfoListener;
import com.zjrb.passport.listener.ZbInitListener;
import com.zjrb.passport.listener.ZbResultListener;
import com.zjrb.passport.net.ApiManager;
import com.zjrb.passport.net.ZbHttpClient;
import com.zjrb.passport.net.interfaces.Call;
import com.zjrb.passport.net.interfaces.CallBack;
import com.zjrb.passport.net.response.Response;
import com.zjrb.passport.processor.AccountProcessor;
import com.zjrb.passport.processor.AuthProcessor;
import com.zjrb.passport.processor.InitProcessor;
import com.zjrb.passport.processor.ResponseProcessor;
import com.zjrb.passport.util.EncryptUtil;
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
     * 初始化接口
     * @param clientId 接入客户端id
     * @param listener
     * @return
     */
    public Call initApp(String clientId, final ZbInitListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.INIT)
                .add("client_id", clientId);
        return get(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                InitProcessor processor = new InitProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }
        });
    }

    /**
     * 获取图形验证码接口
     * @return
     */
    public String getGraphicsCode() {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.SMS_CAPTCHA_IMAGE);
//        return post(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                ResponseProcessor.process(response, listener);
//            }
//        });
        return builder.getUrl();
    }

    /**
     * 获取手机短信验证码接口
     * @param clientId 接入客户端id
     * @param phoneNumber 手机号
     * @param captcha 图形验证码,非必传
     * @param listener
     * @return
     */
    public Call sendCaptcha(String clientId, String phoneNumber, String captcha, final ZbResultListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.SMS_SEND_SECURITY_CODE)
                .add("client_id", clientId)
                .add("phone_number", phoneNumber);
        if (!TextUtils.isEmpty(captcha)) { // 非必传参数
            builder.add("captcha", captcha);
        }
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }

    /**
     * 使用手机号注册通行证接口 post
     * @param clientId 接入客户端id
     * @param phoneNumber 手机号
     * @param security_code 短信验证码
     * @param listener
     * @return
     */
    public Call register(String clientId, String phoneNumber, String security_code, final ZbResultListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_PHONE_REGISTER)
                .add("client_id", clientId)
                .add("phone_number", phoneNumber)
                .add("security_code", security_code);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }

    /**
     * 验证码预检查接口 get请求
     * @param clientId 接入客户端id
     * @param phoneNumber 手机号
     * @param security_code 短信验证码
     * @param listener
     * @return
     */
    public Call checkCaptcha(String clientId, String phoneNumber, String security_code, final ZbResultListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.SMS_CHECK_SECURITY_CODE)
                .add("client_id", clientId)
                .add("phone_number", phoneNumber)
                .add("security_code", security_code);
        return get(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }

    /**
     * 修改密码预检查接口 get请求
     * @param clientId 接入客户端id
     * @param old_password 旧密码
     * @param listener
     * @return
     */
    public Call checkPassWord(String clientId, String old_password, final ZbResultListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_CHECK_PASSWORDS)
                .add("client_id", clientId)
                .add("old_password", old_password);
        return get(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }


    /**
     * 手机号(邮箱)密码认证接口
     * @param clientId 接入客户端id
     * @param phoneNumber 手机号
     * @param password 密码
     * @param captcha 验证码 没有传空
     * @param listener
     * @return
     */
    public Call loginCustom(String clientId, String phoneNumber, String password, String captcha, final ZbAuthListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_PHONENUM_PASSWORD)
                .add("client_id", clientId)
                .add("phone_number", phoneNumber)
                .add("password", EncryptUtil.encryptPassWord(password));
        if (!TextUtils.isEmpty(captcha)) { // 非必传参数
            builder.add("captcha", captcha);
        }
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                AuthProcessor processor = new AuthProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }
        });
    }

    /**
     * 手机号短信验证码认证接口
     * @param clientId 接入客户端id
     * @param phoneNumber 手机号
     * @param security_code 验证码
     * @param listener
     * @return
     */
    public Call loginCaptcha(String clientId, String phoneNumber, String security_code, final ZbAuthListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_PHONENUM_CAPTCHA)
                .add("client_id", clientId)
                .add("phone_number", phoneNumber)
                .add("security_code", security_code);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                AuthProcessor processor = new AuthProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }
        });
    }


    /**
     * 第三方账号登录认证接口
     * @param clientId
     * @param auth_uid 第三方用户唯一id标识
     * @param auth_type 第三方绑定类型
     * @param auth_token 第三方返回的access_token
     * @param listener
     * @return
     */
    public Call loginThird(String clientId, String auth_uid, int auth_type, String auth_token, final ZbAuthListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_THIRD_PARTY_AUTH)
                .add("client_id", clientId)
                .add("auth_uid", auth_uid)
                .add("auth_type", auth_type + "")
                .add("auth_token", auth_token);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                AuthProcessor processor = new AuthProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }
        });
    }

    /**
     * 重置密码接口
     * @param clientId 接入客户端id
     * @param phone_number 手机号
     * @param security_code 验证码
     * @param new_password 新密码（需要使用服务端提供的公钥匙进行RSA加密，将加密结果以base64格式编码）
     * @param listener
     * @return
     */
    public Call resetPassword(String clientId, String phone_number, String security_code, String new_password, final ZbResultListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_RESET_PASSWORD)
                .add("client_id", clientId)
                .add("phone_number", phone_number)
                .add("security_code", security_code)
                .add("new_password", EncryptUtil.encryptPassWord(new_password));
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }

    /**
     * 获取账号详情接口
     * @param clientId 接入客户端id
     * @param listener
     * @return
     */
    public Call getAccountDetail(String clientId, final ZbGetAccountInfoListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_ACCOUNT_DETAIL)
                .add("client_id", clientId);
        return get(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                AccountProcessor processor = new AccountProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }
        });
    }

    /**
     * 修改密码接口
     * @param clientId 接入客户端id
     * @param new_password 新密码
     * @param old_password 旧密码
     * @param listener
     * @return
     */
    public Call changePassword(String clientId, String new_password, String old_password, final ZbResultListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_ALTER_PASSWORD)
                .add("client_id", clientId)
                .add("new_password", EncryptUtil.encryptPassWord(new_password))
                .add("old_password", EncryptUtil.encryptPassWord(old_password));
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }


    /**
     * 修改手机号接口
     * @param clientId 接入客户端id
     * @param new_phone_number 新手机号
     * @param security_code 新手机下发的短信验证码
     * @param listener
     * @return
     */
    public Call changePhoneNum(String clientId, String new_phone_number, String security_code, final ZbResultListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_ALTER_PHONE_NUM)
                .add("client_id", clientId)
                .add("new_phone_number", new_phone_number)
                .add("security_code", security_code);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }

    /**
     * 绑定第三方登录接口
     * @param clientId 接入客户端id
     * @param auth_uid 第三方用户唯一id标识
     * @param auth_type 第三方账户绑定类型
     * @param access_token 第三方返回的access_token
     * @param listener
     * @return
     */
    public Call bindThirdParty(String clientId, String auth_uid, int auth_type,String access_token, final ZbResultListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_BIND_THIRD_PARTY)
                .add("client_id", clientId)
                .add("auth_uid", auth_uid)
                .add("auth_type", auth_type + "")
                .add("access_token", access_token);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }

    /**
     * 解绑第三方登录接口
     * @param clientId 接入客户端id
     * @param auth_type 第三方绑定方式(1 微信，2 qq，3 微博)
     * @param listener
     * @return
     */
    public Call unBindThirdParty(String clientId, int auth_type, final ZbResultListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_UNBIND_THIRD_PARTY)
                .add("client_id", clientId)
                .add("auth_type", auth_type + "");
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }



    /************* 历史版本  **************/

//    /**
//     * 发送注册验证码
//     */
//    public Call sendRegisterCaptcha(String phoneNumber, final ZbCaptchaSendListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.SMS_SEND_REGISTER_TOKEN)
//                                                   .inject()
//                                                   .add("phone_number", phoneNumber);
//        return post(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                ResponseProcessor.process(response, listener);
//            }
//        });
//    }

//    /**
//     * 发送登录验证码
//     */
//    public Call sendLoginCaptcha(String phoneNumber, final ZbCaptchaSendListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.SMS_SEND_LOGIN_TOKEN)
//                                                   .inject()
//                                                   .add("phone_number", phoneNumber);
//
//        return post(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                ResponseProcessor.process(response, listener);
//            }
//        });
//    }
//
//    /**
//     * 发送找回密码验证码
//     */
//    public Call sendRetrieveCaptcha(String phoneNumber, final ZbCaptchaSendListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.SMS_SEND_RESET_TOKEN)
//                                                   .inject()
//                                                   .add("phone_number", phoneNumber);
//        return post(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                ResponseProcessor.process(response, listener);
//            }
//        });
//    }
//
//    /**
//     * 发送绑定手机验证码
//     */
//    public Call sendBindCaptcha(String phoneNumber, final ZbCaptchaSendListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.SMS_SEND_BINDING_TOKEN)
//                                                   .inject()
//                                                   .add("phone_number", phoneNumber);
//        return post(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                ResponseProcessor.process(response, listener);
//            }
//        });
//    }


//    /**
//     * 验证注册验证码
//     */
//    public Call verifyRegisterCaptcha(String phoneNumber, String captcha, final ZbCaptchaVerifyListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.SMS_VALIDATE_REGISTER_TOKEN)
//                                                   .inject()
//                                                   .add("phone_number", phoneNumber)
//                                                   .add("sms_token", captcha);
//        return get(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
//                ResponseProcessor.process(response, processor, listener);
//            }
//        });
//    }
//
//    /**
//     * 验证登录验证码
//     */
//    public Call verifyLoginCaptcha(String phoneNumber, String captcha, final ZbCaptchaVerifyListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.SMS_VALIDATE_LOGIN_TOKEN)
//                                                   .inject()
//                                                   .add("phone_number", phoneNumber)
//                                                   .add("sms_token", captcha);
//        return get(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
//                ResponseProcessor.process(response, processor, listener);
//            }
//        });
//    }
//
//    /**
//     * 验证找回密码验证码
//     */
//    public Call verifyFindCaptcha(String phoneNumber, String captcha, final ZbCaptchaVerifyListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.SMS_VALIDATE_RESET_TOKEN)
//                                                   .inject()
//                                                   .add("phone_number", phoneNumber)
//                                                   .add("sms_token", captcha);
//        return get(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
//                ResponseProcessor.process(response, processor, listener);
//            }
//        });
//    }
//
//    /**
//     * 验证绑定手机验证码
//     */
//    public Call verifyBindCaptcha(String phoneNumber, String captcha, final ZbCaptchaVerifyListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.SMS_VALIDATE_BINDING_TOKEN)
//                                                   .inject()
//                                                   .add("phone_number", phoneNumber)
//                                                   .add("sms_token", captcha);
//        return get(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
//                ResponseProcessor.process(response, processor, listener);
//            }
//        });
//    }

//
//    /**
//     * 手机号注册浙报通行证
//     */
//    public Call register(String phoneNumber, String password, String captcha, final ZbRegisterListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_REGISTER)
//                                                   .inject()
//                                                   .add("phone_number", phoneNumber)
//                                                   .add("password", EncryptUtil.base64Encrypt(password))
//                                                   .add("sms_token", captcha);
//        return post(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                LoginProcessor processor = new LoginProcessor(listener);
//                ResponseProcessor.process(response, processor, listener);
//            }
//        });
//    }
//
//    /**
//     * 手机号密码登录
//     */
//    public Call login(String phoneNumber, String password, final ZbLoginListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_PASSWORD_LOGIN)
//                                                   .inject()
//                                                   .add("phone_number", phoneNumber)
//                                                   .add("password", EncryptUtil.base64Encrypt(password));
//        return post(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                LoginProcessor processor = new LoginProcessor(listener);
//                ResponseProcessor.process(response, processor, listener);
//            }
//        });
//    }
//
//    /**
//     * 自定义账号密码登录
//     */
//    @Deprecated
//    public Call loginCustom(String username, String password, final ZbLoginListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_PASSWORD_LOGIN)
//                                                   .inject()
//                                                   .add("username", username)
//                                                   .add("password", EncryptUtil.base64Encrypt(password));
//        return post(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                LoginProcessor processor = new LoginProcessor(listener);
//                ResponseProcessor.process(response, processor, listener);
//            }
//        });
//    }
//
//    /**
//     * 手机号与验证码登录
//     */
//    public Call loginCaptcha(String phoneNumber, String captcha, final ZbLoginListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_SMS_TOKEN_LOGIN)
//                                                   .inject()
//                                                   .add("phone_number", phoneNumber)
//                                                   .add("sms_token", captcha);
//        return post(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                LoginProcessor processor = new LoginProcessor(listener);
//                ResponseProcessor.process(response, processor, listener);
//            }
//        });
//    }
//
//    /**
//     * 第三方登录
//     */
//    public Call loginThird(int thirdType, String thirdUnionId, final ZbLoginListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.THIRD_PARTY_LOGIN)
//                                                   .inject()
//                                                   .add("auth_type", "" + thirdType)
//                                                   .add("auth_uid", thirdUnionId);
//        return post(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                LoginProcessor processor = new LoginProcessor(listener);
//                ResponseProcessor.process(response, processor, listener);
//            }
//        });
//    }
//
//    /**
//     * 获取通行证详情  Get请求
//     */
//    public Call getInfo(final ZbGetInfoListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_DETAIL).injectWithToken();
//        return get(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                GetInfoProcessor processor = new GetInfoProcessor(listener);
//                ResponseProcessor.process(response, processor, listener);
//            }
//        });
//    }
//
//    /**
//     * 第三方绑定
//     */
//    public Call bindThird(int thirdType, String thirdUnionId, final ZbBindThirdListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.THIRD_PARTY_BIND)
//                                                   .injectWithToken()
//                                                   .add("auth_type", "" + thirdType)
//                                                   .add("auth_uid", thirdUnionId);
//        return post(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                ResponseProcessor.process(response, listener);
//            }
//        });
//    }
//
//    /**
//     * 第三方解绑
//     */
//    public Call unbindThird(int thirdType, final ZbUnBindThirdListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.THIRD_PARTY_UNBIND)
//                                                   .injectWithToken()
//                                                   .add("auth_type", "" + thirdType);
//        return post(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                ResponseProcessor.process(response, listener);
//            }
//        });
//    }
//
//    /**
//     * 绑定浙报通行证手机号
//     */
//    public Call bindPhone(String phoneNumber, String captcha, final ZbBindPhoneListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_BIND_PHONE_NUMBER)
//                                                   .injectWithToken()
//                                                   .add("new_phone_number", phoneNumber)
//                                                   .add("sms_token", captcha);
//        return post(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                ResponseProcessor.process(response, listener);
//            }
//        });
//    }
//
//    /**
//     * 更改通行证密码
//     */
//    public Call changePassword(String oldPassWord, String newPassWord, final ZbChangePasswordListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_CHANGE_PASSWORD)
//                                                   .injectWithToken()
//                                                   .add("old_password", EncryptUtil.base64Encrypt(oldPassWord))
//                                                   .add("new_password", EncryptUtil.base64Encrypt(newPassWord));
//        return post(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                ResponseProcessor.process(response, listener);
//            }
//        });
//    }
//
//    /**
//     * 在修改密码时，检查原密码是否正确的接口 get
//     */
//    public Call checkPassWord(String oldPassWord, final ZbCaptchaVerifyListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_CHECK_PASSWORD)
//                                                   .injectWithToken()
//                                                   .add("password", EncryptUtil.base64Encrypt(oldPassWord));
//        return get(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                VerifyJsonProcessor processor = new VerifyJsonProcessor(listener);
//                ResponseProcessor.process(response, processor, listener);
//            }
//        });
//    }
//
//    /**
//     * 找回通行证密码
//     */
//    public Call findPassword(String phoneNumber, String captcha, String newPassword, final ZbFindPasswordListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_RESET_PASSWORD)
//                                                   .inject()
//                                                   .add("phone_number", phoneNumber)
//                                                   .add("sms_token", captcha)
//                                                   .add("new_password", EncryptUtil.base64Encrypt(newPassword));
//        return post(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                ResponseProcessor.process(response, listener);
//            }
//        });
//    }
//
//    /**
//     * 检测手机号是否已经绑定浙报通行证接口  Get请求
//     */
//    public Call checkBindState(String phoneNumber, final ZbCheckPhoneListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_CHECK_BINDING)
//                                                   .inject()
//                                                   .add("phone_number", phoneNumber);
//        return get(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                CheckPhoneProcessor processor = new CheckPhoneProcessor(listener);
//                ResponseProcessor.process(response, processor, listener);
//            }
//        });
//    }
//
//
//    /**
//     * 退出登录
//     */
//    public Call logout(final ZbLogoutListener listener) {
//        final ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_LOGOUT).injectWithToken();
//        return post(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                ResponseProcessor.process(response, listener, new ResponseProcessor.Interceptor() {
//                    @Override
//                    public void onIntercept() {
//                        //退出清空token
//                        ZbPassport.setToken("");
//                    }
//                });
//            }
//        });
//    }


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

