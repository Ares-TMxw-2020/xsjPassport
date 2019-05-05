package com.zjrb.passport.listener;

/**
 * Date: 2019/2/27 10:10 AM
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 图形验证码回调接口
 */
public interface ZbGraphicListener extends IFailure {

    void onSuccess(byte[] bytes);

}
