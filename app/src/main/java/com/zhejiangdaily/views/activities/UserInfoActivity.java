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
import com.zhejiangdaily.presenters.UmLoginPresenterImpl;
import com.zhejiangdaily.presenters.UserInfoPresenterImpl;
import com.zhejiangdaily.utils.ToastUtil;
import com.zhejiangdaily.views.dialogs.TipDialog;
import com.zjrb.passport.Entity.LoginInfo;
import com.zjrb.passport.Entity.ThirdInfo;
import com.zjrb.passport.constant.ZbConstants;

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

    boolean isBindPhone;

    UserInfoContract.Presenter infoPresenter;
    UmLoginContract.Presenter umPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        infoPresenter = new UserInfoPresenterImpl(this);
        umPresenter = new UmLoginPresenterImpl(this);
        infoPresenter.getUserInfo();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (infoPresenter == null) {
            infoPresenter = new UserInfoPresenterImpl(this);
        }
        infoPresenter.getUserInfo();
    }

    @Override
    public void umLogin(boolean isSuccess, SHARE_MEDIA platform, String uid) {
        if (isSuccess) {
            infoPresenter.bindThird(platform, uid);
        }
    }

    @Override
    public void getUserInfo(boolean isSuccess, LoginInfo info, String errorMsg) {
        if (isSuccess) {
            String phoneNumber = info.getPhoneNumber();
            if (TextUtils.isEmpty(phoneNumber)) {
                tvPhone.setText("未绑定");
                isBindPhone = false;
            } else {
                tvPhone.setText(phoneNumber);
                isBindPhone = true;
            }
            List<ThirdInfo> list = info.getBindList();
            if (list != null && !list.isEmpty()) {
                for (ThirdInfo d : list) {
                    switch (d.getLoginType()) {
                        case ZbConstants.ThirdLogin.QQ:
                            tvQQ.setText("已绑定");
                            break;
                        case ZbConstants.ThirdLogin.SINA:
                            tvSina.setText("已绑定");
                            break;
                        case ZbConstants.ThirdLogin.WECHAT:
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
                case ZbConstants.ThirdLogin.QQ:
                    tvQQ.setText("未绑定");
                    break;
                case ZbConstants.ThirdLogin.SINA:
                    tvSina.setText("未绑定");
                    break;
                case ZbConstants.ThirdLogin.WECHAT:
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
        umPresenter.onUmCallback(requestCode, resultCode, data);
    }

    @Override
    public void onIntentResult(boolean isSuccess, String phone) {
        if (isSuccess && !TextUtils.isEmpty(phone)) {
            tvPhone.setText(phone);
            isBindPhone = true;
        }
    }

    @Override
    public void bindThird(boolean isSuccess, int platform, String errorMsg) {
        if (isSuccess) {
            switch (platform) {
                case ZbConstants.ThirdLogin.QQ:
                    tvQQ.setText("已绑定");
                    break;
                case ZbConstants.ThirdLogin.SINA:
                    tvSina.setText("已绑定");
                    break;
                case ZbConstants.ThirdLogin.WECHAT:
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
                    noticeUnbind(ZbConstants.ThirdLogin.SINA);
                } else {
                    umPresenter.umLogin(SHARE_MEDIA.SINA);
                }
                break;
            case R.id.ll_wechat:
                if ("已绑定".equals(tvWechat.getText().toString())) {
                    noticeUnbind(ZbConstants.ThirdLogin.WECHAT);
                } else {
                    umPresenter.umLogin(SHARE_MEDIA.WEIXIN);
                }
                break;
            case R.id.ll_qq:
                if ("已绑定".equals(tvQQ.getText().toString())) {
                    noticeUnbind(ZbConstants.ThirdLogin.QQ);
                } else {
                    umPresenter.umLogin(SHARE_MEDIA.QQ);
                }
                break;
            case R.id.tv_logout:
                infoPresenter.logout();
                break;
        }
    }


    private void noticeUnbind(final int platform) {
        TipDialog dialog = new TipDialog(this);
        dialog.setTitle("解绑").setMessage("确定解绑吗？").setLeft("取消").setRight("确定");
        dialog.setListener(new TipDialog.Listener() {
            @Override
            public void onLeft() {

            }

            @Override
            public void onRight() {
                if (isBindPhone) {
                    infoPresenter.unbindThird(platform);
                } else {
                    noticePhone();
                }
            }
        });
        dialog.show();
    }


    private void noticePhone() {
        TipDialog dialog = new TipDialog(this);
        dialog.setTitle("未绑定手机号").setMessage("您还未绑定手机号，请先绑定手机号").setLeft("取消").setRight("确定");
        dialog.setListener(new TipDialog.Listener() {
            @Override
            public void onLeft() {

            }

            @Override
            public void onRight() {
                infoPresenter.intentBindPhone();
            }
        });
        dialog.show();
    }
}
