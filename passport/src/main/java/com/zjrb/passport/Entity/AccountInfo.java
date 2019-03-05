package com.zjrb.passport.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 2019/02/25 下午5:21
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 账号详情接口返回实体
 */
public class AccountInfo implements Serializable{

    private static final long serialVersionUID = -1564794395905124705L;

    /**
     * group_id : 1
     * current_client_id : 1
     * account_id : 1
     * phone_number : 18516696580
     * password_reset_required : false
     * third_parties : [{"association_id":1,"name":"微信","logo":"","auth_type":1},{"association_id":2,"name":"QQ","logo":"","auth_type":2},{"association_id":3,"name":"微博","logo":"","auth_type":3}]
     */
    private int group_id;
    private int current_client_id;
    private int account_id;
    private String phone_number;
    private boolean password_reset_required;
    private List<ThirdPartiesBean> third_parties;

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getCurrent_client_id() {
        return current_client_id;
    }

    public void setCurrent_client_id(int current_client_id) {
        this.current_client_id = current_client_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public boolean isPassword_reset_required() {
        return password_reset_required;
    }

    public void setPassword_reset_required(boolean password_reset_required) {
        this.password_reset_required = password_reset_required;
    }

    public List<ThirdPartiesBean> getThird_parties() {
        return third_parties;
    }

    public void setThird_parties(List<ThirdPartiesBean> third_parties) {
        this.third_parties = third_parties;
    }

    public static class ThirdPartiesBean {
        /**
         * association_id : 1
         * name : 微信
         * logo :
         * auth_type : 1
         */

//        private int association_id;
        private String name;
//        private String logo;
        private int auth_type;

//        public int getAssociation_id() {
//            return association_id;
//        }
//
//        public void setAssociation_id(int association_id) {
//            this.association_id = association_id;
//        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

//        public String getLogo() {
//            return logo;
//        }
//
//        public void setLogo(String logo) {
//            this.logo = logo;
//        }

        public int getAuth_type() {
            return auth_type;
        }

        public void setAuth_type(int auth_type) {
            this.auth_type = auth_type;
        }
    }
}
