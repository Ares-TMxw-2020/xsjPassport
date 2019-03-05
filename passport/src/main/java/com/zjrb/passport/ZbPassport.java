package com.zjrb.passport;

import android.content.Context;

import com.zjrb.passport.Entity.AccountInfo;
import com.zjrb.passport.Entity.AuthInfo;
import com.zjrb.passport.Entity.ClientInfo;
import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.listener.ZbAuthListener;
import com.zjrb.passport.listener.ZbGetAccountInfoListener;
import com.zjrb.passport.listener.ZbInitListener;
import com.zjrb.passport.listener.ZbResultListener;
import com.zjrb.passport.net.interfaces.Call;

/**
 * Function: ZbPassport
 * <p>
 * Author: chen.h
 * Date: 2018/6/28
 */
public class ZbPassport {

    private static ZbConfig zbConfig;

    private static NetWork netWork;

    private ZbPassport() {
    }

    public static void init(Context context) {
        zbConfig = new ZbConfig(context);
        netWork = new NetWork();
        ZbPassport.initApp(zbConfig.getAppId() + "", new ZbInitListener() {
            @Override
            public void onSuccess(ClientInfo info) {
                if (info != null) {
                    zbConfig.setSignatureKey(info.getSignature_key()); // 设置签名密钥,30分钟有效期
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {

            }
        });
    }

    public static void init(final Context context, ZbConfig.Builder builder) {
        zbConfig = new ZbConfig(context);
        netWork = new NetWork();
        setZbConfig(builder);
        ZbPassport.initApp(zbConfig.getAppId() + "", new ZbInitListener() {
            @Override
            public void onSuccess(ClientInfo info) {
                if (info != null) {
                    zbConfig.setSignatureKey(info.getSignature_key()); // 设置签名密钥,30分钟有效期
                    // todo 测试代码
                    String phoneNum = "18519123764";
                    String id = "1";
                    String token = "J8BWUjBaYStIHqBu1g9pFjWv";
                    System.out.println("ssssss: " + ZbPassport.getGraphicsCode());
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ZbPassport.sendCaptcha("1", phoneNum, "", new ZbResultListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage) {

                        }
                    });
                    ZbPassport.loginCustom(id, phoneNum, "111", "", new ZbAuthListener() {
                        @Override
                        public void onSuccess(AuthInfo info) {

                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage) {

                        }
                    });
                    ZbPassport.loginCaptcha(id, phoneNum, "11", new ZbAuthListener() {
                        @Override
                        public void onSuccess(AuthInfo info) {

                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage) {

                        }
                    });
                    ZbPassport.checkCaptcha(id, phoneNum, "1234", new ZbResultListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage) {

                        }
                    });
                    ZbPassport.loginThird(id, "id", ZbConstants.ThirdLogin.QQ, "token", new ZbAuthListener() {
                        @Override
                        public void onSuccess(AuthInfo info) {

                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage) {

                        }
                    });
                    ZbPassport.resetPassword(id, phoneNum, "1234", "2222", new ZbResultListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage) {

                        }
                    });
                    ZbPassport.register(id, phoneNum, "1234", new ZbResultListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage) {

                        }
                    });
                    ZbPassport.getAccountDetail(id, token, new ZbGetAccountInfoListener() {
                        @Override
                        public void onSuccess(AccountInfo info) {

                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage) {

                        }
                    });

                    ZbPassport.checkPassWord(id, "11", token, new ZbResultListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage) {

                        }
                    });

                    ZbPassport.changePassword(id, "22", "11",token, new ZbResultListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage) {

                        }
                    });

                    ZbPassport.changePhoneNum(id, "11", "22", token, new ZbResultListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage) {

                        }
                    });

                    ZbPassport.bindThirdParty(id, "uid", ZbConstants.ThirdLogin.QQ, "token", token, new ZbResultListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage) {

                        }
                    });
                    ZbPassport.unBindThirdParty(id, ZbConstants.ThirdLogin.QQ, token, new ZbResultListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {

            }
        });

    }

    public static void setZbConfig(ZbConfig.Builder builder) {
        builder.build(zbConfig);
    }

    /**
     * 设置token
     *
     * @param token token
     */
    public static void setToken(String token) {
        zbConfig.setToken(token);
    }


    public static ZbConfig getZbConfig() {
        return zbConfig;
    }


    /**
     * 初始化接口
     *
     * @param clientId 接入客户端id
     * @param listener
     * @return
     */
    public static Call initApp(String clientId, final ZbInitListener listener) {
        return netWork.initApp(clientId, listener);
    }

    /**
     * 获取图形验证码接口
     *
     * @return
     */
    public static String getGraphicsCode() {
        return netWork.getGraphicsCode();
    }


    /**
     * 获取手机短信验证码接口
     *
     * @param clientId    接入客户端id
     * @param phoneNumber 手机号
     * @param captcha     图形验证码,非必传
     * @param listener
     * @return
     */
    public static Call sendCaptcha(String clientId, String phoneNumber, String captcha, final ZbResultListener listener) {
        return netWork.sendCaptcha(clientId, phoneNumber, captcha, listener);
    }


    /**
     * 验证码预检查接口 get请求
     * @param clientId 接入客户端id
     * @param phoneNumber 手机号
     * @param security_code 短信验证码
     * @param listener
     * @return
     */
    public static Call checkCaptcha(String clientId, String phoneNumber, String security_code, final ZbResultListener listener) {
        return netWork.checkCaptcha(clientId, phoneNumber, security_code, listener);
    }

    /**
     * 修改密码预检查接口 get请求
     * @param clientId 接入客户端id
     * @param old_password 旧密码
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public static Call checkPassWord(String clientId, String old_password, String accessToken, final ZbResultListener listener) {
        return netWork.checkPassWord(clientId, old_password, accessToken, listener);
    }


    /**
     * 手机号密码认证接口
     *
     * @param clientId    接入客户端id
     * @param phoneNumber 手机号
     * @param password    密码
     * @param captcha     验证码 没有传空
     * @param listener
     * @return
     */
    public static Call loginCustom(String clientId, String phoneNumber, String password, String captcha, final ZbAuthListener listener) {
        return netWork.loginCustom(clientId, phoneNumber, password, captcha, listener);
    }

    /**
     * 使用手机号注册通行证接口 post
     * @param clientId 接入客户端id
     * @param phoneNumber 手机号
     * @param security_code 短信验证码
     * @param listener
     * @return
     */
    public static Call register(String clientId, String phoneNumber, String security_code, final ZbResultListener listener) {
        return netWork.register(clientId, phoneNumber, security_code, listener);
    }


    /**
     * 手机号短信验证码认证接口
     *
     * @param clientId      接入客户端id
     * @param phoneNumber   手机号
     * @param security_code 验证码
     * @param listener
     * @return
     */
    public static Call loginCaptcha(String clientId, String phoneNumber, String security_code, final ZbAuthListener listener) {
        return netWork.loginCaptcha(clientId, phoneNumber, security_code, listener);
    }

    /**
     * 第三方账号登录认证接口
     *
     * @param clientId
     * @param auth_uid     第三方用户唯一id标识
     * @param auth_type    第三方绑定类型
     * @param auth_token 第三方返回的access_token
     * @param listener
     * @return
     */
    public static Call loginThird(String clientId, String auth_uid, @ZbConstants.ThirdType int auth_type, String auth_token, final ZbAuthListener listener) {
        return netWork.loginThird(clientId, auth_uid, auth_type, auth_token, listener);
    }


    /**
     * 重置密码接口
     *
     * @param clientId      接入客户端id
     * @param phone_number  手机号
     * @param security_code 验证码
     * @param new_password  新密码（需要使用服务端提供的公钥匙进行RSA加密，将加密结果以base64格式编码）
     * @param listener
     * @return
     */
    public static Call resetPassword(String clientId, String phone_number, String security_code, String new_password, final ZbResultListener listener) {
        return netWork.resetPassword(clientId, phone_number, security_code, new_password, listener);
    }


    /**
     * 获取账号详情接口
     *
     * @param clientId 接入客户端id
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public static Call getAccountDetail(String clientId, String accessToken, final ZbGetAccountInfoListener listener) {
        return netWork.getAccountDetail(clientId, accessToken, listener);
    }

    /**
     * 修改密码接口
     *
     * @param clientId     接入客户端id
     * @param new_password 新密码
     * @param old_password 旧密码
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public static Call changePassword(String clientId, String new_password, String old_password, String accessToken, final ZbResultListener listener) {
        return netWork.changePassword(clientId, new_password, old_password, accessToken, listener);
    }


    /**
     * 修改(绑定)手机号接口
     *
     * @param clientId         接入客户端id
     * @param new_phone_number 新手机号
     * @param security_code    新手机下发的短信验证码
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public static Call changePhoneNum(String clientId, String new_phone_number, String security_code, String accessToken, final ZbResultListener listener) {
        return netWork.changePhoneNum(clientId, new_phone_number, security_code, accessToken, listener);
    }

    /**
     * 绑定第三方登录接口
     *
     * @param clientId     接入客户端id
     * @param auth_uid     第三方用户唯一id标识
     * @param auth_type    第三方账户绑定类型
     * @param auth_token 第三方返回的auth_token
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public static Call bindThirdParty(String clientId, String auth_uid, @ZbConstants.ThirdType int auth_type, String auth_token, String accessToken, final ZbResultListener listener) {
        return netWork.bindThirdParty(clientId, auth_uid, auth_type, auth_token, accessToken, listener);
    }


    /**
     * 解绑第三方登录接口
     *
     * @param clientId  接入客户端id
     * @param auth_type 第三方账号绑定id
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public static Call unBindThirdParty(String clientId, @ZbConstants.ThirdType int auth_type, String accessToken, final ZbResultListener listener) {
        return netWork.unBindThirdParty(clientId, auth_type, accessToken, listener);
    }


}
