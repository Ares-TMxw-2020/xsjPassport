package passportdemo.zjrb.com.zjrbpassport.presenters;

import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.listener.ZbBindListener;
import com.zjrb.passport.listener.ZbListener;

import passportdemo.zjrb.com.zjrbpassport.contracts.ModifyPhoneContract;

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
    public void sendCaptcha(String phone) {
        ZbPassport.sendCaptcha(ZbConstants.SMS_BIND, phone, new ZbListener() {
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
    public void modifyPhone(final String phone, String captcha) {
        ZbPassport.bindPhone(phone, captcha, new ZbBindListener() {
            @Override
            public void onSuccess() {
                view.modifyPhone(true, phone, null);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                view.modifyPhone(false, phone, errorMessage);
            }
        });
    }
}
