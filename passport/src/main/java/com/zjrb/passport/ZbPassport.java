package com.zjrb.passport;

import android.content.Context;
import android.support.annotation.IntDef;

import com.zjrb.passport.listener.ZbBindListener;
import com.zjrb.passport.listener.ZbCheckListener;
import com.zjrb.passport.listener.ZbGetInfoListener;
import com.zjrb.passport.listener.ZbListener;
import com.zjrb.passport.listener.ZbLoginListener;
import com.zjrb.passport.listener.ZbRegisterListener;

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

    }

    public static void sendRetrieveCaptcha(String phoneNumber, ZbListener listener) {

    }

    public static void sendBindCaptcha(String phoneNumber, ZbListener listener) {

    }

    public static void register(String phoneNumber, String password, String captcha, ZbRegisterListener listener) {

    }

    public static void login(String phoneNumber, String password, ZbLoginListener listener) {

    }

    public static void loginCaptcha(String phoneNumber, String captcha, ZbLoginListener listener) {

    }

    public static void loginThird(String thirdUniqueId, ZbLoginListener listener) {

    }

    public static void getInfo(String token, ZbGetInfoListener listener) {

    }

    public static void retrievePassword() {
        //TODO 逻辑待商榷
    }

    public static void resetPassword() {
        //TODO 逻辑待商榷
    }

    public static void bindPhone(String phoneNumber, String captcha, ZbBindListener listener) {}


    public static void bindThird(@ThirdType int thirdType, String thirdUnionId, ZbListener listener) {}

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
