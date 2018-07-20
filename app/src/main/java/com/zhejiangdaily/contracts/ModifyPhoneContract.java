package com.zhejiangdaily.contracts;

import com.zhejiangdaily.presenters.BasePresenter;

/**
 * Function: ModifyPhoneContract
 * <p>
 * Author: chen.h
 * Date: 2018/7/12
 */
public interface ModifyPhoneContract {

    interface Presenter extends BasePresenter {

        void checkPhone(String phone);

        void sendCaptcha(String phone);

        void modifyPhone(String phone, String captcha);
    }

    interface View extends BaseView {

        void checkPhone(boolean isSuccess,boolean isBind, String errorMsg);

        void sendCaptcha(boolean isSuccess, String errorMsg);


        void modifyPhone(boolean isSuccess, String phone, String errorMsg);
    }
}
