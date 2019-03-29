package com.zjrb.passport.util;


import android.util.Base64;

/**
 * Date: 2019/2/26 5:21 PM
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: Base64加密工具
 */
public class Base64Utils {

    public static byte[] decode(String base64) {
        return Base64.decode(base64.getBytes(), Base64.NO_WRAP); // 使用NO_WRAP不使用Default是为了配合后端,后端相应算法未进行换行
    }

    public static String encode(byte[] bytes) {
        return new String(Base64.encode(bytes, Base64.NO_WRAP));
    }

    public static String encodeToString(byte[] plain) {
        return Base64.encodeToString(plain, Base64.NO_WRAP);
    }

    public static byte[] decode(byte[] text) {
        return Base64.decode(text, Base64.NO_WRAP);
    }


//    public static byte[] encode(byte[] plain) {
//        return Base64.encode(plain, Base64.DEFAULT);
//    }
//    public static String encodeToString(byte[] plain) {
//        return Base64.encodeToString(plain, Base64.DEFAULT);
//    }
//    public static byte[] decode(String text) {
//        return Base64.decode(text, Base64.DEFAULT);
//    }
//    public static byte[] decode(byte[] text) {
//        return Base64.decode(text, Base64.DEFAULT);
//    }

}
