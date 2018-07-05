package com.zjrb.passport.listener;

import com.zjrb.passport.domain.ZbInfo;

/**
 * Function: ZbRegisterListener
 * <p>
 * Author: chen.h
 * Date: 2018/6/28
 */
public interface ZbRegisterListener extends IFailure {
    void onSuccess(ZbInfo info);
}
