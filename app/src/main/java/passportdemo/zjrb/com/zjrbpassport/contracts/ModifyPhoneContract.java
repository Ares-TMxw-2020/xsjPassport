package passportdemo.zjrb.com.zjrbpassport.contracts;

import passportdemo.zjrb.com.zjrbpassport.presenters.BasePresenter;

/**
 * Function: ModifyPhoneContract
 * <p>
 * Author: chen.h
 * Date: 2018/7/12
 */
public interface ModifyPhoneContract {

    interface Presenter extends BasePresenter {

        void sendCaptcha(String phone);

        void modifyPhone(String phone, String captcha);
    }

    interface View extends BaseView {

        void sendCaptcha(boolean isSuccess, String errorMsg);


        void modifyPhone(boolean isSuccess, String phone, String errorMsg);
    }
}
