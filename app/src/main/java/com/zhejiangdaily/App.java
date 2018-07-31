package com.zhejiangdaily;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.zhejiangdaily.utils.ToastUtil;
import com.zhejiangdaily.utils.ZbUtil;
import com.zjrb.passport.ZbConfig;
import com.zjrb.passport.ZbPassport;

/**
 * Function: App
 * <p>
 * Author: chen.h
 * Date: 2018/6/28
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ZbUtil.init(this);
        ToastUtil.init(this);
        initPassport();
        initUmengLogin(this);
        LeakCanary.install(this);
    }

    private void initPassport() {
        ZbPassport.init(this, new ZbConfig.Builder().setAppVersion("1.0").setAppUuid("uuid"));
//        ZbPassport.setZbConfig(new ZbConfig.Builder().setAppId(1)
//                                                     .setAppKey("appKey")
//                                                     .setAppSecret("")
//                                                     .setDebug(true)
//                                                     .setEnvType(ZbConstants.Env.DEV)
//                                                     .setAppVersion("Your App version")
//                                                     .setAppUuid("Your App uuid"));
    }

    /**
     * 友盟第三方登录
     */
    private void initUmengLogin(Context context) {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(context).setShareConfig(config);
        PlatformConfig.setWeixin("wx39f76de30d723809", "f5d53fadf9b28d4c5cee5993e1689d58");
        PlatformConfig.setSinaWeibo("4252282571", "65de72c06aa125a5255e639875e927a0", "http://app.qjwb.com.cn/r/seirzoejehxs");
        PlatformConfig.setQQZone("1101175950", "O0vk6yNN7yE7e82e");
    }
}
