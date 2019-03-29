package com.zjrb.passport.util;

import android.util.Log;

import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.net.request.FormBody;
import com.zjrb.passport.net.request.Request;
import com.zjrb.passport.net.response.Response;

import java.util.Map;

/**
 * Function: Logger
 * <p>
 * Author: chen.h
 * Date: 2018/7/9
 */
public class Logger {

    private static final String TAG = "ZbPassport";

    private Logger() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void d(Request request, Response response) {
        if (!ZbPassport.getZbConfig().isDebug()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("↓↓↓ network log ↓↓↓")
          .append("\n")
          .append(request.getMethod().method)
          .append(" ->\t")
          .append(request.getUrl())
          .append("\n");
        Map<String, String> map = request.getHeaders();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
            }
        }
        try {
            FormBody formBody = (FormBody) request.getRequestBody();
            if (formBody != null) {
                sb.append("body ->\t").append(formBody.transferToString()).append("\n");
            }
            sb.append("response ->\t").append(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
        d(sb.toString());
    }


    public static void d(String msg) {
        if (ZbPassport.getZbConfig().isDebug())
            Log.d(TAG, msg + "");
    }

    public static void w(String msg) {
        if (ZbPassport.getZbConfig().isDebug())
            Log.w(TAG, msg + "");
    }

    public static void e(String msg) {
        if (ZbPassport.getZbConfig().isDebug())
            Log.e(TAG, msg + "");
    }

}
