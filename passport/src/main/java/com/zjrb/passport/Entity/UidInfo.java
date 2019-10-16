package com.zjrb.passport.Entity;

import java.io.Serializable;

/**
 * Date: 2019-10-16 16:59
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 钉钉UID实体
 */
public class UidInfo implements Serializable {

    private static final long serialVersionUID = -3287273076930355570L;

    // ｛“uid”：“1233”｝
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
