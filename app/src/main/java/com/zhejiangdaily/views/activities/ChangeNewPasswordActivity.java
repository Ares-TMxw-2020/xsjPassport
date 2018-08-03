package com.zhejiangdaily.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhejiangdaily.R;
import com.zhejiangdaily.contracts.ChangeNewPassWordContract;
import com.zhejiangdaily.presenters.ChangeNewPasswordPresenterImpl;
import com.zhejiangdaily.utils.ZbUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date: 2018/7/12 下午6:57
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 修改密码,输入新密码页面
 */
public class ChangeNewPasswordActivity extends AppCompatActivity implements ChangeNewPassWordContract.View {

    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.et_new_password)
    EditText mEtNewPassword;
    @BindView(R.id.tv_finish)
    TextView mTvFinish;
    ChangeNewPassWordContract.Presenter presenter;
    String oldPassWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        ButterKnife.bind(this);
        ZbUtil.setEditTextInhibitInputSpace(mEtNewPassword);
        presenter = new ChangeNewPasswordPresenterImpl(this);
        Intent intent = getIntent();
        oldPassWord = intent.getStringExtra("oldPassWord");
    }

    @Override
    public Activity getIActivity() {
        return this;
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
                presenter.changePassWord(oldPassWord, mEtNewPassword.getText().toString());
                break;
        }
    }
}
