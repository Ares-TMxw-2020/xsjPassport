package com.zhejiangdaily.presenters;

import com.zhejiangdaily.contracts.BindPhoneContract;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.listener.ZbBindPhoneListener;
import com.zjrb.passport.listener.ZbCaptchaSendListener;
import com.zjrb.passport.listener.ZbCheckPhoneListener;

/**
 * Function: BindPhonePresenter
 * <p>
 * Author: chen.h
 * Date: 2018/7/10
 */
public class BindPhonePresenterImpl implements BindPhoneContract.Presenter {

    private final BindPhoneContract.View view;

    public BindPhonePresenterImpl(BindPhoneContract.View view) {this.view = view;}

    @Override
    public void checkPhone(String phoneNumber) {
        ZbPassport.checkBindState(phoneNumber, new ZbCheckPhoneListener() {
            @Override
            public void onSuccess(boolean isBind) {
                view.checkPhone(true, isBind, null);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                view.checkPhone(false, false, errorMessage);
            }
        });
    }

    @Override
    public void sendCaptcha(String phoneNumber) {
        ZbPassport.sendCaptcha(ZbConstants.Sms.BIND, phoneNumber, new ZbCaptchaSendListener() {
            @Override
            public void onSuccess() {
                view.sendCaptcha(true, null);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                view.sendCaptcha(false, errorMessage);
            }
        });
    }

    @Override
    public void bindPhone(String phoneNumber, String captcha) {
        ZbPassport.bindPhone(phoneNumber, captcha, new ZbBindPhoneListener() {
            @Override
            public void onSuccess() {
                view.bindPhone(true, null);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                view.bindPhone(false, errorMessage);
            }
        });
    }
}
