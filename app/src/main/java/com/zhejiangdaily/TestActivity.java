package com.zhejiangdaily;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.domain.PhoneNumEntity;
import com.zjrb.passport.domain.ZbInfoEntity;
import com.zjrb.passport.listener.ZbBindPhoneListener;
import com.zjrb.passport.listener.ZbBindThirdListener;
import com.zjrb.passport.listener.ZbCaptchaSendListener;
import com.zjrb.passport.listener.ZbChangePasswordListener;
import com.zjrb.passport.listener.ZbCheckListener;
import com.zjrb.passport.listener.ZbGetInfoListener;
import com.zjrb.passport.listener.ZbLoginListener;
import com.zjrb.passport.listener.ZbLogoutListener;
import com.zjrb.passport.listener.ZbRegisterListener;
import com.zjrb.passport.listener.ZbResetPasswordListener;
import com.zjrb.passport.listener.ZbUnBindThirdListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Function: TestActivity
 * <p>
 * Author: chen.h
 * Date: 2018/7/5
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    //    String phone = "13758284975";
    String phone = "18519123764";
    String phone2 = "18516696580";
    /**
     * 第三方，qq和sina 取openId，微信取unionId，用友盟的话，统一取友盟封装的uid
     */
    String qqId = "C8110EC2F1A7AE94B698259B259712C6";
    String sinaId = "2715718183";
    String wechatId = "oPBMXs9w83GSNOjYQ3E3tJMp7Xmk";
    String password = "this_is_a_test_password";

    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.sendRegisterCaptcha, R.id.sendLoginCaptcha, R.id.sendRetrieveCaptcha, R.id.sendBindCaptcha, R.id.register, R.id.login, R.id.loginCaptcha, R.id.getInfo, R.id.bindPhone, R.id.changePassword, R.id.resetPassword, R.id.checkBindState, R.id.logout, R.id.loginThird, R.id.bindThird, R.id.unbindThird})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sendRegisterCaptcha:
                ZbPassport.sendCaptcha(ZbConstants.SMS_REGISTER, phone, new ZbCaptchaSendListener() {
                    @Override
                    public void onSuccess() {
                        showToast("下发注册短信验证码接口 success");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }
                });
                break;
            case R.id.sendLoginCaptcha:
                ZbPassport.sendCaptcha(ZbConstants.SMS_LOGIN, phone, new ZbCaptchaSendListener() {
                    @Override
                    public void onSuccess() {
                        showToast("下发登录短信验证码接口 success");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }
                });
                break;
            case R.id.sendRetrieveCaptcha:
                ZbPassport.sendCaptcha(ZbConstants.SMS_RETRIEVE, phone, new ZbCaptchaSendListener() {
                    @Override
                    public void onSuccess() {
                        showToast("下发绑定找回密码短信验证码接口 success");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }
                });
                break;
            case R.id.sendBindCaptcha:
                ZbPassport.sendCaptcha(ZbConstants.SMS_BIND, phone, new ZbCaptchaSendListener() {
                    @Override
                    public void onSuccess() {
                        showToast("下发绑定新手机号短信验证码接口 success");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }
                });
                break;
            case R.id.register:
                ZbPassport.register(phone, "this_is_a_test_password", "498598", new ZbRegisterListener() {
                    @Override
                    public void onSuccess(ZbInfoEntity info) {
                        showToast("手机号注册浙报通行证接口 success");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }
                });
                break;
            case R.id.login:
                ZbPassport.login(phone, password, new ZbLoginListener() {
                    @Override
                    public void onSuccess(ZbInfoEntity info) {
                        showToast("手机号密码登录浙报通行证接口 success");

                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }
                });
                break;
            case R.id.loginCaptcha:
                ZbPassport.loginCaptcha(phone, "979755", new ZbLoginListener() {
                    @Override
                    public void onSuccess(ZbInfoEntity info) {
                        showToast("手机号与验证码登录浙报通行证接口 success");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }
                });
                break;
            case R.id.getInfo:
                ZbPassport.getInfo(new ZbGetInfoListener() {
                    @Override
                    public void onSuccess(ZbInfoEntity info) {
                        showToast("获取通行证详情接口 success");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }
                });
                break;
            case R.id.bindPhone:
                ZbPassport.bindPhone(phone, "", new ZbBindPhoneListener() {
                    @Override
                    public void onSuccess() {
                        showToast("绑定浙报通行证手机号接口 success");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }
                });
                break;
            case R.id.changePassword:
                ZbPassport.changePassword(password, "", new ZbChangePasswordListener() {
                    @Override
                    public void onSuccess() {
                        showToast("更改通行证密码接口 success");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }
                });
                break;
            case R.id.resetPassword:
                ZbPassport.findPassword(phone, "", "", new ZbResetPasswordListener() {
                    @Override
                    public void onSuccess() {
                        showToast("重置通行证密码接口 success");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }
                });
                break;
            case R.id.checkBindState:
                ZbPassport.checkBindState(phone, new ZbCheckListener() {
                    @Override
                    public void onSuccess(PhoneNumEntity entity) {
                        showToast("检查手机号是否绑定浙报通行证 success");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }
                });
                break;
            case R.id.logout:
                ZbPassport.logout(new ZbLogoutListener() {
                    @Override
                    public void onSuccess() {
                        showToast("退出登录接口 success");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }
                });
                break;
            case R.id.loginThird:
                ZbPassport.loginThird(ZbConstants.LOGIN_QQ, qqId, new ZbLoginListener() {
                    @Override
                    public void onSuccess(ZbInfoEntity info) {
                        showToast("社交平台账号登录/注册接口 success");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }
                });
                break;
            case R.id.bindThird:
                ZbPassport.bindThird(ZbConstants.LOGIN_SINA, sinaId, new ZbBindThirdListener() {
                    @Override
                    public void onSuccess() {
                        showToast("浙报通行证绑定新的社交平台账号接口 success");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }
                });
                break;
            case R.id.unbindThird:
                ZbPassport.unbindThird(ZbConstants.LOGIN_SINA, new ZbUnBindThirdListener() {
                    @Override
                    public void onSuccess() {
                        showToast("浙报通行证解绑社交账号接口 success");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }
                });
                break;
        }
    }
}
