package com.zhejiangdaily.presenters;

import android.content.Intent;
import android.support.annotation.Nullable;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhejiangdaily.contracts.LoginContract;
import com.zhejiangdaily.utils.ToastUtil;
import com.zhejiangdaily.utils.ZbUtil;
import com.zhejiangdaily.views.activities.FindPassWordActivity;
import com.zhejiangdaily.views.activities.RegisterActvity;
import com.zjrb.passport.Entity.LoginInfo;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.constant.ZbConstants;
import com.zjrb.passport.listener.ZbCheckPhoneListener;
import com.zjrb.passport.listener.ZbLoginListener;

import org.json.JSONObject;

/**
 * Function: LoginPresenterImpl
 * <p>
 * Author: chen.h
 * Date: 2018/7/9
 */
public class LoginPresenterImpl implements LoginContract.Presenter {

    private final LoginContract.View view;


    public LoginPresenterImpl(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void findPassWord() {
        Intent intent = new Intent(view.getIActivity(), FindPassWordActivity.class);
        view.getIActivity().startActivity(intent);
    }

    @Override
    public void login(String phone, String password) {
        if (ZbUtil.isNumeric(phone)) { // 纯数字
            if (ZbUtil.isMobileNum(phone)) { // 手机号登录
                checkBind(phone, password);
            } else {
                ToastUtil.show("手机号格式错误");
            }
        } else { // 个性化账号,首位一定是字母
            doCustomLogin(phone, password);
        }
    }

    @Override
    public void loginThird(SHARE_MEDIA platform, String uid) {
        switch (platform) {
            case WEIXIN:
                ZbPassport.loginThird(ZbConstants.ThirdLogin.WECHAT, uid, zbLoginListener);
                break;
            case QQ:
                ZbPassport.loginThird(ZbConstants.ThirdLogin.QQ, uid, zbLoginListener);
                break;
            case SINA:
                ZbPassport.loginThird(ZbConstants.ThirdLogin.SINA, uid, zbLoginListener);
                break;
            default:
                break;
        }

    }

    @Override
    public void gotoRegister() {
        Intent intent = new Intent(view.getIActivity(), RegisterActvity.class);
        view.getIActivity().startActivity(intent);
    }

    private void checkBind(final String phone, final String password) {
        ZbPassport.checkBindState(phone, new ZbCheckPhoneListener() {
            @Override
            public void onSuccess(boolean isBind, @Nullable JSONObject passData) {
                if (isBind) {
                    doLogin(phone, password);
                } else {
                    view.onPhoneNotExist();
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {

            }
        });
    }

    private void doLogin(String phone, String password) {
        if (password == null) {
            view.login(false, "密码不能为空");
        } else if (password.length() < 6) {
            view.login(false, "密码长度小于6位");
        } else {
            ZbPassport.login(phone, password, zbLoginListener);
        }
    }

    /**
     * 个性化账户登录
     * @param username
     * @param password
     */
    private void doCustomLogin(String username, String password) {
        if (password == null) {
            view.login(false, "密码不能为空");
        } else if (password.length() < 6) {
            view.login(false, "密码长度小于6位");
        } else {
            ZbPassport.loginCustom(username, password, zbLoginListener);
        }
    }



    private ZbLoginListener zbLoginListener = new ZbLoginListener() {
        @Override
        public void onSuccess(LoginInfo info, @Nullable JSONObject passData) {
            view.login(true, null);
        }

        @Override
        public void onFailure(int errorCode, String errorMessage) {
            view.login(false, errorMessage);
        }
    };
}
