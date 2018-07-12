package com.zhejiangdaily.contracts;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhejiangdaily.presenters.BasePresenter;

/**
 * Function: LoginContract
 * <p>
 * Author: chen.h
 * Date: 2018/7/9
 */
public interface LoginContract {

    interface Presenter extends BasePresenter {

        void findPassWord();

        void login(String phone, String password);

        void loginThird(SHARE_MEDIA platform, String uid);

        void gotoRegister();
    }

    interface View extends BaseView {
        void onPhoneNotExist();

        void login(boolean isSuccess, String errorMsg);

    }
}
