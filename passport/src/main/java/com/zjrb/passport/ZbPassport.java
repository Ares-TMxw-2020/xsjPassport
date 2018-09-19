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

    public static void init(Context context, ZbConfig.Builder builder) {
        zbConfig = new ZbConfig(context);
        netWork = new NetWork();
        setZbConfig(builder);
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

    /**
     * 设置传递数据
     * @param passData
     */
    public static void setPassData(String passData) {
        zbConfig.setData_bypass(passData);
    }

    public static ZbConfig getZbConfig() {
        return zbConfig;
    }


    /**
     * 发送验证码
     *
     * @param smsType     验证码类型
     * @param phoneNumber 手机号
     */
    public static Call sendCaptcha(@ZbConstants.SmsType int smsType, String phoneNumber, ZbCaptchaSendListener listener) {
        switch (smsType) {
            case ZbConstants.Sms.REGISTER:
                return netWork.sendRegisterCaptcha(phoneNumber, listener);
            case ZbConstants.Sms.LOGIN:
                return netWork.sendLoginCaptcha(phoneNumber, listener);
            case ZbConstants.Sms.FIND:
                return netWork.sendRetrieveCaptcha(phoneNumber, listener);
            case ZbConstants.Sms.BIND:
                return netWork.sendBindCaptcha(phoneNumber, listener);
            default:
                return null;
        }
    }

    /**
     * 验证 验证码正确性
     *
     * @param smsType     验证码类型
     * @param phoneNumber 手机号
     * @param captcha     验证码
     */
    public static Call verifyCaptcha(@ZbConstants.SmsType int smsType, String phoneNumber, String captcha, ZbCaptchaVerifyListener listener) {
        switch (smsType) {
            case ZbConstants.Sms.REGISTER:
                return netWork.verifyRegisterCaptcha(phoneNumber, captcha, listener);
            case ZbConstants.Sms.LOGIN:
                return netWork.verifyLoginCaptcha(phoneNumber, captcha, listener);
            case ZbConstants.Sms.FIND:
                return netWork.verifyFindCaptcha(phoneNumber, captcha, listener);
            case ZbConstants.Sms.BIND:
                return netWork.verifyBindCaptcha(phoneNumber, captcha, listener);
            default:
                return null;
        }
    }

    /**
     * 手机号注册浙报通行证
     *
     * @param phoneNumber 手机号
     * @param password    密码
     * @param captcha     验证码
     */
    public static Call register(String phoneNumber, String password, String captcha, ZbRegisterListener listener) {
        return netWork.register(phoneNumber, password, captcha, listener);
    }

    /**
     * 手机号密码登录浙报通行证
     *
     * @param phoneNumber 手机号
     * @param password    密码
     */
    public static Call login(String phoneNumber, String password, ZbLoginListener listener) {
        return netWork.login(phoneNumber, password, listener);
    }

    /**
     * 自定义账号登录浙报通行证(兼容旧数据登录)
     *
     * @param username 自定义账号
     * @param password 密码
     */
    @Deprecated
    public static Call loginCustom(String username, String password, ZbLoginListener listener) {
        return netWork.loginCustom(username, password, listener);
    }


    /**
     * 手机号与验证码登录
     *
     * @param phoneNumber 手机号
     * @param captcha     验证码
     */
    public static Call loginCaptcha(String phoneNumber, String captcha, ZbLoginListener listener) {
        return netWork.loginCaptcha(phoneNumber, captcha, listener);
    }

    /**
     * 第三方登录
     *
     * @param thirdType     微信，qq，微博
     * @param thirdUniqueId qq和sina 取openId，微信取unionId，用友盟的话，统一取友盟封装的uid
     */
    public static Call loginThird(@ZbConstants.ThirdType int thirdType, String thirdUniqueId, ZbLoginListener listener) {
        return netWork.loginThird(thirdType, thirdUniqueId, listener);
    }

    /**
     * 第三方绑定
     *
     * @param thirdType    微信，qq，微博
     * @param thirdUnionId qq和sina 取openId，微信取unionId，用友盟的话，统一取友盟封装的uid
     */
    public static Call bindThird(@ZbConstants.ThirdType int thirdType, String thirdUnionId, ZbBindThirdListener listener) {
        return netWork.bindThird(thirdType, thirdUnionId, listener);
    }

    /**
     * 第三方解绑
     *
     * @param thirdType 微信，qq，微博 个性化账号
     */
    public static Call unbindThird(@ZbConstants.UnBindType int thirdType, ZbUnBindThirdListener listener) {
        return netWork.unbindThird(thirdType, listener);
    }

    /**
     * 获取通行证详情
     */
    public static Call getInfo(ZbGetInfoListener listener) {
        return netWork.getInfo(listener);
    }

    /**
     * 更改通行证密码
     *
     * @param oldPassWord 原密码
     * @param newPassWord 新密码
     */
    public static Call changePassword(String oldPassWord, String newPassWord, final ZbChangePasswordListener listener) {
        return netWork.changePassword(oldPassWord, newPassWord, listener);
    }

    /**
     * 验证原密码
     *
     * @param oldPassword 原密码
     */
    public static Call checkPassword(String oldPassword, final ZbCaptchaVerifyListener listener) {
        return netWork.checkPassWord(oldPassword, listener);
    }

    /**
     * 找回密码
     *
     * @param phoneNumber 手机号
     * @param captcha     验证码
     * @param newPassword 新密码
     */
    public static Call findPassword(String phoneNumber, String captcha, String newPassword, ZbFindPasswordListener listener) {
        return netWork.findPassword(phoneNumber, captcha, newPassword, listener);
    }

    /**
     * 绑定浙报通行证手机号
     *
     * @param phoneNumber 手机号
     * @param captcha     验证码
     */
    public static Call bindPhone(String phoneNumber, String captcha, ZbBindPhoneListener listener) {
        return netWork.bindPhone(phoneNumber, captcha, listener);
    }

    /**
     * 检查手机号是否绑定浙报通行证
     *
     * @param phoneNumber 手机号
     */
    public static Call checkBindState(String phoneNumber, ZbCheckPhoneListener listener) {
        return netWork.checkBindState(phoneNumber, listener);
    }

    /**
     * 退出登录
     */
    public static Call logout(ZbLogoutListener listener) {
        return netWork.logout(listener);
    }
}
