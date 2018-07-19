package com.zjrb.passport.util;

import com.zjrb.passport.constant.InnerConstant;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Function: EncryptUtil
 * <p>
 * Author: chen.h
 * Date: 2018/7/4
 */
public class EncryptUtil {

    public static String encrypt(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url).append("%%");
        if (params != null && !params.isEmpty()) {
            for (String s : params.keySet()) {
                sb.append(s).append("=").append(params.get(s)).append("&");
            }
            sb.setLength(sb.length() - 1);
            sb.append("%%");
        }
        sb.append(InnerConstant.SALT);
        return encrypt(sb.toString());
    }

    public static String encrypt(String str) {
        String result = str;
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(str.getBytes(InnerConstant.DEF_CODE));
            byte[] r = sha256.digest();
            result = byte2Hex(r);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String temp;
        if (bytes != null) {
            for (byte b : bytes) {
                temp = Integer.toHexString(b & 0xFF);
                if (temp.length() == 1) {
                    //1得到一位的进行补0操作
                    sb.append("0");
                }
                sb.append(temp);
            }
        }
        return sb.toString();
    }
}
