package com.zhejiangdaily.presenters;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.zhejiangdaily.R;
import com.zhejiangdaily.contracts.FindPasswordContract;
import com.zhejiangdaily.utils.ToastUtil;
import com.zhejiangdaily.utils.ZbUtil;
import com.zhejiangdaily.views.activities.FindNewPassWordActivity;
import com.zhejiangdaily.views.activities.RegisterActvity;
import com.zhejiangdaily.views.dialogs.TipDialog;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.listener.ZbCaptchaSendListener;
import com.zjrb.passport.listener.ZbCaptchaVerifyListener;
import com.zjrb.passport.listener.ZbCheckPhoneListener;

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
    public void sendSms(final String phoneNum) {
        if (ZbUtil.isMobileNum(phoneNum)) {
            ZbPassport.checkBindState(phoneNum, new ZbCheckPhoneListener() { // 发送验证码前先校验手机号是否注册过浙报通行证接口
                @Override
                public void onSuccess(boolean isBind, @Nullable String passData) {
                    if (isBind) {
                        ZbPassport.sendCaptcha(ZbConstants.Sms.FIND, phoneNum, new ZbCaptchaSendListener() {
                            @Override
                            public void onSuccess(@Nullable String passData) {

                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {
                                ToastUtil.show(errorMessage);
                            }
                        });
                    } else {
                        TipDialog tipDialog = new TipDialog(view.getIActivity());
                        tipDialog.setTitle("提示")
                                .setMessage("此手机号尚未注册，请先注册")
                                .setLeft("取消")
                                .setRight("注册")
                                .setListener(new TipDialog.Listener() {
                                    @Override
                                    public void onLeft() {

                                    }

                                    @Override
                                    public void onRight() {
                                        view.getIActivity().startActivity(new Intent(view.getIActivity(), RegisterActvity.class));
                                    }
                                });
                        tipDialog.show();
                    }
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
        if (!TextUtils.isEmpty(sms)) {
            if (ZbUtil.isMobileNum(phoneNum)) {
                ZbPassport.verifyCaptcha(ZbConstants.Sms.FIND, phoneNum, sms, new ZbCaptchaVerifyListener() {
                    @Override
                    public void onSuccess(boolean isValid, @Nullable String passData) {
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
            } else {
                if (TextUtils.isEmpty(phoneNum)) {
                    ToastUtil.show("请输入手机号");
                } else {
                    ToastUtil.show("非手机号格式");
                }
            }

        } else {
            ToastUtil.show("请输入验证码");
        }

    }
}
