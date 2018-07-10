package passportdemo.zjrb.com.zjrbpassport.contracts;

import passportdemo.zjrb.com.zjrbpassport.presenters.BasePresenter;

/**
 * Function: BindPhoneContract
 * <p>
 * Author: chen.h
 * Date: 2018/7/10
 */
public interface BindPhoneContract {

    interface Presenter extends BasePresenter {

        void checkPhone(String phoneNumber);

        void sendCaptcha(String phoneNumber);

        void bindPhone(String phoneNumber, String captcha);
    }

    interface View extends BaseView {

        void checkPhone(boolean isSuccess,boolean isExist, String errorMsg);

        void sendCaptcha(boolean isSuccess, String errorMsg);

        void bindPhone(boolean isSuccess, String errorMsg);
    }
}
