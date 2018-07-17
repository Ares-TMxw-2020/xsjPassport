package com.zjrb.passport.util;

import android.util.Log;

import com.zjrb.passport.NetWork;
import com.zjrb.passport.ZbPassport;
import com.zjrb.passport.net.Response;

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

    public static void d(NetWork.ParamsBuilder builder, Response response) {
        if (ZbPassport.getZbConfig().isDebug()) {
            StringBuilder sb = new StringBuilder();
            sb.append("↓↓↓ network log ↓↓↓")
              .append("\n")
              .append("url ->\t\t")
              .append(builder.getUrl())
              .append("\n")
              .append("params ->\t")
              .append(builder.paramString())
              .append("\n")
              .append("response ->\t")
              .append(response.body().string());
            d(sb.toString());
        }
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
