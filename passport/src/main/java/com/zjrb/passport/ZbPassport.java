package com.zjrb.passport;

import android.content.Context;

import com.zjrb.passport.constant.ZbConstants;
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

/**
 * Function: ZbPassport
 * <p>
 * Author: chen.h
 * Date: 2018/6/28
 */
public class ZbPassport {

    private static ZbConfig zbConfig;

    private static NetWork netWork;

    private ZbPassport() {
    }

    public static void init(Context context) {
        zbConfig = new ZbConfig(context);
        netWork = new NetWork();
    }

    public static void init(Context context, ZbConfigBuilder builder) {
        zbConfig = new ZbConfig(context);
        netWork = new NetWork();
        setZbConfig(builder);
    }

    public static void setZbConfig(ZbConfigBuilder builder) {
        builder.build(zbConfig);
    }

    public static void setToken(String token) {
        zbConfig.setToken(token);
    }

    public static ZbConfig getZbConfig() {
        return zbConfig;
    }


    public static void sendCaptcha(@ZbConstants.SmsType int smsType, String phoneNumber, ZbCaptchaSendListener listener) {
        switch (smsType) {
            case ZbConstants.SMS_REGISTER:
                netWork.sendRegisterCaptcha(phoneNumber, listener);
                break;
            case ZbConstants.SMS_LOGIN:
                netWork.sendLoginCaptcha(phoneNumber, listener);
                break;
            case ZbConstants.SMS_FIND:
                netWork.sendRetrieveCaptcha(phoneNumber, listener);
                break;
            case ZbConstants.SMS_BIND:
                netWork.sendBindCaptcha(phoneNumber, listener);
                break;
            default:
                break;
        }
    }

    public static void verifyCaptcha(@ZbConstants.SmsType int smsType, String phoneNumber, String captcha, ZbCaptchaVerifyListener listener) {
        switch (smsType) {
            case ZbConstants.SMS_REGISTER:
                netWork.verifyRegisterCaptcha(phoneNumber, captcha, listener);
                break;
            case ZbConstants.SMS_LOGIN:
                netWork.verifyLoginCaptcha(phoneNumber, captcha, listener);
                break;
            case ZbConstants.SMS_FIND:
                netWork.verifyFindCaptcha(phoneNumber, captcha, listener);
                break;
            case ZbConstants.SMS_BIND:
                netWork.verifyBindCaptcha(phoneNumber, captcha, listener);
                break;
            default:
                break;
        }
    }

    /**
     * 手机号注册浙报通行证
     *
     * @param phoneNumber
     * @param password
     * @param captcha
     * @param listener
     */
    public static void register(String phoneNumber, String password, String captcha, ZbRegisterListener listener) {
        netWork.register(phoneNumber, password, captcha, listener);
    }

    /**
     * 手机号密码登录浙报通行证
     *
     * @param phoneNumber
     * @param password
     * @param listener
     */
    public static void login(String phoneNumber, String password, ZbLoginListener listener) {
        netWork.login(phoneNumber, password, listener);
    }

    /**
     * 手机号与验证码登录
     *
     * @param phoneNumber
     * @param captcha
     * @param listener
     */
    public static void loginCaptcha(String phoneNumber, String captcha, ZbLoginListener listener) {
        netWork.loginCaptcha(phoneNumber, captcha, listener);
    }

    public static void loginThird(@ZbConstants.ThirdType int thirdType, String thirdUniqueId, ZbLoginListener listener) {
        netWork.loginThird(thirdType, thirdUniqueId, listener);
    }

    /**
     * 获取通行证详情
     *
     * @param listener
     */
    public static void getInfo(ZbGetInfoListener listener) {
        netWork.getInfo(listener);
    }

    /**
     * 更改通行证密码
     *
     * @param oldPassWord
     * @param newPassWord
     * @param listener
     */
    public static void changePassword(String oldPassWord, String newPassWord, final ZbChangePasswordListener listener) {
        netWork.changePassword(oldPassWord, newPassWord, listener);
    }

    public static void checkPassword(String oldPassword, final ZbCaptchaVerifyListener listener) {
        netWork.checkPassWord(oldPassword, listener);
    }

    public static void findPassword(String phoneNumber, String captcha, String newPassword, ZbFindPasswordListener listener) {
        netWork.findPassword(phoneNumber, captcha, newPassword, listener);
    }

    /**
     * 绑定浙报通行证手机号
     *
     * @param phoneNumber
     * @param captcha
     * @param listener
     */
    public static void bindPhone(String phoneNumber, String captcha, ZbBindPhoneListener listener) {
        netWork.bindPhone(phoneNumber, captcha, listener);
    }

    /**
     * 检查手机号是否绑定浙报通行证
     *
     * @param phoneNumber
     * @param listener
     */
    public static void checkBindState(String phoneNumber, ZbCheckPhoneListener listener) {
        netWork.checkBindState(phoneNumber, listener);
    }

    /**
     * 退出登录
     *
     * @param listener
     */
    public static void logout(ZbLogoutListener listener) {
        netWork.logout(listener);
    }


    public static void bindThird(@ZbConstants.ThirdType int thirdType, String thirdUnionId, ZbBindThirdListener listener) {
        netWork.bindThird(thirdType, thirdUnionId, listener);
    }

    public static void unbindThird(@ZbConstants.ThirdType int thirdType, ZbUnBindThirdListener listener) {
        netWork.unbindThird(thirdType, listener);
    }


}
