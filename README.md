# 浙报通行证sdk

## 集成
### compile
工程根目录的build.gradle里面添加仓库

```
allprojects {
    repositories {
        maven { url "http://10.100.62.98:8086/nexus/content/groups/public" }
    }
}

```

项目根目录的build.gradle里面添加依赖

```
dependencies {
	compile 'cn.daily.android:passport:0.0.0.17.new-SNAPSHOT'
}

```

### 网络权限
```
<manifest ...>
	<uses-permission android:name="android.permission.INTERNET" />
</manifest>
```

### 参数配置
#### 方式一: Manifest配置
```
<manifest ...>
	<application>
        <meta-data
            android:name="ZBP_APP_ID"
            android:value="1"/>
        <meta-data
            android:name="ZBP_APP_ENV"
            android:value="dev"/>
	</application>
</manifest>
```

#### 方式二:代码配置 推荐使用代码配置方式

```java
//在Application中
        ZbPassport.init(this,
                new ZbConfig.Builder().setEnvType(ZbConstants.Env.TEST)
                        .setDebug(true)
                        .setAppVersion("1.0")
                        .setClientId(1)
                        .setAppUuid("uuid"));

```
参数说明：
client_id：对于每一个app接入方，通行证后台都会分配一个client_id 。
env_type: 通行证sdk提供的接入环境,分别为ZbConstants.Env.DEV(开发)  ZbConstants.Env.TEST(测试) ZbConstants.Env.PRE(预发)  ZbConstants.Env.OFFICIAL(正式环境)

**代码设置与Manifest同时配置代码配置会覆盖Manifest配置的参数,推荐使用代码配置方式**

## 使用说明

### 初始化
//在Application中通过：
ZbPassport.init(Context context); // 该方式会取清单中通过meta-data配置的参数

//或者
ZbPassport.init(Context context, ZbConfig.Builder builder);

示例代码:
```java
ZbPassport.init(this,  new ZbConfig.Builder().setAppId(1)
                                             .setEnvType(ZbConstants.Env.DEV)
                                             .setDebug(true)
                                             .setAppVersion("1.0")
                                             .setAppUuid("uuid"));
```
**代码设置与Manifest相同参数会覆盖Manifest配置的参数,推荐使用代码配置方式**

### 关于验证码
验证码为6位有效数字,非6位的数字服务端不会提示验证码错误(坑需求,需要客户端进行验证码位数的校验) 验证码有效期为10分钟，且只能使用一次,且只能使用一次,且只能使用一次,重要的事情说三遍。

### 重要的Code码说明
ErrorCode.ERROR_NEED_RESET_PASSWORD: 代表该操作需要重置密码
ErrorCode.ERROR_NEED_GRRPHICS: 代表该操作需要进行图形验证码校验
ErrorCode.ERROR_PHONENUM_ALREADY_BIND: 代表该手机号已被其他账号占用（注册手机号、修改手机号、绑定手机号时该手机号已被占用）
ErrorCode.ERROR_THIRD_ALREADY_BIND: 第三方openid已经被其他帐号占用（第三方帐号绑定被占用）
ErrorCode.ERROR_CAN_MERGE: 代表需要进行账号合并的操作

### 关于密码
通行证涉及到密码时密码需要使用RSA加密算法加密，并以BASE64编码方式编码成字符串进行传输,sdk内部已对密码进行加密处理。

## ZbPassport中主要方法
### 初始化接口,对接入方透明,接入方在调用ZbPassport.init(Context context, ZbConfig.Builder builder)方法时,会自动调用该方法,该接口主要用户获取signature_key根据相应的加密算法生成签名
```java
    /**
     * 初始化接口
     *
     * @param listener
     * @return
     */
    public static Call initApp( final ZbInitListener listener) {
        return netWork.initApp(listener);
    }
```

### 注册接口
#### 注册接口复用手机号短信验证码认证接口
```java
      /**
       * 手机号短信验证码认证接口
       *
       * @param phoneNumber   手机号
       * @param security_code 验证码
       * @param listener
       * @return
       */
      public static Call loginCaptcha(String phoneNumber, String security_code, final ZbAuthListener listener) {
          return netWork.loginCaptcha(phoneNumber, security_code, listener);
      }
```

#### 第三方账号同时绑定手机号接口
```java
    /**
     * 第三方账号同时绑定手机号接口 POST
     * @param auth_uid   第三方用户唯一id标识
     * @param auth_type  第三方绑定类型
     * @param auth_token 第三方返回的access_token
     * @param phoneNum 手机号
     * @param smsCode 短信验证码
     * @param listener
     * @return
     */
    public static Call registerThirdBindPhone(String phoneNum, String smsCode, String auth_uid, int auth_type, String auth_token, final ZbAuthListener listener) {
        return netWork.registerThirdBindPhone(phoneNum, smsCode, auth_uid, auth_type, auth_token,listener);
    }
```


### 关于登录认证的接口
通行证涉及到登录相关的操作,请求通行证后台成功后会下发相应的授权码,客户端拿到此授权码到客户端对应后台换取accessToken进行登录认证.

#### 手机号密码认证接口
```java
    /**
     * 手机号密码认证接口
     *
     * @param phoneNumber 手机号
     * @param password    密码
     * @param captcha     图形验证码 没有传空
     * @param listener
     * @return
     */
    public static Call loginCustom(String phoneNumber, String password, String captcha, final ZbAuthListener listener) {
        return netWork.loginCustom(phoneNumber, password, captcha, listener);
    }
```
示例代码:
```java
 ZbPassport.loginCustom(text, password, "", new ZbAuthListener() {
                @Override
                public void onSuccess(AuthInfo info) {
                    if (info != null) {
                        loginValidate(text, info.getCode());
                    } else {
                        LoadingDialogUtils.newInstance().dismissLoadingDialog(false, getString(R.string.zb_login_error));
                        T.showShortNow(ZBPasswordLoginActivity.this, getString(R.string.zb_login_error)); // 登录失败
                    }
                }

                @Override
                public void onFailure(int errorCode, String errorMessage) {
                    if (errorCode == ErrorCode.ERROR_NEED_RESET_PASSWORD) { // 需要重置密码才能登陆的情况

                    } else if (errorCode == ErrorCode.ERROR_NEED_GRRPHICS) {

                    } else {
                        LoadingDialogUtils.newInstance().dismissLoadingDialog(false, getString(R.string.zb_login_error));
                        T.showShortNow(ZBPasswordLoginActivity.this, errorMessage);
                    }
                }
            });
```


#### 手机号和短信验证码认证接口
```java
      /**
       * 手机号短信验证码认证接口
       *
       * @param phoneNumber   手机号
       * @param security_code 验证码
       * @param listener
       * @return
       */
      public static Call loginCaptcha(String phoneNumber, String security_code, final ZbAuthListener listener) {
          return netWork.loginCaptcha(phoneNumber, security_code, listener);
      }
```
示例代码:
```java
        ZbPassport.loginCaptcha(phone, captcha, new ZbAuthListener() {
            @Override
            public void onSuccess(AuthInfo loginInfo) {
                if (loginInfo != null) {
                    // 登录认证
                    loginValidate(phone, loginInfo.getCode());
                } else {
                    LoadingDialogUtils.newInstance().dismissLoadingDialog(false, getString(R.string.zb_login_error));
                    T.showShortNow(LoginMainActivity.this, getString(R.string.zb_login_error)); // 登录失败
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                LoadingDialogUtils.newInstance().dismissLoadingDialogNoText();
                T.showShort(LoginMainActivity.this, errorMessage);
            }
        });
```

#### 三方登录认证接口
```java
   /**
     * 第三方账号登录认证接口
     *
     * @param auth_uid     第三方用户唯一id标识
     * @param auth_type    第三方绑定类型
     * @param auth_token 第三方返回的access_token
     * @param listener
     * @return
     */
    public static Call loginThird( String auth_uid, @ZbConstants.ThirdType int auth_type, String auth_token, final ZbAuthListener listener) {
        return netWork.loginThird( auth_uid, auth_type, auth_token, listener);
    }
```
示例代码:
```java
  public void onComplete(final SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (data != null) {
                if (data.containsKey("errcode")) {
                    mShareAPI.doOauthVerify(mActivity, platform, umUserInfoListener);
                    return;
                } else {
                    mLoginMap = data;
                    share_media = platform;
                    String uid = data.get("uid");
                    String accessToken = data.get("accessToken");
                    int type = ZbConstants.ThirdLogin.WECHAT;
                    switch (platform) {
                        case QQ:
                            type = ZbConstants.ThirdLogin.QQ;
                            break;
                        case SINA:
                            type = ZbConstants.ThirdLogin.SINA;
                            break;
                        case WEIXIN:
                            type = ZbConstants.ThirdLogin.WECHAT;
                            break;
                        default:
                            break;
                    }
                    ZbPassport.loginThird(uid, type, accessToken, zbAuthListener);
                }

            }
```
#### 网易易盾一键登录认证
```java
    /**
     * 网易易盾一键登录认证 
     * @param yd_token 网易易盾token
     * @param mobile_access_token 手机运营商返回token
     * @param listener
     * @return
     */
    public static Call loginYiDun(String yd_token, String mobile_access_token, final ZbAuthListener listener) {
        return netWork.loginYiDun(yd_token, mobile_access_token, listener);
```

### 获取图形验证码接口,回调接口返回byte[]数组供接入方使用
```java
    /**
     * 获取图形验证码接口
     *
     * @return
     */
    public static Call getGraphics(ZbGraphicListener listener) {
        return netWork.getGraphics(listener);
    }
```
示例代码:
```java
         ZbPassport.getGraphics(new ZbGraphicListener() {
                                                        @Override
                                                        public void onSuccess(byte[] bytes) {
                                                            if (bytes != null) {
                                                                GlideApp.with(LoginMainActivity.this).load(bytes).diskCacheStrategy(DiskCacheStrategy.NONE).into(zbGraphicDialog.getIvGrahpic());
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(int errorCode, String errorMessage) {
                                                            ZBToast.showShort(LoginMainActivity.this, errorMessage);
                                                        }
                                                    });
```

### 获取手机短信验证码接口 说明:获取手机短信验证码接口和校验图形验证码接口为同一个接口,若graphicCaptcha传"",代表获取短信验证码;若为非空串,则该接口为校验推行验证码接口,校验成功后自动发送短信验证码
```java
   /**
     * 获取手机短信验证码接口
     *
     * @param phoneNumber 手机号
     * @param graphicCaptcha     图形验证码,非必传
     * @param listener
     * @return
     */
    public static Call sendCaptcha(String phoneNumber, String graphicCaptcha, final ZbResultListener listener) {
        return netWork.sendCaptcha(phoneNumber, graphicCaptcha, listener);
    }
```
示例代码:
```java
             ZbPassport.sendCaptcha(mobile, "", new ZbResultListener() {
                            @Override
                            public void onSuccess() {
                                startTimeCountDown();
                                T.showShortNow(getActivity(), getString(R.string
                                        .zb_sms_send));
                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {
                            }
                        });
```


### 修改密码预校验接口,主要用于修改密码时对旧密码进行校验,浙江新闻客户端目前没有修改密码操作
```java
    /**
     * 修改密码预检查接口 get请求
     * @param old_password 旧密码
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public static Call checkPassWord(String old_password, String accessToken, final ZbResultListener listener) {
        return netWork.checkPassWord(old_password, accessToken, listener);
    }
```

### 验证码预校验接口 主要用于重置密码操作,在输入新密码前对发送的短信验证码进行校验
```java
    /**
     * 验证码预检查接口 get请求
     * @param phoneNumber 手机号
     * @param security_code 短信验证码
     * @param listener
     * @return
     */
    public static Call checkCaptcha(String phoneNumber, String security_code, final ZbResultListener listener) {
        return netWork.checkCaptcha(phoneNumber, security_code, listener);
    }
```
示例代码:
```java
       ZbPassport.checkCaptcha(phoneNum, sms, new ZbResultListener() {
            @Override
            public void onSuccess() {
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putString("phoneNum", phoneNum);
                bundle.putString("sms", sms);
                Nav.with(getActivity()).setExtras(bundle).toPath(RouteManager
                        .ZB_RESET_NEW_PASSWORD);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                T.showShort(ZBResetPasswordActivity.this, errorMessage);
            }
        });
```


### 重置密码接口
```java
    /**
     * 重置密码接口
     *
     * @param phone_number  手机号
     * @param security_code 验证码
     * @param new_password  新密码（需要使用服务端提供的公钥匙进行RSA加密，将加密结果以base64格式编码）
     * @param listener
     * @return
     */
    public static Call resetPassword(String phone_number, String security_code, String new_password, final ZbResultListener listener) {
        return netWork.resetPassword(phone_number, security_code, new_password, listener);
    }
```
示例代码:
```java
       ZbPassport.resetPassword(phoneNum, sms, etPasswordText.getText().toString(), new ZbResultListener() {
            @Override
            public void onSuccess() {
                LoadingDialogUtils.newInstance().dismissLoadingDialog(true);
                // 跳转到账号密码登录页面,手机号自动填充,密码清空
                finish();
                // 关闭 密码登录页面
                AppManager.get().finishActivity(ZBResetPasswordActivity.class);
                AppManager.get().finishActivity(ZBPasswordLoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("mobile", phoneNum);
                Nav.with(ZBResetNewPassWordActivity.this).setExtras(bundle).toPath(RouteManager.ZB_PASSWORD_LOGIN);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                LoadingDialogUtils.newInstance().dismissLoadingDialog(false, errorMessage);
            }
        });
```

### 获取账号详情接口
```java
  /**
     * 获取账号详情接口
     *
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public static Call getAccountDetail(String accessToken, final ZbGetAccountInfoListener listener) {
        return netWork.getAccountDetail(accessToken, listener);
    }
```
示例代码:
```java
                    ZbPassport.getAccountDetail(data.getAccess_token(), new ZbGetAccountInfoListener() {
                        @Override
                        public void onSuccess(AccountInfo info) {
                            if (info != null) {
                                infoList = info.getThird_parties();
//                                if (infoList != null && infoList.size() == 1 && TextUtils.isEmpty(info.getPhone_number())) { // 只有当前三方帐号绑定,解绑时提示先绑定手机号码
//                                    final ZBDialog zbDialog = new ZBDialog(AccountInfoActivity.this);
//                                    zbDialog.setBuilder(new ZBDialog.Builder()
//                                            .setTitle("还未绑定手机号")
//                                            .setMessage("您还未绑定手机号,请先绑定手机号")
//                                            .setOnClickListener(new View.OnClickListener() {
//                                                @Override
//                                                public void onClick(View v) {
//                                                    if (v.getId() == R.id.btn_left) {
//                                                        if (zbDialog.isShowing()) {
//                                                            zbDialog.dismiss();
//                                                        }
//                                                    }
//                                                    if (v.getId() == R.id.btn_right) {
//                                                        // 绑定手机号成功后,自动将第三方解绑
//                                                        isNeedUnBindThird = true;
//                                                        Nav.with(getActivity()).toPath(RouteManager.ZB_MOBILE_BIND);
//                                                    }
//                                                }
//                                            }));
//                                    zbDialog.show();
//                                }
                                // 第三方个数大于等于2,或者第三方个数为1且绑定过手机号,则解绑第三方
                                if ((infoList != null && infoList.size() >= 2) || (infoList.size() == 1 && !TextUtils.isEmpty(info.getPhone_number()))) {
                                    unBindThird(platform);
                                }
                            }
                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage) {
                            Toast.makeText(getApplication(), "解绑失败", Toast.LENGTH_SHORT).show();
                        }
                    });
```

### 修改密码接口  部分客户端没有修改密码功能,只有重置(忘记)密码功能
```java
    /**
     * 修改密码接口
     *
     * @param new_password 新密码
     * @param old_password 旧密码
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public static Call changePassword(String new_password, String old_password, String accessToken, final ZbResultListener listener) {
        return netWork.changePassword( new_password, old_password, accessToken, listener);
    }
```


### 修改(绑定)手机号接口,绑定手机号及修改手机号使用同一个接口
```java
    /**
     * 修改(绑定)手机号接口
     *
     * @param new_phone_number 新手机号
     * @param security_code    新手机下发的短信验证码
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public static Call changePhoneNum(String new_phone_number, String security_code, String accessToken, final ZbResultListener listener) {
        return netWork.changePhoneNum(new_phone_number, security_code, accessToken, listener);
    }
```
示例代码:
```java
ZbPassport.changePhoneNum(mobile, smsCode, token, new ZbResultListener() {
                        @Override
                        public void onSuccess() {
                            isAuthSuccess = true;
                            final ZBBindDialog zbBindDialog = new ZBBindDialog(ZBBindMobileActivity.this);
                            zbBindDialog.setBuilder(new ZBBindDialog.Builder()
                                    .setTitle("绑定成功!")
                                    .setMessage("如果手机号有变动，可在个人中心账号管理页面进行更改")
                                    .setOkText("知道了")
                                    .setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (v.getId() == com.zjrb.core.R.id.btn_ok) {
                                                if (zbBindDialog.isShowing()) {
                                                    zbBindDialog.dismiss();
                                                }
                                                finish();
                                                AppManager.get().finishActivity(LoginMainActivity.class);
                                            }
                                        }
                                    }));
                            zbBindDialog.show();
                            // 更新手机号信息
                            AccountBean account = UserBiz.get().getAccount();
                            account.setPhone_number(mobile); // 实名认证账号
                            UserBiz.get().setAccount(account);
                            Intent intent = new Intent("bind_mobile_successful");
                            LocalBroadcastManager.getInstance(ZBBindMobileActivity.this).sendBroadcast(intent);
                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage) {
                            if (errorCode == ErrorCode.ERROR_PHONENUM_ALREADY_BIND) { // 该手机号已被其他账号占用（注册手机号、修改手机号、绑定手机号被占用）
                                final ZBBindDialog zbBindDialog = new ZBBindDialog(ZBBindMobileActivity.this);
                                zbBindDialog.setBuilder(new ZBBindDialog.Builder()
                                        .setTitle("绑定失败")
                                        .setMessage("该手机号已注册，且绑定有同种类型的第三方帐号")
                                        .setDesc("建议登录原帐号，在个人中心帐号管理页面进行解绑后，再重新进行绑定")
                                        .setOkText("知道了")
                                        .setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (v.getId() == com.zjrb.core.R.id.btn_ok) {
                                                    if (zbBindDialog.isShowing()) {
                                                        zbBindDialog.dismiss();
                                                    }
                                                }
                                            }
                                        }));
                                zbBindDialog.show();
                            } else if (errorCode == ErrorCode.ERROR_CAN_MERGE) { // 进行账号合并的情况
                                new GetMuitiAccountTask(new APIExpandCallBack<MultiAccountBean>() {

                                    @Override
                                    public void onSuccess(MultiAccountBean data) {
                                        if (data != null) {
                                            if (timer != null) {
                                                timer.onFinish();
                                                timer.cancel();
                                            }
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("merge_data", data);
                                            bundle.putString("merge_phone", mobile);
                                            Nav.with(getActivity()).setExtras(bundle).toPath(RouteManager.ZB_ACCOUNT_MERGE);
                                        } else {
                                            T.showShortNow(ZBBindMobileActivity.this, "绑定失败");
                                        }
                                    }

                                    @Override
                                    public void onError(String errMsg, int errCode) {
                                        super.onError(errMsg, errCode);
                                        T.showShortNow(ZBBindMobileActivity.this, errMsg);
                                    }
                                }).setTag(this).exe("phone_number", mobile, smsCode);
                            } else {
                                T.showShortNow(ZBBindMobileActivity.this, errorMessage);
                            }
                        }
                    });
```


### 绑定第三方登录接口
```java
    /**
     * 绑定第三方登录接口
     *
     * @param auth_uid     第三方用户唯一id标识
     * @param auth_type    第三方账户绑定类型
     * @param auth_token 第三方返回的auth_token
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public static Call bindThirdParty(String auth_uid, @ZbConstants.ThirdType int auth_type, String auth_token, String accessToken, final ZbResultListener listener) {
        return netWork.bindThirdParty(auth_uid, auth_type, auth_token, accessToken, listener);
    }
```
示例代码:
```java
          ZbPassport.bindThirdParty(uid, finalType, token, data.getAccess_token(), new ZbResultListener() {
                                    @Override
                                    public void onSuccess() {
                                        if (mBindAccountListener != null) {
                                            mBindAccountListener.onBindSuccess(finalAuthType);
                                            LoadingDialogUtils.newInstance().dismissLoadingDialogNoText();
                                        }
                                    }

                                    @Override
                                    public void onFailure(int errorCode, String errorMessage) {
                                        if (mBindAccountListener != null) {
                                            LoadingDialogUtils.newInstance().dismissLoadingDialogNoText();
                                            mBindAccountListener.onBindFail(finalAuthType, uid, token, errorCode, errorMessage);
                                        }
                                    }
                                });
```



### 解绑第三方登录接口
```java
    /**
     * 解绑第三方登录接口
     *
     * @param auth_type 第三方账号绑定id
     * @param accessToken 接入客户端accessToken
     * @param listener
     * @return
     */
    public static Call unBindThirdParty( @ZbConstants.ThirdType int auth_type, String accessToken, final ZbResultListener listener) {
        return netWork.unBindThirdParty(auth_type, accessToken, listener);
    }
```
示例代码:
```java
    ZbPassport.unBindThirdParty(platform, data.getAccess_token(), new ZbResultListener() {
        @Override
        public void onSuccess() {
             getAccountInfo();
         }

        @Override
        public void onFailure(int errorCode, String errorMessage) {
              etAccountInfo();
        }
    });
```

### 检查手机号是否注册的接口
```java
    /**
     * 检查手机号是否注册的接口
     * @param phoneNumber 手机号
     * @param listener
     * @return
     */
    public static Call checkPhoneNumber(String phoneNumber, final ZbCheckPhoneListener listener) {
        return netWork.checkPhoneNumber(phoneNumber, listener);
    }
```
示例代码:
```java
ZbPassport.checkPhoneNumber(phoneNum, new ZbCheckPhoneListener() {
            @Override
            public void onSuccess(CheckPhoneInfo info) {
                if (info != null && info.isExist()) { // 账号存在
                    sendSms(phoneNum, true, false); // 跳登录输入验证码界面
                } else { // 不存在,跳注册界面
                    // 对话框
                    XSBDialog.createDialog(v.getMyContext(), "该手机号尚未注册是否已有其他账号？", "", "其他账号登录", "现在注册").setOnDialogClickListener(new XSBDialog.OnDialogClickListener() {
                        @Override
                        public void onClick(XSBDialog dialog, boolean isRight) {
                            if (isRight) {
                                showPhoneExistDialog(phoneNum);
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            } else {
                                if (dialog.isShowing()) {
                                    v.clearPhoneNumber();
                                    dialog.dismiss();
                                }
                            }
                        }
                    }).show();
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                showErrorMsg(errorCode, errorMessage);
            }
        });
```

### 第三方账号是否已经绑定检查接口(该接口只是判断三方账号是否在通行证中存在,林参设计的该接口跟是否已绑定手机号没有关系)  返回true,代表已存在通行证id,仍然调用loginThird接口,然后请求各自后端登录认证接口,拿到loginBean,根据phoneNumber判断是否需要进入绑定手机号界面;返回false,进入绑定手机号界面,如需绑定手机号,调用registerThirdBindPhone接口()
```java
    /**
     * 第三方账号是否已经绑定检查接口
     * @param auth_type    第三方账户绑定类型
     * @param auth_uid     第三方用户唯一id标识
     * @param auth_token 第三方返回的auth_token
     * @param listener
     * @return
     */
    public static Call checkThird(String auth_type, String auth_uid, String auth_token, final ZbCheckThirdListener listener) {
        return netWork.checkThird(auth_type, auth_uid, auth_token, listener);
    }
```
#### 注意事项:
session失效处理,账号合并之后,未选取的账号会出现session失效的情况,处理

升级到新版通行证后,使用历史账号登录的处理方式   弹出重置密码的对话框

个性化账号,新版本通行证不支持个性化账号注册(浙江新闻5.6版本新用户不支持个性化账号注册,历史版本的个性化账号必须绑定手机号才算登录成功)

退出登录接入方调用自己服务端的接口,sdk不提供退出登录接口

### 版本记录
0.0.3.xsb-SNAPSHOT  增加钉钉登录类型支持
0.0.1.common-SNAPSHOT  在0.0.3.xsb-SNAPSHOT的基础上,支持易盾一键登录

正式版:
以1开头,1.0.0.0开始往上加  对应0.0.1.common-SNAPSHOT 

