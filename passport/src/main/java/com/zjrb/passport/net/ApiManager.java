package com.zjrb.passport.net;

import android.text.TextUtils;

/**
 * Date: 2018/6/29 上午9:31
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: Api 管理类
 */
public class ApiManager {

    private static final class UrlConstant {

        /**
         * 正式域名
         */
        private static final String BASE_URL = "https://api-new.8531.cn";

    }

    public static final String getBaseUri() {
        // TODO: 2018/6/29  增加不同环境的判断
        return UrlConstant.BASE_URL;
    }

    /**
     * 接口的拼接
     * @param api
     * @return
     */
    public static String jointUrl(String api) {
        return getBaseUri() + api;
    }

    /**
     * 修改host,测试使用
     * @param host
     * @return
     */
    public static String changeHost(String host) {
        // TODO: 2018/6/29 增加判断使用http的条件
        if (TextUtils.isEmpty(host)) {
            return UrlConstant.BASE_URL;
        }
        boolean isUseHttps = false;
        String schema = isUseHttps ? "https" : "http";
        // TODO: 2018/6/29 保存到sp中
        return schema + "://" + host;
    }


}
