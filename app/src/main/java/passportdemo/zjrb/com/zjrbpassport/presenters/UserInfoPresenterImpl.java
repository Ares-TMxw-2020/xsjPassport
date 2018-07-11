package passportdemo.zjrb.com.zjrbpassport.presenters;

import android.content.Intent;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.domain.ZbInfoEntity;
import com.zjrb.passport.listener.ZbGetInfoListener;
import com.zjrb.passport.listener.ZbListener;
import com.zjrb.passport.listener.ZbLogoutListener;

import passportdemo.zjrb.com.zjrbpassport.contracts.UserInfoContract;
import passportdemo.zjrb.com.zjrbpassport.views.activities.BindPhoneActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Function: UserInfoPresenter
 * <p>
 * Author: chen.h
 * Date: 2018/7/10
 */
public class UserInfoPresenterImpl implements UserInfoContract.Presenter {

    private final UserInfoContract.View view;

    private int requestCode = 0x11;

    public UserInfoPresenterImpl(UserInfoContract.View view) {
        this.view = view;
    }

    @Override
    public void getUserInfo() {
        ZbPassport.getInfo(new ZbGetInfoListener() {
            @Override
            public void onSuccess(ZbInfoEntity info) {
                view.getUserInfo(true, info, null);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                view.getUserInfo(false, null, errorMessage);
            }
        });
    }

    @Override
    public void logout() {
        ZbPassport.logout(new ZbLogoutListener() {
            @Override
            public void onSuccess() {
                view.logout(true, null);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                view.logout(false, errorMessage);
            }
        });
    }

    @Override
    public void intentBindPhone() {
        view.getIActivity()
            .startActivityForResult(new Intent(view.getIActivity(), BindPhoneActivity.class), requestCode);
    }

    @Override
    public void bindPhone(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.requestCode && resultCode == RESULT_OK) {
            String phone = data.getStringExtra("phone");
            view.bindPhone(true, phone);
        } else {
            view.bindPhone(false, null);
        }
    }

    @Override
    public void bindThird(SHARE_MEDIA platform, String uid) {
        final int type;
        switch (platform) {
            case QQ:
                type = ZbConstants.LOGIN_QQ;
                break;
            case SINA:
                type = ZbConstants.LOGIN_SINA;
                break;
            case WEIXIN:
                type = ZbConstants.LOGIN_WECHAT;
                break;
            default:
                return;
        }
        ZbPassport.bindThird(type, uid, new ZbListener() {
            @Override
            public void onSuccess() {
                view.bindThird(true, type, null);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                view.bindThird(false, type, errorMessage);
            }
        });
    }

    @Override
    public void unbindThird(final int platform) {
        ZbPassport.unbindThird(platform, new ZbListener() {
            @Override
            public void onSuccess() {
                view.unBindThird(true, platform, null);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                view.unBindThird(false, platform, errorMessage);
            }
        });
    }
}
