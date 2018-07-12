package com.zhejiangdaily.contracts;

import com.zhejiangdaily.presenters.BasePresenter;

/**
 * Date: 2018/7/10 下午5:23
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 输入新密码契约类
 */
public interface FindNewPassWordContract {

    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter {

        void sendNewPassWord(String phoneNum, String sms, String password); // 验证新密码

    }

}
