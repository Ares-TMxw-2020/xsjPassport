package com.zjrb.passport.listener;

import com.zjrb.passport.Entity.UidInfo;

/**
 * Date: 2019/4/8 9:27 AM
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 获取钉钉UID
 */
public interface ZbGetUidListener extends IFailure {

    void onSuccess(UidInfo info);

}
