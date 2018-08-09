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
import com.zjrb.passport.constant.ZbConstants;

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
        ZbPassport.init(this,
                        new ZbConfig.Builder().setEnvType(ZbConstants.Env.TEST)
                                              .setDebug(true)
                                              .setAppVersion("1.0")
                                              .setAppUuid("uuid"));
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
        PlatformConfig.setWeixin("wxc8bcb96e972bd147", "6bde68292c1295c7cf81d47a3a520030");
        PlatformConfig.setSinaWeibo("287017146", "5113d5e528ae8335f230f025bcbd6fa1", "http://www.zjol.com.cn");
        PlatformConfig.setQQZone("101096646", "da6306af99c94ba949029b563a69a9a4");
        PlatformConfig.setDing("dingoahuzuxhqyb2jtypgm");
    }
}
