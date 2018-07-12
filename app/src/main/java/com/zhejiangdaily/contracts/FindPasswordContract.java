package com.zhejiangdaily.contracts;

import com.zhejiangdaily.presenters.BasePresenter;

/**
 * Date: 2018/7/10 下午5:23
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 找回密码契约类
 */
public interface FindPasswordContract {

    interface View extends BaseView {
        void showDesc(); // 验证码错误时显示找回密码提示
    }

    interface Presenter extends BasePresenter {
        void sendSms(String phoneNum); // 发送验证码

        void doNext(String phoneNum, String sms); // 验证验证码
    }

}
