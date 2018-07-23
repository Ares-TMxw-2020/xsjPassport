# 浙报通行证sdk

## 集成
### compile
工程根目录的build.gradle里面添加仓库

```
allprojects {
    repositories {
        maven { url "http://10.100.47.176:8086/nexus/content/groups/public" }
    }
}

```

项目根目录的build.gradle里面添加依赖

```
dependencies {
	compile 'com.zjrb.passport:latest.release'//其中latest.release指代最新浙报通行证SDK版本号，也可以指定明确的版本号，例如1.0.0
}

```

### 网络权限
```
<manifest ...>
	<uses-permission android:name="android.permission.INTERNET" />
</manifest>
```

### 参数配置
#### Manifest配置

```
<manifest ...>
	<application ...>
		<meta-data
			android:name="ZBP_APP_ID"
			android:value="app_id" />
		<meta-data
			android:name="ZBP_APP_KEY"
			android:value="app_key" />
		<meta-data
			android:name="ZBP_APP_SECRET"
			android:value="app_secret" />
		<meta-data
			android:name="ZBP_APP_ENV"
			android:value="app_env" />
	</application>
</manifest>
```

#### 代码配置

```java
//在Application中
ZbPassport.setZbConfig(new ZbConfig.Builder().setAppId(1)
                                             .setAppKey("appKey")
                                             .setAppSecret("appSecre")
                                             .setDebug(true)
                                             .setEnvType(ZbConstants.ENV_TEST)
                                             .setAppVersion("Your App version")
                                             .setAppUuid("Your App uuid"));
```
**代码设置与Manifest相同参数会覆盖Manifest配置的参数**

## 使用

### 初始化

```java
//在Application中
ZbPassport.init(Context context);
//或者
ZbPassport.init(Context context,ZbConfig.Builder builder);

示例代码:
ZbPassport.init(this, new ZbConfig.Builder().setAppVersion("1.0").setAppUuid("uuid"));

```

### 获取短信验证码接口
获取短信验证码接口使用如下方法,第一个参数smsType代表短信验证码的类型,其中ZbConstants.Sms.REGISTER代表注册短信,ZbConstants.Sms.LOGIN代表登录短信,ZbConstants.Sms.FIND代表找回密码短信,ZbConstants.Sms.BIND代表绑定手机号短信:

```java
ZbPassport.sendCaptcha(@ZbConstants.SmsType int smsType, String phoneNumber, ZbCaptchaSendListener listener)
```

#### 获取注册短信验证码示例代码(注:获取登录短信验证码,找回密码短信验证码及绑定手机号短信验证码使用方式同上,只需更改相关Type)

```java
ZbPassport.sendCaptcha(ZbConstants.Sms.REGISTER, phoneNum, new ZbCaptchaSendListener() {
                    @Override
                    public void onSuccess() {
                        ToastUtil.show("下发注册短信验证码接口 success");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        ToastUtil.show(errorMessage);
                    }
                });
```

### 验证短信验证码接口
验证短信验证码接口使用如下方法,第一个参数smsType代表短信验证码的类型,其中ZbConstants.Sms.REGISTER代表注册短信,ZbConstants.Sms.LOGIN代表登录短信,ZbConstants.Sms.FIND代表找回密码短信,ZbConstants.Sms.BIND代表绑定手机号短信:

```java
ZbPassport.verifyCaptcha(@ZbConstants.SmsType int smsType, String phoneNumber, String captcha, ZbCaptchaVerifyListener listener);
```

### 验证手机是否绑定浙报通行证

```java
ZbPassport.checkBindState(String phoneNumber, ZbCheckPhoneListener listener)
```
示例代码

```java
ZbPassport.checkBindState(phoneNumber, new ZbCheckPhoneListener() {
    @Override
    public void onSuccess(boolean isBind) {
        view.checkPhone(true, isBind, null);
    }

    @Override
    public void onFailure(int errorCode, String errorMessage) {
        view.checkPhone(false, false, errorMessage);
    }
});
```


### 注册

```java
ZbPassport.register(String phoneNumber, String password, String captcha, ZbRegisterListener listener);
```
示例代码：

```java
ZbPassport.register(phone, "this_is_a_test_password", "498598", new ZbRegisterListener() {
    @Override
    public void onSuccess(LoginInfo info) {
        showToast("手机号注册浙报通行证接口 success");
    }

    @Override
    public void onFailure(int errorCode, String errorMessage) {
        showToast(errorMessage);
    }
});
```
### 登录

#### 手机+密码

```java
ZbPassport.login(String phoneNumber, String password, ZbLoginListener listener);
```
#### 手机+验证码

```java
ZbPassport.loginCaptcha(String phoneNumber, String captcha, ZbLoginListener listener);
```

#### 第三方登录

```java
ZbPassport.loginThird(@ZbConstants.ThirdType int thirdType, String thirdUniqueId, ZbLoginListener listener);
```
其中第一个参数为ZbConstants.ThirdLogin.WECHAT，ZbConstants.ThirdLogin.QQ，ZbConstants.ThirdLogin.SINA分别代表微信，qq，微博登录
第二个参数为三方平台的id,其中qq和sina 取openId，微信取unionId，用友盟的话，统一取友盟封装的uid

### 获取通行证详情

```java
ZbPassport.getInfo(ZbGetInfoListener listener);
```
示例代码：

```java
ZbPassport.getInfo(new ZbGetInfoListener() {
    @Override
    public void onSuccess(LoginInfo info) {
        showToast("获取通行证详情接口 success");
    }

    @Override
    public void onFailure(int errorCode, String errorMessage) {
        showToast(errorMessage);
    }
});
```

### 密码相关
#### 找回密码

```java
ZbPassport.findPassword(String phoneNumber, String captcha, String newPassword, ZbFindPasswordListener listener)；
```
示例代码：

```java
ZbPassport.findPassword(phoneNum, sms, password, new ZbFindPasswordListener() {
    @Override
    public void onSuccess() {
        ToastUtil.showTextWithImage(R.mipmap.ic_qq, "找回密码成功,请使用新密码登录");
    }

    @Override
    public void onFailure(int errorCode, String errorMessage) {
        ToastUtil.showTextWithImage(R.mipmap.ic_qq, errorMessage);

    }
});
```

#### 修改密码时，检查原密码是否正确的接口

```java
ZbPassport.checkPassword(String oldPassword, final ZbCaptchaVerifyListener listener);
```
请求的回调接口ZbCaptchaVerifyListener里onSuccess(boolean isValid)通过isValid来判断原密码是否正确,isValid为true,原密码验证正确,否则验证失败
示例代码：

```java
ZbPassport.checkPassword(passWord, new ZbCaptchaVerifyListener() { // 验证旧密码是否正确
            @Override
            public void onSuccess(boolean isValid) {
                if (isValid) {
                    Intent intent = new Intent(view.getIActivity(), ChangeNewPasswordActivity.class);
                    intent.putExtra("oldPassWord", passWord);
                    view.getIActivity().startActivity(intent);
                } else {
                    ToastUtil.showTextWithImage(R.mipmap.ic_qq, "原密码错误");
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                ToastUtil.show(errorMessage);
            }
        });
```


#### 修改密码

```java
ZbPassport.changePassword(String oldPassWord, String newPassWord, final ZbChangePasswordListener listener);
```
示例代码：

```java
ZbPassport.changePassword(oldNum, newNum, new ZbChangePasswordListener() {
    @Override
    public void onSuccess() {
        ToastUtil.showTextWithImage(R.mipmap.ic_qq, "修改密码成功");
    }

    @Override
    public void onFailure(int errorCode, String errorMessage) {
        ToastUtil.showTextWithImage(R.mipmap.ic_qq, errorMessage);
    }
});
```

#### 检查手机号是否绑定浙报通行证

```java
ZbPassport.checkBindState(String phoneNumber, ZbCheckPhoneListener listener);
```

#### 绑定浙报通行证手机号

```java
ZbPassport.bindPhone(String phoneNumber, String captcha, ZbBindPhoneListener listener);
```

#### 绑定第三方账号

```java
ZbPassport.bindThird(@ZbConstants.ThirdType int thirdType, String thirdUnionId, ZbBindThirdListener listener)
```
其中第一个参数为ZbConstants.ThirdLogin.WECHAT，ZbConstants.ThirdLogin.QQ，ZbConstants.ThirdLogin.SINA分别代表微信，qq，微博，
第二个参数为三方平台的id，其中**qq和sina取openId，微信取unionId，用友盟的话，统一取友盟封装的uid**

#### 解绑第三方账号

```java
ZbPassport.unbindThird(@ZbConstants.ThirdType int thirdType, ZbUnBindThirdListener listener);
```
其中第一个参数为ZbConstants.ThirdLogin.WECHAT，ZbConstants.ThirdLogin.QQ，ZbConstants.ThirdLogin.SINA分别代表微信，qq，微博

#### 退出登录接口

```java
ZbPassport.logout(ZbLogoutListener listener);
```

#### 关于取消网络请求
ZbPassport中的每个请求都会返回一个Call,调用当前Call的cancel方法可以取消该网络请求
示例代码,以取消下发注册短信验证码的接口请求为例:

```java
Call call = ZbPassport.sendRegisterCaptcha(String phoneNumber, ZbCaptchaListener listener);
call.cancel();
```



