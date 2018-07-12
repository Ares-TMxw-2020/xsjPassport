package com.zhejiangdaily.contracts;

import com.zhejiangdaily.presenters.BasePresenter;

/**
 * Date: 2018/7/10 下午5:23
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 更改密码契约类
 */
public interface ChangePasswordContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {
        void sendSms(String phoneNum); // 发送验证码

        void doNext(String phoneNum, String sms); // 验证验证码
    }

}
