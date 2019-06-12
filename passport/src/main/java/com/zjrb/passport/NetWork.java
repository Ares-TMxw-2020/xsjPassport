package com.zjrb.passport;

import android.text.TextUtils;

import com.zjrb.passport.listener.IFailure;
import com.zjrb.passport.listener.ZbAuthListener;
import com.zjrb.passport.listener.ZbCheckPhoneListener;
import com.zjrb.passport.listener.ZbCheckThirdListener;
import com.zjrb.passport.listener.ZbGetAccountInfoListener;
import com.zjrb.passport.listener.ZbGraphicListener;
import com.zjrb.passport.listener.ZbInitListener;
import com.zjrb.passport.listener.ZbResultListener;
import com.zjrb.passport.net.ApiManager;
import com.zjrb.passport.net.ZbHttpClient;
import com.zjrb.passport.net.interfaces.Call;
import com.zjrb.passport.net.interfaces.CallBack;
import com.zjrb.passport.net.response.Response;
import com.zjrb.passport.processor.AccountProcessor;
import com.zjrb.passport.processor.AuthProcessor;
import com.zjrb.passport.processor.CheckPhoneProcessor;
import com.zjrb.passport.processor.CheckThirdProcessor;
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
     *
     * @param listener
     * @return
     */
    public Call initApp(final ZbInitListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.INIT)
                .add("client_id", ZbPassport.getZbConfig().getClientId() + "");
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
     *
     * @return
     */
    public String getGraphicsCode() {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.SMS_CAPTCHA_IMAGE);
        return builder.getUrl();
    }

    /**
     * 获取图形验证码接口
     *
     * @return
     */
    public Call getGraphics(final ZbGraphicListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.SMS_CAPTCHA_IMAGE);
        return get(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.processBytes(response, listener);
            }
        });
    }


    /**
     * 获取手机短信验证码接口
     *
     * @param phoneNumber 手机号
     * @param captcha     图形验证码,非必传
     * @param listener
     * @return
     */
    public Call sendCaptcha(String phoneNumber, String captcha, final ZbResultListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.SMS_SEND_SECURITY_CODE)
                .add("client_id", ZbPassport.getZbConfig().getClientId() + "")
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

//    /**
//     * 使用手机号注册通行证接口 post
//     *
//     * @param phoneNumber   手机号
//     * @param security_code 短信验证码
//     * @param listener
//     * @return
//     */
//    public Call register(String phoneNumber, String security_code, final ZbResultListener listener) {
//        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_PHONE_REGISTER)
//                .add("client_id", ZbPassport.getZbConfig().getClientId() + "")
//                .add("phone_number", phoneNumber)
//                .add("security_code", security_code);
//        return post(builder, new WrapListener(listener) {
//            @Override
//            public void onSuccess(Response response) {
//                ResponseProcessor.process(response, listener);
//            }
//        });
//    }

    /**
     * 验证码预检查接口 get请求
     *
     * @param phoneNumber   手机号
     * @param security_code 短信验证码
     * @param listener
     * @return
     */
    public Call checkCaptcha(String phoneNumber, String security_code, final ZbResultListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.SMS_CHECK_SECURITY_CODE)
                .add("client_id", ZbPassport.getZbConfig().getClientId() + "")
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
     *
     * @param old_password 旧密码
     * @param accessToken  接入客户端accessToken
     * @param listener
     * @return
     */
    public Call checkPassWord(String old_password, String accessToken, final ZbResultListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_CHECK_PASSWORDS).injectToken(accessToken)
                .add("client_id", ZbPassport.getZbConfig().getClientId() + "")
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
     *
     * @param phoneNumber 手机号
     * @param password    密码
     * @param captcha     验证码 没有传空
     * @param listener
     * @return
     */
    public Call loginCustom(String phoneNumber, String password, String captcha, final ZbAuthListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_PHONENUM_PASSWORD)
                .add("client_id", ZbPassport.getZbConfig().getClientId() + "")
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
     *
     * @param phoneNumber   手机号
     * @param security_code 验证码
     * @param listener
     * @return
     */
    public Call loginCaptcha(String phoneNumber, String security_code, final ZbAuthListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_PHONENUM_CAPTCHA)
                .add("client_id", ZbPassport.getZbConfig().getClientId() + "")
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
     *
     * @param auth_uid   第三方用户唯一id标识
     * @param auth_type  第三方绑定类型
     * @param auth_token 第三方返回的access_token
     * @param listener
     * @return
     */
    public Call loginThird(String auth_uid, int auth_type, String auth_token, final ZbAuthListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_THIRD_PARTY_AUTH)
                .add("client_id", ZbPassport.getZbConfig().getClientId() + "")
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
     * 第三方登录且强制绑定手机号接口 POST   确认,不使用该接口
     * @param auth_uid   第三方用户唯一id标识
     * @param auth_type  第三方绑定类型
     * @param auth_token 第三方返回的access_token
     * @param phoneNum 手机号
     * @param smsCode 短信验证码
     * @param listener
     * @return
     */
    @Deprecated
    public Call loginThirdBindPhone(String auth_uid, int auth_type, String auth_token, String phoneNum, String smsCode, final ZbAuthListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_THIRD_PARTY_BIND_PHONE_AUTH)
                .add("client_id", ZbPassport.getZbConfig().getClientId() + "")
                .add("auth_uid", auth_uid)
                .add("auth_type", auth_type + "")
                .add("auth_token", auth_token)
                .add("phone_number", phoneNum)
                .add("security_code", smsCode);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                AuthProcessor processor = new AuthProcessor(listener);
                ResponseProcessor.process(response, processor, listener);
            }
        });
    }

    /**
     * 第三方账号同时绑定手机号接口 POST
     * @param auth_uid   第三方用户唯一id标识
     * @param auth_type  第三方绑定类型
     * @param auth_token 第三方返回的access_token
     * @param phoneNum 手机号
     * @param smsCode 短信验证码
     * @param listener
     * @return
     */
    public Call registerThirdBindPhone(String phoneNum, String smsCode, String auth_uid, int auth_type, String auth_token, final ZbAuthListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_PHONE_THRID_REGISTER)
                .add("client_id", ZbPassport.getZbConfig().getClientId() + "")
                .add("phone_number", phoneNum)
                .add("security_code", smsCode)
                .add("auth_type", auth_type + "")
                .add("auth_uid", auth_uid)
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
     *
     * @param phone_number  手机号
     * @param security_code 验证码
     * @param new_password  新密码（需要使用服务端提供的公钥匙进行RSA加密，将加密结果以base64格式编码）
     * @param listener
     * @return
     */
    public Call resetPassword(String phone_number, String security_code, String new_password, final ZbResultListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_RESET_PASSWORD)
                .add("client_id", ZbPassport.getZbConfig().getClientId() + "")
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
     *
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public Call getAccountDetail(String accessToken, final ZbGetAccountInfoListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_ACCOUNT_DETAIL).injectToken(accessToken)
                .add("client_id", ZbPassport.getZbConfig().getClientId() + "");
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
     *
     * @param new_password 新密码
     * @param old_password 旧密码
     * @param accessToken  接入客户端accessToken
     * @param listener
     * @return
     */
    public Call changePassword(String new_password, String old_password, String accessToken, final ZbResultListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_ALTER_PASSWORD).injectToken(accessToken)
                .add("client_id", ZbPassport.getZbConfig().getClientId() + "")
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
     *
     * @param new_phone_number 新手机号
     * @param security_code    新手机下发的短信验证码
     * @param accessToken      接入客户端accessToken
     * @param listener
     * @return
     */
    public Call changePhoneNum(String new_phone_number, String security_code, String accessToken, final ZbResultListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_ALTER_PHONE_NUM).injectToken(accessToken)
                .add("client_id", ZbPassport.getZbConfig().getClientId() + "")
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
     *
     * @param auth_uid    第三方用户唯一id标识
     * @param auth_type   第三方账户绑定类型
     * @param auth_token  第三方返回的auth_token
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public Call bindThirdParty(String auth_uid, int auth_type, String auth_token, String accessToken, final ZbResultListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_BIND_THIRD_PARTY).injectToken(accessToken)
                .add("client_id", ZbPassport.getZbConfig().getClientId() + "")
                .add("auth_uid", auth_uid)
                .add("auth_type", auth_type + "")
                .add("auth_token", auth_token);
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }

    /**
     * 解绑第三方登录接口
     *
     * @param auth_type   第三方绑定方式(1 微信，2 qq，3 微博)
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public Call unBindThirdParty(int auth_type, String accessToken, final ZbResultListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_UNBIND_THIRD_PARTY).injectToken(accessToken)
                .add("client_id", ZbPassport.getZbConfig().getClientId() + "")
                .add("auth_type", auth_type + "");
        return post(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                ResponseProcessor.process(response, listener);
            }
        });
    }

    /**
     * 检查手机号是否注册的接口
     * @param phoneNumber
     * @param listener
     * @return
     */
    public Call checkPhoneNumber(String phoneNumber, final ZbCheckPhoneListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_CHECK_PHONE_NUMBER)
                .add("client_id", ZbPassport.getZbConfig().getClientId() + "")
                .add("phone_number", phoneNumber);
        return get(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                CheckPhoneProcessor info = new CheckPhoneProcessor(listener);
                ResponseProcessor.process(response, info, listener);
            }
        });
    }


    /**
     * 第三方账号是否已经绑定检查接口
     * @param auth_type
     * @param auth_uid
     * @param auth_token
     * @param listener
     * @return
     */
    public Call checkThird(String auth_type, String auth_uid, String auth_token, final ZbCheckThirdListener listener) {
        ParamsBuilder builder = new ParamsBuilder().api(ApiManager.EndPoint.PASSPORT_CHECK_THIRD_PARTY)
                .add("client_id", ZbPassport.getZbConfig().getClientId() + "")
                .add("auth_type", auth_type)
                .add("auth_uid", auth_uid)
                .add("auth_token", auth_token);
        return get(builder, new WrapListener(listener) {
            @Override
            public void onSuccess(Response response) {
                CheckThirdProcessor info = new CheckThirdProcessor(listener);
                ResponseProcessor.process(response, info, listener);
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

        WrapListener(IFailure iFailure) {
            this.iFailure = iFailure;
        }

        public abstract void onSuccess(Response response);

        public void onFailure(int errorCode, String errorMessage) {
            iFailure.onFailure(errorCode, errorMessage);
        }
    }

}

