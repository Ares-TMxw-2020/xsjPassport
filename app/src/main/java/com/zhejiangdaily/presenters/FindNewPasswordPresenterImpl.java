package com.zhejiangdaily.presenters;

import com.zhejiangdaily.contracts.FindNewPassWordContract;

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
        // TODO: 2019/3/5
//        ZbPassport.findPassword(phoneNum, sms, password, new ZbFindPasswordListener() {
//            @Override
//            public void onSuccess() {
//                ToastUtil.showTextWithImage(R.mipmap.ic_qq, "找回密码成功,请使用新密码登录");
//                Intent intent = new Intent(view.getIActivity(), LoginActivity.class);
//                view.getIActivity().startActivity(intent);
//                view.getIActivity().finish();
//            }
//
//            @Override
//            public void onFailure(int errorCode, String errorMessage) {
//                ToastUtil.showTextWithImage(R.mipmap.ic_qq, errorMessage);
//
//            }
//        });
    }
}
