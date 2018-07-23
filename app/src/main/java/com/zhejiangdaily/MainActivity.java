package com.zhejiangdaily;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhejiangdaily.utils.ToastUtil;
import com.zhejiangdaily.utils.ZbUtil;
import com.zhejiangdaily.views.activities.LoginActivity;
import com.zhejiangdaily.views.activities.RegisterActvity;
import com.zhejiangdaily.views.activities.UserInfoActivity;
import com.zhejiangdaily.views.dialogs.TipDialog;
import com.zjrb.passport.Entity.LoginInfo;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.listener.ZbCaptchaSendListener;
import com.zjrb.passport.listener.ZbCheckPhoneListener;
import com.zjrb.passport.listener.ZbLoginListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_captcha)
    EditText mEtCaptcha;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_password_login)
    TextView mTvPasswordLogin;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.tv_send)
    TextView mTvSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_login, R.id.tv_password_login, R.id.tv_register, R.id.tv_send})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_login: // 手机号验证码登录
                if (ZbUtil.isMobileNum(mEtPhone.getText().toString())) {
                    checkBind(mEtPhone.getText().toString(), mEtCaptcha.getText().toString()); // 先检查手机号是否已绑定浙报通行证,已绑定的直接登录,未绑定的提示注册
                } else {
                    if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
                        ToastUtil.show("请输入手机号");
                    } else {
                        ToastUtil.show("非手机号格式");
                    }
                }
                break;
            case R.id.tv_password_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_register:
                Intent registerIntent = new Intent(this, RegisterActvity.class);
                startActivity(registerIntent);
                break;
            case R.id.tv_send:
                if (ZbUtil.isMobileNum(mEtPhone.getText().toString())) {
                    ZbPassport.sendCaptcha(ZbConstants.Sms.LOGIN, mEtPhone.getText().toString(), new ZbCaptchaSendListener() {
                        @Override
                        public void onSuccess() {
                            ToastUtil.show("下发登录短信验证码接口 success");
                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage) {
                            ToastUtil.show(errorMessage);
                        }
                    });
                } else {
                    if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
                        ToastUtil.show("请输入手机号");
                    } else {
                        ToastUtil.show("非手机号格式");
                    }
                }
                break;
        }
    }

    private void checkBind(final String phone, final String password) {
        ZbPassport.checkBindState(phone, new ZbCheckPhoneListener() {
            @Override
            public void onSuccess(boolean isBind) {
                if (isBind) {
                    doLogin(phone, password);
                } else {
                    TipDialog tipDialog = new TipDialog(MainActivity.this);
                    tipDialog.setTitle("提示")
                            .setMessage("此手机号尚未注册，请先注册")
                            .setLeft("取消")
                            .setRight("注册")
                            .setListener(new TipDialog.Listener() {
                                @Override
                                public void onLeft() {

                                }

                                @Override
                                public void onRight() {
                                    startActivity(new Intent(MainActivity.this, RegisterActvity.class));
                                }
                            });
                    tipDialog.show();
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                doLogin(phone, password); // 绑定态查询失败,尝试登录
            }
        });
    }

    /**
     * 手机号验证码登录
     * @param phone
     * @param password
     */
    private void doLogin(String phone, String password) {
        ZbPassport.loginCaptcha(phone, password, new ZbLoginListener() {

            @Override
            public void onSuccess(LoginInfo loginInfo) {
                ToastUtil.show("Login Success");
                startActivity(new Intent(MainActivity.this, UserInfoActivity.class));
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                ToastUtil.show(errorMessage);
            }
        });
    }

}
