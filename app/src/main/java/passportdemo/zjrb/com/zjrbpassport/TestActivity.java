package passportdemo.zjrb.com.zjrbpassport;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.listener.ZbListener;

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

    String phone = "13758284975";

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
                ZbPassport.sendRegisterCaptcha(phone, new ZbListener() {
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
                ZbPassport.sendRegisterCaptcha(phone, new ZbListener() {
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
                break;
            case R.id.login:
                break;
            case R.id.loginCaptcha:
                break;
            case R.id.getInfo:
                break;
            case R.id.bindPhone:
                break;
            case R.id.changePassword:
                break;
            case R.id.resetPassword:
                break;
            case R.id.checkBindState:
                break;
            case R.id.logout:
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
