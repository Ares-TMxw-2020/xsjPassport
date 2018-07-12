package com.zhejiangdaily.presenters;

import android.content.Intent;
import android.text.TextUtils;

import com.zhejiangdaily.R;
import com.zhejiangdaily.contracts.FindPasswordContract;
import com.zhejiangdaily.utils.ToastUtil;
import com.zhejiangdaily.utils.ZbUtil;
import com.zhejiangdaily.views.activities.FindNewPassWordActivity;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.listener.ZbCaptchaSendListener;
import com.zjrb.passport.listener.ZbCaptchaVerifyListener;

/**
 * Date: 2018/7/10 下午5:39
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 找回通行证密码
 */
public class FindPassWordPresenterImpl implements FindPasswordContract.Presenter {

    private final FindPasswordContract.View view;

    public FindPassWordPresenterImpl(FindPasswordContract.View view) {
        this.view = view;
    }

    @Override
    public void sendSms(String phoneNum) {
        if (ZbUtil.isMobileNum(phoneNum)) {
            ZbPassport.sendCaptcha(ZbConstants.SMS_FIND,phoneNum, new ZbCaptchaSendListener() {
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

    @Override
    public void doNext(final String phoneNum, final String sms) {
        ZbPassport.verifyCaptcha(ZbConstants.SMS_FIND, phoneNum, sms, new ZbCaptchaVerifyListener() {
            @Override
            public void onSuccess(boolean isValid) {
                if (isValid) {
                    Intent intent = new Intent(view.getIActivity(), FindNewPassWordActivity.class);
                    intent.putExtra("phoneNum", phoneNum);
                    intent.putExtra("sms", sms);
                    view.getIActivity().startActivity(intent);
                    view.showDesc(false);
                } else {
                    view.showDesc(true); //失败时显示描述"您正在为xxx找回密码"
                    ToastUtil.showTextWithImage(R.mipmap.ic_qq, "验证码错误");
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                view.showDesc(true); //失败时显示描述"您正在为xxx找回密码"
                ToastUtil.showTextWithImage(R.mipmap.ic_qq, "验证码错误");
            }
        });
    }
}
