package com.zjrb.passport.Entity;

import java.io.Serializable;

/**
 * Date: 2019/4/8 9:26 AM
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 手机号是否注册过通行证 或者第三方账号是否绑定通行证
 */
public class CheckPhoneInfo implements Serializable {

    private static final long serialVersionUID = -8976679960082710330L;
    private boolean exist;

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}
