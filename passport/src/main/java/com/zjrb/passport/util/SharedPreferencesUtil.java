package com.zjrb.passport.util;

/**
 * Date: 2018/11/7 上午10:15
 * Email: sisq@8531.cn
 * Author: sishuqun
 * Description: SharedPerference工具类
 */
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    public static final String mName = "com.zjrb.passport";
    // 创建一个写入器
    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;
    private static volatile SharedPreferencesUtil mSharedPreferencesUtil;

    // 构造方法
    private SharedPreferencesUtil(Context context) {
        mPreferences = context.getSharedPreferences(mName, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    // 单例模式
    public static SharedPreferencesUtil init(Context context) {
        if (mSharedPreferencesUtil == null) {
            synchronized (SharedPreferencesUtil.class) {
                if (mSharedPreferencesUtil == null) {
                    mSharedPreferencesUtil = new SharedPreferencesUtil(context);
                }
            }
        }
        return mSharedPreferencesUtil;
    }

    public static SharedPreferencesUtil getInstance() {
        if (mSharedPreferencesUtil == null) {
            throw new RuntimeException("ShardPreference未初始化");
        }
        return mSharedPreferencesUtil;
    }

    // 存入数据
    public void putString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    // 获取数据
    public String getString(String key) {
        return mPreferences.getString(key, "");
    }

    // 移除数据
    public void remove(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }
}

