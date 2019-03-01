package com.zjrb.passport.Entity;

import java.io.Serializable;

/**
 * Date: 2019/02/25 下午5:21
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 认证接口返回实体
 */
public class AuthInfo implements Serializable{

    private static final long serialVersionUID = 3254029021071778259L;
    /**
     * grant_type : authorization_code
     * code : 10azEhkb2mV8QGhp3RVrHVme
     * redirect_uri :
     * client_id : 1
     */

    private String grant_type;
    private String code;
    private String redirect_uri;
    private int client_id;

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }
}
