package com.zjrb.passport.listener;

import com.zjrb.passport.Entity.ClientInfo;

/**
 * Date: 2019/2/26 6:50 PM
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 初始化回调接口
 */
public interface ZbInitListener extends IFailure {

    void onSuccess(ClientInfo info);

}
