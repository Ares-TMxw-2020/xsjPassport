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
import com.zhejiangdaily.contracts.FindNewPassWordContract;
import com.zhejiangdaily.presenters.FindNewPasswordPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Date: 2018/7/10 下午4:58
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 输入新密码界面,找回密码及修改
 */
public class FindNewPassWordActivity extends AppCompatActivity implements FindNewPassWordContract.View {

    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_finish)
    TextView mTvFinish;
    @BindView(R.id.et_new_password)
    EditText mEtNewPassword;
    FindNewPassWordContract.Presenter presenter;
    String phoneNum;
    String sms;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        ButterKnife.bind(this);
        presenter = new FindNewPasswordPresenterImpl(this);
        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("phoneNum");
        sms = intent.getStringExtra("sms");
    }

    @OnClick({R.id.tv_back, R.id.tv_finish})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_finish:
                presenter.sendNewPassWord(phoneNum, sms, mEtNewPassword.getText().toString());
                break;
        }
    }

    @Override
    public Activity getIActivity() {
        return this;
    }
}
