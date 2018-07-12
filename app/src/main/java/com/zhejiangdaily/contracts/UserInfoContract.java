package com.zhejiangdaily.contracts;

import android.content.Intent;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhejiangdaily.presenters.BasePresenter;
import com.zjrb.passport.domain.ZbInfoEntity;

/**
 * Function: UserInfoContract
 * <p>
 * Author: chen.h
 * Date: 2018/7/10
 */
public interface UserInfoContract {
    interface Presenter extends BasePresenter {

        void getUserInfo();

        void unbindThird(int platform);

        void intentBindPhone();

        void intentModify();

        void onIntentResult(int requestCode, int resultCode, Intent data);

        void bindThird(SHARE_MEDIA platform, String uid);

        void logout();

    }


    interface View extends BaseView {

        void getUserInfo(boolean isSuccess, ZbInfoEntity info, String errorMsg);

        void unBindThird(boolean isSuccess, int platform, String errorMsg);

        void onIntentResult(boolean isSuccess, String phone);

        void bindThird(boolean isSuccess, int platform, String errorMsg);

        void logout(boolean isSuccess, String errorMsg);
    }
}
