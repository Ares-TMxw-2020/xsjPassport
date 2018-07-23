package com.zhejiangdaily.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhejiangdaily.R;
import com.zhejiangdaily.contracts.BindPhoneContract;
import com.zhejiangdaily.presenters.BindPhonePresenterImpl;
import com.zhejiangdaily.utils.ToastUtil;
import com.zhejiangdaily.views.dialogs.TipDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Function: BindPhoneActivity
 * <p>
 * Author: chen.h
 * Date: 2018/7/5
 */
public class BindPhoneActivity extends AppCompatActivity implements BindPhoneContract.View {


    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_captcha)
    EditText etCaptcha;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.tv_bind)
    TextView tvBind;

    int status = 0;

    BindPhoneContract.Presenter bindPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);
        bindPresenter = new BindPhonePresenterImpl(this);
    }

    private void switchStatue() {
        switch (status) {
            case 1:
                etPhone.setVisibility(View.GONE);
                etCaptcha.setVisibility(View.VISIBLE);
                tvSend.setVisibility(View.VISIBLE);
                tvBind.setText("绑 定");
                break;
            default:
                etPhone.setVisibility(View.VISIBLE);
                etCaptcha.setVisibility(View.GONE);
                tvSend.setVisibility(View.GONE);
                tvBind.setText("下一步");
                break;
        }
    }

    String phoneNumber;

    @OnClick({R.id.tv_back, R.id.tv_send, R.id.tv_bind})
    public void onViewClicked(View view) {
        phoneNumber = etPhone.getText().toString();
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_send:
                bindPresenter.sendCaptcha(phoneNumber);
                break;
            case R.id.tv_bind:
                if (status == 1) {
                    bindPresenter.bindPhone(phoneNumber, etCaptcha.getText().toString());
                } else {
                    bindPresenter.checkPhone(phoneNumber);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void checkPhone(boolean isSuccess, boolean isExist, String errorMsg) {
        if (isSuccess) {
            if (isExist) {
                //号码存在，且不支持合并
                TipDialog tipDialog = new TipDialog(this);
                tipDialog.setTitle("提示")
                         .setMessage("此手机号已经注册账号，请直接登录")
                         .setLeft("取消")
                         .setRight("登录")
                         .setListener(new TipDialog.Listener() {
                             @Override
                             public void onLeft() {

                             }

                             @Override
                             public void onRight() {
                                 ToastUtil.show("当前已经登录");
                             }
                         });
                tipDialog.show();
                status = 0;
            } else {
                status = 1;
            }
            switchStatue();
        } else {
            ToastUtil.show(errorMsg);
        }
    }


    @Override
    public void sendCaptcha(boolean isSuccess, String errorMsg) {
        if (isSuccess) {
            ToastUtil.show("验证码发送成功");
        } else {
            ToastUtil.show(errorMsg);
        }
    }

    @Override
    public void bindPhone(boolean isSuccess, String errorMsg) {
        if (isSuccess) {
            ToastUtil.show("绑定成功");
            Intent intent = new Intent();
            intent.putExtra("phone", phoneNumber);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            ToastUtil.show(errorMsg);
        }
    }

    @Override
    public Activity getIActivity() {
        return this;
    }
}
