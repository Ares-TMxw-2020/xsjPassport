package com.zjrb.passport.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 2019/02/25 下午5:21
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 初始化接口返回实体
 */
public class ClientInfo implements Serializable{

    private static final long serialVersionUID = 5085838627616199558L;
    private int client_id;
    private String app_name;
    private String app_logo;
    private boolean account_merge;
    private String signature_key;
    private List<String> supported_third_party;

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_logo() {
        return app_logo;
    }

    public void setApp_logo(String app_logo) {
        this.app_logo = app_logo;
    }

    public boolean isAccount_merge() {
        return account_merge;
    }

    public void setAccount_merge(boolean account_merge) {
        this.account_merge = account_merge;
    }

    public String getSignature_key() {
        return signature_key;
    }

    public void setSignature_key(String signature_key) {
        this.signature_key = signature_key;
    }

    public List<String> getSupported_third_party() {
        return supported_third_party;
    }

    public void setSupported_third_party(List<String> supported_third_party) {
        this.supported_third_party = supported_third_party;
    }
}
