package com.zjrb.passport;

/**
 * Function: StatusCode
 * <p>
 * Author: chen.h
 * Date: 2018/7/4
 */
public class StatusCode {
    /**
     * 成功
     */
    public static final int SUCCESS = 0;
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
     * Json解析异常
     */
    public static final int ERROR_JSON = 10001;
    /**
     * 错误的接口返回
     */
    public static final int ERROR_INTERFACE_DATA = 10002;


}
