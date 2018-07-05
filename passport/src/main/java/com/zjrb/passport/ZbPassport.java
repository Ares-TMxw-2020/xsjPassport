package com.zjrb.passport;

import android.content.Context;
import android.support.annotation.IntDef;

import com.zjrb.passport.listener.ZbBindListener;
import com.zjrb.passport.listener.ZbChangePasswordListener;
import com.zjrb.passport.listener.ZbCheckListener;
import com.zjrb.passport.listener.ZbGetInfoListener;
import com.zjrb.passport.listener.ZbListener;
import com.zjrb.passport.listener.ZbLoginListener;
import com.zjrb.passport.listener.ZbLogoutListener;
import com.zjrb.passport.listener.ZbRegisterListener;
import com.zjrb.passport.listener.ZbResetPasswordListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
        builder.build(zbConfig);
        netWork = new NetWork();
    }

    public static void setToken(String token) {
        zbConfig.setToken(token);
    }

    static ZbConfig getZbConfig() {
        return zbConfig;
    }

    private static void checkInfo() {
        //TODO 验证配置参数
    }

    public static void checkPhone(String phoneNumber, ZbCheckListener listener) {

    }

    public static void sendRegisterCaptcha(String phoneNumber, ZbListener listener) {
        netWork.sendRegisterCaptcha(phoneNumber, listener);
    }

    public static void sendLoginCaptcha(String phoneNumber, ZbListener listener) {
        netWork.sendLoginCaptcha(phoneNumber, listener);
    }

    public static void sendRetrieveCaptcha(String phoneNumber, ZbListener listener) {
        netWork.sendRetrieveCaptcha(phoneNumber, listener);
    }

    public static void sendBindCaptcha(String phoneNumber, ZbListener listener) {
        netWork.sendBindCaptcha(phoneNumber, listener);
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

    public static void loginThird(@ThirdType int thirdType, String thirdUniqueId, ZbLoginListener listener) {
        netWork.loginThird(thirdType, thirdUniqueId, listener);
    }

    /**
     * 获取通行证详情
     *
     * @param token
     * @param listener
     */
    public static void getInfo(String token, ZbGetInfoListener listener) {
        netWork.getInfo(token, listener);
    }

    /**
     * 更改通行证密码
     *
     * @param token
     * @param oldPassWord
     * @param newPassWord
     * @param listener
     */
    public static void changePassword(String token, String oldPassWord, String newPassWord, final ZbChangePasswordListener listener) {
        netWork.changePassword(token, oldPassWord, newPassWord, listener);
    }

    public static void resetPassword(String phoneNumber, String captcha, String newPassword, ZbResetPasswordListener listener) {
        netWork.resetPassword(phoneNumber, captcha, newPassword, listener);
    }

    public static void bindPhone(String phoneNumber, String captcha, ZbBindListener listener) {

    }

    /**
     * 检查手机号是否绑定浙报通行证
     *
     * @param token
     * @param phoneNumber
     * @param listener
     */
    public void checkBindState(String token, String phoneNumber, ZbCheckListener listener) {
        netWork.checkBindState(token, phoneNumber, listener);
    }

    /**
     * 退出登录
     *
     * @param token
     * @param listener
     */
    public void logout(String token, ZbLogoutListener listener) {
        netWork.logout(token, listener);
    }


    public static void bindThird(@ThirdType int thirdType, String thirdUnionId, ZbListener listener) {
        netWork.bindThird(thirdType, thirdUnionId, listener);
    }

    public static void unbindThird(@ThirdType int thirdType, ZbListener listener) {
        netWork.unbindThird(thirdType, listener);
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({WECHAT, QQ, SINA})
    public @interface ThirdType {
    }

    public static final int WECHAT = 2;
    public static final int QQ = 3;
    public static final int SINA = 4;
}
