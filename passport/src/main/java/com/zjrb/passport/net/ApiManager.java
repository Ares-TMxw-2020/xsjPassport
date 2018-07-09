package com.zjrb.passport.net;

import android.text.TextUtils;

import com.zjrb.passport.ZbPassport;

/**
 * Date: 2018/6/29 上午9:31
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: Api 管理类
 */
public class ApiManager {

    private static String URL = UrlConstant.BASE_URL;

    private static final class UrlConstant {
        /**
         * 开发环境
         */
        private static final String DEVELOP_URL = "http://10.100.64.69";

        /**
         * 正式域名
         */
        // TODO: 2018/7/9 待确认
        private static final String BASE_URL = "http://10.100.64.69";

    }

    public static final String getBaseUri() {
        if (ZbPassport.getZbConfig().isDebug()) {
            return ApiManager.URL;
        } else {
            return UrlConstant.BASE_URL;
        }

    }

    /**
     * 接口的拼接
     * @param api
     * @return
     */
    public static String joinUrl(String api) {
        return getBaseUri() + api;
    }

    /**
     * 修改host,测试使用
     * @param host
     * @return
     */
    public static String changeHost(String host, Request.HttpMethod method) {
        // TODO: 2018/6/29 增加判断使用http的条件
        if (TextUtils.isEmpty(host)) {
            return getBaseUri();
        }
        boolean isUseHttps = false;
        String schema = isUseHttps ? "https" : "http";
        ApiManager.URL = schema + "://" + host;
        return ApiManager.URL;
    }

    public static final class EndPoint {

        /**
         * 下发注册短信验证码接口 post
         */
        public static final String SMS_SEND_REGISTER_TOKEN = "/api/sms/send_register_token";

        /**
         * 下发登录短信验证码接口 post
         */
        public static final String SMS_SEND_LOGIN_TOKEN = "/api/sms/send_login_token";

        /**
         * 下发绑定找回密码短信验证码接口 post
         */
        public static final String SMS_SEND_RESET_TOKEN = "/api/sms/send_reset_token";

        /**
         * 下发绑定新手机号短信验证码接口 post
         */
        public static final String SMS_SEND_BINDING_TOKEN = "/api/sms/send_binding_token";



        /**
         * 手机号注册浙报通行证接口 post
         */
        public static final String PASSPORT_REGISTER = "/api/passport/register";

        /**
         * 手机号与密码登录浙报通行证接口 post
         */
        public static final String PASSPORT_PASSWORD_LOGIN = "/api/passport/password_login";

        /**
         * 手机号与短信验证码登录浙报通行证接口 post
         */
        public static final String PASSPORT_SMS_TOKEN_LOGIN = "/api/passport/sms_token_login";

        /**
         * 获取通行证详情接口 post
         */
        public static final String PASSPORT_DETAIL = "/api/passport/detail";

        /**
         * 绑定浙报通行证手机号接口(社交账号绑定也使用该接口) post
         */
        public static final String PASSPORT_BIND_PHONE_NUMBER = "/api/passport/bind_phone_number";

        /**
         * 更改浙报通行证密码接口 post
         */
        public static final String PASSPORT_CHANGE_PASSWORD = "/api/passport/change_password";

        /**
         * 重置浙报通行证密码接口 post
         */
        public static final String PASSPORT_RESET_PASSWORD = "/api/passport/reset_password";

        /**
         * 检查手机号是否已绑定浙报通行证接口 post
         */
        public static final String PASSPORT_CHECK_BINDING = "/api/passport/check_binding";

        /**
         * 退出登录接口
         */
        public static final String PASSPORT_LOGOUT = "/api/passport/logout";



        /**
         * 社交平台账号登录/注册接口 post
         */
        public static final String THIRD_PARTY_LOGIN = "/api/third_party/login";


        /**
         * 浙报通行证绑定新的社交平台账号接口 post
         */
        public static final String THIRD_PARTY_BIND = "/api/third_party/bind";

        /**
         * 浙报通行证解绑社交账号接口 post
         */
        public static final String THIRD_PARTY_UNBIND = "/api/third_party/unbind";

    }

}
