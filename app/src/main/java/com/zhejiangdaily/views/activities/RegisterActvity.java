package com.zhejiangdaily.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhejiangdaily.R;
import com.zhejiangdaily.utils.ToastUtil;
import com.zhejiangdaily.utils.ZbUtil;
import com.zhejiangdaily.views.dialogs.ZBDialog;
import com.zjrb.passport.Entity.LoginInfo;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.constant.ErrorCode;
import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.listener.ZbCaptchaSendListener;
import com.zjrb.passport.listener.ZbCheckPhoneListener;
import com.zjrb.passport.listener.ZbRegisterListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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
        ZbUtil.setEditTextInhibitInputSpace(mEtPassword);
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
                if (TextUtils.isEmpty(captcha)) {
                    ToastUtil.show("请输入验证码");
                    return;
                } else if (TextUtils.isEmpty(phoneNum)) {
                    ToastUtil.show("请输入手机号");
                    return;
                } else if (TextUtils.isEmpty(passWord)) {
                    ToastUtil.show("请输入密码");
                    return;
                } else if (passWord.length() < 6) {
                    ToastUtil.show("密码长度小于6,请输入6到15位密码!");
                    return;
                }

                ZbPassport.register(phoneNum, passWord, captcha, new ZbRegisterListener() {
                    @Override
                    public void onSuccess(LoginInfo info) {
                        // todo屏幕中间的Toast
                        ToastUtil.showTextWithImage(R.mipmap.ic_qq, "注册成功");
                        Intent intent = new Intent(RegisterActvity.this, UserInfoActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        if (errorCode == ErrorCode.ERROR_PHONE_REGISTERED) {
                            final ZBDialog dialog = new ZBDialog(RegisterActvity.this);
                            dialog.setBuilder(new ZBDialog.Builder().setTitle("提示")
                                    .setMessage("此手机号已经存在,可直接登录")
                                    .setLeftText("取消")
                                    .setRightText("登录")
                                    .setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (v.getId() == R.id.btn_right) {
                                                Intent intent = new Intent(
                                                        RegisterActvity.this,
                                                        LoginActivity.class);
                                                startActivity(intent);
                                            }
                                            if (v.getId() == R.id.btn_left) {
                                                if (dialog.isShowing()) {
                                                    dialog.dismiss();
                                                }
                                            }
                                        }
                                    }));
                            dialog.show();
                        } else {
                            ToastUtil.show(errorMessage);
                        }
                    }
                });
                break;
            case R.id.tv_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_send:
                // 校验手机号
                if (ZbUtil.isMobileNum(phoneNum)) {
                    checkBind(phoneNum); // 发送验证码时校验是否已经注册浙报通行证
                } else {
                    if (TextUtils.isEmpty(phoneNum)) {
                        ToastUtil.show("请输入手机号");
                    } else {
                        ToastUtil.show("非手机号格式");
                    }
                }
                break;
        }
    }

    private void checkBind(final String phone) {
        ZbPassport.checkBindState(phone, new ZbCheckPhoneListener() {
            @Override
            public void onSuccess(boolean isBind) {
                if (isBind) {
                    final ZBDialog dialog = new ZBDialog(RegisterActvity.this);
                    dialog.setBuilder(new ZBDialog.Builder().setTitle("提示")
                            .setMessage("此手机号已经存在,可直接登录")
                            .setLeftText("取消")
                            .setRightText("登录")
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (v.getId() == R.id.btn_right) {
                                        Intent intent = new Intent(
                                                RegisterActvity.this,
                                                LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    if (v.getId() == R.id.btn_left) {
                                        if (dialog.isShowing()) {
                                            dialog.dismiss();
                                        }
                                    }
                                }
                            }));
                    dialog.show();
                } else { // 未绑定过的手机号发送验证码
                    ZbPassport.sendCaptcha(ZbConstants.Sms.REGISTER, phone, new ZbCaptchaSendListener() {
                        @Override
                        public void onSuccess() {
                            ToastUtil.show("下发注册短信验证码接口 success");
                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage) {
                            ToastUtil.show(errorMessage);
                        }
                    });
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                ToastUtil.show(errorMessage);
            }
        });

    }
}
