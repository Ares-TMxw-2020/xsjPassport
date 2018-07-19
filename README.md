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
ZbPassport.setConfig(ZbConfig config);
```

## 使用

### 初始化

```java
//在Application中
ZbPassport.init(Context context);
//或者
ZbPassport.init(Context context,ZbConfigBuilder builder);

示例代码:
ZbPassport.init(this, new ZbConfigBuilder().setAppVersion("1.0").setAppUuid("uuid"));

```

### 获取短信验证码接口
获取短信验证码接口使用如下方法,第一个参数smsType代表短信验证码的类型,其中ZbConstants.SMS_REGISTER代表注册短信,ZbConstants.SMS_LOGIN代表登录短信,ZbConstants.SMS_FIND代表找回密码短信,ZbConstants.SMS_BIND代表绑定手机号短信:
```java
public static Call sendCaptcha(@ZbConstants.SmsType int smsType, String phoneNumber, ZbCaptchaSendListener listener)
```
#### 获取注册短信验证码示例代码

```java
  ZbPassport.sendCaptcha(ZbConstants.SMS_REGISTER, phoneNum, new ZbCaptchaSendListener() {
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

#### 获取登录短信验证码,找回密码短信验证码及绑定手机号短信验证码使用方式同上,只需更改相关Type


### 验证手机是否绑定浙报通行证

```java
ZbPassport.checkBindState(String token, String phoneNumber, ZbCheckListener listener);

interface ZbCheckListener{
	void onSuccess(boolean isExist);
	void onFailure(int errorCode,String errorMessage);
}
```

### 注册

```java
ZbPassport.register(String phoneNumber,String password,String captcha,ZbRegisterListener listener);

interface ZbRegisterListener{
	void onSuccess(ZbInfo info);
	void onFailure(int errorCode,String errorMessage);
}
```

### 登录

#### 手机+密码

```java
ZbPassport.login(String phoneNumber,String password,ZbLoginListener listener);
```
#### 手机+验证码

```java
ZbPassport.loginCaptcha(String phoneNumber,String captcha,ZbLoginListener listener);
```

#### 第三方登录

```java
ZbPassport.loginThird(String thirdUniqueId,ZbLoginListener1 listener);
```

#### 登录回调

```java
interface ZbLoginListener{
	void onSuccess(ZbInfo info);
	void onFailure(int errorCode,String errorMessage);
}
```

### 获取通行证详情

```java
ZbPassport.getInfo(String token , ZbGetInfoListener listener);

interface ZbGetInfoListener{
	void onSuccess(ZbInfo info);
	void onFailure(int errorCode,String errorMessage);
}
```

### 修改相关
#### 找回密码

```java
ZbPassport.changePassword(String phoneNumber,String captcha, ZbRetrieveListener listener);

interface ZbRetrieveListener{
	void onSuccess();
	void onFailure(int errorCode,String errorMessage);
}
```

#### 修改密码

```java
ZbPassport.resetPassword(String phoneNumber,String password, ZbResetListener listener);

interface ZbResetListener{
	void onSuccess();
	void onFailure(int errorCode,String errorMessage);
}

```

#### 绑定或者修改手机

```java
ZbPassport.bindPhone(String phoneNumber,String captcha, ZbBindListener listener);

interface ZbBindListener{
	void onSuccess();
	void onFailure(int errorCode,String errorMessage);
}
```

#### 解绑第三方账号

```java
ZbPassport.unbindThird(String thirdUniqueId,ZbUnbindListener listener);

interface ZbUnbindListener{
	void onSuccess();
	void onFailure(int errorCode,String errorMessage);
}
```

#### 关于取消网络请求
ZbPassport中的每个请求都会返回一个Call,调用当前Call的cancel方法可以取消该网络请求
示例代码:
```java
// 取消下发注册短信验证码的接口请求:
Call call = ZbPassport.sendRegisterCaptcha(String phoneNumber, ZbCaptchaListener listener);
call.cancel();
```



