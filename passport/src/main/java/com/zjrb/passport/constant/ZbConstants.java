package com.zjrb.passport.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Function: ZbConstants
 * <p>
 * Author: chen.h
 * Date: 2018/6/28
 */
public final class ZbConstants {

    /**
     * 开发环境
     */
    public static final int ENV_DEV = 0;
    /**
     * 测试环境
     */
    public static final int ENV_TEST = 1;
    /**
     * 预发布环境
     */
    public static final int ENV_PRE = 2;
    /**
     * 正式环境
     */
    public static final int ENV_OFFICIAL = 3;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ENV_DEV, ENV_TEST, ENV_PRE, ENV_OFFICIAL})
    public @interface EnvType {
    }


    public static final int LOGIN_WECHAT = 2;
    public static final int LOGIN_QQ = 3;
    public static final int LOGIN_SINA = 4;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LOGIN_WECHAT, LOGIN_QQ, LOGIN_SINA})
    public @interface ThirdType {
    }


    /**
     * 注册短信
     */
    public static final int SMS_REGISTER = 1;
    /**
     * 登录短信
     */
    public static final int SMS_LOGIN = 2;
    /**
     * 找回密码短信
     */
    public static final int SMS_FIND = 3;
    /**
     * 绑定手机短信
     */
    public static final int SMS_BIND = 4;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SMS_REGISTER, SMS_LOGIN, SMS_FIND, SMS_BIND})
    public @interface SmsType {
    }
}
