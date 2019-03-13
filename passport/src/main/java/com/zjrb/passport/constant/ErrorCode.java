package com.zjrb.passport.constant;

/**
 * Function: ErrorCode
 * <p>
 * Author: chen.h
 * Date: 2018/7/4
 */
public class ErrorCode {


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
    public static final int ERROR_INTERNAL = 100;

    /**
     * 当前会话非法（session需要初始化）,需要重新请求init接口
     */
    public static final int ERROR_SESSION_NEED_INIT = 101;

    /**
     * 参数签名非法
     */
    public static final int ERROR_SIGNATURE = 102;

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
     * 该手机号已经被其他账号占用
     */
    public static final int ERROR_PHONENUM_ALREADY_BIND = 100006;

    /**
     * 开发平台账号认证失败
     */
    public static final int ERROR_OPEN_AUTH_FAIL = 100007;

    /**
     * 第三方openId已经被其他账号占用
     */
    public static final int ERROR_THIRD_ALREADY_BIND = 100008;

    /**
     * 其他账号已经使用该登录认证信息
     */
    public static final int ERROR_AUTH_INFO_USED = 100009;

    /**
     * 账号信息被禁用
     */
    public static final int ERROR_ACCOUNT_INFO_FORBIDDEN = 100010;

    /**
     * 不支持该认证方式
     */
    public static final int ERROR_NOT_SUPPORT_AUTH = 100011;

    /**
     * 该手机号已被其他账号占用且可以进行账号合并
     */
    public static final int ERROR_PHONENUM_CAN_MERGE = 100012;

    /**
     * 第三方open id已经被其他账号占用且可以进行账号合并
     */
    public static final int ERROR_THIRD_PARTY_CAN_MERGE = 100013;

    /**
     * 需要重置密码
     */
    public static final int ERROR_NEED_RESET_PASSWORD = 100014;




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
