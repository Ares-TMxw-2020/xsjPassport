package com.zjrb.passport.net.util;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

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
            TreeMap<String, String> treeMap = new TreeMap<>(params); // 用TreeMap的原因在于加密时要求参数名的顺序按照字典排序
            for (String s : treeMap.keySet()) {
                sb.append(s).append("=").append(treeMap.get(s)).append("&");
            }
            sb.setLength(sb.length() - 1);
            sb.append("%%");
        }
        sb.append("6tAn>tm*o7M+ba");
        return encrypt(sb.toString());
    }

    public static String encrypt(String str) {
        //TODO
        Log.d("-start- ", str);
        String result = str;
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(str.getBytes("UTF-8"));
            byte[] r = sha256.digest();
            result = byte2Hex(r);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //TODO
        Log.d("-end- ", result);
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
