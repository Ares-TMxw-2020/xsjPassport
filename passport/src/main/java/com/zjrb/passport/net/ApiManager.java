package com.zjrb.passport.net;

import android.text.TextUtils;

import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.constant.ZbConstants;

/**
 * Date: 2018/6/29 上午9:31
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: Api 管理类
 */
public class ApiManager {

    private static final class UrlConstant {
        /**
         * 开发环境
         */
        private static final String DEVELOP_URL = "https://passport-test.8531.cn";
        /**
         * 测试环境
         */
        private static final String TEST_URL = "https://passport-test.8531.cn";
        /**
         * 预发布环境
         */
        private static final String PRE_URL = "https://passport.8531.cn";
        /**
         * 正式域名
         */
        private static final String OFFICIAL_URL = "https://passport.8531.cn";

    }

    /**
     * @return 环境
     */
    public static final String getBaseUri() {
        int envType = ZbPassport.getZbConfig().getEnvType();
        switch (envType) {
            case ZbConstants.Env.DEV:
                return UrlConstant.DEVELOP_URL;
            case ZbConstants.Env.TEST:
                return UrlConstant.TEST_URL;
            case ZbConstants.Env.PRE:
                return UrlConstant.PRE_URL;
            case ZbConstants.Env.OFFICIAL:
                return UrlConstant.OFFICIAL_URL;
            case  ZbConstants.Env.CUSTOM:
                return ZbPassport.getZbConfig().getHost();
            default:
                return UrlConstant.OFFICIAL_URL;
        }
    }

    /**
     * 接口的拼接
     *
     * @param api api
     * @return 完整的url
     */
    public static String joinUrl(String api) {
        return getBaseUri() + api;
    }

    /**
     * 修改host,测试环境使用
     *
     * @param host
     * @return
     */
    public static String changeHost(String host) {
        if (TextUtils.isEmpty(host)) {
            return getBaseUri();
        }
        if (ZbPassport.getZbConfig().isDebug()) { // 测试环境设置强制Https才有效
            boolean isUseHttps = ZbPassport.getZbConfig().isUseHttps();
            String schema = isUseHttps ? "https" : "http";
            return schema + "://" + host;
        } else {
            return getBaseUri();
        }
    }

    public static final class EndPoint {

        /**
         * 会话初始化接口
         * 该接口在用户打开通行证html5页面时全局调用的初始化接口 get
         */
        public static final String INIT = "/web/init";

        /**
         * 获取图形验证码接口
         * 该接口在用户注册、密码登录、获取短信验证码等接口前需要获取图形验证码 post
         */
        public static final String SMS_CAPTCHA_IMAGE = "/web/security/captcha_image";

        /**
         * 获取短信验证码接口
         * 获取手机短信验证码 post
         */
        public static final String SMS_SEND_SECURITY_CODE = "/web/security/send_security_code";

        /**
         * 使用手机号+第三方绑定注册通行证接口
         * post
         */
//        public static final String PASSPORT_PHONE_THRID_REGISTER= "/web/oauth/register";

        /**
         * 验证码预检查接口
         * get
         */
        public static final String SMS_CHECK_SECURITY_CODE= "/web/security/check_security_code";

        /**
         * 手机号和密码认证接口
         * post
         */
        public static final String PASSPORT_PHONENUM_PASSWORD = "/web/oauth/credential_auth";

        /**
         * 手机号和短信验证码认证接口
         * post
         */
        public static final String PASSPORT_PHONENUM_CAPTCHA = "/web/oauth/security_code_auth";

        /**
         * 第三方账号登录认证接口
         * post
         */
        public static final String PASSPORT_THIRD_PARTY_AUTH = "/web/oauth/third_party_auth";

        /**
         * 第三方账号登录且强制绑定手机号接口(跟林参确认,可以去掉不用)
         * post
         */
        public static final String PASSPORT_THIRD_PARTY_BIND_PHONE_AUTH = "/web/account/bind_phone_number_auth";

        /**
         * 密码重置接口(忘记密码)
         * post
         */
        public static final String PASSPORT_RESET_PASSWORD = "/web/oauth/reset_password";

        /**
         * 获取账号详情接口
         * get
         */
        public static final String PASSPORT_ACCOUNT_DETAIL = "/web/account/detail";

        /**
         * 修改密码预检查接口
         * GET
         */
        public static final String PASSPORT_CHECK_PASSWORDS = "/web/account/check_password";

        /**
         * 修改密码接口
         * post
         */
        public static final String PASSPORT_ALTER_PASSWORD = "/web/account/alter_password";

        /**
         * 修改手机号接口
         * post
         */
        public static final String PASSPORT_ALTER_PHONE_NUM = "/web/account/alter_phone_number";

        /**
         * 绑定第三方登录接口
         * post
         */
        public static final String PASSPORT_BIND_THIRD_PARTY = "/web/account/bind_third_party";

        /**
         * 解绑第三方登录接口
         * post
         */
        public static final String PASSPORT_UNBIND_THIRD_PARTY = "/web/account/unbind_third_party";

        /**
         * 检查手机号是否注册过通行证
         * get
         */
        public static final String PASSPORT_CHECK_PHONE_NUMBER = "/web/account/check_phone_number";

        /**
         * 第三方账号是否已经绑定检查接口
         * get
         */
        public static final String PASSPORT_CHECK_THIRD_PARTY = "/web/oauth/register_check";


    }

}
