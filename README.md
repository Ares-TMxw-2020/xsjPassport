浙报通行证sdk

# 集成
## compile
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

## 网络权限
```
<manifest ...>
	<uses-permission android:name="android.permission.INTERNET" />
</manifest>
```

## 参数配置
### Manifest配置

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

### 代码配置
```
//在Application中
ZbPassport.setConfig(ZbConfig config);
```


# 使用

## 初始化
```
//在Application中
ZbPassport.init(Context context);
//或者
ZbPassport.init(Context context,ZbConfig config);
```

## 获取短信验证码
### 获取注册短信验证码
```java
ZbPassport.sendRegisterCaptcha(String phoneNumber, ZbCaptchaListener listener);
```
### 获取登录短信验证码
```
ZbPassport.sendLoginCaptcha(String phoneNumber, ZbCaptchaListener listener);
```
### 获取找回密码短信验证码
```
ZbPassport.sendRetrieveCaptcha(String phoneNumber，ZbCaptchaListener listener);
```
### 获取绑定新手机短信验证码
```
ZbPassport.sendBindCaptcha(String phoneNumber，ZbCaptchaListener listener);
```
### 短信接口回调
```
interface ZbCaptchaListener{
	void onSuccess();
	void onFailure(int errorCode,String errorMessage);
}
```
## 验证手机是否注册浙报通行证
```
ZbPassport.checkPhone(String phoneNumber);

interface ZbCheckListener{
	void onSuccess(boolean isExist);
	void onFailure(int errorCode,String errorMessage);
}
```
## 注册
```
ZbPassport.register(String phoneNumber,String password,String smsCaptcha,ZbRegisterListener listener);

interface ZbRegisterListener{
	void onSuccess(ZbInfo info);
	void onFailure(int errorCode,String errorMessage);
}
```

## 登录
### 手机+密码
```
ZbPassport.login(String phoneNumber,String password,ZbLoginListener listener);
```
### 手机+验证码
```
ZbPassport.loginCaptcha(String phoneNumber,String captcha,ZbLoginListener listener);
```
### 第三方登录
```
ZbPassport.loginThird(String thirdUniqueId,ZbLoginListener1 listener);
```
### 登录回调
```
interface ZbLoginListener{
	void onSuccess(ZbInfo info);
	void onFailure(int errorCode,String errorMessage);
}
```
## 获取通行证详情
```
ZbPassport.getInfo(String token , ZbGetInfoListener listener);

interface ZbGetInfoListener{
	void onSuccess(ZbInfo info);
	void onFailure(int errorCode,String errorMessage);
}
```
## 修改相关
### 找回密码
```
ZbPassport.retrievePassword(String phoneNumber,String captcha, ZbRetrieveListener listener);

interface ZbRetrieveListener{
	void onSuccess();
	void onFailure(int errorCode,String errorMessage);
}
```
### 修改密码
```
ZbPassport.resetPassword(String phoneNumber,String password, ZbResetListener listener);

interface ZbResetListener{
	void onSuccess();
	void onFailure(int errorCode,String errorMessage);
}

```
### 绑定或者修改手机
```
ZbPassport.bindPhone(String phoneNumber,String captcha, ZbBindListener listener);

interface ZbBindListener{
	void onSuccess();
	void onFailure(int errorCode,String errorMessage);
}
```
### 解绑第三方账号
```
ZbPassport.unbindThird(String thirdUniqueId,Listener listener);

interface ZbUnbindListener{
	void onSuccess();
	void onFailure(int errorCode,String errorMessage);
}
```



