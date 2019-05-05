package com.zjrb.passport.listener;

import com.zjrb.passport.Entity.CheckPhoneInfo;

/**
 * Date: 2019/4/8 9:27 AM
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 三方账号是否已经绑定通行证回调接口
 */
public interface ZbCheckThirdListener extends IFailure {

    void onSuccess(CheckPhoneInfo info);

}
