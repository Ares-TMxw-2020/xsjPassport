package com.zhejiangdaily;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.zhejiangdaily.IUtil.log;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Function: LoginActivityTest
 * <p>
 * Author: chen.h
 * Date: 2018/7/24
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    private UiDevice mUiDevice;
    private String app_name = "com.zhejiangdaily";
    private ActivityManager activityManager;

    @Before
    public void setUp() throws Exception {
        mUiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context mContext = InstrumentationRegistry.getContext();
        mUiDevice.pressHome();
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(app_name);
        assertNotNull(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);

        IUtil.wait(mUiDevice, app_name, 3);

        UiObject o_login = mUiDevice.findObject(new UiSelector().resourceId("com.zhejiangdaily:id/tv_password_login"));
        o_login.click();


        IUtil.wait(mUiDevice, app_name, 3);
    }

    @Test
    public void onClick() throws Exception {
        Context tContext = InstrumentationRegistry.getTargetContext();
        activityManager = (ActivityManager) tContext.getSystemService(Context.ACTIVITY_SERVICE);
        String oldName = IUtil.getCurrentActivityName(activityManager);

        UiObject o_phone = mUiDevice.findObject(new UiSelector().resourceId("com.zhejiangdaily:id/et_phone"));
        UiObject2 o_pwd = mUiDevice.findObject(By.res("com.zhejiangdaily:id/et_password"));
        UiObject o_login = mUiDevice.findObject(new UiSelector().resourceId("com.zhejiangdaily:id/tv_login"));
        o_phone.setText("13758284975");
        o_pwd.setText("123456");
        o_login.click();


        Thread.sleep(3000);
        String newName = IUtil.getCurrentActivityName(activityManager);

        log("oldName " + oldName);
        log("newName " + newName);
        assertNotEquals(oldName, newName);

    }


    @Test
    public void loginQQ() throws Exception {
        Context tContext = InstrumentationRegistry.getTargetContext();
        activityManager = (ActivityManager) tContext.getSystemService(Context.ACTIVITY_SERVICE);
        String oldName = IUtil.getCurrentActivityName(activityManager);

        UiObject o_qq = mUiDevice.findObject(new UiSelector().resourceId("com.zhejiangdaily:id/iv_qq"));
        o_qq.click();

        IUtil.wait(mUiDevice, app_name, 10);


        UiObject o_retry = mUiDevice.findObject(new UiSelector().text("重新拉取授权信息"));
        while (o_retry.exists()) {
            o_retry.click();
            IUtil.wait(mUiDevice, app_name, 10);
        }

        UiObject o_login = mUiDevice.findObject(new UiSelector().text("登录"));
        o_login.click();

        IUtil.wait(mUiDevice, app_name, 10);

        String newName = IUtil.getCurrentActivityName(activityManager);

        assertNotEquals(oldName, newName);
    }

    @Test
    public void loginSina() throws Exception {
        Context tContext = InstrumentationRegistry.getTargetContext();
        activityManager = (ActivityManager) tContext.getSystemService(Context.ACTIVITY_SERVICE);
        String oldName = IUtil.getCurrentActivityName(activityManager);

//        UiObject o_sina = mUiDevice.findObject(new UiSelector().resourceId("com.zhejiangdaily:id/iv_sina"));
//        o_sina.click();

        UiObject2 o_sina = mUiDevice.findObject(By.res("com.zhejiangdaily:id/iv_sina"));
        o_sina.click();

        IUtil.wait(mUiDevice, app_name, 10);
        String newName = IUtil.getCurrentActivityName(activityManager);

        assertNotEquals(oldName, newName);
    }

    public void loginWechat() throws Exception {

    }
}
