package com.zhejiangdaily.presenters;

import android.content.Intent;
import android.text.TextUtils;

import com.zhejiangdaily.R;
import com.zhejiangdaily.contracts.ChangePasswordContract;
import com.zhejiangdaily.utils.ToastUtil;
import com.zhejiangdaily.views.activities.ChangeNewPasswordActivity;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.listener.ZbCaptchaVerifyListener;

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
    public void doNext(final String passWord) {
        if (TextUtils.isEmpty(passWord)) {
            ToastUtil.show("请输入密码!");
            return;
        }
        if (passWord.length() < 6) {
            ToastUtil.show("密码长度小于6,请输入6到15位密码!");
            return;
        }
        ZbPassport.checkPassword(passWord, new ZbCaptchaVerifyListener() { // 验证旧密码是否正确
            @Override
            public void onSuccess(boolean isValid) {
                if (isValid) {
                    Intent intent = new Intent(view.getIActivity(), ChangeNewPasswordActivity.class);
                    intent.putExtra("oldPassWord", passWord);
                    view.getIActivity().startActivity(intent);
                } else {
                    ToastUtil.showTextWithImage(R.mipmap.ic_qq, "原密码错误");
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                ToastUtil.show(errorMessage);
            }
        });

    }
}
