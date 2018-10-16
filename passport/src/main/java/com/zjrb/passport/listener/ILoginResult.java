package com.zjrb.passport.listener;

import android.support.annotation.Nullable;

import com.zjrb.passport.Entity.LoginInfo;

/**
 * Function: ILoginResult
 * <p>
 * Author: chen.h
 * Date: 2018/7/12
 */
public interface ILoginResult extends IFailure {

    void onSuccess(LoginInfo loginInfo, @Nullable String passData);
}
