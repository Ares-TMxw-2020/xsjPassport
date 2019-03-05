package com.zhejiangdaily.presenters;

import android.text.TextUtils;

import com.zhejiangdaily.contracts.ChangeNewPassWordContract;
import com.zhejiangdaily.utils.ToastUtil;

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
        // TODO: 2019/3/5
//        ZbPassport.changePassword(oldNum, newNum, new ZbChangePasswordListener() {
//            @Override
//            public void onSuccess() {
//                ToastUtil.showTextWithImage(R.mipmap.ic_qq, "修改密码成功");
//                Intent intent = new Intent(view.getIActivity(), UserInfoActivity.class);
//                view.getIActivity().startActivity(intent);
//                view.getIActivity().finish();
//            }
//
//            @Override
//            public void onFailure(int errorCode, String errorMessage) {
//                ToastUtil.showTextWithImage(R.mipmap.ic_qq, errorMessage);
//            }
//        });
    }
}
