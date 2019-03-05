package com.zhejiangdaily.presenters;

import android.content.Intent;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhejiangdaily.contracts.UserInfoContract;
import com.zhejiangdaily.views.activities.BindPhoneActivity;
import com.zhejiangdaily.views.activities.ModifyActivity;
import com.zjrb.passport.constant.ZbConstants;

import static android.app.Activity.RESULT_OK;

/**
 * Function: UserInfoPresenter
 * <p>
 * Author: chen.h
 * Date: 2018/7/10
 */
public class UserInfoPresenterImpl implements UserInfoContract.Presenter {

    private final UserInfoContract.View view;

    private final int requestCode_bind = 0x11;
    private final int requestCode_modify = 0x12;

    public UserInfoPresenterImpl(UserInfoContract.View view) {
        this.view = view;
    }

    @Override
    public void getUserInfo() {
        // TODO: 2019/3/5
//        ZbPassport.getInfo(new ZbGetInfoListener() {
//            @Override
//            public void onSuccess(LoginInfo info) {
//                view.getUserInfo(true, info, null);
//            }
//
//            @Override
//            public void onFailure(int errorCode, String errorMessage) {
//                view.getUserInfo(false, null, errorMessage);
//            }
//        });
    }

    @Override
    public void logout() {
        // TODO: 2019/3/5
//        ZbPassport.logout(new ZbLogoutListener() {
//            @Override
//            public void onSuccess() {
//                view.logout(true, null);
//            }
//
//            @Override
//            public void onFailure(int errorCode, String errorMessage) {
//                view.logout(false, errorMessage);
//            }
//        });
    }

    @Override
    public void intentBindPhone() {
        view.getIActivity()
            .startActivityForResult(new Intent(view.getIActivity(), BindPhoneActivity.class), requestCode_bind);
    }

    @Override
    public void intentModify() {
        view.getIActivity()
            .startActivityForResult(new Intent(view.getIActivity(), ModifyActivity.class), requestCode_modify);
    }

    @Override
    public void onIntentResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case requestCode_bind:
                if (resultCode == RESULT_OK) {
                    String phone = data.getStringExtra("phone");
                    view.onIntentResult(true, phone);
                } else {
                    view.onIntentResult(false, null);
                }
                break;
            case requestCode_modify:
                if (resultCode == RESULT_OK) {
                    String phone = data.getStringExtra("phone");
                    view.onIntentResult(true, phone);
                }
                break;
        }
    }

    @Override
    public void bindThird(SHARE_MEDIA platform, String uid) {
        final int type;
        switch (platform) {
            case QQ:
                type = ZbConstants.ThirdLogin.QQ;
                break;
            case SINA:
                type = ZbConstants.ThirdLogin.SINA;
                break;
            case WEIXIN:
                type = ZbConstants.ThirdLogin.WECHAT;
                break;
            default:
                return;
        }
        // TODO: 2019/3/5  
//        ZbPassport.bindThird(type, uid, new ZbBindThirdListener() {
//            @Override
//            public void onSuccess() {
//                view.bindThird(true, type, null);
//            }
//
//            @Override
//            public void onFailure(int errorCode, String errorMessage) {
//                view.bindThird(false, type, errorMessage);
//            }
//        });
    }

    @Override
    public void unbindThird(final int platform) {
        // TODO: 2019/3/5
//        ZbPassport.unbindThird(platform, new ZbUnBindThirdListener() {
//            @Override
//            public void onSuccess() {
//                view.unBindThird(true, platform, null);
//            }
//
//            @Override
//            public void onFailure(int errorCode, String errorMessage) {
//                view.unBindThird(false, platform, errorMessage);
//            }
//        });
    }
}
