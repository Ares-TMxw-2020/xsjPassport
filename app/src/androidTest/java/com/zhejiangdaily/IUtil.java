package com.zhejiangdaily;

import android.app.ActivityManager;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import java.util.List;

/**
 * Function: IUtil
 * <p>
 * Author: chen.h
 * Date: 2018/7/26
 */
public class IUtil {

    public static String getCurrentActivityName(ActivityManager am) {
        if (am == null) {
            return "";
        }
        List<ActivityManager.AppTask> taskList = am.getAppTasks();
        if (taskList == null || taskList.isEmpty()) {
            return "";
        }
        ActivityManager.AppTask appTask = taskList.get(0);
        ActivityManager.RecentTaskInfo recentTaskInfo = appTask.getTaskInfo();
        return recentTaskInfo.topActivity.getClassName();
    }


    public static void wait(UiDevice uiDevice, String appName, int second) {
        uiDevice.waitForWindowUpdate(appName, second * 1000);
    }


    public static void log(String message) {
        Log.d("-test-", message);
    }
}
