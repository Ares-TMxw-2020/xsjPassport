package passportdemo.zjrb.com.zjrbpassport.utils;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * Function: Toast
 * <p>
 * Author: chen.h
 * Date: 2018/7/9
 */
public class T {

    private static Context appContext;

    public static void init(Application app) {
        appContext = app.getApplicationContext();
    }

    public static void show(String msg) {
        Toast.makeText(appContext, msg, Toast.LENGTH_SHORT).show();
    }

}
