package com.zjrb.passport.domain;

/**
 * Date: 2018/7/5 下午4:14
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 手机号是否绑定通行证返回实体
 */
public class PhoneNumEntity {

    private String phone_number;

    private boolean phone_number_taken;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public boolean isPhone_number_taken() {
        return phone_number_taken;
    }

    public void setPhone_number_taken(boolean phone_number_taken) {
        this.phone_number_taken = phone_number_taken;
    }
}
