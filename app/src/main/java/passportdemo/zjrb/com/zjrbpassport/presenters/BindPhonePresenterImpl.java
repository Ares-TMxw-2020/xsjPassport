package passportdemo.zjrb.com.zjrbpassport.presenters;

import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.domain.PhoneNumEntity;
import com.zjrb.passport.listener.ZbCheckListener;

import passportdemo.zjrb.com.zjrbpassport.contracts.BindPhoneContract;

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
        ZbPassport.checkBindState(phoneNumber, new ZbCheckListener() {
            @Override
            public void onSuccess(PhoneNumEntity entity) {
                view.checkPhone(true, entity.isPhone_number_taken(), null);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                view.checkPhone(false, false, errorMessage);
            }
        });
    }

    @Override
    public void sendCaptcha(String phoneNumber) {

    }

    @Override
    public void bindPhone(String phoneNumber, String captcha) {

    }
}
