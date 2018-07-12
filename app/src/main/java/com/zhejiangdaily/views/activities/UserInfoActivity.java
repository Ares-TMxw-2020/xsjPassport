package com.zhejiangdaily.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhejiangdaily.MainActivity;
import com.zhejiangdaily.R;
import com.zhejiangdaily.contracts.UmLoginContract;
import com.zhejiangdaily.contracts.UserInfoContract;
import com.zhejiangdaily.presenters.UserInfoPresenterImpl;
import com.zhejiangdaily.utils.ToastUtil;
import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.domain.BindingListEntity;
import com.zjrb.passport.domain.ZbInfoEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Function: UserInfoActivity
 * <p>
 * Author: chen.h
 * Date: 2018/7/5
 */
public class UserInfoActivity extends AppCompatActivity implements UserInfoContract.View, UmLoginContract.View {

    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_wechat)
    TextView tvWechat;
    @BindView(R.id.tv_sina)
    TextView tvSina;
    @BindView(R.id.tv_qq)
    TextView tvQQ;

    UserInfoContract.Presenter infoPresenter;
    UmLoginContract.Presenter umPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        infoPresenter = new UserInfoPresenterImpl(this);
//        umPresenter = new UmLoginPresenterImpl(this);
        infoPresenter.getUserInfo();
    }


    @Override
    public void umLogin(boolean isSuccess, SHARE_MEDIA platform, String uid) {
        if (isSuccess) {
            infoPresenter.bindThird(platform, uid);
        }
    }

    @Override
    public void getUserInfo(boolean isSuccess, ZbInfoEntity info, String errorMsg) {
        if (isSuccess) {
            String phoneNumber = info.getPhone_number();
            if (TextUtils.isEmpty(phoneNumber)) {
                tvPhone.setText("未绑定");
            } else {
                tvPhone.setText(phoneNumber);
            }
            List<BindingListEntity> list = info.getBinding_list();
            if (list != null && !list.isEmpty()) {
                for (BindingListEntity d : list) {
                    switch (d.getAuth_type()) {
                        case ZbConstants.LOGIN_QQ:
                            tvQQ.setText("已绑定");
                            break;
                        case ZbConstants.LOGIN_SINA:
                            tvSina.setText("已绑定");
                            break;
                        case ZbConstants.LOGIN_WECHAT:
                            tvWechat.setText("已绑定");
                            break;
                    }
                }
            } else {
                tvWechat.setText("未绑定");
                tvQQ.setText("未绑定");
                tvSina.setText("未绑定");
            }
        } else {
            ToastUtil.show(errorMsg);
        }
    }

    @Override
    public void unBindThird(boolean isSuccess, int platform, String errorMsg) {
        if (isSuccess) {
            switch (platform) {
                case ZbConstants.LOGIN_QQ:
                    tvQQ.setText("未绑定");
                    break;
                case ZbConstants.LOGIN_SINA:
                    tvSina.setText("未绑定");
                    break;
                case ZbConstants.LOGIN_WECHAT:
                    tvWechat.setText("未绑定");
                    break;
            }
        } else {
            ToastUtil.show(errorMsg);
        }
    }

    @Override
    public void logout(boolean isSuccess, String errorMsg) {
        if (isSuccess) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            ToastUtil.show(errorMsg);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        infoPresenter.onIntentResult(requestCode, resultCode, data);
    }

    @Override
    public void onIntentResult(boolean isSuccess, String phone) {
        if (isSuccess && !TextUtils.isEmpty(phone)) {
            tvPhone.setText(phone);
        } else {
            tvPhone.setText("未绑定");
        }
    }

    @Override
    public void bindThird(boolean isSuccess, int platform, String errorMsg) {
        if (isSuccess) {
            switch (platform) {
                case ZbConstants.LOGIN_QQ:
                    tvQQ.setText("已绑定");
                    break;
                case ZbConstants.LOGIN_SINA:
                    tvSina.setText("已绑定");
                    break;
                case ZbConstants.LOGIN_WECHAT:
                    tvWechat.setText("已绑定");
                    break;
            }
        } else {
            ToastUtil.show(errorMsg);
        }
    }

    @Override
    public Activity getIActivity() {
        return this;
    }

    @OnClick({R.id.ll_phone, R.id.ll_sina, R.id.ll_wechat, R.id.ll_qq, R.id.tv_logout, R.id.tv_modify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_modify:
                infoPresenter.intentModify();
                break;
            case R.id.ll_phone:
                infoPresenter.intentBindPhone();
                break;
            case R.id.ll_sina:
                if ("已绑定".equals(tvSina.getText().toString())) {
                    infoPresenter.unbindThird(ZbConstants.LOGIN_SINA);
                } else {
                    umPresenter.umLogin(SHARE_MEDIA.SINA);
                }
                break;
            case R.id.ll_wechat:
                if ("已绑定".equals(tvWechat.getText().toString())) {
                    infoPresenter.unbindThird(ZbConstants.LOGIN_WECHAT);
                } else {
                    umPresenter.umLogin(SHARE_MEDIA.WEIXIN);
                }
                break;
            case R.id.ll_qq:
                if ("已绑定".equals(tvQQ.getText().toString())) {
                    infoPresenter.unbindThird(ZbConstants.LOGIN_QQ);
                } else {
                    umPresenter.umLogin(SHARE_MEDIA.QQ);
                }
                break;
            case R.id.tv_logout:
                infoPresenter.logout();
                break;
        }
    }
}
