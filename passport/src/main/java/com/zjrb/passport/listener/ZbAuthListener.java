package com.zjrb.passport.listener;

import com.zjrb.passport.Entity.AuthInfo;

/**
 * Date: 2019/2/27 10:10 AM
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 认证接口回调
 */
public interface ZbAuthListener extends IFailure {

    void onSuccess(AuthInfo info);

}
