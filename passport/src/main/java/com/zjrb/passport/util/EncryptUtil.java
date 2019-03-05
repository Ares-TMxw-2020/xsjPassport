package com.zjrb.passport.util;

import com.zjrb.passport.constant.ZbConstants;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


/**
 * Function: EncryptUtil
 * <p>
 * Author: chen.h
 * Date: 2018/7/4
 */
public class EncryptUtil {

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
        return sb.toString().toLowerCase();
    }


    /**
     * sha256_HMAC加密
     * @param message 消息
     * @param secret  秘钥
     * @return 加密后字符串
     */
    public static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byte2Hex(bytes);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    /**
     * 对密码进行加密,先进行RSA加密,然后进行Base64编码
     * @param password
     * @return
     */
    public static String encryptPassWord(String password) {
        if (password != null) {
            try {
                return Base64Utils.encode(RSAUtils.encryptByPublicKey(password.getBytes(), ZbConstants.PASSPORT_PUBLIC_KEY));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

}
