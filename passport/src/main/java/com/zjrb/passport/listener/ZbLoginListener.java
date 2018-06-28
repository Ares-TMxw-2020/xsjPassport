package com.zjrb.passport.listener;

import com.zjrb.passport.ZbInfo;

/**
 * Function: ZbLoginListener
 * <p>
 * Author: chen.h
 * Date: 2018/6/28
 */
public interface ZbLoginListener extends IFailure {
    void onSuccess(ZbInfo info);
}
