package com.zhejiangdaily.presenters;

import android.content.Intent;

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
    public void doNext(final String phoneNum) {
        ZbPassport.checkPassword(phoneNum, new ZbCaptchaVerifyListener() { // 验证旧密码是否正确
            @Override
            public void onSuccess(boolean isValid) {
                if (isValid) {
                    Intent intent = new Intent(view.getIActivity(), ChangeNewPasswordActivity.class);
                    intent.putExtra("oldPassWord", phoneNum);
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
