package passportdemo.zjrb.com.zjrbpassport.presenters;

import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.domain.ZbInfoEntity;
import com.zjrb.passport.listener.ZbGetInfoListener;
import com.zjrb.passport.listener.ZbListener;
import com.zjrb.passport.listener.ZbLogoutListener;

import passportdemo.zjrb.com.zjrbpassport.contracts.UserInfoContract;

/**
 * Function: UserInfoPresenter
 * <p>
 * Author: chen.h
 * Date: 2018/7/10
 */
public class UserInfoPresenterImpl implements UserInfoContract.Presenter {

    private final UserInfoContract.View view;

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
