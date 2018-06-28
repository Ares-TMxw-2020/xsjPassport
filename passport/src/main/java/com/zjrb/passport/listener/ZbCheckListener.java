package com.zjrb.passport.listener;

/**
 * Function: ZbCheckListener
 * <p>
 * Author: chen.h
 * Date: 2018/6/28
 */
public interface ZbCheckListener extends IFailure {
    void onSuccess(boolean isExist);
}
