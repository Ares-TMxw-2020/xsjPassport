package com.zjrb.passport.Entity;

import java.util.List;

/**
 * Function: LoginInfo
 * <p>
 * Author: chen.h
 * Date: 2018/7/12
 */
public class LoginInfo {
    private int passportId;
    private String phoneNumber;
    private String token;
    private int currentLoginType;
    private boolean isNewUser;
    private List<ThirdInfo> bindList;

    public int getPassportId() {
        return passportId;
    }

    public void setPassportId(int passportId) {
        this.passportId = passportId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCurrentLoginType() {
        return currentLoginType;
    }

    public void setCurrentLoginType(int currentLoginType) {
        this.currentLoginType = currentLoginType;
    }

    public boolean isNewUser() {
        return isNewUser;
    }

    public void setNewUser(boolean newUser) {
        isNewUser = newUser;
    }

    public List<ThirdInfo> getBindList() {
        return bindList;
    }

    public void setBindList(List<ThirdInfo> bindList) {
        this.bindList = bindList;
    }

}
