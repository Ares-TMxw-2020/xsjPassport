package passportdemo.zjrb.com.zjrbpassport.contracts;

import com.umeng.socialize.bean.SHARE_MEDIA;

import passportdemo.zjrb.com.zjrbpassport.presenters.BasePresenter;

/**
 * Function: UmLoginContract
 * <p>
 * Author: chen.h
 * Date: 2018/7/9
 */
public interface UmLoginContract {

    interface Presenter extends BasePresenter {
        void umLogin(SHARE_MEDIA platform);
    }

    interface View extends BaseView {

        void umLogin(boolean isSuccess, SHARE_MEDIA platform, String uid);
    }
}
