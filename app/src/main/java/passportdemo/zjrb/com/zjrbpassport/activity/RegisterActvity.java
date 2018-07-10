package passportdemo.zjrb.com.zjrbpassport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.domain.ZbInfoEntity;
import com.zjrb.passport.listener.ZbListener;
import com.zjrb.passport.listener.ZbRegisterListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import passportdemo.zjrb.com.zjrbpassport.R;
import passportdemo.zjrb.com.zjrbpassport.views.activities.LoginActivity;

/**
 * Date: 2018/7/9 上午11:39
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 注册页面
 */
public class RegisterActvity extends AppCompatActivity {

    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_send)
    TextView mTvSend;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_captcha)
    EditText mEtCaptcha;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_register, R.id.tv_login, R.id.tv_send})
    public void onClick(View v) {
        String phoneNum = mEtPhone.getText().toString();
        String passWord = mEtPassword.getText().toString();
        String captcha = mEtCaptcha.getText().toString();
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_register:
                ZbPassport.register(phoneNum, passWord, captcha, new ZbRegisterListener() {
                    @Override
                    public void onSuccess(ZbInfoEntity info) {
                        // TODO: 2018/7/9 Toast封装
                        showToast("注册成功");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        showToast(errorMessage);
                    }
                });
                break;
            case R.id.tv_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_send:
                if (TextUtils.isEmpty(phoneNum)) {
                    showToast("请输入手机号");
                    return;
                } else if (TextUtils.isEmpty(passWord)) {
                    showToast("请输入密码");
                    return;
                } else if (TextUtils.isEmpty(captcha)) {
                    showToast("请输入验证码");
                    return;
                }
                // TODO: 2018/7/9 校验手机号
                ZbPassport.sendRegisterCaptcha(phoneNum, new ZbListener() {
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
        }
    }

    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

}
