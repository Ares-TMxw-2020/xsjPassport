package com.zhejiangdaily.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhejiangdaily.R;
import com.zhejiangdaily.contracts.LoginContract;
import com.zhejiangdaily.contracts.UmLoginContract;
import com.zhejiangdaily.presenters.LoginPresenterImpl;
import com.zhejiangdaily.presenters.UmLoginPresenterImpl;
import com.zhejiangdaily.utils.ToastUtil;
import com.zhejiangdaily.views.dialogs.TipDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Function: LoginActivity
 * <p>
 * Author: chen.h
 * Date: 2018/7/5
 */
public class LoginActivity extends AppCompatActivity implements LoginContract.View, UmLoginContract.View {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;

    private LoginContract.Presenter loginPresenter;
    private UmLoginContract.Presenter umPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenterImpl(this);
        umPresenter = new UmLoginPresenterImpl(this);
    }


    @Override
    public Activity getIActivity() {
        return this;
    }

    @Override
    public void onPhoneNotExist() {
        TipDialog tipDialog = new TipDialog(this);
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

                     }
                 });
        tipDialog.show();
    }


    @Override
    public void umLogin(boolean isSuccess, SHARE_MEDIA platform, String uid) {
        if (isSuccess) {
            ToastUtil.show("Third login success");
            loginPresenter.loginThird(platform, uid);
        } else {
            ToastUtil.show("Third login Fail");

        }
    }

    @Override
    public void login(boolean isSuccess, String errorMsg) {
        if (isSuccess) {
            ToastUtil.show("Login Success");
            startActivity(new Intent(this, UserInfoActivity.class));
        } else {
            ToastUtil.show(errorMsg);
        }
    }


    @OnClick({R.id.tv_login, R.id.tv_register, R.id.tv_forget_pwd, R.id.iv_sina, R.id.iv_wechat, R.id.iv_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                loginPresenter.login(etPhone.getText().toString(), etPassword.getText().toString());
                break;
            case R.id.tv_register:
                break;
            case R.id.tv_forget_pwd:
                loginPresenter.findPassWord(); // 进入找回密码界面
                break;
            case R.id.iv_sina:
                umPresenter.umLogin(SHARE_MEDIA.SINA);
                break;
            case R.id.iv_wechat:
                umPresenter.umLogin(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.iv_qq:
                umPresenter.umLogin(SHARE_MEDIA.QQ);
                break;
        }
    }

}
