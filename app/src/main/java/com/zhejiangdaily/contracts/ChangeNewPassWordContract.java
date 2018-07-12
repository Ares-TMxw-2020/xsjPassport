package com.zhejiangdaily.contracts;

import com.zhejiangdaily.presenters.BasePresenter;

/**
 * Date: 2018/7/10 下午5:23
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 修改密码 输入新密码界面契约类
 */
public interface ChangeNewPassWordContract {

    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter {

        void changePassWord(String oldNum, String newNum); // 修改密码

    }

}
