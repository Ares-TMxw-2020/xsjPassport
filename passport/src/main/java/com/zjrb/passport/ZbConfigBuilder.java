package com.zjrb.passport;

import android.text.TextUtils;

import com.zjrb.passport.constant.ZbConstants;

/**
 * Function: ZbConfigBuilder
 * <p>
 * Author: chen.h
 * Date: 2018/6/28
 */
public class ZbConfigBuilder {
    private int appId;
    private String appKey;
    private String appSecret;
    private int envType;
    private boolean isDebug;

    public ZbConfigBuilder setAppId(int appId) {
        this.appId = appId;
        return this;
    }

    public ZbConfigBuilder setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public ZbConfigBuilder setAppSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }

    public ZbConfigBuilder setEnvType(@ZbConstants.EnvType int envType) {
        this.envType = envType;
        return this;
    }

    public ZbConfigBuilder setDebug(boolean isDebug) {
        this.isDebug = isDebug;
        return this;
    }

    public void build(ZbConfig zbConfig) {
        if (appId != 0) {
            zbConfig.setAppId(appId);
        }
        if (TextUtils.isEmpty(appKey)) {
            zbConfig.setAppKey(appKey);
        }
        if (TextUtils.isEmpty(appSecret)) {
            zbConfig.setAppSecret(appSecret);
        }
        zbConfig.setEnvType(envType);
        zbConfig.setDebug(isDebug);
    }
}
