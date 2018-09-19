package com.zhejiangdaily.presenters;

import android.support.annotation.Nullable;

import com.zhejiangdaily.contracts.ModifyPhoneContract;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.listener.ZbBindPhoneListener;
import com.zjrb.passport.listener.ZbCaptchaSendListener;
import com.zjrb.passport.listener.ZbCheckPhoneListener;

import org.json.JSONObject;

/**
 * Function: ModifyPhonePresenterImpl
 * <p>
 * Author: chen.h
 * Date: 2018/7/12
 */
public class ModifyPhonePresenterImpl implements ModifyPhoneContract.Presenter {

    private final ModifyPhoneContract.View view;

    public ModifyPhonePresenterImpl(ModifyPhoneContract.View view) {this.view = view;}


    @Override
    public void checkPhone(String phone) {
        ZbPassport.checkBindState(phone, new ZbCheckPhoneListener() {
            @Override
            public void onSuccess(boolean isBind, @Nullable JSONObject passData) {
                view.checkPhone(true, isBind, "该手机已经注册浙报通行证");
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                view.checkPhone(false, false, errorMessage);
            }
        });
    }

    @Override
    public void sendCaptcha(String phone) {
        ZbPassport.sendCaptcha(ZbConstants.Sms.BIND, phone, new ZbCaptchaSendListener() {
            @Override
            public void onSuccess(@Nullable JSONObject passData) {
                view.sendCaptcha(true, null);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                view.sendCaptcha(false, errorMessage);
            }
        });
    }

    @Override
    public void modifyPhone(final String phone, String captcha) {
        ZbPassport.bindPhone(phone, captcha, new ZbBindPhoneListener() {
            @Override
            public void onSuccess(@Nullable JSONObject passData) {
                view.modifyPhone(true, phone, null);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                view.modifyPhone(false, phone, errorMessage);
            }
        });
    }
}
