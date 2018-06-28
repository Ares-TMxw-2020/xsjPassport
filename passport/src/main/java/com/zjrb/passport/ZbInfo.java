package com.zjrb.passport;

import java.util.List;

/**
 * Function: ZbInfo
 * <p>
 * Author: chen.h
 * Date: 2018/6/28
 */
public class ZbInfo {

    private int passport_id;
    private String phone_number;
    private String access_token;
    private int current_auth_type;
    private boolean is_new;
    private List<BindingListBean> binding_list;

    public int getPassport_id() { return passport_id;}

    public void setPassport_id(int passport_id) { this.passport_id = passport_id;}

    public String getPhone_number() { return phone_number;}

    public void setPhone_number(String phone_number) { this.phone_number = phone_number;}

    public String getAccess_token() { return access_token;}

    public void setAccess_token(String access_token) { this.access_token = access_token;}

    public int getCurrent_auth_type() { return current_auth_type;}

    public void setCurrent_auth_type(int current_auth_type) { this.current_auth_type = current_auth_type;}

    public boolean isIs_new() { return is_new;}

    public void setIs_new(boolean is_new) { this.is_new = is_new;}

    public List<BindingListBean> getBinding_list() { return binding_list;}

    public void setBinding_list(List<BindingListBean> binding_list) { this.binding_list = binding_list;}

    public static class BindingListBean {

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
}
