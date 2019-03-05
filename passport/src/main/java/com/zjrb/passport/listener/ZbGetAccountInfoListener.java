package com.zjrb.passport.listener;

import com.zjrb.passport.Entity.AccountInfo;

/**
 * Date: 2019/2/27 10:37 AM
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 获取账号详情接口回调
 */
public interface ZbGetAccountInfoListener extends IFailure {

    void onSuccess(AccountInfo info);

}
