package passportdemo.zjrb.com.zjrbpassport.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.domain.BindingListEntity;
import com.zjrb.passport.domain.ZbInfoEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import passportdemo.zjrb.com.zjrbpassport.MainActivity;
import passportdemo.zjrb.com.zjrbpassport.R;
import passportdemo.zjrb.com.zjrbpassport.contracts.UserInfoContract;
import passportdemo.zjrb.com.zjrbpassport.presenters.UserInfoPresenterImpl;
import passportdemo.zjrb.com.zjrbpassport.utils.ToastUtil;

/**
 * Function: UserInfoActivity
 * <p>
 * Author: chen.h
 * Date: 2018/7/5
 */
public class UserInfoActivity extends AppCompatActivity implements UserInfoContract.View {

    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.ll_sina)
    LinearLayout llSina;
    @BindView(R.id.ll_wechat)
    LinearLayout llWechat;
    @BindView(R.id.ll_qq)
    LinearLayout llQq;

    UserInfoContract.Presenter infoPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        infoPresenter = new UserInfoPresenterImpl(this);
        infoPresenter.getUserInfo();
    }


    @Override
    public void getUserInfo(boolean isSuccess, ZbInfoEntity info, String errorMsg) {
        if (isSuccess) {
            String phoneNumber = info.getPhone_number();
            if (TextUtils.isEmpty(phoneNumber)) {
                llPhone.setVisibility(View.GONE);
            } else {
                llPhone.setVisibility(View.VISIBLE);
                tvPhone.setText(phoneNumber);
            }
            List<BindingListEntity> list = info.getBinding_list();
            if (list != null && !list.isEmpty()) {
                for (BindingListEntity d : list) {
                    switch (d.getAuth_type()) {
                        case ZbConstants.LOGIN_QQ:
                            llQq.setVisibility(View.VISIBLE);
                            break;
                        case ZbConstants.LOGIN_SINA:
                            llQq.setVisibility(View.VISIBLE);
                            break;
                        case ZbConstants.LOGIN_WECHAT:
                            llQq.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            } else {
                llQq.setVisibility(View.GONE);
                llSina.setVisibility(View.GONE);
                llWechat.setVisibility(View.GONE);
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
                    llQq.setVisibility(View.GONE);
                    break;
                case ZbConstants.LOGIN_SINA:
                    llQq.setVisibility(View.GONE);
                    break;
                case ZbConstants.LOGIN_WECHAT:
                    llQq.setVisibility(View.GONE);
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
    public Activity getIActivity() {
        return this;
    }

    @OnClick({R.id.ll_phone, R.id.ll_sina, R.id.ll_wechat, R.id.ll_qq, R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_phone:
                startActivity(new Intent(this, BindPhoneActivity.class));
                break;
            case R.id.ll_sina:
                infoPresenter.unbindThird(ZbConstants.LOGIN_SINA);
                break;
            case R.id.ll_wechat:
                infoPresenter.unbindThird(ZbConstants.LOGIN_WECHAT);
                break;
            case R.id.ll_qq:
                infoPresenter.unbindThird(ZbConstants.LOGIN_QQ);
                break;
            case R.id.tv_logout:
                infoPresenter.logout();
                break;
        }
    }
}
