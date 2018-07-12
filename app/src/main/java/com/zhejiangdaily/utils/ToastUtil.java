package com.zhejiangdaily.utils;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Function: Toast
 * <p>
 * Author: chen.h
 * Date: 2018/7/9
 */
public class ToastUtil {

    private static Context appContext;

    public static void init(Application app) {
        appContext = app.getApplicationContext();
    }

    public static void show(String msg) {
        Toast.makeText(appContext, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 带图片的Toast
     * @param resId
     * @param msg
     */
    public static void showTextWithImage(int resId, String msg) {
        Toast toast = Toast.makeText(appContext, msg, Toast.LENGTH_SHORT);
        LinearLayout layout = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(appContext);
        imageView.setImageResource(resId);
        layout.addView(imageView, 0);
        toast.show();
    }

}
