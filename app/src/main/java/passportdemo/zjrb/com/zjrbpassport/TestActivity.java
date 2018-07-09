package passportdemo.zjrb.com.zjrbpassport;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.domain.PhoneNumEntity;
import com.zjrb.passport.domain.ZbInfoEntity;
import com.zjrb.passport.listener.ZbBindListener;
import com.zjrb.passport.listener.ZbChangePasswordListener;
import com.zjrb.passport.listener.ZbCheckListener;
import com.zjrb.passport.listener.ZbGetInfoListener;
import com.zjrb.passport.listener.ZbListener;
import com.zjrb.passport.listener.ZbLoginListener;
import com.zjrb.passport.listener.ZbLogoutListener;
import com.zjrb.passport.listener.ZbRegisterListener;
import com.zjrb.passport.listener.ZbResetPasswordListener;

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

    String phone = "18519123764";
    String phone2 = "18516696580";
    String password = "this_is_a_test_password";

    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.sendRegisterCaptcha, R.id.sendLoginCaptcha, R.id.sendRetrieveCaptcha, R.id.sendBindCaptcha, R.id.register, R.id.login, R.id.loginCaptcha, R.id.getInfo, R.id.bindPhone, R.id.changePassword, R.id.resetPassword, R.id.checkBindState, R.id.logout, R.id.loginThird, R.id.bindThird, R.id.unbindThird})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sendRegisterCaptcha:
                ZbPassport.sendRegisterCaptcha(phone, new ZbListener() {
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
                ZbPassport.sendLoginCaptcha(phone, new ZbListener() {
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
                ZbPassport.sendRetrieveCaptcha(phone, new ZbListener() {
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
                ZbPassport.sendBindCaptcha(phone, new ZbListener() {
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
                ZbPassport.register(phone, "this_is_a_test_password", "629551", new ZbRegisterListener() {
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
                ZbPassport.bindPhone(phone, "", new ZbBindListener() {
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
                ZbPassport.resetPassword(phone, "", "", new ZbResetPasswordListener() {
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
                break;
            case R.id.bindThird:
                break;
            case R.id.unbindThird:
                break;
        }
    }
}
