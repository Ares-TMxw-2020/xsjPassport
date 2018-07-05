package com.zjrb.passport.domain;

/**
 * Date: 2018/7/5 下午3:15
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 注册及登录接口返回的binding_list实体类
 */
public class BindingListEntity {


    private int binding_id;
    private int auth_type;
    private String auth_uid;
    private String binding_name;
    private String binding_logo;

    public int getBinding_id() { return binding_id;}

    public void setBinding_id(int binding_id) { this.binding_id = binding_id;}

    public int getAuth_type() { return auth_type;}

    public void setAuth_type(int auth_type) { this.auth_type = auth_type;}

    public String getAuth_uid() { return auth_uid;}

    public void setAuth_uid(String auth_uid) { this.auth_uid = auth_uid;}

    public String getBinding_name() { return binding_name;}

    public void setBinding_name(String binding_name) { this.binding_name = binding_name;}

    public String getBinding_logo() { return binding_logo;}

    public void setBinding_logo(String binding_logo) { this.binding_logo = binding_logo;}

}
