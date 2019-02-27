package com.zjrb.passport.util;


import android.util.Base64;

/**
 * Date: 2019/2/26 5:21 PM
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: TODO
 */
public class Base64Utils {

    public static byte[] encode(byte[] plain) {
        return Base64.encode(plain, Base64.DEFAULT);
    }
    public static String encodeToString(byte[] plain) {
        return Base64.encodeToString(plain, Base64.DEFAULT);
    }
    public static byte[] decode(String text) {
        return Base64.decode(text, Base64.DEFAULT);
    }
    public static byte[] decode(byte[] text) {
        return Base64.decode(text, Base64.DEFAULT);
    }
}
