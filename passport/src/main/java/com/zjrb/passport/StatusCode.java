package com.zjrb.passport;

/**
 * Function: StatusCode
 * <p>
 * Author: chen.h
 * Date: 2018/7/4
 */
public class StatusCode {
    public static final int SUCCESS = 0;
    public static final int ERROR_SERVER = 200000;
    public static final int ERROR_PARAM = 200001;
    public static final int ERROR_SIGN = 200003;
    public static final int ERROR_SMS_FAILED_SHORT_TIME = 300000;
    public static final int ERROR_SMS_INVALID = 300001;
    public static final int ERROR_PHONE_REGISTERED = 400001;
    public static final int ERROR_PHONE_NOT_REGISTERED = 400002;
    public static final int ERROR_PASSWORD = 400003;
    public static final int ERROR_SAME_PASSWORD = 400004;
    public static final int ERROR_PASSPORT_NOT_EXIST = 500001;
    public static final int ERROR_THIRD_BOUND = 600000;
    public static final int ERROR_KEEP_ONE = 600001;
    public static final int ERROR_THIRD_NOT_EXIST = 600002;
    public static final int ERROR_THIRD_BIND_FAILED = 600003;


    public static String getMessage(int code) {
        switch (code) {
            case SUCCESS:
                return "";
            case ERROR_SERVER:
                return "内部服务器错误";
            case ERROR_PARAM:
                return "请求参数有误";
            case ERROR_SIGN:
                return "请求签名参数非法";
            case ERROR_SMS_FAILED_SHORT_TIME:
                return "短时间内无法再次获取短信验证码";
            case ERROR_SMS_INVALID:
                return "短信验证码无效";
            case ERROR_PHONE_REGISTERED:
                return "该手机号已经注册浙报通行证";
            case ERROR_PHONE_NOT_REGISTERED:
                return "该手机号未注册浙报通行证";
            case ERROR_PASSWORD:
                return "密码不匹配";
            case ERROR_SAME_PASSWORD:
                return "新密码与旧密码一样";
            case ERROR_PASSPORT_NOT_EXIST:
                return "通行证ID不存在";
            case ERROR_THIRD_BOUND:
                return "该社交账号已被其他通行证账号绑定";
            case ERROR_KEEP_ONE:
                return "至少需要保留一种登录方式";
            case ERROR_THIRD_NOT_EXIST:
                return "第三方绑定信息不存在";
            case ERROR_THIRD_BIND_FAILED:
                return "社交账号绑定信息验证失败";
            default:
                return "错误的结果";

        }

    }
}
