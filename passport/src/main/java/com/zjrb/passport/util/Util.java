package com.zjrb.passport.util;

import android.text.TextUtils;

import com.zjrb.passport.net.ApiManager;
import com.zjrb.passport.net.request.Request;
import com.zjrb.passport.net.request.RequestBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * Date: 2018/6/28 下午4:19
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: 网络帮助类
 */
public class Util {

    public static void checkMethod(Request.HttpMethod method, RequestBody body) {
        if (method == null) {
            throw new NullPointerException("请求方式不能为空");
        }
        if (body != null && Request.HttpMethod.checkNoBody(method)) {
            throw new IllegalStateException(method + "请求方式不能有请求体");
        }
        if (body == null && Request.HttpMethod.checkNeedBody(method)) {
            throw new IllegalStateException(method + "请求方式必须有请求体");
        }
    }

    /**
     * get请求url封装
     * @param urlPath
     * @param params
     * @return
     */
    public static String buildGetUrl(String urlPath, Map<String, String> params) {
        if (TextUtils.isEmpty(urlPath) || params == null || params.size() == 0) {
            return urlPath;
        }
        if (!urlPath.endsWith("?")) {
            urlPath += "?";
        }
        String paramsStr = buildGetParams(params);
        StringBuilder sbUrl = new StringBuilder(urlPath);
        sbUrl.append(paramsStr);
        return sbUrl.toString();
    }

    public static String buildGetParams(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            if (TextUtils.isEmpty(key)) {
                continue;
            }
            try {
                // TODO: 2019/3/4  
                if (TextUtils.equals(key, InnerConstant.SIGN)) { // sign为sha256加密后的字串,不包含特殊字符,这里进不进行encode都行,这里跟ios逻辑保持一致,不再encode
                    sb = sb.append(key + "=" + (TextUtils.isEmpty(params.get(key)) ? "" : params.get(key)) + "&");
                } else {
                    sb = sb.append(key + "=" + (TextUtils.isEmpty(params.get(key)) ? "" : URLEncoder.encode(params.get(key), "UTF-8")) + "&");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        String paramsStr = sb.substring(0, sb.length() - 1);
        return paramsStr;
    }


    public static String getValueEncoded(String value) {
        if (value == null) return "null";
        String newValue = value.replace("\n", "");
        for (int i = 0, length = newValue.length(); i < length; i++) {
            char c = newValue.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                try {
                    return URLEncoder.encode(newValue, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return newValue;
    }


    /**
     * 生成签名
     * @param method
     * @param api
     * @param params
     * @param requestId
     * @param accessToken
     * @param signatureKey
     * @return
     */
    public static String generateSignature(String method, String api, Map<String, String> params, String requestId, String accessToken, String signatureKey) {
        StringBuilder sb = new StringBuilder();
        sb.append(method.toLowerCase()).append("%%").append(api);
        // 将上述 client_id / phone_number / passport_id / unbind 四个参数（参数值是未经过urlencode的）与url拼接成 request_uri，参数名的顺序按照字典排序，如果请求不带参数，则不需要加分割问号?
        if (params != null && !params.isEmpty()) {
            sb.append("?");
            for (String s : params.keySet()) {
                sb.append(s).append("=").append(TextUtils.isEmpty(params.get(s)) ? "" : params.get(s)).append("&"); // 值不需要进行encode
            }
            sb.setLength(sb.length() - 1); // 去掉最后一个&
        }
        sb.append("%%").append(requestId).append("%%");
        if (Util.isNeedAccessToken(api)) { // 请求头里面有accessToken的,拼接accessToken
            sb.append(accessToken);
        }
        System.out.println("sssssss  SignString: " + sb.toString());
        return EncryptUtil.sha256_HMAC(sb.toString(), signatureKey);
    }

    /**
     * 接口请求头是否需要添加X-SIGNATURE,会话初始化及图形验证码接口不需要
     * @param api 请求接口
     * @return
     */
    public static boolean isNeedSignature(String api) {
        return !TextUtils.equals(api, ApiManager.EndPoint.INIT) && !TextUtils.equals(api, ApiManager.EndPoint.SMS_CAPTCHA_IMAGE);
    }

    /**
     * 接口请求头是否需要添加X-AGENT-CREDENTIAL  获取账号详情接口,修改密码接口,修改手机号接口,绑定及解绑三方账号接口需要添加
     * @param api 请求接口
     * @return
     */
    public static boolean isNeedAccessToken(String api) {
        return TextUtils.equals(api, ApiManager.EndPoint.PASSPORT_ACCOUNT_DETAIL) || TextUtils.equals(api, ApiManager.EndPoint.PASSPORT_ALTER_PASSWORD)
                || TextUtils.equals(api, ApiManager.EndPoint.PASSPORT_ALTER_PHONE_NUM) || TextUtils.equals(api, ApiManager.EndPoint.PASSPORT_BIND_THIRD_PARTY)
                || TextUtils.equals(api, ApiManager.EndPoint.PASSPORT_UNBIND_THIRD_PARTY) || TextUtils.equals(api, ApiManager.EndPoint.PASSPORT_CHECK_PASSWORDS);
    }

}
