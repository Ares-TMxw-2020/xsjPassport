package com.zhejiangdaily.presenters;

import com.zhejiangdaily.R;
import com.zhejiangdaily.contracts.FindNewPassWordContract;
import com.zhejiangdaily.utils.ToastUtil;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.listener.ZbFindPasswordListener;

/**
 * Date: 2018/7/12 下午6:02
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 找回密码,输入新密码
 */
public class FindNewPasswordPresenterImpl implements FindNewPassWordContract.Presenter {

    private final FindNewPassWordContract.View view;

    public FindNewPasswordPresenterImpl(FindNewPassWordContract.View view) {
        this.view = view;
    }

    @Override
    public void sendNewPassWord(String phoneNum, String sms, String password) {
        ZbPassport.findPassword(phoneNum, sms, password, new ZbFindPasswordListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showTextWithImage(R.mipmap.ic_qq, "找回密码成功");
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                ToastUtil.showTextWithImage(R.mipmap.ic_qq, "找回密码失败");

            }
        });
    }
}
