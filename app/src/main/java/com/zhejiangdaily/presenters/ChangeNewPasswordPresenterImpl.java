package com.zhejiangdaily.presenters;

import android.content.Intent;

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
        ZbPassport.changePassword(oldNum, newNum, new ZbChangePasswordListener() {
            @Override
            public void onSuccess() {
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
