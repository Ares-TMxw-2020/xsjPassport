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
import com.zjrb.passport.net.interfaces.Call;

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


    public static Call sendCaptcha(@ZbConstants.SmsType int smsType, String phoneNumber, ZbCaptchaSendListener listener) {
        switch (smsType) {
            case ZbConstants.SMS_REGISTER:
                return netWork.sendRegisterCaptcha(phoneNumber, listener);
            case ZbConstants.SMS_LOGIN:
                return netWork.sendLoginCaptcha(phoneNumber, listener);
            case ZbConstants.SMS_FIND:
                return netWork.sendRetrieveCaptcha(phoneNumber, listener);
            case ZbConstants.SMS_BIND:
                return netWork.sendBindCaptcha(phoneNumber, listener);
            default:
                return null;
        }
    }

    public static Call verifyCaptcha(@ZbConstants.SmsType int smsType, String phoneNumber, String captcha, ZbCaptchaVerifyListener listener) {
        switch (smsType) {
            case ZbConstants.SMS_REGISTER:
                return netWork.verifyRegisterCaptcha(phoneNumber, captcha, listener);
            case ZbConstants.SMS_LOGIN:
                return netWork.verifyLoginCaptcha(phoneNumber, captcha, listener);
            case ZbConstants.SMS_FIND:
                return netWork.verifyFindCaptcha(phoneNumber, captcha, listener);
            case ZbConstants.SMS_BIND:
                return netWork.verifyBindCaptcha(phoneNumber, captcha, listener);
            default:
                return null;
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
    public static Call register(String phoneNumber, String password, String captcha, ZbRegisterListener listener) {
        return netWork.register(phoneNumber, password, captcha, listener);
    }

    /**
     * 手机号密码登录浙报通行证
     *
     * @param phoneNumber
     * @param password
     * @param listener
     */
    public static Call login(String phoneNumber, String password, ZbLoginListener listener) {
        return netWork.login(phoneNumber, password, listener);
    }

    /**
     * 手机号与验证码登录
     *
     * @param phoneNumber
     * @param captcha
     * @param listener
     */
    public static Call loginCaptcha(String phoneNumber, String captcha, ZbLoginListener listener) {
        return netWork.loginCaptcha(phoneNumber, captcha, listener);
    }

    public static Call loginThird(@ZbConstants.ThirdType int thirdType, String thirdUniqueId, ZbLoginListener listener) {
        return netWork.loginThird(thirdType, thirdUniqueId, listener);
    }

    /**
     * 获取通行证详情
     *
     * @param listener
     */
    public static Call getInfo(ZbGetInfoListener listener) {
        return netWork.getInfo(listener);
    }

    /**
     * 更改通行证密码
     *
     * @param oldPassWord
     * @param newPassWord
     * @param listener
     */
    public static Call changePassword(String oldPassWord, String newPassWord, final ZbChangePasswordListener listener) {
        return netWork.changePassword(oldPassWord, newPassWord, listener);
    }

    public static Call checkPassword(String oldPassword, final ZbCaptchaVerifyListener listener) {
        return netWork.checkPassWord(oldPassword, listener);
    }

    public static Call findPassword(String phoneNumber, String captcha, String newPassword, ZbFindPasswordListener listener) {
        return netWork.findPassword(phoneNumber, captcha, newPassword, listener);
    }

    /**
     * 绑定浙报通行证手机号
     *
     * @param phoneNumber
     * @param captcha
     * @param listener
     */
    public static Call bindPhone(String phoneNumber, String captcha, ZbBindPhoneListener listener) {
        return netWork.bindPhone(phoneNumber, captcha, listener);
    }

    /**
     * 检查手机号是否绑定浙报通行证
     *
     * @param phoneNumber
     * @param listener
     */
    public static Call checkBindState(String phoneNumber, ZbCheckPhoneListener listener) {
        return netWork.checkBindState(phoneNumber, listener);
    }

    /**
     * 退出登录
     *
     * @param listener
     */
    public static Call logout(ZbLogoutListener listener) {
        return netWork.logout(listener);
    }


    public static Call bindThird(@ZbConstants.ThirdType int thirdType, String thirdUnionId, ZbBindThirdListener listener) {
        return netWork.bindThird(thirdType, thirdUnionId, listener);
    }

    public static Call unbindThird(@ZbConstants.ThirdType int thirdType, ZbUnBindThirdListener listener) {
        return netWork.unbindThird(thirdType, listener);
    }


}
