package com.zhejiangdaily.presenters;

import android.content.Intent;
import android.text.TextUtils;

import com.zhejiangdaily.contracts.FindPasswordContract;
import com.zhejiangdaily.utils.ToastUtil;
import com.zhejiangdaily.utils.ZbUtil;
import com.zhejiangdaily.views.activities.NewPassWordActivity;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.listener.ZbListener;

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
            ZbPassport.sendCaptcha(ZbConstants.SMS_RETRIEVE,phoneNum, new ZbListener() {
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
    public void doNext(String phoneNum, String sms) {
        // TODO: 2018/7/10
        boolean success = true; // 校验验证码 成功 跳输入新密码界面
        if (success) {
            Intent intent = new Intent(view.getIActivity(), NewPassWordActivity.class);
            // TODO: 2018/7/10 传参
            view.getIActivity().startActivity(intent);
        } else {
            view.showDesc();
        }
    }
}
