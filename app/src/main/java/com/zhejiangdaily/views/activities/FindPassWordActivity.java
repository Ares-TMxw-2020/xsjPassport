package com.zhejiangdaily.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhejiangdaily.R;
import com.zhejiangdaily.contracts.FindPasswordContract;
import com.zhejiangdaily.presenters.FindPassWordPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date: 2018/7/10 下午4:58
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 找回密码界面
 */
public class FindPassWordActivity extends AppCompatActivity implements FindPasswordContract.View {

    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_captcha)
    EditText mEtCaptcha;
    @BindView(R.id.tv_send)
    TextView mTvSend;
    @BindView(R.id.tv_next)
    TextView mTvNext;

    private FindPasswordContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
        presenter = new FindPassWordPresenterImpl(this);
    }

    @Override
    public void showDesc() {

    }

    @Override
    public Activity getIActivity() {
        return this;
    }

    @OnClick({R.id.tv_back, R.id.tv_send, R.id.tv_next})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_send:
                presenter.sendSms(mEtPhone.getText().toString());
                break;
            case R.id.tv_next:
                presenter.doNext(mEtPhone.getText().toString(), mEtCaptcha.getText().toString());
                break;
        }
    }
}
