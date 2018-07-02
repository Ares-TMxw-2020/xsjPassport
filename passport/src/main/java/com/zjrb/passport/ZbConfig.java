package com.zjrb.passport;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.zjrb.passport.constant.ZbConstants;

/**
 * Function: ZbConfig
 * <p>
 * Author: chen.h
 * Date: 2018/6/28
 */
final class ZbConfig {

    private int appId;
    private String appKey;
    private String appSecret;
    private int envType;

    public ZbConfig() {}

    public ZbConfig(Context context) {
        ApplicationInfo info = null;
        try {
            info = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info != null) {
            appId = info.metaData.getInt(ZbConstants.META_ID);
            appKey = info.metaData.getString(ZbConstants.META_KEY);
            appSecret = info.metaData.getString(ZbConstants.META_SECRET);
            envType = info.metaData.getInt(ZbConstants.META_ENV);
        }
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public int getEnvType() {
        return envType;
    }

    public void setEnvType(int envType) {
        this.envType = envType;
    }

}
