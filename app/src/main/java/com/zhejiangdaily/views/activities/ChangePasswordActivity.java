package com.zhejiangdaily.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhejiangdaily.R;
import com.zhejiangdaily.contracts.ChangePasswordContract;
import com.zhejiangdaily.presenters.ChangePassWordPresenterImpl;
import com.zhejiangdaily.utils.ZbUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Date: 2018/7/10 下午4:59
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 更改密码界面
 */
public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordContract.View {

    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_next)
    TextView mTvNext;
    ChangePasswordContract.Presenter presenter;
    @BindView(R.id.et_old_password)
    EditText mEtOldPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_password);
        ButterKnife.bind(this);
        ZbUtil.setEditTextInhibitInputSpace(mEtOldPassword);
        presenter = new ChangePassWordPresenterImpl(this);
    }

    @OnClick({R.id.tv_back, R.id.tv_next})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_next:
                presenter.doNext(mEtOldPassword.getText().toString());
                break;
        }
    }

    @Override
    public Activity getIActivity() {
        return this;
    }
}
