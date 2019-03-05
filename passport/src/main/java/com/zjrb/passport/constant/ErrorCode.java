package com.zjrb.passport.constant;

/**
 * Function: ErrorCode
 * <p>
 * Author: chen.h
 * Date: 2018/7/4
 */
public class ErrorCode {

    /**
     * 内部服务器错误
     */
    public static final int ERROR_SERVER = 200000;
    /**
     * 请求参数有误
     */
    public static final int ERROR_PARAM = 200001;
    /**
     * 请求签名参数非法
     */
    public static final int ERROR_SIGN = 200003;
    /**
     * 短时间内无法再次获取短信验证码
     */
    public static final int ERROR_SMS_FAILED_SHORT_TIME = 300000;
    /**
     * 短信验证码无效
     */
    public static final int ERROR_SMS_INVALID = 300001;
    /**
     * 该手机号已经注册浙报通行证
     */
    public static final int ERROR_PHONE_REGISTERED = 400001;
    /**
     * 该手机号未注册浙报通行证
     */
    public static final int ERROR_PHONE_NOT_REGISTERED = 400002;
    /**
     * 密码不匹配
     */
    public static final int ERROR_PASSWORD = 400003;
    /**
     * 新密码与旧密码一样
     */
    public static final int ERROR_SAME_PASSWORD = 400004;
    /**
     该手机号已经注册浙报通行证，且可进行合并操作
     */
    public static final int ERROR_PHONE_REGISTERED_CAN_MERGE= 400005;
    /**
     表示需要重置密码以后才能登陆(手机密码组合才会有这个提示)
     */
    public static final int ERROR_PHONE_LGOIIN_NEED_RESET= 400009;
    /**
     * 通行证ID不存在
     */
    public static final int ERROR_PASSPORT_NOT_EXIST = 500001;
    /**
     * 该社交账号已被其他通行证账号绑定
     */
    public static final int ERROR_THIRD_BOUND = 600000;
    /**
     * 至少需要保留一种登录方式
     */
    public static final int ERROR_KEEP_ONE = 600001;
    /**
     * 第三方绑定信息不存在
     */
    public static final int ERROR_THIRD_NOT_EXIST = 600002;
    /**
     * 社交账号绑定信息验证失败
     */
    public static final int ERROR_THIRD_BIND_FAILED = 600003;
    /**
     * 该社交平台账号已被其他通行证绑定，且可进行账号合并操作
     */
    public static final int ERROR_THIRD_REGISTERED_CAN_MERGE = 600004;

    /**
     * Json解析异常
     */
    public static final int ERROR_JSON = 10001;
    /**
     * 错误的接口返回
     */
    public static final int ERROR_INTERFACE_DATA = 10002;



    /**
     * 内部服务器错误
     */
    public static final int ERROR_INTERNAL = 1000;

    /**
     * 当前会话非法（session需要初始化）
     */
    public static final int ERROR_SESSION_NEED_INIT = 1001;

    /**
     * 参数签名非法
     */
    public static final int ERROR_SIGNATURE = 1002;

    /**
     * 请求异常（要求的参数缺失，要求的请求头缺失等）
     */
    public static final int ERROR_REQUEST = 1003;

    /**
     * 无法获取用户状态信息
     */
    public static final int ERROR_NOT_ACCESS_INFO = 100000;

    /**
     * 账号不存在
     */
    public static final int ERROR_ACCOUNT_NOT_EXIST = 100001;


    /**
     * 新密码与旧密码不能保持一致
     */
    public static final int ERROR_PASSWORD_EQUAL = 100003;

    /**
     * 账号与密码不匹配
     */
    public static final int ERROR_ACCOUNT_PASSWORD_NO_MATCH = 100004;

    /**
     * 新手机号与旧手机号不能保持一致
     */
    public static final int ERROR_PHONENUM_EQUAL = 100005;

    /**
     * 该第三方绑定信息已经被其他账号占用
     */
    public static final int ERROR_THIRD_ALREADY_BIND = 100006;

    /**
     * 验证码请求次数过于频繁
     */
    public static final int ERROR_VALIDATECODE_MORE = 200001;
    /**
     * 验证码无效
     */
    public static final int ERROR_VALIDATECODE_NO_EFFECT = 200002;
    /**
     * 图形验证码无效
     */
    public static final int ERROR_GRAPHICS_VALIDATECODE_NO_EFFECT = 200003;
    /**
     * 该请求需要附加图形验证码
     */
    public static final int ERROR_NEED_GRRPHICS = 200004;

    /**
     * 应用方信息不存在或者状态异常
     */
    public static final int ERROR_APP_NOT_EXIST = 700001;
    /**
     * 回调地址非法
     */
    public static final int ERROR_LOOKBACK_ADDRESS_ILLEGAL = 800001;
    /**
     * 授权码信息无效
     */
    public static final int ERROR_AUTHCODE_INVALID = 800002;
    /**
     * access_token无效
     */
    public static final int ERROR_ACCESS_TOKEN_INVALID = 800003;
    /**
     * refresh_token
     */
    public static final int ERROR_REFRESH_TOKEN = 800004;
    /**
     * 不支持该grant_type
     */
    public static final int ERROR_GRANT_TYPE_NOT_SUPPORT = 800005;
    /**
     * 参数丢失或者格式异常
     */
    public static final int ERROR_FORMAT_ILLEGAL = 900001;
    /**
     * rypto数据异常
     */
    public static final int ERROR_RYPTO_DATA = 900003;



}
