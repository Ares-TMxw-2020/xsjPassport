package com.zhejiangdaily.presenters;

import android.text.TextUtils;

import com.zhejiangdaily.contracts.ChangePasswordContract;
import com.zhejiangdaily.utils.ToastUtil;
import com.zhejiangdaily.utils.ZbUtil;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.listener.ZbListener;

/**
 * Date: 2018/7/12 上午11:46
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 更改密码
 */
public class ChangePassWordPresenterImpl implements ChangePasswordContract.Presenter {

    private final ChangePasswordContract.View view;

    public ChangePassWordPresenterImpl(ChangePasswordContract.View view) {
        this.view = view;
    }

    @Override
    public void doNext(String phoneNum) {
        if (ZbUtil.isMobileNum(phoneNum)) {
            ZbPassport.sendCaptcha(ZbConstants.SMS_RETRIEVE, phoneNum, new ZbListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(int errorCode, String errorMessage) {
                    ToastUtil.show(errorMessage);
                }
            });
        } else {
            if (TextUtils.isEmpty(phoneNum)) {
                ToastUtil.show("请输入手机号");
            } else {
                ToastUtil.show("非手机号格式");
            }
        }
    }
}
