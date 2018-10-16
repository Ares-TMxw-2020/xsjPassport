package com.zhejiangdaily.presenters;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.zhejiangdaily.R;
import com.zhejiangdaily.contracts.ChangeNewPassWordContract;
import com.zhejiangdaily.utils.ToastUtil;
import com.zhejiangdaily.views.activities.UserInfoActivity;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.listener.ZbChangePasswordListener;

/**
 * Date: 2018/7/12 下午6:02
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 修改密码,输入新密码界面
 */
public class ChangeNewPasswordPresenterImpl implements ChangeNewPassWordContract.Presenter {

    private final ChangeNewPassWordContract.View view;

    public ChangeNewPasswordPresenterImpl(ChangeNewPassWordContract.View view) {
        this.view = view;
    }

    @Override
    public void changePassWord(String oldNum, String newNum) {
        if (TextUtils.isEmpty(newNum)) {
            ToastUtil.show("请输入新密码!");
            return;
        }
        if (newNum.length() < 6) {
            ToastUtil.show("密码长度小于6,请输入6到15位密码!");
            return;
        }
        ZbPassport.changePassword(oldNum, newNum, new ZbChangePasswordListener() {
            @Override
            public void onSuccess(@Nullable String passData) {
                ToastUtil.showTextWithImage(R.mipmap.ic_qq, "修改密码成功");
                Intent intent = new Intent(view.getIActivity(), UserInfoActivity.class);
                view.getIActivity().startActivity(intent);
                view.getIActivity().finish();
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                ToastUtil.showTextWithImage(R.mipmap.ic_qq, errorMessage);
            }
        });
    }
}
