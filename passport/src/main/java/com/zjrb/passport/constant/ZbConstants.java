package com.zjrb.passport.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.zjrb.passport.constant.ZbConstants.Env.CUSTOM;
import static com.zjrb.passport.constant.ZbConstants.Env.DEV;
import static com.zjrb.passport.constant.ZbConstants.Env.OFFICIAL;
import static com.zjrb.passport.constant.ZbConstants.Env.PRE;
import static com.zjrb.passport.constant.ZbConstants.Env.TEST;
import static com.zjrb.passport.constant.ZbConstants.ThirdLogin.DINGDING;
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
    public static String PASSPORT_COOKIE = "PASSPORT_COOKIE";
    public static String PASSPORT_SIGNATURE_KEY = "PASSPORT_SIGNATURE_KEY";
    public static String PASSPORT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD6XO7e9YeAOs+cFqwa7ETJ+WXizPqQeXv68i5vqw9pFREsrqiBTRcg7wB0RIp3rJkDpaeVJLsZqYm5TW7FWx/iOiXFc+zCPvaKZric2dXCw27EvlH5rq+zwIPDAJHGAfnn1nmQH7wR3PCatEIb8pz5GFlTHMlluw4ZYmnOwg+thwIDAQAB";
    public static long PASSPORT_SIGN_EXPIRED = 30 * 60 * 1000; // sign 30分钟的有效期
    public static String PASSPORT_NETTIME = "PASSPORT_NETTIME";

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
        /**
         * 自定义环境
         */
        public static final int CUSTOM = 4;
    }


    /**
     * 第三方登录方式
     */
    public static class ThirdLogin {
        /**
         * 微信
         */
        public static final int WECHAT = 1;
        /**
         * QQ
         */
        public static final int QQ = 2;
        /**
         * 新浪微博
         */
        public static final int SINA = 3;
        /**
         * 钉钉
         */
        public static final int DINGDING = 5;

    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({DEV, TEST, PRE, OFFICIAL, CUSTOM})
    public @interface EnvType {
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({WECHAT, QQ, SINA, DINGDING})
    public @interface ThirdType {
    }

}
