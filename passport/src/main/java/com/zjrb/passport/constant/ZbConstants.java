package com.zjrb.passport.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.zjrb.passport.constant.ZbConstants.Env.DEV;
import static com.zjrb.passport.constant.ZbConstants.Env.OFFICIAL;
import static com.zjrb.passport.constant.ZbConstants.Env.PRE;
import static com.zjrb.passport.constant.ZbConstants.Env.TEST;
import static com.zjrb.passport.constant.ZbConstants.Sms.BIND;
import static com.zjrb.passport.constant.ZbConstants.Sms.FIND;
import static com.zjrb.passport.constant.ZbConstants.Sms.LOGIN;
import static com.zjrb.passport.constant.ZbConstants.ThirdLogin.QQ;
import static com.zjrb.passport.constant.ZbConstants.ThirdLogin.SINA;
import static com.zjrb.passport.constant.ZbConstants.ThirdLogin.WECHAT;

/**
 * Function: ZbConstants
 * <p>
 * Author: chen.h
 * Date: 2018/6/28
 */
public final class ZbConstants {

    public static String PASSPORT_TOKEN = "PASSPORT_TOKEN";

    /**
     * 环境配置
     */
    public static class Env {
        /**
         * 开发环境
         */
        public static final int DEV = 0;
        /**
         * 测试环境
         */
        public static final int TEST = 1;
        /**
         * 预发布环境
         */
        public static final int PRE = 2;
        /**
         * 正式环境
         */
        public static final int OFFICIAL = 3;
    }


    /**
     * 第三方登录方式
     */
    public static class ThirdLogin {
        /**
         * 微信
         */
        public static final int WECHAT = 2;
        /**
         * QQ
         */
        public static final int QQ = 3;
        /**
         * 新浪微博
         */
        public static final int SINA = 4;

    }


    /**
     * 短信类型
     */
    public static class Sms {
        /**
         * 注册短信
         */
        public static final int REGISTER = 1;
        /**
         * 登录短信
         */
        public static final int LOGIN = 2;
        /**
         * 找回密码短信
         */
        public static final int FIND = 3;
        /**
         * 绑定手机短信
         */
        public static final int BIND = 4;
    }

    /**
     * 个性化
     */
    public static final int CUSTOM = 5;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({DEV, TEST, PRE, OFFICIAL})
    public @interface EnvType {
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({WECHAT, QQ, SINA})
    public @interface ThirdType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({WECHAT, QQ, SINA, CUSTOM})
    public @interface UnBindType {
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Sms.REGISTER, LOGIN, FIND, BIND})
    public @interface SmsType {
    }
}
