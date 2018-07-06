package com.zjrb.passport.listener;

import com.zjrb.passport.domain.PhoneNumEntity;

/**
 * Function: ZbCheckListener
 * <p>
 * Author: chen.h
 * Date: 2018/6/28
 */
public interface ZbCheckListener extends IFailure {
    void onSuccess(PhoneNumEntity entity);
}
