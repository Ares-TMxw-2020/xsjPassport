package com.zjrb.passport;

import android.content.Context;

import com.zjrb.passport.Entity.ClientInfo;
import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.listener.ZbAuthListener;
import com.zjrb.passport.listener.ZbCheckPhoneListener;
import com.zjrb.passport.listener.ZbCheckThirdListener;
import com.zjrb.passport.listener.ZbGetAccountInfoListener;
import com.zjrb.passport.listener.ZbGetUidListener;
import com.zjrb.passport.listener.ZbGraphicListener;
import com.zjrb.passport.listener.ZbInitListener;
import com.zjrb.passport.listener.ZbResultListener;
import com.zjrb.passport.net.interfaces.Call;

/**
 * Date: 2018/6/29 上午11:49
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 通行证主要方法
 */
public class ZbPassport {

    private static ZbConfig zbConfig;

    private static NetWork netWork;

    private ZbPassport() {
    }

    public static void init(Context context) {
        zbConfig = new ZbConfig(context);
        netWork = new NetWork();
        ZbPassport.initApp(new ZbInitListener() {
            @Override
            public void onSuccess(ClientInfo info) {
                if (info != null) {
                    zbConfig.setSignatureKey(info.getSignature_key()); // 设置签名密钥,30分钟有效期
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {

            }
        });
    }

    public static void init(final Context context, ZbConfig.Builder builder) {
        zbConfig = new ZbConfig(context);
        netWork = new NetWork();
        setZbConfig(builder);
        ZbPassport.initApp(new ZbInitListener() {
            @Override
            public void onSuccess(ClientInfo info) {
                if (info != null) {
                    zbConfig.setSignatureKey(info.getSignature_key()); // 设置签名密钥,30分钟有效期
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
            }
        });

    }

    public static void setZbConfig(ZbConfig.Builder builder) {
        builder.build(zbConfig);
    }

    /**
     * 设置token
     *
     * @param token token
     */
    public static void setToken(String token) {
        zbConfig.setToken(token);
    }


    public static ZbConfig getZbConfig() {
        return zbConfig;
    }


    /**
     * 初始化接口
     *
     * @param listener
     * @return
     */
    public static Call initApp( final ZbInitListener listener) {
        return netWork.initApp(listener);
    }

    /**
     * 获取图形验证码接口
     *
     * @return
     */
//    public static String getGraphicsCode() {
//        return netWork.getGraphicsCode();
//    }

    /**
     * 获取图形验证码接口
     *
     * @return
     */
    public static Call getGraphics(ZbGraphicListener listener) {
        return netWork.getGraphics(listener);
    }



    /**
     * 获取手机短信验证码接口
     *
     * @param phoneNumber 手机号
     * @param graphicCaptcha     图形验证码,非必传
     * @param listener
     * @return
     */
    public static Call sendCaptcha(String phoneNumber, String graphicCaptcha, final ZbResultListener listener) {
        return netWork.sendCaptcha(phoneNumber, graphicCaptcha, listener);
    }


    /**
     * 验证码预检查接口 get请求
     * @param phoneNumber 手机号
     * @param security_code 短信验证码
     * @param listener
     * @return
     */
    public static Call checkCaptcha(String phoneNumber, String security_code, final ZbResultListener listener) {
        return netWork.checkCaptcha(phoneNumber, security_code, listener);
    }

    /**
     * 修改密码预检查接口 get请求
     * @param old_password 旧密码
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public static Call checkPassWord(String old_password, String accessToken, final ZbResultListener listener) {
        return netWork.checkPassWord(old_password, accessToken, listener);
    }


    /**
     * 手机号密码认证接口
     *
     * @param phoneNumber 手机号
     * @param password    密码
     * @param captcha     验证码 没有传空
     * @param listener
     * @return
     */
    public static Call loginCustom(String phoneNumber, String password, String captcha, final ZbAuthListener listener) {
        return netWork.loginCustom(phoneNumber, password, captcha, listener);
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
    public static Call registerThirdBindPhone(String phoneNum, String smsCode, String auth_uid, int auth_type, String auth_token, final ZbAuthListener listener) {
        return netWork.registerThirdBindPhone(phoneNum, smsCode, auth_uid, auth_type, auth_token,listener);
    }


    /**
     * 手机号短信验证码认证接口
     *
     * @param phoneNumber   手机号
     * @param security_code 验证码
     * @param listener
     * @return
     */
    public static Call loginCaptcha(String phoneNumber, String security_code, final ZbAuthListener listener) {
        return netWork.loginCaptcha(phoneNumber, security_code, listener);
    }

    /**
     * 第三方账号登录认证接口
     *
     * @param auth_uid     第三方用户唯一id标识
     * @param auth_type    第三方绑定类型
     * @param auth_token 第三方返回的access_token
     * @param listener
     * @return
     */
    public static Call loginThird( String auth_uid, @ZbConstants.ThirdType int auth_type, String auth_token, final ZbAuthListener listener) {
        return netWork.loginThird( auth_uid, auth_type, auth_token, listener);
    }

    /**
     * 网易易盾一键登录认证
     * @param yd_token 网易易盾token
     * @param mobile_access_token 手机运营商返回token
     * @param listener
     * @return
     */
    public static Call loginYiDun(String yd_token, String mobile_access_token, final ZbAuthListener listener) {
        return netWork.loginYiDun(yd_token, mobile_access_token, listener);
    }

    /**
     * 钉钉登录接口
     * @param code
     * @param listener
     * @return
     */
    public static Call loginDingding(String code, final ZbAuthListener listener) {
            return netWork.loginDingding(code, listener);
    }

    /**
     * 获取钉钉UID
     * @param code
     * @param listener
     * @return
     */
    public static Call getDingdingUid(String code, final ZbGetUidListener listener) {
        return netWork.getDingdingUid(code, listener);
    }

    /**
     * 第三方登录且强制绑定手机号接口  (跟林参确认,该接口可去掉,不使用)
     * @param auth_uid   第三方用户唯一id标识
     * @param auth_type  第三方绑定类型
     * @param auth_token 第三方返回的access_token
     * @param phoneNum 手机号
     * @param smsCode 短信验证码
     * @param listener
     * @return
     */
    @Deprecated
    public static Call loginThirdBindPhone(String auth_uid, int auth_type, String auth_token, String phoneNum, String smsCode, final ZbAuthListener listener) {
        return netWork.loginThirdBindPhone(auth_uid, auth_type, auth_token, phoneNum, smsCode, listener);
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
    public static Call resetPassword(String phone_number, String security_code, String new_password, final ZbResultListener listener) {
        return netWork.resetPassword(phone_number, security_code, new_password, listener);
    }


    /**
     * 获取账号详情接口
     *
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public static Call getAccountDetail(String accessToken, final ZbGetAccountInfoListener listener) {
        return netWork.getAccountDetail(accessToken, listener);
    }

    /**
     * 修改密码接口
     *
     * @param new_password 新密码
     * @param old_password 旧密码
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public static Call changePassword(String new_password, String old_password, String accessToken, final ZbResultListener listener) {
        return netWork.changePassword( new_password, old_password, accessToken, listener);
    }


    /**
     * 修改(绑定)手机号接口
     *
     * @param new_phone_number 新手机号
     * @param security_code    新手机下发的短信验证码
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public static Call changePhoneNum(String new_phone_number, String security_code, String accessToken, final ZbResultListener listener) {
        return netWork.changePhoneNum(new_phone_number, security_code, accessToken, listener);
    }

    /**
     * 绑定第三方登录接口
     *
     * @param auth_uid     第三方用户唯一id标识
     * @param auth_type    第三方账户绑定类型
     * @param auth_token 第三方返回的auth_token
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public static Call bindThirdParty(String auth_uid, @ZbConstants.ThirdType int auth_type, String auth_token, String accessToken, final ZbResultListener listener) {
        return netWork.bindThirdParty(auth_uid, auth_type, auth_token, accessToken, listener);
    }


    /**
     * 解绑第三方登录接口
     *
     * @param auth_type 第三方账号绑定id
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public static Call unBindThirdParty( @ZbConstants.ThirdType int auth_type, String accessToken, final ZbResultListener listener) {
        return netWork.unBindThirdParty(auth_type, accessToken, listener);
    }

    /**
     * 检查手机号是否注册的接口
     * @param phoneNumber 手机号
     * @param listener
     * @return
     */
    public static Call checkPhoneNumber(String phoneNumber, final ZbCheckPhoneListener listener) {
        return netWork.checkPhoneNumber(phoneNumber, listener);
    }


    /**
     * 第三方账号是否已经绑定检查接口(该接口只是判断三方账号是否在通行证中存在,林参设计的该接口跟是否已绑定手机号没有关系)
     * @param auth_type    第三方账户绑定类型
     * @param auth_uid     第三方用户唯一id标识
     * @param auth_token 第三方返回的auth_token
     * @param listener
     * @return
     */
    public static Call checkThird(String auth_type, String auth_uid, String auth_token, final ZbCheckThirdListener listener) {
        return netWork.checkThird(auth_type, auth_uid, auth_token, listener);
    }


}
