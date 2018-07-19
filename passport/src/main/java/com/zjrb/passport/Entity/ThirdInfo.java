package com.zjrb.passport.Entity;

/**
 * Date: 2018/7/19 下午5:21
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 绑定的三方平台信息实体类
 */
public class ThirdInfo {
    private int bindId;
    private int loginType;
    private String uid;
    private String channelName;
    private String logoUrl;

    public int getBindId() {
        return bindId;
    }

    public void setBindId(int bindId) {
        this.bindId = bindId;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
