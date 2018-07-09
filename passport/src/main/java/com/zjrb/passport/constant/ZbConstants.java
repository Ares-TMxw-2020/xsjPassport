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
    public static final String META_ID = "ZBP_APP_ID";
    public static final String META_KEY = "ZBP_APP_KEY";
    public static final String META_SECRET = "ZBP_APP_SECRET";
    public static final String META_ENV = "ZBP_APP_ENV";


    public static final int ENV_DEV = 0;
    public static final int ENV_TEST = 1;
    public static final int ENV_PRE = 2;
    public static final int ENV_OFFICIAL = 3;

    public static final String DEV = "dev";
    public static final String TEST = "test";
    public static final String PRE = "pre";
    public static final String OFFICIAL = "official";

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ENV_DEV, ENV_TEST, ENV_PRE, ENV_OFFICIAL})
    public @interface EnvType {
    }


    public static final int WECHAT = 2;
    public static final int QQ = 3;
    public static final int SINA = 4;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({WECHAT, QQ, SINA})
    public @interface ThirdType {
    }
}
