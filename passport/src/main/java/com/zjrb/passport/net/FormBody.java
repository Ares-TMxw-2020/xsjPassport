package com.zjrb.passport.net;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * Date: 2018/6/29 上午10:57
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 表单封装,参照okhttp
 */
public class FormBody extends RequestBody {

    final Map<String, String> map;

    public FormBody(Builder builder) {
        this.map = builder.map;
    }

    @Override
    String contentType() {
        return "application/x-www-form-urlencoded; charset=UTF-8";
    }

    @Override
    void writeTo(OutputStream outputStream) throws IOException {
        try {
            outputStream.write(transferToString(map).getBytes("UTF-8"));
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    private String transferToString(Map<String, String> map) throws UnsupportedEncodingException{
        StringBuilder sb = new StringBuilder();
        if (map != null && !map.isEmpty()) {
            Set<String> set = map.keySet();
            for (String s : set) {
                if (!TextUtils.isEmpty(sb)) {
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(s, "UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(map.get(s), "UTF-8"));
            }
            return sb.toString();
        }
        return "";
    }

   public static class Builder {

       private Map<String, String> map;

       public Builder() {
           map = new ArrayMap<>();
       }

       public Builder add(String key, String value) {
           if (!TextUtils.isEmpty(key)) {
               map.put(key, value);
           }
           return this;
       }

       public Builder map(Map<String, String> map) {
           this.map = map;
           return this;
       }

       public FormBody build() {
           return new FormBody(this);
       }

   }

}
