package passportdemo.zjrb.com.zjrbpassport;

import android.content.Context;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

/**
 * 第三方SDK初始化类
 */
public class BaseInit {

    /**
     * 子线程异步初始化处理在这里
     */
    public static void init(Context context, String channel) {
        initUmeng(channel, context);
        initUmengLogin(context);
    }

    /**
     * 友盟第三方登录
     */
    private static void initUmengLogin(Context context) {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(context).setShareConfig(config);
        PlatformConfig.setWeixin("wxc8bcb96e972bd147", "6bde68292c1295c7cf81d47a3a520030");
        PlatformConfig.setSinaWeibo("287017146", "5113d5e528ae8335f230f025bcbd6fa1", "http://www.zjol.com.cn");
        PlatformConfig.setQQZone("101096646", "da6306af99c94ba949029b563a69a9a4");
        PlatformConfig.setDing("dingoahuzuxhqyb2jtypgm");
    }

    // 初始化友盟  发送策略需商定 90s一次还是实时 还是下次启动
    private static void initUmeng(String channel, Context context) {
        UMConfigure.init(context, "535879d256240b8965030920", channel, UMConfigure.DEVICE_TYPE_PHONE, "");
    }

}
