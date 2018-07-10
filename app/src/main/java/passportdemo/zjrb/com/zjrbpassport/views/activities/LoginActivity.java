package passportdemo.zjrb.com.zjrbpassport.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import passportdemo.zjrb.com.zjrbpassport.R;
import passportdemo.zjrb.com.zjrbpassport.contracts.LoginContract;
import passportdemo.zjrb.com.zjrbpassport.contracts.UmLoginContract;
import passportdemo.zjrb.com.zjrbpassport.presenters.LoginPresenterImpl;
import passportdemo.zjrb.com.zjrbpassport.presenters.UmLoginPresenterImpl;
import passportdemo.zjrb.com.zjrbpassport.utils.T;
import passportdemo.zjrb.com.zjrbpassport.views.dialogs.TipDialog;

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
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_quick_login)
    TextView tvQuickLogin;
    @BindView(R.id.tv_third)
    TextView tvThird;
    @BindView(R.id.iv_sina)
    ImageView ivSina;
    @BindView(R.id.iv_wechat)
    ImageView ivWechat;
    @BindView(R.id.iv_qq)
    ImageView ivQq;

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
    public Activity getActivity() {
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
    public void onLoginSuccess() {
        T.show("Login Success");
    }

    @Override
    public void onLoginFail() {
        T.show("Login Fail");
    }

    @Override
    public void onThirdLoginSuccess(SHARE_MEDIA platform, String uid) {
        T.show("Third login success");
        loginPresenter.loginThird(platform, uid);
    }


    @Override
    public void onThirdLoginFail() {
        T.show("Third login Fail");
    }

    @OnClick({R.id.tv_login, R.id.tv_register, R.id.tv_quick_login, R.id.iv_sina, R.id.iv_wechat, R.id.iv_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                loginPresenter.login(etPhone.getText().toString(), etPassword.getText().toString());
                break;
            case R.id.tv_register:
                break;
            case R.id.tv_quick_login:
                break;
            case R.id.iv_sina:
                umPresenter.login(SHARE_MEDIA.SINA);
                break;
            case R.id.iv_wechat:
                umPresenter.login(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.iv_qq:
                umPresenter.login(SHARE_MEDIA.QQ);
                break;
        }
    }

}
