package com.zjrb.passport.listener;

import com.zjrb.passport.LoginInfo;

/**
 * Function: ILoginResult
 * <p>
 * Author: chen.h
 * Date: 2018/7/12
 */
public interface ILoginResult extends IFailure {

    void onSuccess(LoginInfo loginInfo);
}
