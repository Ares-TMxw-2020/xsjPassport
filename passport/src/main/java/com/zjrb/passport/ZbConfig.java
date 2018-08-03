package com.zjrb.passport;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.util.InnerConstant;
import com.zjrb.passport.util.Logger;

/**
 * Function: ZbConfig
 * <p>
 * Author: chen.h
 * Date: 2018/6/28
 */
public final class ZbConfig {
    private int appId;
    private String appKey;
    private String appSecret;
    private int envType;
    private String token;
    private boolean isDebug;
    private String appVersion;
    private String appUuid;
    private String ua;
    private boolean isUseHttps; // 是否强制使用https

    ZbConfig(Context context) {
        if (context == null) {
            return;
        }
        ApplicationInfo info = null;
        try {
            info = context.getPackageManager()
                          .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(e.getMessage());
        }
        if (info != null) {
            appId = info.metaData.getInt(InnerConstant.META_ID);
            appKey = info.metaData.getString(InnerConstant.META_KEY);
            appSecret = info.metaData.getString(InnerConstant.META_SECRET);
            String env = info.metaData.getString(InnerConstant.META_ENV, InnerConstant.DEV);
            resolveEnv(env);
        }
    }

    private void resolveEnv(String env) {
        switch (env) {
            case InnerConstant.TEST:
                envType = ZbConstants.Env.TEST;
                isDebug = false;
                break;
            case InnerConstant.PRE:
                envType = ZbConstants.Env.PRE;
                isDebug = false;
                break;
            case InnerConstant.OFFICIAL:
                envType = ZbConstants.Env.OFFICIAL;
                isDebug = false;
                break;
            default:
                envType = ZbConstants.Env.DEV;
                isDebug = true;
                break;
        }
    }

    public int getAppId() {
        return appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public int getEnvType() {
        return envType;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public String getToken() {
        return token;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getAppUuid() {
        return appUuid;
    }

    public void initUA() {
        StringBuilder sb = new StringBuilder();
        sb.append("ANDROID")
          .append(";")
          .append(Build.VERSION.RELEASE)
          .append(";")
          .append(appId)
          .append(";")
          .append(appVersion)
          .append(";")
          .append(BuildConfig.PASSPORT_VERSION)
          .append(";")
          .append(appUuid)
          .append(";")
          .append(Build.MODEL);
        ua = sb.toString();
    }

    public String getUA() {
        return ua;
    }

    void setAppId(int appId) {
        this.appId = appId;
    }

    void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    void setEnvType(int envType) {
        this.envType = envType;
    }

    void setDebug(boolean debug) {
        isDebug = debug;
        if (envType == ZbConstants.Env.OFFICIAL) {
            isDebug = false;
        }
    }

    void setToken(String token) {
        this.token = token;
    }


    void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    void setAppUuid(String appUuid) {
        this.appUuid = appUuid;
    }

    public boolean isUseHttps() {
        return isUseHttps;
    }

    /**
     * 设置是否强制使用Https,只有debug条件下设置才有效
     *
     * @param useHttps
     */
    public void setUseHttps(boolean useHttps) {
        if (isDebug) {
            isUseHttps = useHttps;
        }
        if (envType == ZbConstants.Env.OFFICIAL) {
            isUseHttps = false;
        }
    }

    /**
     * Function: ZbConfigBuilder
     */
    public static class Builder {
        private int appId;
        private String appKey;
        private String appSecret;
        private int envType;
        private boolean isDebug;
        private boolean isSetDebug;
        private String appVersion;
        private String appUuid;

        public Builder setAppId(int appId) {
            this.appId = appId;
            return this;
        }

        public Builder setAppKey(String appKey) {
            this.appKey = appKey;
            return this;
        }

        public Builder setAppSecret(String appSecret) {
            this.appSecret = appSecret;
            return this;
        }

        public Builder setEnvType(@ZbConstants.EnvType int envType) {
            this.envType = envType;
            return this;
        }

        public Builder setDebug(boolean isDebug) {
            this.isSetDebug = true;
            this.isDebug = isDebug;
            return this;
        }

        public Builder setAppVersion(String appVersion) {
            this.appVersion = appVersion;
            return this;
        }

        public Builder setAppUuid(String appUuid) {
            this.appUuid = appUuid;
            return this;
        }

        public void build(ZbConfig zbConfig) {
            if (appId != 0) {
                zbConfig.setAppId(appId);
            }
            if (!TextUtils.isEmpty(appKey)) {
                zbConfig.setAppKey(appKey);
            }
            if (!TextUtils.isEmpty(appSecret)) {
                zbConfig.setAppSecret(appSecret);
            }
            zbConfig.setEnvType(envType);
            if (isSetDebug) {
                zbConfig.setDebug(isDebug);
            }
            zbConfig.setAppVersion(appVersion);
            zbConfig.setAppUuid(appUuid);
            zbConfig.initUA();
        }
    }
}
