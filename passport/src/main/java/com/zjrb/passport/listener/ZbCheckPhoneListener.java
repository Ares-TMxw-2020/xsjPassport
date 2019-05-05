package com.zjrb.passport.listener;

import com.zjrb.passport.Entity.CheckPhoneInfo;

/**
 * Date: 2019/4/8 9:27 AM
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 手机号是否注册过通行证回调
 */
public interface ZbCheckPhoneListener extends IFailure {

    void onSuccess(CheckPhoneInfo info);

}
